package kz.zvezdochet.core.ui.extension;

import kz.zvezdochet.core.bean.Base;
import kz.zvezdochet.core.ui.listener.IEditorElementListener;
import kz.zvezdochet.core.ui.view.ElementListView;
import kz.zvezdochet.core.ui.view.ElementView;
import kz.zvezdochet.core.ui.view.View;

/**
 * Абстрактный расширитель для работы со списком 
 * элементов разных типов
 * @author Nataly
 * 
 * @see ElementListExtPoint Прототип расширителя списка
 */
public abstract class ElementListProvider extends ElementListExtPoint {
	public ElementListProvider() {}

	/**
	 * Метод, определяющий редактор для указанного объекта.
	 * Здесь инициализируется переменная elementViewId
	 */
	protected void setObjectView(Object element) {}
	
	public void addElement(View view, IEditorElementListener listener) {
		try {
			Base element = (Base)((ElementListView)view).addElement();
			if (element != null) {
				setObjectView(element);
//				elementView = view.getSite().getPage().showView(getElementViewId());
				((ElementView)elementView).setListener(listener);
				((ElementView)elementView).setElement(element);
			}
//			updateStatus(view, "Добавление элемента", false);
		} catch (Exception e) {
//			updateStatus(view, "Ошибка инициализации редактора элемента", true);
			e.printStackTrace();
		}
	}
	
	public void editElement(View view, IEditorElementListener listener) {
		try {
			Base element = (Base)((ElementListView)view).getElement();
			if (element != null) {
				setObjectView(element);
//				elementView = view.getSite().getPage().showView(getElementViewId());
				((ElementView)elementView).setListener(listener);
				((ElementView)elementView).setElement(element);
			}
//			updateStatus(view, "Редактирование элемента", false);
		} catch (Exception e) {
//			updateStatus(view, "Ошибка инициализации редактора элемента", true);
			e.printStackTrace();
		}
	}
}
