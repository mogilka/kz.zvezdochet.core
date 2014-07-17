package kz.zvezdochet.core.ui.util;

import kz.zvezdochet.core.util.PlatformUtil;

import org.eclipse.jface.dialogs.MessageDialog;

/**
 * Класс, предоставляющий вызов пользовательских сообщений
 * @author Nataly Didenko
 *
 */
public class DialogUtil {

	/**
	 * Вывод диалогового предупреждения
	 * @param title - заголовок окна
	 * @param messageBody - сообщение
	 * */
	public static void alertError(String messageBody) {
		MessageDialog.openError(
			PlatformUtil.getDisplayShell(), Messages.getString("DialogUtil.Error"), messageBody); //$NON-NLS-1$
	} 

	/**
	 * Вывод диалогового предупреждения
	 * @param title - заголовок окна
	 * @param messageBody - сообщение
	 * */
	public static void alertWarning(String messageBody) {
		MessageDialog.openWarning(
			PlatformUtil.getDisplayShell(), Messages.getString("DialogUtil.Warning"), messageBody); //$NON-NLS-1$
	} 

	/**
	 * Вывод диалогового предупреждения
	 * @param title - заголовок окна
	 * @param messageBody - сообщение
	 * */
	public static boolean alertConfirm(String messageBody) {
		return MessageDialog.openConfirm(
			PlatformUtil.getDisplayShell(), Messages.getString("DialogUtil.Confirmation"), messageBody); //$NON-NLS-1$
	} 

	/**
	 * Вывод диалогового предупреждения
	 * @param title - заголовок окна
	 * @param messageBody - сообщение
	 * */
	public static void alertInfo(String messageBody) {
		MessageDialog.openInformation(
			PlatformUtil.getDisplayShell(), Messages.getString("DialogUtil.Information"), messageBody); //$NON-NLS-1$
	} 

	/**
	 * Вывод диалогового предупреждения
	 * @param title - заголовок окна
	 * @param messageBody - сообщение
	 * */
	public static boolean alertQuestion(String messageBody) {
		return MessageDialog.openQuestion(
			PlatformUtil.getDisplayShell(), Messages.getString("DialogUtil.Confirmation"), messageBody); //$NON-NLS-1$
	} 
}
