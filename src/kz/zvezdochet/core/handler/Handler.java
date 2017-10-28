package kz.zvezdochet.core.handler;

/**
 * Прототип визуального действия
 * @author Nataly Didenko
 */
public abstract class Handler {
	/**
	 * Режим обработки элемента
	 */
	public static int MODE_SAVE = 0;

	/**
	 * Вывод сообщения в строке состояния
	 * @param msg сообщение
	 * @param isError <true> - выводить красным цветом
	 */
	public static void updateStatus(String msg, boolean isError) {
//		StatusUtil.writeToStatusLine(msg, isError, view.getViewSite().getActionBars());
	}

	/**
	 * Метод, возвращающий сообщение об ошибке
	 * в контексте выполнения конкретного действия.
	 * Данный метод может быть определен в наследниках
	 * для отображения специфичного объяснения возникающих сбоев
	 * @return сообщение об ошибке
	 */
	public String getActionErrorMessage() {
		return "Ошибка обработки данных";
	}
}
