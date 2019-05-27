package kz.zvezdochet.core.ui.listener;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

/**
 * Обработчик ввода числовых данных
 * @author Natalie Didenko
 *
 */
public class DigitInputListener implements Listener {
	public void handleEvent(Event event) {
		String string = event.text;
        char[] chars = new char[string.length()];
        string.getChars(0, chars.length, chars, 0);
        for (int i = 0; i < chars.length; i++) {
        	if (!('0' <= chars[i] && chars[i] <= '9')) {
        		event.doit = false;
        		return;
        	}
        }
	}
}
