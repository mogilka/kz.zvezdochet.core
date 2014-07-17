package kz.zvezdochet.core.ui.extension;

import kz.zvezdochet.core.ui.view.View;

/**
 * Прототип расширителя представления 
 * для изменения отображения его элементов
 * с учетом номенклатуры и бизнес-правил предметной области
 * @author nataly
 */
public abstract class ViewDecorationExtPoint {
	/**
	 * Изменение визуальных элементов представления
	 * @param view представление
	 */
	public void visit(View view) {};
}
