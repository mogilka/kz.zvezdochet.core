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
	 */
	public void checkView() {
		if (view != null)
			try {
				view.check(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

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
	 * Удаление композита расширения
	 */
	public void dispose() {
		if (composite != null) {
//			composite.dispose();TODO
			composite = null;
		}
	}

	/**
	 * Инициализация расширяемого объекта
	 * @param extended расширяемая модель
	 */
	public void setModel(Model extended) {
		this.model = extended;
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
	 * Возвращает имя расширения
	 * @return имя расширения
	 */
	public String getName() {
		return "";
	}

	/**
	 * Удаление расширения
	 */
	public void delete() {
//		if (null == extension || null == ((Model)extension).getId())
//			initExtension();
//		IModelService service = getService();
//		if (service != null)
//			try {
//				service.delete(((Model)extension).getId());
//			} catch (DataAccessException e) {
//				e.printStackTrace();
//			}
	}

	/**
	 * Возвращает сервис расширения
	 * @return сервис расширения
	 */
	public abstract IModelService getService();
	
	/**
	 * Композит расширения 
	 */
	protected ModelView composite = null;
	/**
	 * Расширяемая модель
	 */
	protected Model model = null;

	/**
	 * Возвращает расширяемую модель
	 * @return модель
	 */
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
		if (null == model) return;
		if (null == composite || 
				null == ((ModelComposite)composite).getGroup() ||
				((ModelComposite)composite).getGroup().isDisposed()) {
			composite = (ModelView)initComposite(parent);
		}
		if (composite != null) {
			composite.setModel(model, true);
			composite.setStateListener(stateListener);
		}
	}

	public List<Model> getModelList() throws DataAccessException {
		return getService().getList();
	}

	/**
	 * Инициализация композита расширения
	 * @param parent контейнер виджетов
	 * @return композит расширения
	 */
	public View initComposite(Composite parent) {
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
	 * Возвращает обработчик отображения данных таблицы
	 * @return обработчик отображения таблицы
	 */
	public abstract IBaseLabelProvider getLabelProvider();

	/**
	 * Синхронизации модели с представлением
	 * @param mode режим синхронизации
	 * @throws Exception
	 */
	public void syncModel(int mode) throws Exception {
		try {
			if (composite != null)
				composite.syncModel(mode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Возвращает путь к изображению расширения
	 * @return путь к файлу иконки расширения
	 */
	public abstract String getIconURI();
}
