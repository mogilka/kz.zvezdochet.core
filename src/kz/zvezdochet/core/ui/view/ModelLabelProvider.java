package kz.zvezdochet.core.ui.view;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * Обработчик отображения модели в таблице
 * @author Nataly Didenko
 */
public class ModelLabelProvider extends LabelProvider implements ITableLabelProvider {
	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}
	@Override
	public String getColumnText(Object element, int columnIndex) {
		return (element == null) ? "" : element.toString(); //$NON-NLS-1$
	}
}	
