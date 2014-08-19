package kz.zvezdochet.core.util;

import java.util.List;

import kz.zvezdochet.core.bean.Model;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

/**
 * Методы и константы общего назначения
 * @author Nataly Didenko
 */
public class CoreUtil {
	/**
	 * Проверка наличия строки в массиве
	 * @param array строковый массив
	 * @param string строка
	 * @return признак наличия элемента
	 */
	public static boolean isArrayContainsString(String[] array, String string) {
		for (String s : array)
			if (s.equals(string))
				return true;
		return false;
	}

	/**
	 * Объединение двух массивов
	 * @author Sun Forum
	 * @param array1 массив
	 * @param array2 массив
	 * @return объединенный массив
	 */
	public Object[] concatTwoArrays(Object[] array1, Object[] array2) {
		Object[] array = new Object[array1.length + array2.length];
		System.arraycopy(array1, 0, array, 0, array1.length);
		System.arraycopy(array2, 0, array, array1.length, array2.length);
		return array;
	}

	/**
	 * Преобразование списка объектов в двумерный массив
	 * @param query
	 * @param url
	 * @param servlet
	 * @return
	 */
	public Object[] getArrayFromList(List<Model> list) {
		if (null == list) return null;
		Object[] array = null;
//		try {
//			array = new Object[list.size()];
//			for (int i = 0; i < list.size(); i++) {
//				Object[] row = (Object[])list.get(i);
//				for (int j = 0; j < v.size(); j++)
//					a[j] = v.elementAt(j);
//				array[i] = a;
//			}
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
		return array;
	}

	/**
	 * Преобразование значения RGB в цветовой объект
	 * @param rgbString строковое представление RGB,
	 * где значения красного, зеленого и синего цвета
	 * перечислены через запятую (255,255,255)
	 * @return цвет
	 */
	public static Color rgbToColor(String rgbString) {
		if (null == rgbString || 0 == rgbString.length()) return null;
		String[] rgb = rgbString.split(",");
		return new Color(Display.getDefault(), 
			new RGB(Integer.valueOf(rgb[0]), Integer.valueOf(rgb[1]), Integer.valueOf(rgb[2])));
	}

	/**
	 * Преобразование значения RGB в цветовой объект
	 * @param rgbString строковое представление RGB,
	 * где значения красного, зеленого и синего цвета
	 * перечислены через запятую (255,255,255)
	 * @return цвет
	 */
	public static String colorToRGB(Color color) {
		if (null == color) return null;
		return color.getRed() + "," + color.getGreen() + "," + color.getBlue();
	}
	
	/**
	 * Преобразование цветового объекта в 16-ричное значение
	 * @param color цвет
	 * @return 16-ричное представление цвета
	 */
	public static String colorToHex(Color color) {
		if (null == color) return null;
		String hexred = Integer.toHexString(color.getRed());
		String hexgreen = Integer.toHexString(color.getGreen());
		String hexblue = Integer.toHexString(color.getBlue());
		return hexred + hexgreen + hexblue;
	}

	/**
	 * Проверка наличия числа в массиве
	 * @param array массив чисел
	 * @param value целое число
	 * @return признак наличия элемента
	 */
	public static boolean isArrayContainsNumber(int[] array, int value) {
		for (int n : array)
			if (n == value) return true;
		return false;
	}
}
