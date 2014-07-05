package kz.zvezdochet.core.tool;

/**Данный класс отвечает за сравнивание объектов*/
public class Comparator {
	
	/**Статический метод, выполняет сравнение двух объектов. Если оба параметра
	 * равны null или являются одним объектом или метод equals возвращает true,
	 * то метод возвращает true. В противном случае - false.
	 * Пример:
	 * boolean bHomePart = Comparator.isEquals(txtHomePart.getText(),address.getHomePart());
	 * @param Object a первый объект
	 * @param Object b второй объект
	 * */
	public static  boolean isEquals(Object a, Object b) {
		return (a == b) 
			|| ((a != null) && (a.equals(b)));		
	} 

}
