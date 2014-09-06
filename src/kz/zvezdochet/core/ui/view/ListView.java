package kz.zvezdochet.core.ui.view;

import kz.zvezdochet.core.service.DataAccessException;
import kz.zvezdochet.core.ui.ArrayLabelProvider;
import kz.zvezdochet.core.ui.comparator.TableSortListenerFactory;
import kz.zvezdochet.core.ui.listener.StateChangedListener;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

/**
 * Прототип табличного представления данных
 * @author Nataly Didenko
 */
public abstract class ListView extends View {
	
	/**
	 * Визуальный контейнер
	 */
	public Composite container;
	/**
	 * Массив данных таблицы
	 */
	protected Object data;

	/**
	 * Таблица елементов
	 */
	protected Table table;
	protected TableViewer tableViewer;
	
	@Override
	public View create(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		container.setLayout(new FormLayout());
		initFilter();

		tableViewer = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		addColumns();
		init(parent);
		try {
			initControls();
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setLabelProvider(getLabelProvider());
		
		if (tableViewer != null) {
			StateChangedListener listener = new StateChangedListener();
			tableViewer.addSelectionChangedListener(listener);
			tableViewer.addDoubleClickListener(listener);
		}
		initTable();
		return null;
	}

	/**
	 * Создание столбцов таблицы
	 */
	protected void addColumns() {
		for (TableColumn column : table.getColumns())
			column.dispose();
		String[] columns = initTableColumns();
		if (columns != null)
			for (String column : columns) {
				TableColumn tableColumn = new TableColumn(table, SWT.NONE);
				tableColumn.setText(column);		
				tableColumn.addListener(SWT.Selection, TableSortListenerFactory.getListener(
					TableSortListenerFactory.STRING_COMPARATOR));
			}
	}

	/**
	 * Инициализация массива столбцов таблицы
	 * @return массив столбцов таблицы
	 */
	protected abstract String[] initTableColumns();

	/**
	 * Обновление содержимого таблицы
	 */
	public void refreshData() {
		initTable();
	}

	/**
	 * Поиск обработчика отображения содержимого таблицы
	 * @return обработчик отображения элемента таблицы
	 */
	protected IBaseLabelProvider getLabelProvider() {
		return new ArrayLabelProvider();
	}

	/**
	 * Инициализация таблицы значениями из БД
	 */
	protected void initTable() {
		try {
			showBusy(true);
			if (data != null)
				tableViewer.setInput(data);
			for (int i = 0; i < table.getColumnCount(); i++)
				table.getColumn(i).pack();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			showBusy(false);
		}
	}

	/**
	 * Проверка модифицируемости списка элементов
	 */
	public boolean isEditable() {
		return tableViewer != null;
	}

	public void initFilter() {}

	/**
	 * Инициализация содержимого таблицы
	 * @param data массив данных
	 */
	public void setData(Object data) {
		try {
			showBusy(true);
			this.data = data;
			initTable();	
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
	 * Обновление таблицы 
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
	public void reset() {
		table.removeAll();
	}

	public ColumnViewer getColumnViewer() {
		return tableViewer;
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

	/**
	 * Делаем фильтр невидимым
	 */
	public void hideFilter() {}
	
	/**
	 * Делаем фильтр видимым
	 */
	public void showFilter() {}
}
