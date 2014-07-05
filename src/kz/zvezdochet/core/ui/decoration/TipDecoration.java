package kz.zvezdochet.core.ui.decoration;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.swt.widgets.Control;

/**
 * Класс, обеспечивающий декорирование полей ввода рекомендательным сообщением
 * @author nataly
 *
 */
public class TipDecoration extends ControlDecoration {
	public TipDecoration(Control control, int position) {
		super(control, position);
		FieldDecoration decoration = FieldDecorationRegistry.getDefault().
		   getFieldDecoration(FieldDecorationRegistry.DEC_CONTENT_PROPOSAL);
		setImage(decoration.getImage());
		setDescriptionText("");
	}
}
