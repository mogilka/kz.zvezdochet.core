package kz.zvezdochet.core.ui.extension;


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
	 * Возвращает расширения представления
	 * @return массив расширений представления
	 */
//	public List<ModelExtension> getExtensions();
}
