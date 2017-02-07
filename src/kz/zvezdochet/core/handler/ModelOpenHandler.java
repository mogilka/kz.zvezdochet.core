package kz.zvezdochet.core.handler;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.contexts.Active;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;

import kz.zvezdochet.core.bean.Model;
import kz.zvezdochet.core.ui.util.DialogUtil;
import kz.zvezdochet.core.ui.view.ModelListView;
import kz.zvezdochet.core.ui.view.ModelView;

/**
 * Обработчик открытия модели из списка в редакторе
 * @author Nataly Didenko
 *
 */
public class ModelOpenHandler extends Handler {
	@Inject
	protected EPartService partService;
	protected String partid;
	protected ModelListView listpart;

	@Execute
	public void execute(@Active MPart activePart, @Named("kz.zvezdochet.core.commandparameter.openmodel") String partid) {
		this.partid = partid;
		listpart = (ModelListView)activePart.getObject();
		Model model = (Model)listpart.getModel();
		checkPart(model);
	}

	@CanExecute
	public boolean canExecute() {
		return true;
	}

	/**
	 * Проверка состояния представления
	 * @param model модель
	 */
	protected void checkPart(Model model) {
		MPart part = partService.findPart(partid);
	    if (part.isDirty()) {
			if (DialogUtil.alertConfirm(
				"Открытый ранее объект не сохранён\n"
					+ "и утратит внесённые изменения,\n"
					+ "если вы откроете новый. Продолжить?")) {
				openPart(part, model);
			}
	    } else
	    	openPart(part, model);
	}

	/**
	 * Отображение модели в представлении
	 * @param part представление
	 * @param model модель
	 * @return представление модели
	 */
	protected void openPart(MPart part, Model model) {
		if (model != null) {
			ModelView view = (ModelView)part.getObject();
			view.setListView(listpart);
			view.setModel(model, true);
//			view.setListener(listpart);
		}
	    part.setVisible(true);
	    try {
		    partService.showPart(part, PartState.VISIBLE);
		    afterOpenPart();
		} catch (IllegalStateException e) {
			//Application does not have an active window
			e.printStackTrace();
		}
	}

	/**
	 * Операции, выполняемые после открытия модели в редакторе
	 */
	protected void afterOpenPart() {};
}
