package kz.zvezdochet.core.ui.view;

import javax.inject.Inject;

import kz.zvezdochet.core.bean.Model;
import kz.zvezdochet.core.ui.listener.IModelListListener;

import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.swt.widgets.Composite;

/**
 * Прототип редактора модели
 * @author Nataly Didenko
 */
public abstract class ModelPart extends ModelView {
	
	@Override
	public void notifyChange() {
		part.setDirty(true);
		super.notifyChange();
	}

	@Override
	public void setModel(Model model, boolean sync) {
		super.setModel(model, sync);
		deactivateUnaccessable();
	}
	
	/**
	 * Создание модели
	 * @return модель
	 */
	public abstract Model addModel();
	
	/**
	 * Создание представления. 
	 * Реализовано по шаблону Template method
	 * @param parent Базовый композит представления
	 */
	@Override
	public View create(Composite parent) {
		super.create(parent);
//		this.viewTitle = this.getTitle();
		decorate();
		init(parent);
		deactivateUnaccessable();
		return null;
	}
	
	/**
	 * Управление доступом к функциям модификации данных
	 */
	protected void deactivateUnaccessable() {
		boolean changed = (model != null) 
			&& part.isDirty() 
			&& isEditable();
//		if (applyAction != null)
//			applyAction.setEnabled(changed);
	}

	/**
	 * Методы, реагирующие на модификацию элементов подчиненного списка представления.
	 * Если дочерний элемент или список дочерних элементов был изменен,
	 * обновляем родительский объект и меняем состояние представление на "изменен" 
	 * @see IModelListListener
	 */
	public void onCreate(Object object) {
		notifyChange();
	}
	public void onDelete(Object object) {
		notifyChange();
	}
	public void onChange(Object element) {
		notifyChange();
	}

	@Inject
	protected MPart part;
	  
	@Override
	public void onSave(Model model) {
		part.setDirty(false);
		super.onSave(model);
	}
}
