package kz.zvezdochet.core.ui.extension;

import kz.zvezdochet.core.ui.view.View;

import org.eclipse.swt.widgets.Composite;

/**
 * Прототип интерфейса расширений
 * @author Nataly Didenko
 *
 */
public interface IExtension {
	/**
	 * Инициализация композита расширения
	 * @param parent контейнер виджетов
	 * @return композит расширения
	 */
	public View initComposite(Composite parent);
	/**
	 * Проверка соответствия расширяемого объекта загружаемому расширению
	 * @param object атрибут расширяемого объекта
	 */
	public boolean canHandle(Object object);

}
