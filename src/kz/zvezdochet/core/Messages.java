package kz.zvezdochet.core;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.service.prefs.Preferences;

import kz.zvezdochet.core.util.Constants;

/**
 * Обработчик переводов
 * @author Natalie Didenko
 */
public class Messages /*extends org.eclipse.osgi.util.NLS*/ {
	/**
	 * Название мультиязычных файлов из папки OSGI-INF/l10n
	 * Данная папка должна быть добавлена в список ресурсов-исходников проекта
	 */
	private static final String BUNDLE_NAME = "bundle"; //$NON-NLS-1$
/*
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}
*/
	private static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private Messages() {}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	public static void init() {
		Preferences preferences = InstanceScope.INSTANCE.getNode("kz.zvezdochet");
		Preferences recent = preferences.node(Constants.PREF_LANG);
		String lang = recent.get(Constants.PREF_LANG, "ru");
		RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME, new Locale(lang));
	}
}
