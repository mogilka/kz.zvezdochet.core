package kz.zvezdochet.core.ui.view;

import kz.zvezdochet.core.ui.listener.StateChangedListener;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

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
	public Composite create(Composite parent) {
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
		return null;
	}

	/**
	 * Создание столбцов таблицы
	 */
	protected void addColumns() {}

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
	 * Обработчик отображения содержимого таблицы
	 */
	protected class ArrayLabelProvider extends LabelProvider
									implements ITableLabelProvider, ITableColorProvider {
		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}
		@Override
		public String getColumnText(Object element, int columnIndex) {
			Object[] array = (Object[])element;
			Object val = array[columnIndex];
			return (null == val) ? "" : val.toString();
		}
		@Override
		public Color getForeground(Object element, int columnIndex) {
			return null;
		}
		@Override
		public Color getBackground(Object element, int columnIndex) {
			return null;
		}
	}	

	/**
	 * Инициализация таблицы значениями из БД
	 */
	protected void initTable() {
		try {
			showBusy(true);
			tableViewer.setInput(data);
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
