package kz.zvezdochet.core.ui.view;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import kz.zvezdochet.core.service.DataAccessException;
import kz.zvezdochet.core.ui.ArrayLabelProvider;
import kz.zvezdochet.core.ui.comparator.TableSortListenerFactory;
import kz.zvezdochet.core.ui.listener.ListSelectionListener;

/**
 * Прототип табличного представления данных
 * @author Natalie Didenko
 */
public abstract class ListView extends View implements IFilterable {
	
	/**
	 * Массив данных таблицы
	 */
	protected Object data;

	/**
	 * Таблица елементов
	 */
	protected TableViewer tableViewer;
	protected Group group;
	
	@Override
	protected void init(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		container.setLayout(new FillLayout());
		initFilter(parent);

		sashForm = new SashForm(container, SWT.HORIZONTAL);
		tableViewer = new TableViewer(sashForm, SWT.BORDER | SWT.FULL_SELECTION);
		group = new Group(sashForm, SWT.NONE); 
		sashForm.setWeights(new int[] { 5, 1 });

		Table table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		addColumns();
	}

	/**
	 * Создание столбцов таблицы
	 */
	protected void addColumns() {
		removeColumns();
		String[] columns = initTableColumns();
		if (columns != null)
			for (String column : columns) {
				TableColumn tableColumn = new TableColumn(tableViewer.getTable(), SWT.NONE);
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
			Table table = tableViewer.getTable();
			if (null == data)
				table.removeAll();
			else {
				tableViewer.setInput(data);
				for (int i = 0; i < table.getColumnCount(); i++)
					table.getColumn(i).pack();
			}
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

	@Override
	public void initFilter(Composite parent) {}

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
	protected void arrange(Composite parent) {
		GridLayoutFactory.swtDefaults().applyTo(parent);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(container);
		GridLayoutFactory.swtDefaults().applyTo(container);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(sashForm);
		GridLayoutFactory.swtDefaults().applyTo(sashForm);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(tableViewer.getTable());
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
		tableViewer.getTable().removeAll();
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

	/**
	 * Удаление столбцов таблицы
	 */
	protected void removeColumns() {
		for (TableColumn column : tableViewer.getTable().getColumns())
			column.dispose();
	}

	/**
	 * Поиск обработчика выделения элементов списка
	 * @return обработчик выделения элементов списка
	 */
	public ListSelectionListener getSelectionListener() {
		return new ListSelectionListener();
	}

	/**
	 * Возвращает содержимое представления
	 * @return табличное содержимое
	 */
	public Object getData() {
		return data;
	}

	/**
	 * Инициализация контейнера справа от таблицы
	 */
	protected void initGroup() {}

	@Override
	protected void initControls() throws DataAccessException {
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setLabelProvider(getLabelProvider());
		
		ListSelectionListener listener = getSelectionListener();
		tableViewer.addSelectionChangedListener(listener);
		tableViewer.addDoubleClickListener(listener);
		initTable();
		initGroup();
	}
}
