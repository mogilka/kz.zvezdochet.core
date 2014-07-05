package kz.zvezdochet.core.bean;

/**
 * Абстрактная сущность, определяющая свойства
 * однотипных текстовых справочников,
 * структура которых имеет вид "идентификатор:код:значение"
 * @author Nataly
 * 
 * @see BaseEntity Базовая сущность
 */
public class Reference extends BaseEntity {
	private static final long serialVersionUID = 1613816375946166946L;

	public Reference() {}
		
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
}
