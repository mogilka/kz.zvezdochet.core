package kz.zvezdochet.core.ui.extension;

import java.util.List;

import kz.zvezdochet.core.ui.view.ElementComposite;
import kz.zvezdochet.core.ui.view.ElementView;
import kz.zvezdochet.core.ui.view.View;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Composite;

/**
 * Прототип интерфейса расширителя точки расширения,
 * обеспечивающий отображение дополнительных визуальных компонентов
 * @author Nataly
 */
public abstract interface IElementExtensionProvider extends IExtensionProvider {
	/**
	 * Метод, возвращающий идентификатор расширяемого представления
	 * @return идентификатор представления
	 */
	public String getExtensionViewId();
	/**
	 * Инициализация слушателя состояния представления
	 * @param listener слушатель
	 */
	public void initStateListener(IExtensionStateListener listener);
	/**
	 * Отображение визуальных представлений расширителей
	 */
	public void showAdditionalViews();
	/**
	 * Метод предназначен для сохранения открываемых представлений.
	 * Данный метод при необходимости хранения ссылки на дополнительное представление 
	 * должен быть переопределен в наследниках
	 */
	public void addShowingView(View view);
	/**
	 * Добавление композитов расширителей в расширяемое представление
	 * @param parent композит-контейнер представления
	 */
	public void provideAdditionalControls(Composite parent);
	/**
	 * Закрытие визуальных представлений расширения
	 */
	public void close();
	/**
	 * Определение состояния расширителя
	 * @return 
	 * <b>true</b> - состояние было изменено<br> 
	 * <b>false</b> - состояние не было изменено
	 */
	public boolean isStateChanged();
	/**
	 * Поиск дополнительных действий расширителей
	 * для отображения в панели инструментов основного представления
	 * @return список действий
	 */
	public List<Action> getToolbarActions();
	/**
	 * Передача ссылки на основное представление в расширитель
	 */
	public void setView(ElementView view);
	/**
	 * Метод, возвращающий расширяемое представление
	 * @return представление
	 */
	public ElementView getView();
	/** 
	 * Метод, возвращающий контейнер виджетов расширителя
	 * @return композит
	 */
	public Composite getContainer();
	/** 
	 * Инициализация контейнера виджетов расширителя
	 * @param сontainer композит расширяемого представления
	 */
	public void setСontainer(Composite сontainer);
	/**
	 * Проверка правильности заполненности полей расширяемого представления
	 * @throws BusinessRuleException
	 */
	public void checkModelForSave();
	/**
	 * Удаление композитов расширения
	 */
	public void disposeComposites();
	/**
	 * Метод, возвращающий композит расширения
	 * @return композит расширения
	 */
	public ElementComposite getExtensionComposite();
}
