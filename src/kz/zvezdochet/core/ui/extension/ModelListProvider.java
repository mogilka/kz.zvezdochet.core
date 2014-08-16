package kz.zvezdochet.core.ui.extension;

import org.eclipse.ui.IViewPart;

import kz.zvezdochet.core.bean.Model;
import kz.zvezdochet.core.handler.Handler;
import kz.zvezdochet.core.ui.listener.ISaveListener;
import kz.zvezdochet.core.ui.util.DialogUtil;
import kz.zvezdochet.core.ui.util.GUIutil;
import kz.zvezdochet.core.ui.view.ModelListView;
import kz.zvezdochet.core.ui.view.ModelView;
import kz.zvezdochet.core.ui.view.View;

/**
 * Прототип расширителя справочника моделей
 * @author Nataly Didenko
 */
public abstract class ModelListProvider {
	/**
	 * Идентификатор представления редактора елементов 
	 */
	protected String modelViewId;
	
	/**
	 * Ссылка на представление-редактор елементов 
	 */
	protected IViewPart modelView;

	/**
	 * Метод, определяющий редактор для указанного объекта.
	 * Здесь инициализируется переменная elementViewId
	 */
	protected void setObjectView(Object element) {}
	
	public void addModel(View view, ISaveListener listener) {
		try {
			Model model = (Model)((ModelListView)view).addModel();
			if (model != null) {
				setObjectView(model);
//				elementView = view.getSite().getPage().showView(getElementViewId());
//				((ModelView)modelView).setListener(listener);
				((ModelView)modelView).setModel(model, true);
			}
//			updateStatus(view, "Добавление элемента", false);
		} catch (Exception e) {
//			updateStatus(view, "Ошибка инициализации редактора элемента", true);
			e.printStackTrace();
		}
	}
	
	public void editModel(View view, ISaveListener listener) {
		try {
			Model model = (Model)((ModelListView)view).getModel();
			if (model != null) {
				setObjectView(model);
//				elementView = view.getSite().getPage().showView(getElementViewId());
//				((ModelView)modelView).setListener(listener);
				((ModelView)modelView).setModel(model, true);
			}
//			updateStatus(view, "Редактирование элемента", false);
		} catch (Exception e) {
//			updateStatus(view, "Ошибка инициализации редактора элемента", true);
			e.printStackTrace();
		}
	}

	/**
	 * Удаление объекта.
	 * Извлекает из списка элемент, вызывает у его сервиса 
	 * метод удаления и закрывает редактор
	 * @param view представление списка
	 * @param listener слушатель, отслеживающий сохранение объекта
	 */
	public void deleteModel(View view, ISaveListener listener) {
		Model model = (Model)((ModelListView)view).getModel();
		if (DialogUtil.alertConfirm(GUIutil.DO_YOU_REALLY_WANNA_DELETE_ENTRY)) {
//			if (entity.getChildren() != null && entity.getChildren().size() > 0) {
//				DialogUtil.alertWarning(GUIutil.DELETING_OBJECT_HAS_CHILDREN);
//				return;
//			} TODO написать свой обработчик экстремальных удалений
//			((ElementListView)view).getService().deleteElement(entity); TODO свой метод
			Handler.updateStatus(Messages.getString("ElementListExtPoint.ElementDeleted"), false); //$NON-NLS-1$
			listener.onSave(model);
//			elementView = view.getSite().getPage().findView(getElementViewId()); 
			if (modelView != null) 
				((View)modelView).reset();
		} else {
			Handler.updateStatus(Messages.getString("ElementListExtPoint.CancelDeletingElement"), false); //$NON-NLS-1$
			listener.onCancel((Model)((ModelListView)view).getModel());
		}
	}

	/**
	 * Метод, возвращающий идентификатор редактора объекта
	 * @return идентификатор представления
	 */
	protected String getElementViewId () {
		return modelViewId;
	}
}
