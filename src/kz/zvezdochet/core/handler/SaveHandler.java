package kz.zvezdochet.core.handler;

import kz.zvezdochet.core.bean.BaseEntity;
import kz.zvezdochet.core.service.IEntityService;
import kz.zvezdochet.core.ui.listener.IEditorElementListener;
import kz.zvezdochet.core.ui.util.DialogUtil;
import kz.zvezdochet.core.ui.view.ElementView;
import kz.zvezdochet.core.ui.view.View;

import org.eclipse.e4.core.di.annotations.Execute;

/**
 * Действие для сохранения объекта в БД
 * @author Nataly Didenko
 */
public class SaveHandler extends Handler {

	public SaveHandler(View view) {
		super(view);
//		setText(Messages.getString("ApplyElementAction.Save")); //$NON-NLS-1$
//		setImageDescriptor(Activator.getImageDescriptor("icons/applyElement.gif")); //$NON-NLS-1$
	}

	@Execute
	public void execute() {
		updateStatus(Messages.getString("ApplyElementAction.SavingElement"), false); //$NON-NLS-1$
		BaseEntity element = null;
		try {
			if (!((ElementView)view).checkViewValues()) return;
			element = ((ElementView)view).getElement();
			element = saveElement(element);
			((ElementView)view).setElement(element, false);
			updateStatus(Messages.getString("ApplyElementAction.ElementSaved"), false); //$NON-NLS-1$
			IEditorElementListener listener = ((ElementView)view).getListener();
			if (listener != null)
				listener.onSave(element);
		} catch (Exception e) {
			DialogUtil.alertError(e.getMessage());
			updateStatus(Messages.getString("ApplyElementAction.ErrorSavingElement"), true); //$NON-NLS-1$
			e.printStackTrace();
		}
	}

	/**
	 * Сохранение объекта в БД
	 * @param element объект, подлежащий сохранению
	 * @return сохраненный объект 
	 */
	public BaseEntity saveElement(BaseEntity element) throws Exception {
		IEntityService service = null;
		try {
			service = element.getEntityService();
			element = service.saveEntity(element);
		} catch (Exception e) {
			updateStatus(Messages.getString("ApplyElementAction.ErrorSavingElement"), true); //$NON-NLS-1$
			DialogUtil.alertError(getActionErrorMessage());
			e.printStackTrace();
		}
		return element;
	}
}
