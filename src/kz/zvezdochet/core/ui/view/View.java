package kz.zvezdochet.core.ui.view;

import org.eclipse.swt.widgets.Composite;

/**
 * Прототип представления системы
 * @author Nataly Didenko
 */
public abstract class View {
	
	/**
	 * Признак, определяющий, были ли изменены данные в представлении
	 */
	protected boolean isStateChanged = false;
	
	/**
	 * Признак, определяющий, изменены ли пользователем данные представления
	 */
	protected boolean codeEdit = false;
	
	/**
	 * Инициализация элементов управления
	 */
	protected abstract void initializeControls();
	
	public boolean isStateChanged() {
		return isStateChanged;
	}
	
	public void setCodeEdit(boolean codeEdit) {
		this.codeEdit = codeEdit;
	}
	
	public boolean isCodeEdit() {
		return codeEdit;
	}
	
	/**
	 * Изменение заголовка в соответствии с состоянием представления.
	 * Если данные были изменены, добавляем в текст заголовка звездочку
	 */
	public void setIsStateChanged(boolean isChange) {
		if (isCodeEdit() || (isChange == this.isStateChanged))
			return;
		this.isStateChanged = isChange;
//		if (this.isStateChanged)
//			setPartName("*" + viewTitle);
//		else
//			setPartName(viewTitle);
	}
	
	public void setViewTitle(String title) {
//		setPartName(title);
	}
	
	/**
	 * Создание элементов управления
	 * @param parent композит-родитель
	 **/
	public void createComposite(Composite parent) {}
	
	/**
	 * Закрытие представления
	 */
	public void close () {
//		if (canClose())
//			this.getSite().getPage().hideView(this);
	}
	
	/**
	 * Очистка значений элементов управления
	 */
	public void clearView() {}

	/**
	 * Проверка заполненности элементов управления
	 * @return
	 * @throws Exception
	 */
	public boolean checkViewValues() throws Exception {
		return false;
	}
}
