package kz.zvezdochet.core.ui.view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.widgets.Composite;

import kz.zvezdochet.core.service.DataAccessException;
import kz.zvezdochet.core.ui.extension.IExtension;
import kz.zvezdochet.core.ui.extension.ModelExtension;

/**
 * Прототип представления
 * @author Nataly Didenko
 */
public abstract class View {

	public void setTitle(String title) {
//		setPartName(title);
	}
	
	/**
	 * Создание элементов управления
	 * @param parent композит-родитель
	 * @return представление
	 **/
	public View create(Composite parent) {
		try {
			initControls();
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Закрытие представления
	 */
	public void close () {
//		if (canClose())
//			this.getSite().getPage().hideView(this);
	}
	
	/**
	 * Очистка элементов управления
	 */
	public void reset() {}

	/**
	 * Выравнивание визуальных компонентов
	 * @param composite контейнер виджетов
	 */
	protected void init(Composite composite) {}
	/**
	 * Инициализация элементов управления
	 */
	protected void initControls() throws DataAccessException {}
	/**
	 * Проверка введённых значений
	 * @param mode режим проверки содержимого
	 * @return true - поля заполнены корректно
	 */
	public abstract boolean check(int mode) throws Exception;

	/**
	 * Идентификатор точки расширения представления
	 */
	protected String extPointId = "";
	/**
	 * Массив расширений представления
	 */
	protected List<ModelExtension> extensions;

	/**
	 * Инициализация расширений
	 * @return массив расширений
	 */
	public List<ModelExtension> getExtensions() {
		if (null == extensions)
			extensions = getExtensionProviders(extPointId);
		return extensions;
	}

	/**
	 * Поиск расширений в реестра
	 * @param extPointID идентификатор точки расширения
	 * @return массив расширений
	 */
	private IConfigurationElement[] getExtensions(String extPointID) {
		IConfigurationElement[] extensions = null;
		IExtensionPoint point = Platform.getExtensionRegistry().getExtensionPoint(extPointID);
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
	public List<ModelExtension> getExtensionProviders(String extPointID) {
		IConfigurationElement[] extensions = getExtensions(extPointID);
		if (extensions != null) {
			List<ModelExtension> providers = new ArrayList<ModelExtension>();
			for (int i = 0; i < extensions.length; i++) {
				try {
					IExtension provider = (IExtension)extensions[i].
							createExecutableExtension("class"); //$NON-NLS-1$
					providers.add((ModelExtension)provider);
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
