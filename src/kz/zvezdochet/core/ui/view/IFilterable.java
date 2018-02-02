package kz.zvezdochet.core.ui.view;

import org.eclipse.swt.widgets.Composite;

/**
 * Интерфейс представления, имеющего фильтр объектов
 * @author Nataly Didenko
 *
 */
public abstract interface IFilterable {
	/**
	 * Инициализация фильтра
	 * @param parent контейнер
	 */
	public void initFilter(Composite parent);
}
