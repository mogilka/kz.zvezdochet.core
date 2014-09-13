package kz.zvezdochet.core.bean;

import org.eclipse.swt.graphics.Color;

/**
 * Прототип диаграммного толкования
 * @author Nataly Didenko
 */
public abstract class DiagramDictionary extends TextGenderDictionary implements IColorizedObject, IDiagramObject {
	private static final long serialVersionUID = 3257825153209037032L;

	/**
	 * Цвет
	 */
	private Color color;
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Наименование для диаграммы
	 */
	private String diaName;
	
	public String getDiaName() {
		return diaName;
	}

	public void setDiaName(String diaName) {
		this.diaName = diaName;
	}
}
