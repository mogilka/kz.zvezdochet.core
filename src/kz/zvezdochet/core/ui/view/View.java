package kz.zvezdochet.core.ui.view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Composite;

import kz.zvezdochet.core.service.DataAccessException;
import kz.zvezdochet.core.ui.extension.IExtension;
import kz.zvezdochet.core.ui.extension.ModelExtension;

/**
 * Прототип представления
 * @author Natalie Didenko
 */
public abstract class View {
	/**
	 * Визуальный контейнер
	 */
	private Composite container;
	/**
	 * Контейнер с разделителем
	 */
	protected SashForm sashForm;

	public void setTitle(String title) {
//		setPartName(title);
	}
	
	/**
	 * Создание представления
	 * @param parent композит-родитель
	 * @return представление
	 **/
	public View create(Composite parent) {
		try {
			container = parent;
			init(parent);
			initControls();
			arrange(parent);
			decorate();
			deactivateUnaccessable();
		} catch (DataAccessException e) {
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
	 * Инициализация представления
	 * @param composite контейнер виджетов
	 */
	protected void init(Composite composite) {}
	/**
	 * Инициализация элементов управления
	 */
	protected void initControls() throws DataAccessException {}

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
	 */
	public void initExtensions() {
		if (null == extensions)
			extensions = getExtensions(extPointId);
	}

	/**
	 * Поиск расширений в реестре
	 * @param extPointID идентификатор точки расширения
	 * @return массив расширений
	 */
	private IConfigurationElement[] getConfigurationElements(String extPointID) {
		IConfigurationElement[] elements = null;
		IExtensionPoint point = Platform.getExtensionRegistry().getExtensionPoint(extPointID);
		if (point != null)
			elements = Platform.getExtensionRegistry()
				.getExtensionPoint(extPointID).getConfigurationElements();
		return elements;
	}

	/**
	 * Поиск расширений
	 * @param extPointID идентификатор точки расширения
	 * @return список провайдеров расширений
	 */
	private List<ModelExtension> getExtensions(String extPointID) {
		IConfigurationElement[] elements = getConfigurationElements(extPointID);
		if (elements != null) {
			List<ModelExtension> extensions = new ArrayList<ModelExtension>();
			for (int i = 0; i < elements.length; i++) {
				try {
					IExtension provider = (IExtension)elements[i].createExecutableExtension("class"); //$NON-NLS-1$
					extensions.add((ModelExtension)provider);
					//System.out.println("extensions.getClass()\t" + provider.getClass());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return extensions;
		}
		return null;
	}

	/**
	 * Выравнивание визуальных элементов
	 * @param composite контейнер виджетов
	 */
	protected void arrange(Composite composite) {
		GridLayoutFactory.swtDefaults().applyTo(composite);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(sashForm);
		GridLayoutFactory.swtDefaults().applyTo(sashForm);
	}

	/**
	 * Декорирование визуальных компонентов
	 */
	protected void decorate() {}

	/**
	 * Управление доступом к функциям модификации данных
	 */
	protected void deactivateUnaccessable() {
//		boolean changed = (model != null)
//			&& isEditable();
//		if (applyAction != null)
//			applyAction.setEnabled(changed);
	}

	/**
	 * Метод, обновляющий контейнер и
	 * заново задающий расположение элементов управления
	 */
	public void refreshView() {
		container.layout(true, true);
	}
}
