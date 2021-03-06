package kz.zvezdochet.core.ui.decoration;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.swt.widgets.Control;

import kz.zvezdochet.core.ui.util.GUIutil;

/**
 * Декоратор поля ввода с неправильным форматом значения
 * @author Natalie Didenko
 *
 */
public class ErrorDecoration extends ControlDecoration {
	public ErrorDecoration(Control control, int position) {
		super(control, position);
		FieldDecoration decoration = FieldDecorationRegistry.getDefault().
		   getFieldDecoration(FieldDecorationRegistry.DEC_ERROR);
		setImage(decoration.getImage());
		setDescriptionText(GUIutil.INPUT_ERROR);
	}
}
