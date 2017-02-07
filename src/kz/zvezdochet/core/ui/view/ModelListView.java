package kz.zvezdochet.core.ui.view;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.TableColumn;

import kz.zvezdochet.core.bean.Model;
import kz.zvezdochet.core.ui.comparator.TableSortListenerFactory;
import kz.zvezdochet.core.ui.listener.IModelListListener;

/**
 * Прототип табличного представления моделей
 * @author Nataly Didenko
 */
public abstract class ModelListView extends ListView {
	
	/**
	 * Композит фильтра
	 */
	public Group grFilter;
	protected ViewerFilter viewerFilter;
	
	/**
	 * Метод, возвращающий выделенный элемент таблицы
	 * @return Object выделенный элемент таблицы
	 */
	public Object getModel() {
		IStructuredSelection selected = 
			(IStructuredSelection)tableViewer.getSelection();
		if (selected.isEmpty())
			return null;
		return selected.getFirstElement();
	}
	
	/**
	 * Создание строкового столбца таблицы
	 * @param width ширина столбца
	 * @param title заголовок столбца
	 */
	protected void addColumn(int width, String title) {
		addColumn(width, title, TableSortListenerFactory.STRING_COMPARATOR);	
	}
	
	/**
	 * Создание столбца таблицы
	 * @param width ширина столбца
	 * @param title заголовок столбца
	 * @param comparatorType вид сортировки столбца, определяемый содержащимися в нем данными
	 */
	protected void addColumn(int width, String title, int comparatorType) {
		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(width);
		tableColumn.setText(title);		
		tableColumn.addListener(
			SWT.Selection, TableSortListenerFactory.getListener(comparatorType));	
	}
	
	@Override
	public boolean isEditable() {
		if (null == getModel())
			return false;
		else
			return super.isEditable();
	}

	/**
	 * Добавление модели в таблицу
	 * @param model модель
	 */
	public void addModel(Model model) {
		@SuppressWarnings("unchecked")
		List<Model> modelist = (List<Model>)data;
		if (modelist.size() > 0) 
			if (modelist.contains(model))
				return;
		modelist.add((Model)model);
		data = modelist;
		tableViewer.add(model);
		tableViewer.setSelection(new StructuredSelection(model));
	}
	
	/**
	 * Удаление модели из таблицы
	 * @param model удаляемая модель
	 */
	public void onDelete(Model model) {
		tableViewer.refresh();
		@SuppressWarnings("unchecked")
		List<Model> modelist = (List<Model>)data;
		modelist.remove(model);
		data = modelist;
		setData(modelist);
	}

	/**
	 * Редактирование елемента таблицы
	 * @param element редактируемый елемент
	 * @param update true|false модифицированная|новая модель
	 */
	public void onUpdate(Object element, boolean update) {
		if (null == element)
			return;
		if (update)
			tableViewer.refresh(element);
		else
			tableViewer.add(element);
	}
	
	/**
	 * Изменяет значение заданного элемента в списке
	 * @param model объект-модель
	 */
	public void updateModel(Model model) {
		@SuppressWarnings("unchecked")
		List<Model> modelist = (List<Model>)data;
		if (modelist != null) {
			for (Model entity : modelist) {
				if (entity.equals(model)) {
					if (entity != model) {
						updateModel(entity, model);
					}
					return;
				}
			}
		}
	}

	/**
	 * Применяет изменения для элемента
	 * @param model элемент списка
	 * @param modified элемент с измененными данными
	 */
	public void updateModel(Model model, Model modified) {
		tableViewer.editElement(model, 0);
	}

	/**
	 * Обработчик добавления/удаления объектов
	 */
	protected IModelListListener listListener;
	
	/**
	 * Инициализация обработчика событий списка
	 */
	public void setListListener(IModelListListener listener) {
		this.listListener = listener;
	}

	@Override
	protected IBaseLabelProvider getLabelProvider() {
		return new ModelLabelProvider();
	}

	@Inject
	protected MPart part;

	/**
	 * Создание экземпляра модели
	 * @return модель
	 */
	public abstract Model createModel();
}
