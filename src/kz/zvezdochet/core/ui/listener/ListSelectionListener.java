package kz.zvezdochet.core.ui.listener;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;

/**
 * Обработчик выделения элементов списка
 * @author Nataly Didenko
 *
 */
public class ListSelectionListener implements ISelectionChangedListener, 
		IDoubleClickListener {
	
	public void doubleClick(DoubleClickEvent event) {
//		if (editAction != null && editAction.isEnabled())
//			editAction.run();
	}

	public void selectionChanged(SelectionChangedEvent event) {
//		deactivateUnaccessable();
	}
}
