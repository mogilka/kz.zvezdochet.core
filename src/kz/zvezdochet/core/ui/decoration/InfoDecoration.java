package kz.zvezdochet.core.ui.decoration;

import kz.zvezdochet.core.ui.util.GUIutil;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.swt.widgets.Control;

/**
 * Класс, обеспечивающий декорирование полей ввода информационным сообщением
 * @author nataly
 *
 */
public class InfoDecoration extends ControlDecoration {
	public InfoDecoration(Control control, int position) {
		super(control, position);
		FieldDecoration decoration = FieldDecorationRegistry.getDefault().
		   getFieldDecoration(FieldDecorationRegistry.DEC_INFORMATION);
		setImage(decoration.getImage());
		setDescriptionText(GUIutil.CTRL_BLANK);
	}
}
