package kz.zvezdochet.core.handler;

import kz.zvezdochet.core.bean.Model;
import kz.zvezdochet.core.service.IBaseService;
import kz.zvezdochet.core.ui.listener.IEditorElementListener;
import kz.zvezdochet.core.ui.util.DialogUtil;
import kz.zvezdochet.core.ui.view.ModelView;
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
		Model element = null;
		try {
			if (!((ModelView)view).check(0)) return;
			element = ((ModelView)view).getModel(Handler.MODE_SAVE);
			element = saveElement(element);
			((ModelView)view).setModel(element, false);
			updateStatus(Messages.getString("ApplyElementAction.ElementSaved"), false); //$NON-NLS-1$
			IEditorElementListener listener = ((ModelView)view).getListener();
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
	public Model saveElement(Model element) throws Exception {
		IBaseService service = null;
		try {
			service = element.getService();
			element = service.save(element);
		} catch (Exception e) {
			updateStatus(Messages.getString("ApplyElementAction.ErrorSavingElement"), true); //$NON-NLS-1$
			DialogUtil.alertError(getActionErrorMessage());
			e.printStackTrace();
		}
		return element;
	}
}
