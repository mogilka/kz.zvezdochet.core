package kz.zvezdochet.core.ui.listener;

/**
 * Интерфейс отслеживания добавления, удаления и
 * изменения элементов списка  
 * @author Nataly Didenko
 */
public interface IModelListListener {
	/**
	 * Обработчик добавления элемента 
	 * @param model новый элемент
	 */
	public void onCreate(Object model);

	/**
	 * Обработчик удаления элемента 
	 * @param model удаляемый элемент
	 */
	public void onDelete(Object model);

	/**
	 * Обработчик модификации элемента 
	 * @param model изменяемый элемент
	 */
	public void onChange(Object model);
}
