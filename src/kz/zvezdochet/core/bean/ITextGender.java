package kz.zvezdochet.core.bean;

import java.util.List;

/**
 * Прототип справочника, содержащего гендерные толкования
 * @author Nataly Didenko
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
}
