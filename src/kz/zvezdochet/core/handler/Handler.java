package kz.zvezdochet.core.handler;

import kz.zvezdochet.core.ui.view.View;

/**
 * Прототип визуального действия
 * @author Nataly Didenko
 */
public abstract class Handler {

	/**
	 * Ссылка на представление, где расположено действие
	 */
	protected View view;
	
	/**
	 * Параметризованный конструктор
	 * @param view представление 
	 */
	public Handler(View view) {
		if (view == null)
			throw new IllegalArgumentException("Параметр не должен быть пустым"); //TODO extract to const
		this.view = view;
	}
	
	/**
	 * Параметризованный конструктор
	 * @param view представление 
	 * @param text надпись действия
	 * @param style стиль действия
	 * */
	public Handler(View view, String text, int style) {
		if (view == null)
			throw new IllegalArgumentException("Параметр не должен быть пустым");
		this.view = view;
	}

	/**
	 * Вывод сообщения в строке состояния
	 * @param msg сообщение
	 * @param isError <true> - выводить красным цветом
	 */
	protected void updateStatus(String msg, boolean isError) {
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
