package kz.zvezdochet.core.ui.decoration;

import kz.zvezdochet.core.ui.util.GUIutil;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.swt.widgets.Control;

/**
 * Класс, обеспечивающий декорирование полей ввода сообщением об ошибке
 * @author nataly
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
