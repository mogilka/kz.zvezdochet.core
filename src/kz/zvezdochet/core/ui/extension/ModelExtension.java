package kz.zvezdochet.core.ui.extension;

import java.util.List;

import kz.zvezdochet.core.bean.Dictionary;
import kz.zvezdochet.core.bean.Model;
import kz.zvezdochet.core.service.DataAccessException;
import kz.zvezdochet.core.service.IModelService;
import kz.zvezdochet.core.ui.view.ModelComposite;
import kz.zvezdochet.core.ui.view.ModelView;
import kz.zvezdochet.core.ui.view.View;

import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.swt.widgets.Composite;

/**
 * Прототип расширения модели
 * @author Nataly Didenko
 */
public abstract class ModelExtension implements IExtension {
	/**
	 * Расширяемая модель
	 */
	protected Model extended = null;
	/**
	 * Модель расширения
	 */
	protected Model extension = null;
	/**
	 * Представление расширяемого объекта
	 */
	protected ModelView view;
	/**
	 * Композит расширяемого представления,
	 * который выполняет роль контейнера виджетов расширения
	 */
	private Composite сontainer;
	/**
	 * Обработчик изменений элемента расширения
	 */
	protected IExtensionStateListener stateListener = null;

	/**
	 * Возвращает идентификатор расширяемого представления
	 * @return идентификатор представления
	 */
	public abstract String getExtensionViewId();

	/** 
	 * Возвращает контейнер виджетов расширения
	 * @return композит
	 */
	public Composite getContainer() {
		return сontainer;
	}
	
	/** 
	 * Инициализация контейнера виджетов расширения
	 * @param сontainer композит расширяемого представления
	 */
	public void setСontainer(Composite сontainer) {
		this.сontainer = сontainer;
	}
	
	/**
	 * Инициализация обработчика состояния представления
	 * @param listener обработчик состояния представления
	 */
	public void initStateListener(IExtensionStateListener listener) {
		this.stateListener = listener;
	}
		
	/**
	 * Передача ссылки на основное представление в расширение
	 */
	public void setView(ModelView view) {
		this.view = view;
	}

	/**
	 * Метод, возвращающий расширяемое представление
	 * @return представление
	 */
	public ModelView getView() {
		return view;
	}
	
	/**
	 * Проверка правильности заполненности полей расширяемого представления
	 * @throws BusinessRuleException
	 */
	public void checkModelForSave() {
		if (view != null)
			try {
				view.check(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	/**
	 * Метод предназначен для сохранения открываемых представлений.
	 * Данный метод при необходимости хранения ссылки на дополнительное представление 
	 * должен быть переопределен в наследниках
	 */
	public void addShowingView(View view) {}
	/**
	 * Добавление композитов расширения в расширяемое представление
	 * @param parent композит-контейнер представления
	 */
	public void provideAdditionalControls(Composite parent) {}

	/**
	 * Отображение визуальных представлений расширений
	 */
	public abstract void initView();
	
	/**
	 * Закрытие визуальных представлений расширения
	 */
	public void close() {
//		if (extensionViewId != null) {
//			IWorkbenchPage page=PlatformUtil.getActivePage();
//			if(page==null) return;
//			View view = (View)page.findView(extensionViewId);
//			if (view != null)
//				view.close();
//		}
//		disposeComposites();
//		extended = null;
	}
	
	/**
	 * Определение состояния расширения
	 * @return 
	 * <b>true</b> - состояние было изменено<br> 
	 * <b>false</b> - состояние не было изменено
	 */
	public boolean isChanged() {
		boolean changed = false; 
//		if (view != null) 
//			changed = view.isStateChanged();
		if (composite != null) 
			changed = composite.isChanged();
		return changed;
	}
	
	/**
	 * Удаление композитов расширения
	 */
	public void disposeComposites() {
		extension = null;
		if (composite != null) {
			composite.dispose();
			composite = null;
		}
	}

	/**
	 * Метод, возвращающий композит расширения
	 * @return композит расширения
	 */
	public ModelComposite getExtensionComposite() {
		return composite;
	}
	/**
	 * Инициализация расширяемого объекта
	 * @param extended расширяемая модель
	 */
	public void initExtended(Model extended) {
		this.extended = extended;
	}
	/**
	 * Инициализация расширения
	 */
	public void initExtension() {}
	/**
	 * Проверка соответствия расширяемого объекта загружаемому расширению
	 * @param object атрибут расширяемого объекта
	 */
	public abstract boolean canHandle(Object object);
	/**
	 * Проверка соответствия параметров объекта конкретному расширению
	 * @param true - модель является расширением исходной модели
	 */
	public boolean isAvailable() {
		return false;
	}
	/**
	 * Сохранение данных расширения
	 * @param extended расширяемая модель
	 */
	public void save(Model extended) {}
	/**
	 * Возвращает имя расширения
	 * @return имя расширения
	 */
	public String getExtensionName() {
		return "";
	}
	/**
	 * Удаление расширения
	 */
	public void deleteExtension() {
		if (extension == null || ((Model)extension).getId() == null)
			initExtension();
		IModelService service = getExtensionService();
		if (service != null)
			try {
				service.delete(((Model)extension).getId());
			} catch (DataAccessException e) {
				e.printStackTrace();
			}
	}
	/**
	 * Возвращает расширение
	 * @return модель расширения
	 */
	public Model getExtension() {
		return extension;
	}

	/**
	 * Возвращает расширяемый объект 
	 * @return расширяемая модель
	 */
	public Model getExtended() {
		return extended;
	}
	/**
	 * Возвращает сервис расширения
	 * @return сервис расширения
	 */
	public abstract IModelService getExtensionService();
	
	/**
	 * Композит расширения 
	 */
	protected ModelComposite composite = null;
	/**
	 * Модель расширения
	 */
	protected Model model = null;

	public Model getModel() {
		if (composite != null) {
			composite.setModel(model, false);
			try {
				model = (Dictionary)composite.getModel(0, false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return model;
	}

	/**
	 * Инициализация представления расширяемого объекта
	 * @param view представление модели
	 */
	public void initExtensionView(ModelView view) {
		this.view = view;
	}

	/**
	 * Инициализация композитов расширений
	 * @param parent композит-контейнер представления
	 */
	public void initComposites(Composite parent) {
		if (model == null) return;
		if (initExtensionComposite() != null) {}
		else {
			if (composite == null || 
					composite.getGroup() == null || composite.getGroup().isDisposed()) {
				composite = initExtensionComposite();
				if (composite != null)
					composite.create(parent);
			}
		}
		if (composite != null) {
			composite.setModel(model, true);
			composite.setStateListener(stateListener);
		}
	}

	public List<Model> getModelList() throws DataAccessException {
		return getExtensionService().getList();
	}
	/**
	 * Инициализация композита расширения
	 * @return композит расширения
	 */
	public ModelComposite initExtensionComposite() {
		return null;
	}
	/**
	 * Проверка заполненности и корректности расширения
	 * @return true - необходимые поля заполнены верно
	 */
	public boolean check() {
		try {
			return composite != null ? composite.check(0) : true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * Инициализация новой модели
	 * @return модель
	 */
	public abstract Model create();
	/**
	 * Поиск колонок таблицы расширения
	 * @return массив колонок таблицы
	 */
	public abstract String[] getTableColumns();
	/**
	 * Возвращает обработчик отображения столбцов таблицы
	 * @return
	 */
	public abstract IBaseLabelProvider getLabelProvider();
}
