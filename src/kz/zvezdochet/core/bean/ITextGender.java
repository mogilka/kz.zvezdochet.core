package kz.zvezdochet.core.bean;

/**
 * Прототип справочника, содержащего толкования
 * для мужского, женского пола, а также для детей
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
	 * @return толкование
	 */
	public GenderText getGenderText();
	/**
	 * Инициализация гендерных толкований
	 * @param genderText толкование
	 */
	public void setGenderText(GenderText genderText);
}
