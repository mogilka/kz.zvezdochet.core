package kz.zvezdochet.core.bean;

import java.util.List;

import kz.zvezdochet.core.service.DataAccessException;
import kz.zvezdochet.core.service.TextGenderService;

/**
 * Прототип справочника гендерных значений
 * @author Nataly Didenko
 */
public abstract class TextGenderDictionary extends Dictionary implements ITextGender {
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
}
