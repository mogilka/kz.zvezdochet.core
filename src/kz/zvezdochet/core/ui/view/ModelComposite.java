package kz.zvezdochet.core.ui.view;

import java.util.List;

import kz.zvezdochet.core.bean.Model;
import kz.zvezdochet.core.service.DataAccessException;
import kz.zvezdochet.core.ui.extension.IExtensionStateListener;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;

/**
 * Прототип композита, встраиваемого в 
 * представление расширяемого объекта
 * @author Nataly
 */
public abstract class ModelComposite {
	protected Group group;
	protected StateChangedListener stateChangedListener;
	
	/**
	 * Обработчик изменения состояния композита
	 */
	protected IExtensionStateListener stateListener = null;
	
	/**
	 * Элемент расширения
	 */
	protected Object model = null;

	/**
	 * Список расширения для служебных целей
	 */
	protected List<Model> elementList = null;
	
	/**
	 * Слушатель расширения
	 */
	protected Listener listener;
	
	/**
	 * Базовый композит, на который будут 
	 * добавляться элементы управления
	 */
	protected Composite parent;
	
	/**
	 * Переменная, определяющая состояние композита
	 */
	protected boolean isStateChanged = false;
	
	/**
	 * Проверка введенных значений
	 */
	public boolean checkViewValues() {
		return true;
	}
	
	/**
	 * Обработчик событий элементов управления
	 */
	public class StateChangedListener implements ModifyListener, KeyListener,
							ISelectionChangedListener, SelectionListener,
							IDoubleClickListener, MouseListener {
		public void modifyText(ModifyEvent e) {
			notifyChange();
		}
		public void selectionChanged(SelectionChangedEvent event) {
			notifyChange();
		}
		public void widgetDefaultSelected(SelectionEvent e) {}
		public void widgetSelected(SelectionEvent e) {	
			notifyChange();
		}
		public void keyPressed(KeyEvent e) {
			notifyChange();
		}
		public void keyReleased(KeyEvent e) {
			notifyChange();
		}
		public void doubleClick(DoubleClickEvent event) {
			notifyChange();
		}
		public void mouseDoubleClick(org.eclipse.swt.events.MouseEvent e) {
			notifyChange();
		}
		public void mouseDown(org.eclipse.swt.events.MouseEvent e) {
			notifyChange();
		}
		public void mouseUp(org.eclipse.swt.events.MouseEvent e) {
			notifyChange();
		}
	}

	/**
	 * Реакция на изменение состояния элементов управления
	 * после операции сохранения
	 */
	public void notifySave() {
		setIsStateChanged(false);
	}
	
	/**
	 * Реакция на изменение состояния элементов управления
	 */
	public void notifyChange() {
		setIsStateChanged(true);
	}

	/**
	 * Инициализация слушателя расширения
	 */
	public void setListener(Listener listener) {
		this.listener = listener;
	}
	
	/**
	 * Метод, возвращающий слушателя расширения
	 */
	public Listener getListener() {
		return listener;
	}
	
	/**
	 * Инициализация элемента представления
	 */
	public void setModel(Object model) {		
		setModel(model, true);
	}
	
	public void setModel(Object model, boolean refresh) {		
		if (model == null) return;
		if (refresh) {
			this.model = model;
			syncView();
		} else if (this.model != null ){
			((Model)this.model).setId(
				((Model)model).getId()	
			);
		}
		setIsStateChanged(false);
	}
	
	/**
	 * Метод, возвращающий элемент представления
	 */
	public Object getModel() {
		syncModel();
		return model;
	}
	
	/**
	 * Синхронизация модели с представлением
	 */
	protected abstract void syncView();
	
	/**
	 * Синхронизация представления с моделью
	 */
	public abstract void syncModel();
	
	/**
	 * Метод, определяющий, изменилось ли состояние композита
	 * @return <true> - если состояние изменилось,
	 * <false> - если не изменилось
	 */
	public boolean isStateChanged() {
		return isStateChanged;
	}
	
	/**
	 * Метод, отмечающий, что состояние композита изменилось
	 */
	public void setIsStateChanged(boolean isChange) {
		if (isCodeEdit() || (isChange == isStateChanged)) return;
		isStateChanged = isChange;
		if (listener != null)
			listener.handleEvent(null);
	}

	/**
	 * Переменная, определяющая режим редактирования
	 */
	protected boolean codeEdit = false;
	
	public void setCodeEdit(boolean codeEdit) {
		this.codeEdit = codeEdit;
	}

	public boolean isCodeEdit() {
		return codeEdit;
	}
	
	public Composite create(Composite composite) {
		GridDataFactory.fillDefaults().grab(true, false).applyTo(group);
		GridLayoutFactory.swtDefaults().numColumns(1).applyTo(group);
		return null;
	}

	/**
	 * Обновление визуального представления композита
	 */
	public void refresh() {
		syncView();
	}

	/**
	 * Удаление композита
	 */
	public void dispose() {
		if (group != null)
			group.dispose();
	}

	/**
	 * Признак того, что элементы представления
	 * могут редактироваться
	 */
	protected boolean editable = true;
	
	/**
	 * Проверка, является ли представление редактируемым
	 * @return <true> - представление доступно для редактирования,
	 * <false> - представление доступно только для чтения
	 */
	public boolean isEditable() {
		return editable;
	}

	public Group getGroup() {
		return group;
	}

	/**
	 * Очистка значений элементов управления
	 */
	public void clear() {}

	/**
	 * Сохранение элемента композита
	 * @param extended объект, расширяемый элементом композита
	 */
	public void saveModel(Object extended) throws DataAccessException {}
	
	/**
	 * Инициализация обрабочика изменения состояния композита
	 * @param stateListener слушатель
	 */
	public void setStateListener(IExtensionStateListener stateListener) {
		this.stateListener = stateListener;
	}
}
