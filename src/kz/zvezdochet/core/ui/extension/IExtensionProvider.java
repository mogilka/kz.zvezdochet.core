package kz.zvezdochet.core.ui.extension;

/**
 * Прототип интерфейса расширителя точки расширения
 * @author Nataly Didenko
 */
public abstract interface IExtensionProvider extends IExtension {
	/**
	 * Инициализация расширяемого объекта
	 * @param extended расширяемый объект
	 */
	public void initExtendedObject(Object extended);
	/**
	 * Инициализация элемента расширения
	 */
	public void initExtension();
	/**
	 * Проверка соответствия объекта загружаемому расширению
	 */
	public boolean canWorkWithObject();
	/**
	 * Проверка соответствия параметров объекта 
	 * для работы с конкретным расширением
	 */
	public boolean isAvailableToObject();
	/**
	 * Сохранение данных расширения.
	 * Данный метод должен быть обязательно переопределен в наследниках
	 * @param object расширяемый объект
	 */
	public void save(Object extended);
	/**
	 * Метод, возвращающий расширяемый объект 
	 * @return расширяемый объект
	 */
	public Object getExtendedObject();
	/**
	 * Метод, возвращающий элемент расширения 
	 * @return расширяющий объект
	 */
	public Object getExtensionElement();
	/**
	 * Удаление элемента расширения.
	 * Данный метод должен быть обязательно переопределен в наследниках
	 */
	public void deleteExtension();
	/**
	 * Метод, возвращающий наименование класса расширения.
	 * Данный метод должен быть обязательно переопределен в наследниках
	 * @return имя расширения
	 */
	public String getExtensionName();
	/**
	 * Поиск расширения объекта
	 * @param extendedId идентификатор расширяемого объекта
	 * @return элемент расширения
	 */
	public Object getExtension(Long extendedId);
	/**
	 * Метод, возвращающий интерфейс, управляющий расширением на уровне БД.
	 * Данный метод должен быть обязательно переопределен в наследниках
	 * @return сервис расширения
	 */
	public Object getExtensionService();
}
