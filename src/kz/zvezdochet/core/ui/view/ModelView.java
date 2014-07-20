package kz.zvezdochet.core.ui.view;

import javax.inject.Inject;

import kz.zvezdochet.core.bean.Model;
import kz.zvezdochet.core.ui.listener.IElementListListener;
import kz.zvezdochet.core.ui.listener.ISaveListener;

import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;

/**
 * Прототип представления-редактора объекта
 * @see View Прототип представления
 * 
 * @author Nataly Didenko
 */
public abstract class ModelView extends View implements ISaveListener {
	/**
	 * Элемент представления (объект-домен)
	 */
	protected Model model;

	/**
	 * Проверка введенных значений
	 * @param mode режим проверки элемента
	 * @return <true> - поля заполнены корректно
	 */
	public boolean check(int mode) throws Exception {	
		return false;
	}
	
	/**
	 * Обработчик событий представления
	 */
	public class StateChangedListener implements ModifyListener, 
			ISelectionChangedListener, SelectionListener, 
			KeyListener, MouseListener {
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
		public void keyPressed(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {
			notifyChange();
		}
		public void mouseDoubleClick(MouseEvent e) {}
		public void mouseDown(MouseEvent e) {
			notifyChange();
		}
		public void mouseUp(MouseEvent e) {
			notifyChange();
		}
	}

	/**
	 * Оповещение о том, что данные представления изменены
	 * Активируем кнопку сохранения и фиксируем 
	 * состояние представления как измененное,
	 * если оно изменено пользователем, а не программно
	 */
	public void notifyChange() {
		part.setDirty(true);
//		if (!isCodeEdit())
//			deactivateUnaccessable();
	}

	/**
	 * Инициализация модели представления
	 * @param model модель
	 * @param sync признак, требуется ли предварительная синхронизация модели с представлением
	 */
	public void setModel(Model model, boolean sync) {		
		if (null == model) return;
		if (sync) {
			this.model = model;
			syncView();
		} else if (this.model != null && model.getId() != null)
			((Model)this.model).setId(model.getId());
		deactivateUnaccessable();
	}
	
	/**
	 * Возвращает модель представления
	 * @param mode режим запроса модели
	 * @param sync признак, требуется ли предварительная синхронизация модели с представлением
	 */
	public Model getModel(int mode, boolean sync) throws Exception {
		if (sync)
			syncModel(mode);
		return model;
	}
	
	/**
	 * Создание элемента представления.
	 * Наследники должны переопределить данный метод, 
	 * чтобы он возвращал новый экземпляр конкретного класса
	 * @return созданный объект-домен
	 * */
	public Object addModel() {
		return model;
	}
	
	/**
	 * Создание представления. 
	 * Реализовано по шаблону Template method
	 * @param parent Базовый композит представления
	 */
	@Override
	public void create(Composite parent) {
		super.create(parent);
//		this.viewTitle = this.getTitle();
		decorate();
		init(parent);
		initControls();
		deactivateUnaccessable();
	}
	
	/**
	 * Синхронизация представления с моделью
	 */
	protected abstract void syncView();
	
	/**
	 * Синхронизация модели с представлением
	 * @param mode режим синхронизации элемента
	 */
	protected abstract void syncModel(int mode) throws Exception;
	
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
	 * Инициализация элементов управления
	 */
	protected void initControls() {}
	
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

	/**
	 * Блокировка визуальных элементов управления
	 * @param editable <true> - представление доступно для редактирования,
	 * <false> - представление доступно только для чтения
	 */
	public void setEditable(boolean editable) {
		this.editable = editable;
		deactivateUnaccessable(editable);
		lockControls(editable);
	}

	/**
	 * Деактивация ненужных действий
	 * @param lockType вид блокировки (как правило, должен быть равен <false>)
	 */
	protected void deactivateUnaccessable(boolean lockType) {
//		if (applyAction != null)
//			applyAction.setEnabled(lockType);
	}

	/**
	 * Блокировка редактируемых полей
	 * @param lockType признак установки/снятия блокировки
	 */
	protected void lockControls(boolean lockType) {}

	/**
	 * Отображение прогресса выполняемой операции
	 * @param busy признак блокировки графического интерфейса представления
	 */
    public void showBusy(boolean busy) {
		final boolean uibusy = busy;
		new Thread() {
			public void run() {
				PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
					public void run() {
						if (uibusy)
							updateStatus(Messages.getString("ElementView.DataInitializing"), false); //$NON-NLS-1$
						else
							updateStatus(Messages.getString("ElementView.DataLoaded"), false); //$NON-NLS-1$
					}
				});
			}
		}.start();				
    }
    
	/**
	 * Вывод сообщения в строке состояния
	 * @param msg сообщение
	 * @param isError <true> - выводить красным цветом
	 */
	protected void updateStatus(String msg, boolean isError) {
//		StatusUtil.writeToStatusLine(msg, isError, getViewSite().getActionBars());
	}

	/**
	 * Методы, реагирующие на модификацию элементов подчиненного списка представления.
	 * Если дочерний элемент или список дочерних элементов был изменен,
	 * обновляем родительский объект и меняем состояние представление на "изменен" 
	 * @see IElementListListener
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

	/**
	 * Обработчик изменения состояния представления
	 */
//	protected IExtensionStateListener stateListener = null;

	/**
	 * Инициализация обработчика изменения состояния представления
	 */
//	public void setStateListener(IExtensionStateListener stateListener) {
//		this.stateListener = stateListener;
//	}

	/**
	 * Метод, возвращающий обработчика изменения состояния представления
	 */
//	public IExtensionStateListener getStateListener() {
//		return stateListener;
//	}
	
	/**
	 * Декорирование визуальных компонентов
	 * в соответствии с требованиями предметной области
	 */
	protected void decorate() {}

	@Inject
	protected MPart part;
	  
	@Override
	public void onSave(Model model) {
		part.setDirty(false);
		setModel(model, false);
//		deactivateUnaccessable();
	}
	@Override
	public void onCancel(Model model) {
	}
}
