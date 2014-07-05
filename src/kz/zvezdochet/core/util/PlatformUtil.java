package kz.zvezdochet.core.util;

import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;

/**
 * Класс, обеспечивающий методы для работы с объектами рабочей среды
 * @author Nataly
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
	 * Метод, возвращающий активный контейнер представлений окна
	 */
	public static IWorkbenchPage getActivePage() {
		return getActiveWindow().getActivePage();
	}

	/**
	 * Метод, возвращающий shell
	 * @param title - заголовок окна
	 * @param messageBody - сообщение
	 * */
	public static Shell getDisplayShell() {
		return new Shell(Display.getDefault());
	} 

	/**
	 * Метод, возвращающий активное окно
	 */
	public static IWorkbenchWindow getActiveWindow() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow();		
	}

	/**
	 * Метод, возвращающий из реестра массив расширителей 
	 * точки расширения документа
	 * @return массив расширителей
	 */
	public static IConfigurationElement[] getExtPointExtensions(String extPointID) {
		IConfigurationElement[] extensions = null;
		IExtensionPoint point =
			Platform.getExtensionRegistry().getExtensionPoint(extPointID);
		if (point != null)
			extensions = Platform.getExtensionRegistry()
				.getExtensionPoint(extPointID).getConfigurationElements();
		return extensions;
	}
}
