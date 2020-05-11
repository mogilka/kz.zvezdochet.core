package kz.zvezdochet.core.ui.listener;

import kz.zvezdochet.core.bean.Model;

/**
 * Интерфейс обратной связи действия сохранения с представлением
 * @author Natalie Didenko
 */
public interface ISaveListener {
	/**
	 * Обрабатывает модель после сохранения
	 * @param model модель
	 * @param update true|false модифицированная|новая модель
	 */
	public void onSave(Model model, boolean update);
	/**
	 * Обрабатывает модель после отмены сохранения
	 * @param model модель
	 */
	public void onCancel(Model model);
	/**
	 * Проверка введённых значений
	 * @param mode режим проверки содержимого
	 * @return true - поля заполнены корректно
	 */
	public boolean check(int mode) throws Exception;
	/**
	 * Возвращает модель представления
	 * @param mode режим запроса модели
	 * @param sync признак, требуется ли предварительная синхронизация модели с представлением
	 */
	public Model getModel(int mode, boolean sync) throws Exception;
}
