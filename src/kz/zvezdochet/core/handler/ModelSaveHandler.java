package kz.zvezdochet.core.handler;

import org.eclipse.e4.core.contexts.Active;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;

import kz.zvezdochet.core.bean.Model;
import kz.zvezdochet.core.service.IModelService;
import kz.zvezdochet.core.ui.util.DialogUtil;
import kz.zvezdochet.core.ui.view.ModelView;

/**
 * Обработчик сохранения модели
 * @author Nataly Didenko
 *
 */
public class ModelSaveHandler extends Handler {
	
	@Execute
	public void execute(@Active MPart activePart) {
		updateStatus(Messages.getString("ApplyElementAction.SavingElement"), false); //$NON-NLS-1$
		try {
			int mode = Handler.MODE_SAVE;
			ModelView part = (ModelView)activePart.getObject();
			if (!part.check(mode))
				return;
			Model model = part.getModel(mode, true);
			boolean existing = model.getId() != null;
			model = saveModel(model);
			updateStatus(Messages.getString("ApplyElementAction.ElementSaved"), false); //$NON-NLS-1$
			//TODO после сохранения делать недоступным сохранение пока данные снова не изменятся
			part.onSave(model, existing);
		} catch (Exception e) {
			DialogUtil.alertError(e.getMessage());
			updateStatus(Messages.getString("ApplyElementAction.ErrorSavingElement"), true); //$NON-NLS-1$
			e.printStackTrace();
		}
	}
		
	/**
	 * Сохранение объекта в БД
	 * @param model объект, подлежащий сохранению
	 * @return сохраненный объект 
	 */
	private Model saveModel(Model model) throws Exception {
		try {
			IModelService service = model.getService();
			model = service.save(model);
		} catch (Exception e) {
			updateStatus(Messages.getString("ApplyElementAction.ErrorSavingElement"), true); //$NON-NLS-1$
			DialogUtil.alertError("error");
			e.printStackTrace();
		}
		return model;
	}
}
