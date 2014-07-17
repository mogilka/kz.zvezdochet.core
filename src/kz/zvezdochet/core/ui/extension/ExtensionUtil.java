package kz.zvezdochet.core.ui.extension;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

/**
 * Класс, обеспечивающий методы для работы с расширениями объектов
 * @author Nataly Didenko
 *
 */
public class ExtensionUtil {
	
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

	/**
	 * Формирование списка провайдеров расширений
	 * @param extPointID
	 * @return список расширителей
	 */
	public static List<IExtension> getExtensionProviders(String extPointID) {
		IConfigurationElement[] extensions = getExtPointExtensions(extPointID); 
		List<IExtension> providers = new ArrayList<IExtension>();
		if (extensions != null)
			for (int i = 0; i < extensions.length; i++) {
				try {
					IExtension provider = (IExtension)extensions[i].
							createExecutableExtension("class"); //$NON-NLS-1$
					providers.add(provider);
					//System.out.println("extensions.getClass()\t" + provider.getClass());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		return providers;
	}
}
