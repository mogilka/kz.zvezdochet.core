package kz.zvezdochet.core.bean;

/**
 * Интерфейс объекта, отображаемого в диаграмме
 * @author Nataly Didenko
 *
 */
public interface IDiagramObject {
	/**
	 * Метод, возвращающий наименование объекта в диаграмме	
	 * @return наименование
	 */
	public String getDiaName();
	/**
	 * Инициализация наименования объекта для диаграммы
	 * @param diaName наименование
	 */
	public void setDiaName(String diaName);
}
