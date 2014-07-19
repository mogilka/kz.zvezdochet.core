package kz.zvezdochet.core.ui.extension;

import java.util.ArrayList;
import java.util.List;

import kz.zvezdochet.core.bean.Model;
import kz.zvezdochet.core.ui.view.ModelComposite;
import kz.zvezdochet.core.ui.view.ModelView;
import kz.zvezdochet.core.ui.view.View;
import kz.zvezdochet.core.util.PlatformUtil;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;

/**
 * Прототип визуального расширителя объекта
 * @author Nataly
 */
public abstract class ElementExtensionProvider extends ExtensionProvider implements IElementExtensionProvider {
	/**
	 * Идентификатор расширяемого представления 
	 */
	protected String extensionViewId;
	/**
	 * Представление, используемое в расширение 
	 */
	protected ModelView view;
	/**
	 * Композит расширения 
	 */
	protected ModelComposite extensionComposite = null;
	/**
	 * Композит расширяемого представления,
	 * который выполняет роль контейнера виджетов расширения
	 */
	private Composite сontainer;
	/**
	 * Обработчик изменений элемента расширения
	 */
	protected IExtensionStateListener stateListener = null;

	@Override
	public String getExtensionViewId() {
		return extensionViewId;
	}

	@Override
	public Composite getContainer() {
		return сontainer;
	}
	
	@Override
	public void setСontainer(Composite сontainer) {
		this.сontainer = сontainer;
	}
	
	@Override
	public void initStateListener(IExtensionStateListener listener) {
		this.stateListener = listener;
	}
		
	@Override
	public void setView(ModelView view) {
		this.view = view;
	}

	@Override
	public ModelView getView() {
		return view;
	}
	
	@Override
	public void checkModelForSave() {
		if (view != null)
			try {
				view.check(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	@Override
	public void addShowingView(View view) {}
	@Override
	public void provideAdditionalControls(Composite parent) {}

	@Override
	public void showAdditionalViews() {
		if (getExtensionViewId() != null) {
			ModelView view = null;
			IWorkbenchPage page = PlatformUtil.getActivePage();
			try {
				view = (ModelView)page.findView(extensionViewId);
				if (view == null)
					view = (ModelView)page.showView(extensionViewId, null, IWorkbenchPage.VIEW_CREATE);
			} catch (PartInitException e) {
				e.printStackTrace();
			}	
//			view.setStateListener(stateListener);
			view.setModel((Model)extension);
			addShowingView(view);	
		}
	}
	
	@Override
	public void close() {
		if (extensionViewId != null) {
			IWorkbenchPage page=PlatformUtil.getActivePage();
			if(page==null) return;
			View view = (View)page.findView(extensionViewId);
			if (view != null)
				view.close();
		}
		disposeComposites();
		extended = null;
	}
	
	@Override
	public boolean isStateChanged() {
		boolean changed = false; 
		if (view != null) 
			changed = view.isStateChanged();
		if (extensionComposite != null) 
			changed = extensionComposite.isStateChanged();
		return changed;
	}
	
	@Override
	public List<Action> getToolbarActions() {
		return new ArrayList<Action>();
	}
	
	@Override
	public void disposeComposites() {
		extension = null;
		if (extensionComposite != null) {
			extensionComposite.dispose();
			extensionComposite = null;
		}
	}

	/**
	 * Инициализация композита расширения.
	 * Данный метод должен быть обязательно переопределен в наследниках
	 * @return композит расширения
	 */
	protected ModelComposite createExtensionComposite() {
		return null;
	}

	@Override
	public ModelComposite getExtensionComposite() {
		return extensionComposite;
	}
}
