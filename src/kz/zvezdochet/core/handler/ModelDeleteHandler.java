package kz.zvezdochet.core.handler;

import javax.inject.Inject;

import kz.zvezdochet.core.bean.Model;
import kz.zvezdochet.core.service.IModelService;
import kz.zvezdochet.core.ui.util.DialogUtil;
import kz.zvezdochet.core.ui.view.ModelListView;

import org.eclipse.e4.core.contexts.Active;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

/**
 * Обработчик удаления модели из списка
 * @author Nataly Didenko
 *
 */
public class ModelDeleteHandler extends Handler {
	@Inject
	protected EPartService partService;

	@Execute
	public void execute(@Active MPart activePart) {
		ModelListView listpart = (ModelListView)activePart.getObject();
		Model model = (Model)listpart.getModel();
		if (model != null) {
			if (DialogUtil.alertConfirm("Удалить объект?")) {
				try {
					IModelService service = model.getService();
					service.delete(model.getId());
					listpart.onDelete(model);
				} catch (Exception e) {
					updateStatus("Объект не удалён", true);
					DialogUtil.alertError("error");
					e.printStackTrace();
				}
			}			
		}
	}

	@CanExecute
	public boolean canExecute() {
		return true;
	}
}
