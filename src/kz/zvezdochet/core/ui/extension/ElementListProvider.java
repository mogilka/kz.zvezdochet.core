package kz.zvezdochet.core.ui.extension;

import kz.zvezdochet.core.bean.Model;
import kz.zvezdochet.core.ui.listener.IEditorElementListener;
import kz.zvezdochet.core.ui.view.ModelListView;
import kz.zvezdochet.core.ui.view.ModelView;
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
			Model element = (Model)((ModelListView)view).addModel();
			if (element != null) {
				setObjectView(element);
//				elementView = view.getSite().getPage().showView(getElementViewId());
				((ModelView)elementView).setListener(listener);
				((ModelView)elementView).setModel(element);
			}
//			updateStatus(view, "Добавление элемента", false);
		} catch (Exception e) {
//			updateStatus(view, "Ошибка инициализации редактора элемента", true);
			e.printStackTrace();
		}
	}
	
	public void editElement(View view, IEditorElementListener listener) {
		try {
			Model element = (Model)((ModelListView)view).getModel();
			if (element != null) {
				setObjectView(element);
//				elementView = view.getSite().getPage().showView(getElementViewId());
				((ModelView)elementView).setListener(listener);
				((ModelView)elementView).setModel(element);
			}
//			updateStatus(view, "Редактирование элемента", false);
		} catch (Exception e) {
//			updateStatus(view, "Ошибка инициализации редактора элемента", true);
			e.printStackTrace();
		}
	}
}
