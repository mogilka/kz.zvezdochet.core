package kz.zvezdochet.core.ui.view;

import javax.inject.Inject;

import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Composite;

import kz.zvezdochet.core.bean.Model;
import kz.zvezdochet.core.service.DataAccessException;
import kz.zvezdochet.core.ui.listener.IModelListListener;

/**
 * Прототип редактора модели
 * @author Natalie Didenko
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
		part.setDirty(false);
	}
	
	@Override
	protected void init(Composite parent) {
//		super.init(parent);
		sashForm = new SashForm(parent, SWT.HORIZONTAL);
	}
	
	/**
	 * Управление доступом к функциям модификации данных
	 */
	protected void deactivateUnaccessable() {
//		boolean changed = (model != null) 
//			&& part.isDirty() 
//			&& isEditable();
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
	public void onSave(Model model, boolean update) {
		part.setDirty(false);
		super.onSave(model, update);
	}

	@Override
	protected void initControls() throws DataAccessException {
		sashForm.setWeights(new int[] { 5, 1 });
	}
}
