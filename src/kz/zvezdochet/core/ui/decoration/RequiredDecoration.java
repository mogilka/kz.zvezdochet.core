package kz.zvezdochet.core.ui.decoration;

import kz.zvezdochet.core.ui.util.GUIutil;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.swt.widgets.Control;

/**
 * Класс, обеспечивающий декорирование полей ввода,
 * которые обязательны для заполнения
 * @author nataly
 *
 */
public class RequiredDecoration extends ControlDecoration {
	public RequiredDecoration(Control control, int position) {
		super(control, position);
		FieldDecoration decoration = FieldDecorationRegistry.getDefault().
		   getFieldDecoration(FieldDecorationRegistry.DEC_REQUIRED);
		setImage(decoration.getImage());
		setDescriptionText(GUIutil.REQUIRED_FIELD);
	}
}
