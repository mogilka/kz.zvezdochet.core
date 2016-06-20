package kz.zvezdochet.core.ui.decoration;

import kz.zvezdochet.core.ui.util.GUIutil;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.swt.widgets.Control;

/**
 * Декоратор поля ввода с автозаполнением
 * @author Nataly Didenko
 *
 */
public class ContentProposalDecoration extends ControlDecoration {
	public ContentProposalDecoration(Control control, int position) {
		super(control, position);
		FieldDecoration decoration = FieldDecorationRegistry.getDefault().
		   getFieldDecoration(FieldDecorationRegistry.DEC_CONTENT_PROPOSAL);
		setImage(decoration.getImage());
		setDescriptionText(GUIutil.CTRL_BLANK);
	}
}
