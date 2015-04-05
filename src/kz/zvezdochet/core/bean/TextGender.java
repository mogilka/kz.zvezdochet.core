package kz.zvezdochet.core.bean;

import kz.zvezdochet.core.service.ModelService;
import kz.zvezdochet.core.service.TextGenderService;

/**
 * Толкование для различных типов персон:
 * 	- мужчины
 * 	- женщины
 * 	- дети
 * @author Nataly Didenko
 */
public class TextGender extends Model {
	private static final long serialVersionUID = 7663798183215999740L;

	/**
	 * Текст
	 */
    private String text;
	/**
	 * Тип
	 * man|woman|child мужчина|женщина|ребёнок
	 */
    private String type;
	/**
	 * Идентификатор связанного объекта
	 */
    private long objectid;
	/**
	 * Наименование таблицы связанного объекта
	 */
    private String objectype;

	public String getObjectType() {
		return objectype;
	}

	public void setObjectType(String objectype) {
		this.objectype = objectype;
	}

	public long getObjectId() {
		return objectid;
	}

	public void setObjectId(long objectid) {
		this.objectid = objectid;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public ModelService getService() {
		return new TextGenderService();
	}
}
