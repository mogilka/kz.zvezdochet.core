package kz.zvezdochet.core.ui.listener;

/**
 * Интерфейс обратной связи точки расширения списка с расширителем
 * @author Nataly Didenko
 */
public interface IEditorElementListener {
	/**
	 * Метод, вызываемый расширителем после сохранения объекта в редакторе
	 * @param element объект
	 */
	public void onSave(Object element);
	/**
	 * Метод, вызываемый расширителем после отмены сохранения объекта
	 * @param element объект
	 */
	public void onCancelSave(Object element);
}
