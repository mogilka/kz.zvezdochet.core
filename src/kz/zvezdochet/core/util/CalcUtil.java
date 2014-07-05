package kz.zvezdochet.core.util;

import java.text.DecimalFormat;

public class CalcUtil {
	
	public static double decToDeg(double dec) {
		//преобразование действительного значения в градусное
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

	public static int trunc(double d) {
		//извлечение из действительного числа целой части
		return (int)d;
	}

	public static double roundTo(double d, int n) {
		//округление действительного числа до указанной точности
		//параметр n указывает на количество цифр после запятой
		//и не должен быть меньше нуля
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
	 * Определение разности между точками на окружности
	 * @param one координата первой точки
	 * @param two координата второй точки
	 * @return расстояние между точками в градусах
	 */
	public static double getDifference(double one, double two) {
		double res = 0.0;
		if (Double.compare(one, two) > 0) {
			res = one - two;
			if (res >= 189) res = 360 - one + two;
		} else {
			res = two - one;
			if (res >= 189) res = 360 - two + one;
		}
		return res;
	}
}