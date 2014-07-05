package kz.zvezdochet.core.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import kz.zvezdochet.core.service.EntityService;

/**
 * Базовый класс для всех доменов предметной области
 * @author Nataly Didenko
 */
public abstract class BaseEntity implements Serializable {
	private static final long serialVersionUID = 2841496201855643759L;

	/**
	 * Уникальный идентификатор сущности в таблице БД
	 */
	protected Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isSaved() {
		return (id != null && id != 0);
	}

    /**
     * Структура общего назначения
     */
    private Map<String, Object> data = new HashMap<String, Object>();
    
    /**
     * Метод, возвращающий структуру данных сущности
     * @return структура данных
     */
    public Map<String, Object> getData() {
    	return data;
    }
    
    /**
     * Метод, инициализирующий структуру данных сущности
     * @param data структура данных
     */
    public void setData(Map<String, Object> data) {
    	this.data = data;
    }

    /**
     * Метод, извлекающий параметр сущности по ключу
     * @param key имя параметра
     * @return значение параметра
     */
    public Object getData(String key) {
    	return (data.containsKey(key)) ? data.get(key) : null;
    }
    
    /**
     * Метод, задающий параметр сущности
     * @param key имя параметра
     * @param value значение параметра
     */
    public void setData(String key, Object value) {
    	data.put(key, value);
    }

    /**
     * Возвращает сервис, поставляющий данные сущности из БД
     * @return сервис сущности
     */
    public static EntityService getService() {
    	return null;
    }

    /**
     * Возвращает сервис, поставляющий данные сущности, от которой наследован объект
     * @return сервис сущности
     */
    public EntityService getEntityService() {
    	return getService();
    }
}
