package kz.zvezdochet.core.ui.extension;

import kz.zvezdochet.core.ui.view.ModelView;


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
	private ModelView view = null;

	/**
	 * Параметризованный конструктор
	 * @param view представление документа
	 */
	public ExtensionStateListener(ModelView view) {
		this.view = view;		
	}

	/**
	 * Оповещение основного представления об изменениях
	 */
	public void notifyStateChanged() {
		view.notifyChange();
	}
}
