package kz.zvezdochet.core.bean;

/**
 * Прототип модели с гендерным толкованием
 * @author Nataly Didenko
 */
public abstract class TextGenderModel extends Model implements ITextGender {
	private static final long serialVersionUID = 7203713223993014957L;

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
