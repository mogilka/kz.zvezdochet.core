package kz.zvezdochet.core.ui.listener;

import kz.zvezdochet.core.bean.Model;

/**
 * Интерфейс обратной связи действия сохранения с представлением
 * @author Nataly Didenko
 */
public interface ISaveListener {
	/**
	 * Обрабатывает модель после сохранения
	 * @param model модель
	 */
	public void onSave(Model model);
	/**
	 * Обрабатывает модель после отмены сохранения
	 * @param model модель
	 */
	public void onCancel(Model model);
}
