package kz.zvezdochet.core.ui.view;

import org.eclipse.swt.widgets.Composite;

/**
 * Прототип представления
 * @author Nataly Didenko
 */
public abstract class View {
	
	public void setTitle(String title) {
//		setPartName(title);
	}
	
	/**
	 * Создание элементов управления
	 * @param parent композит-родитель
	 **/
	public void create(Composite parent) {}
	
	/**
	 * Закрытие представления
	 */
	public void close () {
//		if (canClose())
//			this.getSite().getPage().hideView(this);
	}
	
	/**
	 * Очистка значений элементов управления
	 */
	public void clear() {}

	/**
	 * Выравнивание визуальных компонентов
	 * @param composite контейнер виджетов
	 */
	protected void init(Composite composite) {}
}
