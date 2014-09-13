package kz.zvezdochet.core.ui.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * Прототип представления браузера для отображения в нем элемента
 * @author Nataly Didenko
 *
 */
public abstract class BrowserModelView extends ModelView {
	protected Browser browser;
	
	@Override
	public View create(Composite parent) {
		parent.setLayout(new FillLayout());
		browser = new Browser(parent, SWT.BORDER);
		return null;
	}
	
	@Override
	public void reset() {
		browser.setText("");
	}
}
