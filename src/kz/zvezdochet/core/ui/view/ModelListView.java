package kz.zvezdochet.core.ui.view;

import java.util.List;

import kz.zvezdochet.core.bean.Model;
import kz.zvezdochet.core.ui.comparator.TableSortListenerFactory;
import kz.zvezdochet.core.ui.listener.IElementListListener;
import kz.zvezdochet.core.ui.listener.ISelectElementListener;
import kz.zvezdochet.core.ui.listener.StateChangedListener;

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

/**
 * Прототип списочного представления данных
 * @see View Прототип представления
 * 
 * @author Nataly Didenko
 */
public abstract class ModelListView extends View {
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
	public Group grFilter;
	protected ViewerFilter viewerFilter;
	
	/**
	 * Массив расширителей точки расширения списка
	 */
	protected IConfigurationElement[] extensions;
	
	protected ISelectElementListener selectListener;

	protected List<Model> modelList;

	@Override
	public void create(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		container.setLayout(new FormLayout());
		initFilter();

		tableViewer = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		addColumns();
		init(parent);

		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setLabelProvider(getLabelProvider());
		
		if (tableViewer != null) {
			StateChangedListener listener = new StateChangedListener();
			tableViewer.addSelectionChangedListener(listener);
			tableViewer.addDoubleClickListener(listener);
		}
		initTable();
	}
	
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
	protected class ModelLabelProvider extends LabelProvider
									implements ITableLabelProvider {
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}
		public String getColumnText(Object element, int columnIndex) {
			return (element == null) ? "" : element.toString(); //$NON-NLS-1$
		}
	}	
	
	/**
	 * Поиск обработчика отображения содержимого таблицы
	 * @return ElementListLabelProvider
	 */
	protected IBaseLabelProvider getLabelProvider() {
		return new ModelLabelProvider();
	}
	
	/**
	 * Инициализация таблицы значениями из БД
	 */
	protected void initTable() {
		try {
			showBusy(true);
			tableViewer.setInput(modelList);
			for (int i = 0; i < table.getColumnCount(); i++)
				table.getColumn(i).pack();
		} finally {
			showBusy(false);
		}
	}

	/**
	 * Проверка модифицируемости списка элементов
	 */
	public boolean isEditable() {
		if (null == getModel())
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
	 * @param model новый елемент
	 */
	public void addModel(Object model) {
		if (modelList.size() > 0) 
			if (modelList.contains(model)) {
				editModel(model);
				return;
			}
		modelList.add(0, (Model)model);
		tableViewer.add(model);
		tableViewer.setSelection(new StructuredSelection(model));
	}
	
	/**
	 * Удаление елемента из таблицы
	 * @param model удаляемый елемент
	 */
	public void deleteModel(Object model) {
		modelList.remove(model);
		setModelList(modelList);
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
	 * Инициализация списка элементов
	 * @param list список элементов
	 */
	public void setModelList(List<Model> list) {
		try {
			showBusy(true);
			modelList = list;
			if (modelList != null) 
				tableViewer.setInput(modelList);	
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

	public void initFilter() {
//		createTableFilter();
	}

	/**
	 * Изменяет значение заданного элемента в списке
	 * @param model объект-модель
	 */
	public void updateModel(Model model) {
		if (modelList != null) {
			for (Model entity : modelList) {
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
	
	public void showBusy(final boolean busy) {
//		new Thread() {
//			public void run() {
//				PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
//					public void run() {
////						if (busy)
////							updateStatus(Messages.getString("ElementListView.ListInitializing"), false); //$NON-NLS-1$
////						else
////							updateStatus(Messages.getString("ElementListView.DataLoaded"), false); //$NON-NLS-1$
//					}
//				});
//			}
//		}.start();				
    }

	public Object addModel() {
		return null;
	}
}
