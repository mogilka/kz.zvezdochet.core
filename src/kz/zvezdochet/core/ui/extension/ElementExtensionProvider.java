package kz.zvezdochet.core.ui.extension;

import java.util.ArrayList;
import java.util.List;

import kz.zvezdochet.core.bean.Base;
import kz.zvezdochet.core.ui.view.ElementComposite;
import kz.zvezdochet.core.ui.view.ElementView;
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
	protected ElementView view;
	/**
	 * Композит расширения 
	 */
	protected ElementComposite extensionComposite = null;
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
	public void setView(ElementView view) {
		this.view = view;
	}

	@Override
	public ElementView getView() {
		return view;
	}
	
	@Override
	public void checkModelForSave() {
		if (view != null)
			try {
				view.checkViewValues();
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
			ElementView view = null;
			IWorkbenchPage page = PlatformUtil.getActivePage();
			try {
				view = (ElementView)page.findView(extensionViewId);
				if (view == null)
					view = (ElementView)page.showView(extensionViewId, null, IWorkbenchPage.VIEW_CREATE);
			} catch (PartInitException e) {
				e.printStackTrace();
			}	
//			view.setStateListener(stateListener);
			view.setElement((Base)extension);
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
	protected ElementComposite createExtensionComposite() {
		return null;
	}

	@Override
	public ElementComposite getExtensionComposite() {
		return extensionComposite;
	}
}