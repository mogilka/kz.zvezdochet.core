package kz.zvezdochet.core.bean;

import java.io.Serializable;

import kz.zvezdochet.core.service.BaseService;

/**
 * Прототип объекта предметной области
 * @author Nataly Didenko
 */
public abstract class Base implements Serializable {
	private static final long serialVersionUID = 1419179855113656076L;

	/**
	 * Идентификатор
	 */
	protected Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    /**
     * Возвращает сервис, поставляющий объект из БД
     * @return сервис объекта для взаимодействия с БД
     */
    public abstract BaseService getService();
}
