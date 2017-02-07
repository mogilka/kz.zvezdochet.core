package kz.zvezdochet.core.handler;

import javax.inject.Named;

import org.eclipse.e4.core.contexts.Active;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;

import kz.zvezdochet.core.bean.Model;
import kz.zvezdochet.core.ui.view.ModelListView;

/**
 * Обработчик добавления новой модели в список и открытия её в редакторе
 * @author Nataly Didenko
 *
 */
public class ModelAddHandler extends ModelOpenHandler {

	@Execute
	public void execute(@Active MPart activePart, @Named("kz.zvezdochet.core.commandparameter.addmodel") String partid) {
		this.partid = partid;
		listpart = (ModelListView)activePart.getObject();
		Model model = listpart.createModel();
		checkPart(model);
	}
}
