package kz.zvezdochet.core.ui.extension;

import java.util.List;

/**
 * Интерфейс расширяемого представления,
 * в которое внедряются дополнительные композиты
 * @author nataly
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
	public List<IExtension> getExtensionProviders();
}
