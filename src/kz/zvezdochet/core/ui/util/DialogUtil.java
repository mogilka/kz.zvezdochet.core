package kz.zvezdochet.core.ui.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;

import kz.zvezdochet.core.util.PlatformUtil;

/**
 * Обработчик пользовательских сообщений
 * @author Nataly Didenko
 *
 */
public class DialogUtil {

	/**
	 * Вывод ошибки
	 * @param message сообщение
	 */
	public static int alertError(String message) {
		return alert(SWT.ICON_ERROR, "Ошибка", message);
	} 

	/**
	 * Вывод предупреждения
	 * @param message сообщение
	 */
	public static int alertWarning(String message) {
		return alert(SWT.ICON_WARNING, "Предупреждение", message);
	} 

	/**
	 * Вывод подтверждения
	 * @param message сообщение
	 */
	public static int alertConfirm(String message) {
		return alert(SWT.ICON_QUESTION, "Подтверждение", message);
	} 

	/**
	 * Вывод информации
	 * @param message сообщение
	 * */
	public static int alertInfo(String message) {
		return alert(SWT.ICON_INFORMATION, "Информация", message);
	}

	/**
	 * Генерация диалогового окна
	 * @param type тип иконки
	 * @param title заголовок
	 * @param message сообщение
	 * */
	private static int alert(int type, String title, String message) {
		MessageBox dialog = new MessageBox(PlatformUtil.getDisplayShell(), type|SWT.OK|SWT.CANCEL);
		dialog.setText(title);
		dialog.setMessage(message);
		return dialog.open();
	} 

	/**
	 * Поиск размера экрана
	 * @return точка с размерами
	 * */
	public static Point getScreenSize() {
		Device device = Display.getDefault();
		Rectangle rect = device.getClientArea();
		return new Point(rect.width, rect.height);
	}
}
