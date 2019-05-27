/**
 * 
 */
package kz.zvezdochet.core.ui.listener;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

/**
 * Обработчик визуальных событий
 * @author Natalie Didenko
 *
 */
public class StateChangedListener implements ModifyListener, ISelectionChangedListener, 
		IDoubleClickListener, KeyListener, MouseListener, SelectionListener {
	
	public void doubleClick(DoubleClickEvent event) {
//		if (editAction != null && editAction.isEnabled())
//			editAction.run();
	}

	public void modifyText(ModifyEvent e) {
//		if (tableViewer != null)
//			tableViewer.refresh(false);
	}

	public void selectionChanged(SelectionChangedEvent event) {
//		deactivateUnaccessable();
	}
	
	public void keyReleased(KeyEvent e) {
//		deactivateUnaccessable();
	}
	public void keyPressed(KeyEvent e) {
//		deactivateUnaccessable();
	}
	
	public void mouseDoubleClick(MouseEvent e) {
//		deactivateUnaccessable();
	}
	
	public void mouseDown(MouseEvent e) {
//		deactivateUnaccessable();
	}
	
	public void mouseUp(MouseEvent e) {
//		deactivateUnaccessable();
	}
	
	public void widgetDefaultSelected(SelectionEvent e) {}
	
	public void widgetSelected(SelectionEvent e) {
//		deactivateUnaccessable();
	}
}
