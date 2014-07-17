package kz.zvezdochet.core.ui.decoration;

import kz.zvezdochet.core.ui.util.GUIutil;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.swt.widgets.Control;

/**
 * Класс, обеспечивающий декорирование полей ввода предупреждающим сообщением
 * @author Nataly Didenko
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
