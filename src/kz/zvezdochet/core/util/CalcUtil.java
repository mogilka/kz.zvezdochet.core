package kz.zvezdochet.core.util;

import java.text.DecimalFormat;
import java.util.Arrays;

import swisseph.SweDate;

/**
 * Набор методов для вычислений
 * @author Natalie Didenko
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
	public static double incrementCoord(double coord, double age, boolean increment) {
		double res;
		if (increment) {
			double val = coord + age;
			res = (val > 360) ? val - 360 : val;
		} else
			res = (coord > age) ? res = coord - age : coord + 360 - age;
		return res;
	}

	/**
	 * Расчет конфигурации гороскопа
	 * @param args массив данных
	 * java -jar /var/www/tablo.moe/backend/Stargazer.jar calc 2017-11-02 01:45:00 6 37.22 -5.58 zvezdochet.guru
	 */
  	public static double getTdjut(int iyear, int imonth, int iday, int ihour, int imin, int isec, double dlat, double dlon, double dzone) {
		try {
	  		//обрабатываем координаты места
	  		if (0 == dlat && 0 == dlon)
	  			dlat = 51.48; //по умолчанию Гринвич

	  		//обрабатываем время
	  	  	double timing = (double)ihour; //час по местному времени
	  		if (dzone < 0) {
	  			if (timing < (24 + dzone))
	  				timing -= dzone;
	  			else {
	  				/*
	  				 * Если час больше разности 24 часов и зоны, значит по Гринвичу будет следующий день,
	  				 * поэтому нужно увеличить указанную дату на 1 день
	  				 */
	  				timing = timing - dzone - 24;
	  				if (iday < 28)
	  					++iday;
	  				else if (31 == iday) {
	  					iday = 1;  							
	  					if (12 == imonth) {
	  	  					++iyear;
	  	  					imonth = 1;
	  					} else
	  						++imonth;
	  				} else if (30 == iday) {
	  					if (Arrays.asList(new Integer[] {4,6,9,11}).contains(imonth)) {
	  						++imonth;
	  						iday = 1;
	  					} else
	  						iday = 31;
	  				} else if (2 == imonth) {
	  					if (29 == iday) {
	  	  					imonth = 3;
	  	  					iday = 1;
	  					} else if (28 == iday) {
	  						if (DateUtil.isLeapYear(iyear))
	  							iday = 29;
	  						else {
	  							imonth = 3;
	  							iday = 1;
	  						}
	  					}
	  				} else //28 и 29 числа месяцев кроме февраля
	  					++iday;
	  			}
	  		} else {
	  			if (timing >= dzone)
	  				timing -= dzone;
	  			else {
	  				/*
	  				 * Если час меньше зоны, значит по Гринвичу будет предыдущий день,
	  				 * поэтому нужно уменьшить указанную дату на 1 день
	  				 */
	  				timing = timing + 24 - dzone;
	  				if (iday > 1)
	  					--iday;
	  				else {
	  					if (1 == imonth) {
	  						--iyear;
	  						imonth = 12;
	  						iday = 31;
	  					} else if (3 == imonth) {
	  						imonth = 2;
	  						iday = DateUtil.isLeapYear(iyear) ? 29 : 28;
	  					} else if (Arrays.asList(new Integer[] {2,4,6,8,9,11}).contains(imonth)) {
	  						--imonth;
	  						iday = 31;
	  					} else if (Arrays.asList(new Integer[] {5,7,10,12}).contains(imonth)) {
	  						--imonth;
	  						iday = 30;
	  					}
	  				}
	  			}
	  		}
	  		if (timing >= 24)
	  			timing -= 24;
	  		ihour = (int)timing; //гринвичский час

	  		//обрабатываем дату
			double tjd, tjdut, dhour;
	  		dhour = ihour + imin / 60.0 + isec / 3600.0;
	  		tjd = SweDate.getJulDay(iyear, imonth, iday, dhour);
	  		//Universal Time
	  		tjdut = tjd;
	  		return tjdut;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
  	}

	/**
	 * Сравнение значений координат двух точек
	 * @param one координата первой точки
	 * @param two координата второй точки
	 * @param diff угол между точками
	 * @return true - первая точка больше или равна второй
	 */
	public static boolean compareAngles(double one, double two, double diff) {
		int compared = Double.compare(one, two);
		if (0 == compared)
			return true;

		if (compared > 0) {
			if (diff < 1)
				return false;

			if (diff >= 180)
				return false;
			else
				return true;
		} else {
			double res = two - one;
			if (res >= 189)
				return true;
		}
		return false;
	}
}
