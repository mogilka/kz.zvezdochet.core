package kz.zvezdochet.core.ui.view;

import kz.zvezdochet.core.ui.ArrayLabelProvider;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

/**
 * Обработчик отображения модели в таблице
 * @author Nataly Didenko
 */
public class ModelLabelProvider extends ArrayLabelProvider implements ITableLabelProvider {
	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}
	@Override
	public String getColumnText(Object element, int columnIndex) {
		return (null == element) ? "" : element.toString(); //$NON-NLS-1$
	}
	@Override
	public Color getForeground(Object element, int columnIndex) {
		return null;
	}
	@Override
	public Color getBackground(Object element, int columnIndex) {
		return null;
	}
}
