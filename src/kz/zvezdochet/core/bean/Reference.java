package kz.zvezdochet.core.bean;

import kz.zvezdochet.core.service.BaseService;

/**
 * Прототип объекта текстового справочника,
 * структура которого имеет вид "идентификатор:код:значение"
 * @author Nataly Didenko
 * 
 * @see Model прототип объекта предметной области
 */
public class Reference extends Model {
	private static final long serialVersionUID = 211870081291580287L;

	/**
	 * Код
	 */
	protected String code;
	
	/**
	 * Текстовое значение справочника
	 */
	protected String name;

	/**
	 * Описание
	 */
	private String description;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name != null ? name : "";
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	@Override
	public BaseService getService() {
		return null;
	}
}
