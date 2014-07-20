package kz.zvezdochet.core.ui.extension;

import kz.zvezdochet.core.bean.Model;
import kz.zvezdochet.core.service.DataAccessException;
import kz.zvezdochet.core.service.IModelService;

/**
 * Прототип расширителя объекта
 * @author Nataly Didenko
 */
public abstract class ExtensionProvider implements IExtensionProvider {
	/**
	 * Расширяемый объект
	 */
	protected Object extended = null;
	/**
	 * Элемент расширения
	 */
	protected Object extension = null;

	@Override
	public void initExtendedObject(Object extended) {
		this.extended = extended;
	}
	
	@Override
	public void initExtension() {}
	
	@Override
	public boolean canWorkWithObject() {
		return false;
	}
	
	@Override
	public boolean isAvailableToObject() {
		return false;
	}

	@Override
	public void save(Object extended) {}

	@Override
	public String getExtensionName() {
		return null;
	}

	@Override
	public void deleteExtension() {
		if (extension == null || ((Model)extension).getId() == null)
			initExtension();
		IModelService service = (IModelService)getExtensionService();
		if (service != null)
			try {
				service.delete(((Model)extension).getId());
			} catch (DataAccessException e) {
				e.printStackTrace();
			}
	}

	@Override
	public Object getExtensionElement() {
		return extension;
	}

	@Override
	public Object getExtendedObject() {
		return extended;
	}

	@Override
	public Object getExtension(Long extendedId) {
		return null;
	}

	/**
	 * Создание нового экземпляра расширения объекта.
	 * Данный метод должен быть обязательно переопределен в наследниках
	 * @return специфичное расширение объекта
	 */
	protected Object createExtension() {
		return null;
	}

	@Override
	public Object getExtensionService() {
		return null;
	}
}
