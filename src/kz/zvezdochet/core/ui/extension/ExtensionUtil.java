package kz.zvezdochet.core.ui.extension;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

/**
 * Класс, обеспечивающий методы для работы с расширениями
 * @author Nataly Didenko
 *
 */
public class ExtensionUtil { 
	
	/**
	 * Поиск расширений в реестра
	 * @param extPointID идентификатор точки расширения
	 * @return массив расширений
	 */
	private static IConfigurationElement[] getExtensions(String extPointID) {
		IConfigurationElement[] extensions = null;
		IExtensionPoint point =
			Platform.getExtensionRegistry().getExtensionPoint(extPointID);
		if (point != null)
			extensions = Platform.getExtensionRegistry()
				.getExtensionPoint(extPointID).getConfigurationElements();
		return extensions;
	}

	/**
	 * Поиск провайдеров расширений
	 * @param extPointID идентификатор точки расширения
	 * @return список провайдеров расширений
	 */
	public static List<ModelExtensionProvider> getExtensionProviders(String extPointID) {
		IConfigurationElement[] extensions = getExtensions(extPointID); 
		if (extensions != null) {
			List<ModelExtensionProvider> providers = new ArrayList<ModelExtensionProvider>();
			for (int i = 0; i < extensions.length; i++) {
				try {
					IExtension provider = (IExtension)extensions[i].
							createExecutableExtension("class"); //$NON-NLS-1$
					providers.add((ModelExtensionProvider)provider);
					//System.out.println("extensions.getClass()\t" + provider.getClass());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return providers;
		}
		return null;
	}
}
