package kz.zvezdochet.core.ui.listener;

/**
 * Интерфейс отслеживания добавления, удаления и
 * изменения элементов списка  
 * @author Nataly
 */
public interface IElementListListener {
	/**
	 * Обработчик добавления элемента 
	 * @param element новый элемент
	 */
	public void onCreate(Object element);

	/**
	 * Обработчик удаления элемента 
	 * @param element удаляемый элемент
	 */
	public void onDelete(Object element);

	/**
	 * Обработчик модификации элемента 
	 * @param element изменяемый элемент
	 */
	public void onChange(Object element);
}
