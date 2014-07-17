package kz.zvezdochet.core.ui.view;

import java.util.List;

import kz.zvezdochet.core.bean.Base;
import kz.zvezdochet.core.ui.comparator.TableSortListenerFactory;
import kz.zvezdochet.core.ui.listener.IElementListListener;
import kz.zvezdochet.core.ui.listener.ISelectElementListener;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.PlatformUI;

/**
 * Прототип списочного представления данных
 * @see View Прототип представления
 * 
 * @author Nataly Didenko
 */
public abstract class ElementListView extends View {
	protected String extPointId = "";
	
	/**
	 * Визуальный контейнер
	 */
	public Composite container;
	
	/**
	 * Таблица елементов
	 */
	protected Table table;
	protected TableViewer tableViewer;
	
	/**
	 * Композит фильтра
	 */
	public Group filterGroup;
	protected ViewerFilter filter;
	
	/**
	 * Массив расширителей точки расширения списка
	 */
	protected IConfigurationElement[] extensions;
	
	protected ISelectElementListener selectListener;

	protected List<Base> elementList;

	@Override
	public void create(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		container.setLayout(new FormLayout());
//		this.parent = parent;
		addColumns();
		addFilter();

		tableViewer.setContentProvider(new ArrayContentProvider());		
		tableViewer.setLabelProvider(getLabelProvider());
		
//		if (tableViewer != null) {
//			stateChangedListener = new StateChangedListener();
//			tableViewer.addSelectionChangedListener(stateChangedListener);
//			tableViewer.addDoubleClickListener(stateChangedListener);
//		}
		
		initTable();
	}
	
	/**
	 * Метод, возвращающий выделенный элемент таблицы
	 * @return Object выделенный элемент таблицы
	 */
	public Object getElement() {
		IStructuredSelection selected = 
			(IStructuredSelection)tableViewer.getSelection();
		if (selected.isEmpty())
			return null;
		return selected.getFirstElement();
	}
	
	/**
	 * Создание столбцов таблицы
	 */
	protected void addColumns() {}

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
	
	public void refreshData() {
		initTable();
	}
	
	/**
	 * Стандартный класс отображения содержимого таблицы
	 */
	private class ElementListLabelProvider extends LabelProvider
									implements ITableLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}
		public String getColumnText(Object element, int columnIndex) {
			return (element == null) ? "" : element.toString(); //$NON-NLS-1$
		}
	}	
	
	/**
	 * Метод, возвращающий стандартный провайдер отображения содержимого таблицы
	 * @return ElementListLabelProvider
	 */
	protected IBaseLabelProvider getLabelProvider() {
		return new ElementListLabelProvider();
	}
	
	/**
	 * Инициализация таблицы значениями из БД
	 */
	protected void initTable() {
		try {
			showBusy(true);
			tableViewer.setInput(elementList);		
		} finally {
			showBusy(false);
		}
	}

	/**
	 * Проверка модифицируемости списка элементов
	 */
	public boolean isEditable() {
		if (null == getElement())
			return false;
		else
			return tableViewer != null;
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
	 * @param element новый елемент
	 */
	public void addElement(Object element) {
		if (elementList.size() > 0) 
			if (elementList.contains(element)) {
				editElement(element);
				return;
			}
		elementList.add(0, (Base)element);
		tableViewer.add(element);
		tableViewer.setSelection(new StructuredSelection(element));
	}
	
	/**
	 * Удаление елемента из таблицы
	 * @param element удаляемый елемент
	 */
	public void deleteElement(Object element) {
		elementList.remove(element);
		setElements(elementList);
	}

	/**
	 * Редактирование елемента таблицы
	 * @param element редактируемый елемент
	 */
	public void editElement(Object element) {
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
	 * Инициализация списка элементов
	 * @param elementsList список элементов
	 */
	public void setElements(List<Base> elementsList) {
		try {
			showBusy(true);
			elementList = elementsList;
			if (elementList != null) 
				tableViewer.setInput(elementList);	
		} finally {
			showBusy(false);
		}
	}

	@Override
	protected void init(Composite parent) {
		GridLayoutFactory.swtDefaults().applyTo(parent);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(container);
		GridLayoutFactory.swtDefaults().applyTo(container);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(table);
	}

	/**
	 * Обновление списка 
	 */
	public void refresh() {
		tableViewer.refresh();
	}
	
	/**
	 * Метод, обновляющий контейнер и
	 * заново задающий расположение элементов управления
	 */
	public void refreshView() {
		container.layout(true, true);
	}

	/**
	 * Очистка таблицы
	 */
	public void clear() {
		table.removeAll();
	}

	public ColumnViewer getColumnViewer() {
		return tableViewer;
	}

	public void addFilter() {
//		createTableFilter();
	}

	/**
	 * Изменяет значение заданного элемента в списке
	 * @param element
	 */
	public void updateElement(Base element) {
		if (elementList != null) {
			int index = 0;
			for (Base entity : elementList) {
				if (entity.equals(element)) {
					if (entity != element) {
						updateElement(entity, element);
					}
					return;
				}
				index++;
			}
		}
	}

	/**
	 * Применяет изменения для элемента
	 * @param entity элемент списка
	 * @param element элемент с измененными данными
	 */
	public void updateElement(Base entity, Base element) {
		tableViewer.editElement(entity, 0);
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
	
	public void showBusy(final boolean busy) {
		new Thread() {
			public void run() {
				PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
					public void run() {
//						if (busy)
//							updateStatus(Messages.getString("ElementListView.ListInitializing"), false); //$NON-NLS-1$
//						else
//							updateStatus(Messages.getString("ElementListView.DataLoaded"), false); //$NON-NLS-1$
					}
				});
			}
		}.start();				
    }

	public Object addElement() {
		return null;
	}
}
