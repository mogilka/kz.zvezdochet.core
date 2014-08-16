package kz.zvezdochet.core.ui.provider;

import kz.zvezdochet.core.bean.Dictionary;

import org.eclipse.jface.viewers.LabelProvider;

/**
 * Обработчик отображения значений справочника
 * @author Nataly Didenko
 *
 */
public class DictionaryLabelProvider extends LabelProvider { 
	public String getText(Object element) {				
		if (element instanceof Dictionary) {
			Dictionary type = (Dictionary)element;
			return type.getName();
		}
		return "";
	}
}
