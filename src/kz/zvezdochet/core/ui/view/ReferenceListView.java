package kz.zvezdochet.core.ui.view;

import java.util.List;

import kz.zvezdochet.core.bean.Reference;
import kz.zvezdochet.core.ui.comparator.TableSortListenerFactory;
import kz.zvezdochet.core.ui.listener.StateChangedListener;

import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * Прототип представления стандартного списочного справочника значений
 * @author Nataly
 * 
 * @see ModelListView Прототип списочных представлений
 */
public class ReferenceListView extends ModelListView {
	private Text txtName;
	
	@Override
	public void create(Composite parent) {
		grFilter = new Group(container, SWT.NONE);
		final FormData fd = new FormData();
		fd.bottom = new FormAttachment(0, 55);
		fd.right = new FormAttachment(100, -5);
		fd.top = new FormAttachment(0, 5);
		fd.left = new FormAttachment(0, 5);
		grFilter.setLayoutData(fd);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 4;
		grFilter.setLayout(gridLayout);
		grFilter.setText(Messages.getString("ReferenceListView.Filter")); //$NON-NLS-1$

		final Label label = new Label(grFilter, SWT.NONE);
		label.setText(Messages.getString("ReferenceListView.Name")); //$NON-NLS-1$
		txtName = new Text(grFilter, SWT.BORDER);
		final GridData gd_txtName = new GridData(SWT.FILL, SWT.CENTER, true, false);
		txtName.setLayoutData(gd_txtName);
		txtName.addModifyListener(new StateChangedListener());

		tableViewer = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(false);
		final FormData fd_table = new FormData();
		fd_table.bottom = new FormAttachment(100, -5);
		fd_table.right = new FormAttachment(grFilter, 0, SWT.RIGHT);
		fd_table.top = new FormAttachment(grFilter, 5, SWT.BOTTOM);
		fd_table.left = new FormAttachment(grFilter, 0, SWT.LEFT);
		table.setLayoutData(fd_table);
	}
	
	/**
	 * Класс фильтрации списка
	 */
	private class ReferenceListFilter extends ViewerFilter {
		public boolean select(Viewer viewer, Object parentElement, Object element) {
			if (element == null) return true;
//			if (filterAction != null && !filterAction.isChecked()) 
//				return true;
			Reference reference = (Reference)element;
			if (reference.getName() == null)
				return true;
			return (reference.getName().equals("") || //$NON-NLS-1$
					reference.getName().toLowerCase().
					contains(txtName.getText().toLowerCase()));
		}		
	}
	
	protected void setTableProviders() {
		tableViewer.setContentProvider(new TableContentProvider());		
		tableViewer.setLabelProvider(getLabelProvider());
	} 	
	
	public class TableContentProvider implements ITreeContentProvider {
		public Object[] getChildren(Object parentElement) {
			return new Object[] {};
		}
		public Object getParent(Object element) {
			return null;
		}
		public boolean hasChildren(Object element) {
			return false;
		}
		@SuppressWarnings("unchecked")
		public Object[] getElements(Object inputElement) {
			return  (inputElement != null) ? ((List<Object>)inputElement).toArray() : new Object[0];
		}
		public void dispose() {}
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {}
	}

	protected void createTableFilter() {
		viewerFilter = new ReferenceListFilter();	
		tableViewer.addFilter(viewerFilter);
	}
	
	protected void addColumns() {
		addColumn(400, Messages.getString("ReferenceListView.Name"), TableSortListenerFactory.STRING_COMPARATOR); //$NON-NLS-1$
		addColumn(100, Messages.getString("ReferenceListView.Code"), TableSortListenerFactory.STRING_COMPARATOR); //$NON-NLS-1$
	}	
	
	private static class ReferenceLabelProvider extends LabelProvider
										implements ITableLabelProvider {
		private static final int COLUMN_NAME = 0;
		private static final int COLUMN_CODE = 1;
		
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			if (element instanceof Reference) {
				switch (columnIndex) {
				case COLUMN_NAME: return ((Reference)element).getName();
				case COLUMN_CODE: return ((Reference)element).getCode();
				}
			}
			return null;
		}
	}
	
	protected IBaseLabelProvider getLabelProvider() {
		return new ReferenceLabelProvider();
	}

	protected void initTable() {
		try {
			showBusy(true);
			if (data != null) 
				tableViewer.setInput(data);
		} finally {
			showBusy(false);
		}
	}
}