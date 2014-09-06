package kz.zvezdochet.core.bean;

/**
 * Прототип справочника гендерных значений
 * @author Nataly Didenko
 */
public class TextGenderDictionary extends Dictionary implements ITextGender {
	private static final long serialVersionUID = 6051185469564751147L;

	@Override
	public String getText() {
		return text;
	}

	@Override
	public void setText(String text) {
		this.text = text;		
	}

	@Override
	public GenderText getGenderText() {
		return genderText;
	}

	@Override
	public void setGenderText(GenderText genderText) {
		this.genderText = genderText;
	}
	/**
	 * Текст
	 */
    private String text;
	/**
	 * Толкование для обоих полов и детей
	 */
    private GenderText genderText;
}