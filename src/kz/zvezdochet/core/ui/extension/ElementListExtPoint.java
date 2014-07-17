package kz.zvezdochet.core.ui.extension;

import kz.zvezdochet.core.bean.Base;
import kz.zvezdochet.core.ui.listener.IEditorElementListener;
import kz.zvezdochet.core.ui.util.DialogUtil;
import kz.zvezdochet.core.ui.util.GUIutil;
import kz.zvezdochet.core.ui.view.ElementListView;
import kz.zvezdochet.core.ui.view.ElementView;
import kz.zvezdochet.core.ui.view.View;

import org.eclipse.ui.IViewPart;

/**
 * Прототип расширителя списочного представления элементов
 * @author Nataly Didenko
 */
public abstract class ElementListExtPoint {
	/**
	 * Идентификатор представления редактора елементов 
	 */
	protected String elementViewId;
	
	/**
	 * Ссылка на представление-редактор елементов 
	 */
	protected IViewPart elementView;
	
	/**
	 * Добавление нового объекта.
	 * Извлекает из списка создаваемый элемент и
	 * открывает его в редакторе
	 * @param view представление списка
	 * @param listener слушатель, отслеживающий сохранение объекта
	 */
	public void addElement(View view, IEditorElementListener listener) {
		try {
			Base element = (Base)((ElementListView)view).addElement();
			if (element != null) {
//					elementView = view.getSite().getPage().showView(getElementViewId());
					((ElementView)elementView).setListener(listener);
					((ElementView)elementView).setElement(element);
					((ElementView)elementView).setEditable(((ElementView)view).isEditable());
//					((ElementView)elementView).setFocus();
			}
			updateStatus(view, Messages.getString("ElementListExtPoint.AddingElement"), false); //$NON-NLS-1$
		} catch (Exception e) {
			updateStatus(view, Messages.getString("ElementListExtPoint.ErrorElementAdding"), true); //$NON-NLS-1$
			DialogUtil.alertError(e.getMessage());
		}
	}
	
	/**
	 * Редактирование объекта.
	 * Извлекает из списка элемент и открывает его в редакторе
	 * @param view представление списка
	 * @param listener слушатель, отслеживающий сохранение объекта
	 */
	public void editElement(View view, IEditorElementListener listener) {
		try {
			Base element = (Base)((ElementListView)view).getElement();
			if (element != null) {
//				elementView = view.getSite().getPage().showView(getElementViewId());
				((ElementView)elementView).setListener(listener);
				((ElementView)elementView).setElement(element);
			}
			updateStatus(view, Messages.getString("ElementListExtPoint.EditingElement"), false); //$NON-NLS-1$
		} catch (Exception e) {
			updateStatus(view, Messages.getString("ElementListExtPoint.ErrorEditorInitializing"), true); //$NON-NLS-1$
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
	public void deleteElement(View view, IEditorElementListener listener) {
		Base entity = (Base)((ElementListView)view).getElement();
		if (DialogUtil.alertConfirm(GUIutil.DO_YOU_REALLY_WANNA_DELETE_ENTRY)) {
//			if (entity.getChildren() != null && entity.getChildren().size() > 0) {
//				DialogUtil.alertWarning(GUIutil.DELETING_OBJECT_HAS_CHILDREN);
//				return;
//			} TODO написать свой обработчик экстремальных удалений
//			((ElementListView)view).getService().deleteElement(entity); TODO свой метод
			updateStatus(view, Messages.getString("ElementListExtPoint.ElementDeleted"), false); //$NON-NLS-1$
			listener.onSave(entity);
//			elementView = view.getSite().getPage().findView(getElementViewId()); 
			if (elementView != null) 
				((View)elementView).clear();
		} else {
			updateStatus(view, Messages.getString("ElementListExtPoint.CancelDeletingElement"), false); //$NON-NLS-1$
			listener.onCancelSave(((ElementListView)view).getElement());
		}
	}
	
	/**
	 * Метод, возвращающий идентификатор редактора объекта
	 * @return идентификатор представления
	 */
	protected String getElementViewId () {
		return elementViewId;
	}

	/**
	 * Вывод сообщения в строке состояния
	 * @param msg сообщение
	 * @param isError <true> - выводить красным цветом
	 */
	protected void updateStatus(View view, String msg, boolean isError) {
//		StatusUtil.writeToStatusLine(msg, isError, view.getViewSite().getActionBars());
	}
}
