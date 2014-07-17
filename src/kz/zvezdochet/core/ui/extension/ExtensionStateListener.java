package kz.zvezdochet.core.ui.extension;

import kz.zvezdochet.core.ui.view.ElementView;


/**
 * Класс, отслеживающий изменение расширений объекта
 * @author Nataly
 * 
 * @see IExtensionStateListener Интерфейс слушателя расширений объекта
 */
public class ExtensionStateListener implements IExtensionStateListener {
	/**
	 * Ссылка на представление расширяемого объекта
	 */
	private ElementView view = null;

	/**
	 * Параметризованный конструктор
	 * @param view представление документа
	 */
	public ExtensionStateListener(ElementView view) {
		this.view = view;		
	}

	/**
	 * Оповещение основного представления об изменениях
	 */
	public void notifyStateChanged() {
		view.notifyChange();
	}
}