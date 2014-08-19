package kz.zvezdochet.core.handler;

import javax.inject.Inject;

import kz.zvezdochet.core.bean.Model;
import kz.zvezdochet.core.ui.util.DialogUtil;
import kz.zvezdochet.core.ui.view.ModelView;

import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;

/**
 * Обработчик открытия представления модели
 * @author Nataly Didenko
 *
 */
public class ModelOpenHandler extends Handler {
	@Inject
	protected EPartService partService;

	/**
	 * Проверка состояния представления
	 * @param model модель
	 */
	protected void checkPart(String viewid, Model model) {
		MPart part = partService.findPart(viewid);
	    if (part.isDirty()) {
			if (DialogUtil.alertConfirm(
					"Открытый ранее объект не сохранён\n"
					+ "и утратит все внесённые изменения,\n"
					+ "если Вы откроете новый. Продолжить?")) {
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
	protected ModelView openPart(MPart part, Model model) {
	    part.setVisible(true);
	    try {
		    partService.showPart(part, PartState.VISIBLE);
		} catch (IllegalStateException e) {
			//Application does not have an active window
		}
		ModelView modelView = null;
		if (model != null)
			modelView = (ModelView)part.getObject();
	    return modelView;
	}
}
