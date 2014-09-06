package kz.zvezdochet.core.bean;

import kz.zvezdochet.core.service.ModelService;

/**
 * Толкование для различных типов персон:
 * 	- мужчины
 * 	- женщины
 * 	- дети
 * @author Nataly Didenko
 */
public class GenderText extends Model {
	private static final long serialVersionUID = 7663798183215999740L;

	/**
	 * Текст для мужчины
	 */
    private String maletext;
	/**
	 * Текст для женщины
	 */
    private String femaletext;
	/**
	 * Текст для ребёнка
	 */
    private String childtext;

	public String getChildtext() {
		return childtext;
	}

	public void setChildtext(String childtext) {
		this.childtext = childtext;
	}

	public String getMaletext() {
		return maletext;
	}

	public void setMaletext(String maletext) {
		this.maletext = maletext;
	}

	public String getFemaletext() {
		return femaletext;
	}

	public void setFemaletext(String femaletext) {
		this.femaletext = femaletext;
	}

	@Override
	public ModelService getService() {
		return null;
	}
}
