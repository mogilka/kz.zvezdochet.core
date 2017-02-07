package kz.zvezdochet.core.ui.extension;

import java.util.List;

import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.swt.widgets.Composite;

import kz.zvezdochet.core.bean.Dictionary;
import kz.zvezdochet.core.bean.Model;
import kz.zvezdochet.core.service.DataAccessException;
import kz.zvezdochet.core.service.IModelService;
import kz.zvezdochet.core.ui.view.ModelComposite;
import kz.zvezdochet.core.ui.view.ModelView;
import kz.zvezdochet.core.ui.view.View;

/**
 * Прототип расширения модели
 * @author Nataly Didenko
 */
public abstract class ModelExtension implements IExtension {
	/**
	 * Представление расширяемого объекта
	 */
	protected ModelView part;
	/**
	 * Обработчик изменений элемента расширения
	 */
	protected IExtensionStateListener stateListener = null;

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
	public void setPart(ModelView view) {
		this.part = view;
	}

	/**
	 * Проверка правильности заполненности полей расширяемого представления
	 */
	public void checkPart() {
		if (part != null)
			try {
				part.check(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

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
//		if (composite != null)
//			changed = composite.isChanged();
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
	
	@Override
	public abstract boolean canHandle(Object object);
	
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

	@Override
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

	/**
	 * Очистка композита расширения
	 */
	public void reset() {
		if (composite != null) 
			composite.reset();
	}
}
