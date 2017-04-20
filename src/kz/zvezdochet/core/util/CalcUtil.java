package kz.zvezdochet.core.util;

import java.text.DecimalFormat;

/**
 * Набор методов для вычислений
 * @author Nataly Didenko
 *
 */
public class CalcUtil {

	/**
	 * Преобразование действительного значения в градусное
	 * @param dec действительное значение
	 * @return действительное значение, в котором
	 * целая часть представляет градусы, а дробная - минуты
	 */
	public static double decToDeg(double dec) {
		double d = trunc(dec);
		return roundToDeg(d, (dec - d) * 0.6);
	}

	public static double degToDec(double deg) {
		//преобразование градусного значения в действительное
		double d = trunc(deg);
		return roundToDec(d + (deg - d) / 0.6);
	}

	private static double roundToDeg(double intpart, double fraction) {
		//округление градусного числа до вида 0.00 (0.00-0.59)
		double n;
		//получаем десятичную часть числа, округленную до двух знаков
		//после запятой, как целое значение:
		int m = (int)Math.round(fraction * 100);
		//если "десятичная часть" больше или равна 60, увеличиваем на единицу
		//целую часть, а от десятичной отнимаем 60 (минут)
		if (m >= 60)
			n = intpart + 1 + (fraction - 0.6);
		else
			n = intpart + fraction;
		//при этом следует учесть, что градус 360 переходит в 1, а не в 361:
		if (Math.abs(n) >= 360) {
			//если градусное значение больше 360 - отнимаем от него 360:
			if (n > 0)
				return roundTo(n - 360, 2);
			//если градусное значение равно 360   ??????
	        else
	        	return roundTo(n + 360, 2);
		} else {
			//если градусное значение меньше 360 - оставляем его как есть:
			return roundTo(n, 2);
		}
	}

	private static double roundToDec(double m) {
		//округление действительного числа до вида 0.00 (0.00-0.99)
		//аналог RoundTo
		
		//получаем десятичную часть числа, округленную до двух знаков
		//после запятой, как целое значение:
		double tm = trunc(m);
		int k = (int)Math.round((m - tm) * 100);
		//складываем целую и десятичную части числа:
		return roundTo(tm + k * 0.01, 2);
	}

	/**
	 * Извлечение целой части из действительного числа
	 * @param d действительное число
	 * @return целая часть
	 */
	public static int trunc(double d) {
		return (int)d;
	}

	/**
	 * Округление действительного числа до указанной точности
	 * @param d число
	 * @param n количество цифр после запятой. Не должно быть меньше нуля
	 * @return огруглённое число
	 */
	public static double roundTo(double d, int n) {
		if (n <= 0) {
			return Math.round(d);
		} else {
			String s = "";
			for (int i = 0; i < n; i++) s += "#";
			return Double.parseDouble(formatNumber("###." + s, d));
		}
	}
	
	public static String formatNumber(String pattern, double value) {
        DecimalFormat df = new DecimalFormat(pattern);
        String s = df.format(value);
        int comma = s.indexOf(",");
        if (comma >= 0) {
        	StringBuffer sb = new StringBuffer(s);
        	sb.replace(comma, comma + 1, ".");
        	return sb.toString();
        } else return s;
    }

	/**
	 * Определение количества градусов между точками на окружности
	 * @param one координата первой точки
	 * @param two координата второй точки
	 * @return расстояние между точками в градусах
	 */
	public static double getDifference(double one, double two) {
		double res = 0.0;
		if (Double.compare(one, two) > 0) {
			res = one - two;
			if (res >= 189)
				res = 360 - one + two;
		} else {
			res = two - one;
			if (res >= 189)
				res = 360 - two + one;
		}
		return res;
	}

	/**
	 * Определение координаты точки окружности при отложении от неё заданного отрезка
	 * @param coord начальная координата точки (положительное число)
	 * @param age угол, который нужно отложить от начальной точки
	 * @return конечная координата точки на окружности с учётом начала отсчёта (нулевого градуса)
	 */
	public static double getAgedCoord(double coord, double age) {
		coord += age;
		if (coord > 360)
			coord -= 360;
		return coord;
	}

	/**
	 * Корректировка координат для определения
	 * местонахождения объекта на участке космограммы
	 * (используется для домов)
	 * @param margin1 начальный градус сектора
	 * @param margin2 конечный градус сектора
	 * @param point координата точки
	 * @return массив модифицированных значений точки и верхней границы сектора
	 */ 
	public static double[] checkMarginalValues(double margin1, double margin2, double point) {
		//если границы сектора находятся по разные стороны нуля
		if (margin1 > 200 & margin2 < 160) {
			//если координата точки находится по другую сторону
			//от нуля относительно второй границы,
			//увеличиваем эту границу на 2*Pi
			if (Math.abs(point) > 200)
				margin2 += 360;
			else if (Math.abs(point) < 160) {
				//если градус планеты меньше 160,
				//увеличиваем его, а также вторую границу на 2*Pi
		       point = Math.abs(point) + 360;
		       margin2 += 360;
			}
		}
		//если же границы находятся по одну сторону от нуля,
		//оставляем координаты как есть
		return new double[] {margin2, point};
	}
}
