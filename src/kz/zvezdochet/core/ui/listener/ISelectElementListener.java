package kz.zvezdochet.core.ui.listener;

/**
 * Интерфейс выбора элемента из списка  
 * @author Nataly Didenko
 */
public interface ISelectElementListener {
	/**
	 * Метод, вызываемый после выбора объекта 
	 * @param element объект
	 */
	public void onSelect(Object element);
}
