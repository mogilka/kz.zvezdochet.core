package kz.zvezdochet.core.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * Утилита для работы с числами
 * @author Natalie Didenko
 */
public class NumberUtil {

	/**
	 * Преобразование объекта в длинное целое
	 * @param object объект
	 * @return число
	 */
	public static Long getLongFromObject(Object object) {
		if (null == object) return null;
		if (object instanceof BigDecimal)
			return new Long(((BigDecimal)object).longValue());
		else if (object instanceof BigInteger)
			return new Long(((BigInteger)object).longValue());
		return null;
	}

	/**
	 * Преобразование объекта в целое число
	 * @param object объект
	 * @return число
	 */
	public static Integer getIntegerFromObject(Object object) {
		if (null == object) return null;
		return new Integer(((BigDecimal)object).intValue());
	}

	/**
	 * Удаление ведущих нулей из значений даты
	 * @param s строка, представляющая число (дату или месяц)
	 * @return откорректированное числовое значение
	 */
	public static String trimLeadZero(String s) {
  		return (s.indexOf('0') == 0) ?
  			String.valueOf(s.charAt(1)) : s;
  	}

	/**
	 * Округление действительного числа до нужного количества цифр после запятой
	 * @param val действительное число
	 * @param pres точность округления
	 * @return округлённое число
	 * @author Luca Vix https://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places#answer-25645952
	 */
	public static double round(double val, int pres) {
		return new BigDecimal(String.valueOf(val)).setScale(pres, RoundingMode.HALF_UP).doubleValue();
	}

	/**
	 * Поиск английского постфикса для количественного значения
	 * @param num число
	 * @return постфикс
	 */
	public static String getPostfix(int num) {
		switch (num) {
			case 1: return "st";
			case 2: return "nd";
			case 3: return "rd";
			case 21: return "st";
			case 22: return "nd";
			case 23: return "rd";
			default: return "th";
		}
	}
}
