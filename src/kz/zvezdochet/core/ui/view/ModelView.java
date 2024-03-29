package kz.zvezdochet.core.ui.view;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
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

import kz.zvezdochet.core.bean.Model;
import kz.zvezdochet.core.ui.extension.IExtensionStateListener;
import kz.zvezdochet.core.ui.extension.ModelExtension;
import kz.zvezdochet.core.ui.listener.ISaveListener;

/**
 * Прототип представления модели
 * @author Natalie Didenko
 */
public abstract class ModelView extends View implements ISaveListener {
	/**
	 * Модель
	 */
	protected Model model;

	protected StateChangedListener stateChangedListener;
	/**
	 * Связанное представление списка
	 */
	protected ModelListView listView;

	public ModelListView getListView() {
		return listView;
	}

	public void setListView(ModelListView listView) {
		this.listView = listView;
	}

	/**
	 * Обработчик событий представления
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
		@Override
		public void doubleClick(DoubleClickEvent event) {
			notifyChange();
		}
	}

	/**
	 * Оповещаем, что данные представления изменены
	 */
	public void notifyChange() {
//		if (!isCodeEdit())
//			deactivateUnaccessable();
	}

	/**
	 * Инициализация модели представления
	 * @param model модель
	 * @param sync признак, требуется ли предварительная синхронизация модели с представлением
	 */
	public void setModel(Model model, boolean sync) {		
		this.model = model;
		if (null == model) {
			reset();
			return;
		}
		if (sync)
			model.init(true);
		else if (this.model != null && model.getId() != null)
			((Model)this.model).setId(model.getId());
		syncView();
	}

	@Override
	public Model getModel(int mode, boolean sync) throws Exception {
		if (sync)
			syncModel(mode);
		return model;
	}
	
	/**
	 * Синхронизация представления с моделью
	 */
	protected abstract void syncView();
	
	/**
	 * Синхронизация модели с представлением
	 * @param mode режим синхронизации элемента
	 */
	public abstract void syncModel(int mode) throws Exception;
	
	/**
	 * Признак того, что элементы представления
	 * могут редактироваться
	 */
	protected boolean editable = true;
	
	/**
	 * Проверка, является ли представление редактируемым
	 * @return true|false доступно для редактирования|только для чтения
	 */
	public boolean isEditable() {
		return editable;
	}

	/**
	 * Блокировка редактируемых элементов управления
	 * @param editable true|false - редактирование доступно|не доступно
	 */
	public void setEditable(boolean editable) {
		this.editable = editable;
//		deactivateUnaccessable(editable);
	}

	/**
	 * Отображение прогресса выполняемой операции
	 * @param busy признак блокировки графического интерфейса представления
	 */
    public void showBusy(final boolean busy) {
//		new Thread() {
//			public void run() {
//				PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
//					public void run() {
//						if (busy)
//							updateStatus(Messages.getString("ElementView.DataInitializing"), false); //$NON-NLS-1$
//						else
//							updateStatus(Messages.getString("ElementView.DataLoaded"), false); //$NON-NLS-1$
//					}
//				});
//			}
//		}.start();				
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
	 * Обработчик изменения состояния представления
	 */
	private IExtensionStateListener stateListener = null;

	/**
	 * Инициализация обработчика изменения состояния представления
	 */
	public void setStateListener(IExtensionStateListener stateListener) {
		this.stateListener = stateListener;
	}

	/**
	 * Метод, возвращающий обработчика изменения состояния представления
	 */
	public IExtensionStateListener getStateListener() {
		return stateListener;
	}
	

	@Override
	public void onSave(Model model, boolean update) {
		setModel(model, false);
		if (listView != null)
			listView.onUpdate(model, update);
//		deactivateUnaccessable();

		if (extensions != null)
			for (ModelExtension extension : extensions)
				extension.save();
	}

	@Override
	public void onCancel(Model model) {}
}
