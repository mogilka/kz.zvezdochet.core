package kz.zvezdochet.core.ui.view;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;

import kz.zvezdochet.core.service.DataAccessException;

/**
 * Прототип композита, встраиваемого в редактор модели
 * @author Natalie Didenko
 */
public abstract class ModelComposite extends ModelView {
	protected Group group;
	
	/**
	 * Слушатель расширения
	 */
	protected Listener listener;
	
	/**
	 * Реакция на изменение состояния элементов управления
	 * после операции сохранения
	 */
//	public void notifySave() {
//		setChanged(false);
//	}
//	
//	@Override
//	public void notifyChange() {
//		setChanged(true);
//	}

	/**
	 * Инициализация слушателя расширения
	 */
	public void setListener(Listener listener) {
		this.listener = listener;
	}
	
	/**
	 * Метод, возвращающий слушателя расширения
	 */
	public Listener getListener() {
		return listener;
	}
	
//	@Override
//	public void setChanged(boolean changed) {
//		super.setChanged(changed);
//		if (listener != null)
//			listener.handleEvent(null);
//	}

	@Override
	public View create(Composite composite) {
		GridDataFactory.fillDefaults().grab(true, false).applyTo(group);
		GridLayoutFactory.swtDefaults().numColumns(1).applyTo(group);
		return null;
	}

	/**
	 * Обновление визуального представления композита
	 */
	public void refresh() {
		syncView();
	}

	/**
	 * Удаление композита
	 */
	public void dispose() {
		if (group != null)
			group.dispose();
	}

	public Group getGroup() {
		return group;
	}

	/**
	 * Сохранение элемента композита
	 * @param extended объект, расширяемый элементом композита
	 */
	public void saveModel(Object extended) throws DataAccessException {}
}
