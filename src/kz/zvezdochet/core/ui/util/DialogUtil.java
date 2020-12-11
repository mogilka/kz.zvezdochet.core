package kz.zvezdochet.core.ui.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.ui.dialogs.ListSelectionDialog;

import kz.zvezdochet.core.util.PlatformUtil;

/**
 * Обработчик диалоговых окон
 * @author Natalie Didenko
 * @todo http://www.java2s.com/Code/Java/SWT-JFace-Eclipse/JFacesProgressMonitorDialog.htm
 */
public class DialogUtil {

	/**
	 * Вывод ошибки
	 * @param exception исключение
	 */
	public static void alertError(Exception exception) {
		MultiStatus status = createMultiStatus(exception);
        ErrorDialog.openError(PlatformUtil.getDisplayShell(), "Ошибка", exception.getLocalizedMessage(), status);
	} 

	/**
	 * Вывод предупреждения
	 * @param message сообщение
	 */
	public static void alertWarning(String message) {
		MessageDialog.openWarning(PlatformUtil.getDisplayShell(), "Предупреждение", message);
	} 

	/**
	 * Вывод подтверждения
	 * @param message сообщение
 	 * @return true|false - Ok|Cancel
	 */
	public static boolean alertConfirm(String message) {
		return MessageDialog.openConfirm(PlatformUtil.getDisplayShell(), "Подтверждение", message);
	} 

	/**
	 * Вывод информации
	 * @param message сообщение
	 */
	public static void alertInfo(String message) {
		MessageDialog.openInformation(PlatformUtil.getDisplayShell(), "Информация", message);
	}

	/**
	 * Генерация статуса исключения
	 * @param t ошибка
	 * @return статус исключения
	 */
	private static MultiStatus createMultiStatus(Throwable t) {
        List<Status> childStatuses = new ArrayList<>();
        StackTraceElement[] stackTraces = Thread.currentThread().getStackTrace();

        for (StackTraceElement stackTrace: stackTraces) {
            Status status = new Status(IStatus.ERROR,
                    "com.vogella.tasks.ui", stackTrace.toString());
            childStatuses.add(status);
        }

        MultiStatus ms = new MultiStatus("kz.zvezdochet.core.ui",
        	IStatus.ERROR, childStatuses.toArray(new Status[] {}),
        	t.toString(), t);
        return ms;
    }

	/**
	 * Вывод вопроса
	 * @param title заголовок
	 * @param message сообщение
	 * @param labels массив ответов
 	 * @return 0 by default
	 */
	public static int alertQuestion(String title, String message, String[] labels) {
		MessageDialog dialog = new MessageDialog(PlatformUtil.getDisplayShell(), title, null, message, MessageDialog.QUESTION, labels, 0);
		int result = dialog.open();
//		System.out.println(result);
		return result;
	} 

	/**
	 * Вывод выбора
	 * @param title заголовок
	 * @param message сообщение
	 * @param labels массив ответов
	 * @param selections массив ответов по умолчанию
 	 * @return массив выбранных ответов
	 */
	public static Object[] alertDialog(String title, String message, String[] labels, Object[] selections) {
		ListSelectionDialog dialog = new ListSelectionDialog(PlatformUtil.getDisplayShell(), labels,
			ArrayContentProvider.getInstance(), new LabelProvider(), message);
		dialog.setTitle(title);
		dialog.setInitialSelections(selections);
		dialog.open();
		Object[] result = dialog.getResult();
		System.out.println(result);
		return result;
	} 
}
