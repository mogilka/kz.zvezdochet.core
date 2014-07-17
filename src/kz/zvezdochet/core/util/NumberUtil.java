package kz.zvezdochet.core.util;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Класс, предоставляющий методы для работы с числами
 * @author Nataly Didenko
 */
public class NumberUtil {

	/**
	 * Преобразование объекта в длинное целое
	 * @param object объект
	 * @return число
	 */
	public static Long getLongFromObject(Object object) {
		if (object == null) return null;
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
		if (object == null) return null;
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
}
