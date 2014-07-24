package kz.zvezdochet.core.ui.view;

import java.util.List;

import kz.zvezdochet.core.bean.Model;
import kz.zvezdochet.core.ui.comparator.TableSortListenerFactory;
import kz.zvezdochet.core.ui.listener.IElementListListener;
import kz.zvezdochet.core.ui.listener.ISelectElementListener;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.TableColumn;

/**
 * Прототип табличного представления моделей
 * @see View Прототип представления
 * @author Nataly Didenko
 */
public abstract class ModelListView extends ListView {
	protected String extPointId = "";
	
	/**
	 * Композит фильтра
	 */
	public Group grFilter;
	protected ViewerFilter viewerFilter;
	
	/**
	 * Массив расширителей точки расширения списка
	 */
	protected IConfigurationElement[] extensions;
	
	protected ISelectElementListener selectListener;

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
	 * Поиск в реестре приложения расширений списка
	 * @return массив расширений
	 */
	public IConfigurationElement[] getExtensions() {
		if (extensions == null)
			extensions = Platform.getExtensionRegistry()
				.getExtensionPoint(extPointId).getConfigurationElements();
		return extensions;
	}
	
	/**
	 * Добавление елемента в таблицу
	 * @param model новый елемент
	 */
	public void addModel(Object model) {
		@SuppressWarnings("unchecked")
		List<Model> modelist = (List<Model>)data;
		if (modelist.size() > 0) 
			if (modelist.contains(model)) {
				editModel(model);
				return;
			}
		modelist.add(0, (Model)model);
		data = modelist;
		tableViewer.add(model);
		tableViewer.setSelection(new StructuredSelection(model));
	}
	
	/**
	 * Удаление елемента из таблицы
	 * @param model удаляемый елемент
	 */
	public void deleteModel(Object model) {
		@SuppressWarnings("unchecked")
		List<Model> modelist = (List<Model>)data;
		modelist.remove(model);
		data = modelist;
		setData(modelist);
	}

	/**
	 * Редактирование елемента таблицы
	 * @param element редактируемый елемент
	 */
	public void editModel(Object element) {
		if (element != null)
			tableViewer.refresh(element);
	}
	
	/**
	 * Делаем фильтр невидимым
	 */
	public void hideFilter() {}
	
	/**
	 * Делаем фильтр видимым
	 */
	public void showFilter() {}

	public ISelectElementListener getSelectListener() {
		return selectListener;
	}

	public void setSelectListener(ISelectElementListener selectListener) {
		this.selectListener = selectListener;
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
	 * Обработчик событий добавления/удаления объектов
	 * @see IElementListListener
	 */
	protected IElementListListener listListener;
	
	/**
	 * Инициализация обработчика событий списка
	 */
	public void setListListener(IElementListListener listener) {
		this.listListener = listener;
	}
	
	public Object addModel() {
		return null;
	}

	@Override
	protected IBaseLabelProvider getLabelProvider() {
		return new ModelLabelProvider();
	}

	/**
	 * Обработчик отображения модели в таблице
	 */
	protected class ModelLabelProvider extends LabelProvider
									implements ITableLabelProvider {
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}
		public String getColumnText(Object element, int columnIndex) {
			return (element == null) ? "" : element.toString(); //$NON-NLS-1$
		}
	}	
}
