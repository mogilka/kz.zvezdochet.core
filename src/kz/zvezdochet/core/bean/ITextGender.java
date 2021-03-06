package kz.zvezdochet.core.bean;

import java.util.List;

/**
 * Прототип справочника, содержащего гендерные толкования
 * @author Natalie Didenko
 *
 */
public interface ITextGender {
	/**
	 * Поиск текстового толкования
	 * @return толкование
	 */
	public String getText();
	/**
	 * Инициализация текстового толкования
	 * @param genderText толкование
	 */
	public void setText(String text);
	/**
	 * Поиск гендерных толкований
	 * @param female true|false женский|мужской
	 * @param child true|false детский|взрослый
	 * @return список толкований
	 */
	public List<TextGender> getGenderTexts(boolean female, boolean child);
	/**
	 * Поиск гендерного толкования для ребёнка
	 * @return толкование
	 */
	public TextGender getChildText();
	/**
	 * Поиск гендерных толкований по типу
	 * @param type love|family|deal любовный|семейный|партнёрский гороскоп
	 * @return список толкований
	 */
	public TextGender getGenderText(String type);
}
