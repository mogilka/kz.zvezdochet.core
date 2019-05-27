package kz.zvezdochet.core.ui.extension;

import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.swt.widgets.Composite;

import kz.zvezdochet.core.bean.Model;
import kz.zvezdochet.core.service.IModelService;
import kz.zvezdochet.core.ui.view.ListView;
import kz.zvezdochet.core.ui.view.View;

/**
 * Прототип интерфейса расширений
 * @author Natalie Didenko
 *
 */
public interface IExtension {
	/**
	 * Инициализация композита расширения
	 * @param parent контейнер виджетов
	 * @return композит расширения
	 */
	public View initComposite(Composite parent);
	/**
	 * Проверка соответствия расширяемого объекта загружаемому расширению
	 * @param object атрибут расширяемого объекта
	 */
	public boolean canHandle(Object object);
	/**
	 * Инициализация фильтра расширения
	 * @param parent контейнер фильтра
	 */
	public void initFilter(ListView listView, Composite parent);
	/**
	 * Возвращает имя расширения
	 * @return наименование
	 */
	public String getName();
	/**
	 * Возвращает сервис расширения
	 * @return сервис расширения
	 */
	public abstract IModelService getService();
	/**
	 * Инициализация новой модели
	 * @return модель
	 */
	public abstract Model create();
	/**
	 * Поиск колонок таблицы расширения
	 * @return массив колонок таблицы
	 */
	public abstract String[] getTableColumns();
	/**
	 * Возвращает обработчик отображения данных таблицы
	 * @return обработчик отображения таблицы
	 */
	public abstract IBaseLabelProvider getLabelProvider();
	/**
	 * Возвращает путь к изображению расширения
	 * @return путь к файлу иконки расширения
	 */
	public abstract String getIconURI();
	/**
	 * Сохранение модели расширения
	 */
	public void save();
}
