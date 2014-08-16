package kz.zvezdochet.core.ui.extension;

import java.util.List;

/**
 * Интерфейс расширяемого представления,
 * в которое встраиваются дополнительные композиты
 * @author Nataly Didenko
 *
 */
public interface IExtendableView {
	/**
	 * Инициализация расширений объекта
	 */
	public void initExtensions();
	/**
	 * Метод, возвращающий список расширителей представления
	 */
	public List<ModelExtensionProvider> getExtensionProviders();
}
