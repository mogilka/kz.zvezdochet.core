package kz.zvezdochet.core.ui.util;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * Класс обеспечивающий централизованное хранение
 * параметров, общедоступных для всех компонентов системы
 * @author Nataly Didenko
 *
 */
public class GUIutil {
	/**
	 * Пользовательские сообщения
	 */
	public static final String SOME_FIELDS_NOT_FILLED = Messages.getString("GUIutil.SomeFieldsNotFilled"); //$NON-NLS-1$
	public static final String NO_PARENT_NODE_SELECTED = Messages.getString("GUIutil.NoParentNodeSelected"); //$NON-NLS-1$
	public static final String DO_YOU_REALLY_WANNA_DELETE_ENTRY = Messages.getString("GUIutil.DoYouReallyWannaDeleteEntry"); //$NON-NLS-1$
	public static final String DATETIME_INVALID = Messages.getString("GUIutil.InvalidDate"); //$NON-NLS-1$
	public static final String DELETING_OBJECT_HAS_CHILDREN = Messages.getString("GUIutil.DeletingObjectHasChildren"); //$NON-NLS-1$
	public static final String UNABLE_DELETE_SYSTEM_OBJECT = Messages.getString("GUIutil.UnableDeleteSystemObject"); //$NON-NLS-1$
	public static final String IS_NOT_ELEMENT = Messages.getString("GUIutil.IsNotElement"); //$NON-NLS-1$
	public static final String INVALID_DATE_RANGE = Messages.getString("GUIutil.InvalidDateRange"); //$NON-NLS-1$
	public static final String MUST_BE_SELECT_ELEMENT = Messages.getString("GUIutil.MustbeSelectElement"); //$NON-NLS-1$
	public static final String REQUIRED_FIELD = Messages.getString("GUIutil.RequiredField"); //$NON-NLS-1$
		
	/**
	 * Подсказки, отображаемые в декорированных полях
	 */
	public static final String CTRL_BLANK = Messages.getString("GUIutil.Ctrl-Blank"); //$NON-NLS-1$
	public static final String INPUT_ERROR = Messages.getString("GUIutil.WrongValue"); //$NON-NLS-1$
	public static final String INPUT_WARNING = Messages.getString("GUIutil.IncorrectValue"); //$NON-NLS-1$
	
	/**
	 * Установка цвета фона через диалоговое окно
	 * @param shell окно приложения
	 * @param control визуальный компонент, в котором нужно поменять фон
	 */
	public static void setBackgroundViaDialog(Shell shell, Control control) {
		ColorDialog dlg = new ColorDialog(shell);
        dlg.setText("Выберите цвет");
		dlg.setRGB(control.getBackground().getRGB());
        RGB rgb = dlg.open();
        if (rgb != null)
        	control.setBackground(new Color(shell.getDisplay(), rgb));
	}
}
