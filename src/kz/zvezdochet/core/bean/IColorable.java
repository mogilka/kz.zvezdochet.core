package kz.zvezdochet.core.bean;

import org.eclipse.swt.graphics.Color;

/**
 * Интерфейс объекта, имеющего цвет
 * @author Natalie Didenko
 *
 */
public interface IColorable {
	/**
	 * Метод, возвращающий цвет объекта	
	 * @return цвет
	 */
	public Color getColor();
	/**
	 * Инициализация цвета объекта
	 * @param color цвет
	 */
	public void setColor(Color color);
}
