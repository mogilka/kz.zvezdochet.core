package kz.zvezdochet.core.ui.decoration;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.swt.widgets.Control;

import kz.zvezdochet.core.ui.util.GUIutil;

/**
 * Декоратор поля ввода с предупреждающим сообщением
 * @author Natalie Didenko
 *
 */
public class WarningDecoration extends ControlDecoration {
	public WarningDecoration(Control control, int position) {
		super(control, position);
		FieldDecoration decoration = FieldDecorationRegistry.getDefault().
		   getFieldDecoration(FieldDecorationRegistry.DEC_WARNING);
		setImage(decoration.getImage());
		setDescriptionText(GUIutil.INPUT_WARNING);
	}
}
