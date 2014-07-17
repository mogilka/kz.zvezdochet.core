package kz.zvezdochet.core.ui.provider;

import org.eclipse.jface.viewers.LabelProvider;

import kz.zvezdochet.core.bean.Reference;

/**
 * Класс, обеспечивающий отображение 
 * текстовых значений справочника
 * @author Nataly Didenko
 *
 */
public class ReferenceLabelProvider extends LabelProvider {
	public String getText(Object element) {				
		if (element instanceof Reference) {
			Reference type = (Reference)element;
			return type.getName();
		}
		return "";
	}
}
