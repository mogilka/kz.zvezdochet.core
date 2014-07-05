package kz.zvezdochet.core.ui.util;

import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IActionBars;

/**
 * Класс, предоставляющий методы для работы со строкой состояния
 * @author Nataly
 *
 */
public class StatusUtil {
		//IStatusLineManager statusLineManager = IEditorPart.getEditorSite().getActionBarContributor();
		//CoreUtil.getActivePage();
		//getViewSite().getActionBars().getToolBarManager();

//	private static Color TEXT_COLOR = Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
//	private static Color ERROR_COLOR = Display.getDefault().getSystemColor(SWT.COLOR_RED);

	/** 
	* Вывод заданного текста в строку состояния JFace 
	* @param msg текст
	* @param isError <true> - вывод строки красным цветом 
	* @param actionBars набор панелей действий представления или редактора
	* для доступа к IStatusLineManager 
	* @sample 
	* //write to StatusBar from View<br>
	* private void updateStatus() {<br>
	* &nbsp;&nbsp;&nbsp;&nbsp;StatusUtil.writeToStatusLine(<br>
	* &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	* "statustext", false, getViewSite().getActionBars());<br>
	* }
	*
	*/ 
	public static void writeToStatusLine(String msg, boolean isError, 
												IActionBars actionBars) { 
		final IStatusLineManager slm = actionBars.getStatusLineManager(); 
		if (isError) { 
			Display.getCurrent().getSystemImage(SWT.ICON_ERROR); 
			slm.setErrorMessage(null, msg); 
		} else { 
			Display.getCurrent().getSystemImage(SWT.ICON_WORKING); 
			slm.setErrorMessage(null, ""); // required! 
			slm.setMessage(msg); 
		} 
	}
}
