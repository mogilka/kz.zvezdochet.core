package kz.zvezdochet.core.ui.view;

import java.util.List;

import kz.zvezdochet.core.service.DataAccessException;
import kz.zvezdochet.core.ui.extension.ExtensionUtil;
import kz.zvezdochet.core.ui.extension.ModelExtension;

import org.eclipse.swt.widgets.Composite;

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
			extensions = ExtensionUtil.getExtensionProviders(extPointId);
		return extensions;
	}
}
