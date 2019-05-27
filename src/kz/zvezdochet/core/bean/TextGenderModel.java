package kz.zvezdochet.core.bean;

import java.util.List;

import kz.zvezdochet.core.service.DataAccessException;
import kz.zvezdochet.core.service.TextGenderService;

/**
 * Прототип модели с гендерным толкованием
 * @author Natalie Didenko
 */
public abstract class TextGenderModel extends Model implements ITextGender {
	private static final long serialVersionUID = -2466065854923291059L;

	@Override
	public String getText() {
		return text;
	}

	@Override
	public void setText(String text) {
		this.text = text;		
	}

	@Override
	public List<TextGender> getGenderTexts(boolean female, boolean child) {
		try {
			return new TextGenderService().find(this, female, child);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Текст
	 */
    private String text;
    
    @Override
    public String toString() {
    	return text != null ? text : "";
    }

	@Override
	public TextGender getChildText() {
		try {
			return new TextGenderService().findChild(this);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public TextGender getGenderText(String type) {
		try {
			return new TextGenderService().find(this, type);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void init(boolean mode) {}
}
