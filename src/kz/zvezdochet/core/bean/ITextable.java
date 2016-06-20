package kz.zvezdochet.core.bean;

/**
 * Интерфейс объекта, имеющего толкования
 * @author Nataly Didenko
 *
 */
public interface ITextable {
	/**
	 * Метод, возвращающий цвет объекта	
	 * @return цвет
	 */
	public String getText();
	/**
	 * Инициализация цвета объекта
	 * @param color цвет
	 */
	public void setText(String text);
}
