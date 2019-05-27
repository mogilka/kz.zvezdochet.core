package kz.zvezdochet.core.ui.extension;

/**
 * Интерфейс обработки изменений состояния элемента расширения
 * @author Natalie Didenko
 */
public interface IExtensionStateListener {
	/**
	 * Оповещение расширяемого представления 
	 * об изменениях состояния объектов расширения
	 */
	public void notifyStateChanged();
}
