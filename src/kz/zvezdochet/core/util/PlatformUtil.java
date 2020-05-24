package kz.zvezdochet.core.util;

import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.Bundle;

/**
 * Утилита для работы с объектами рабочей среды
 * @author Natalie Didenko
 *
 */
public class PlatformUtil { 
	
	public PlatformUtil() {}

	/**
	 * Метод, преобразующий указанный файловый путь в URL
	 * @param pliginID идентификатор плагина
	 * @param path путь
	 * @return URL
	 * @throws IOException исключение ввода-вывода
	 */
	public static URL getPath(String pliginID, String path) throws IOException {
		Bundle bundle = Platform.getBundle(pliginID);
		URL fileURL = FileLocator.find(bundle, new Path(path), null);
		URL url = FileLocator.resolve(fileURL);
		return url;
	}

	/**
	 * Метод, возвращающий shell
	 * @param title - заголовок окна
	 * @param messageBody - сообщение
	 * */
	public static Shell getDisplayShell() {
		return Display.getCurrent().getActiveShell();
	} 
}
