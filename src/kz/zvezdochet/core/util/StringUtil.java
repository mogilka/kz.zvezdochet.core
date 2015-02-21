package kz.zvezdochet.core.util;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * Утилита для работы со строками
 * @author Nataly Didenko
 */
public class StringUtil {
	
	/**
	 * Преобразование наименования, написанного заглавными буквами,
	 * в строку со строчными буквами и первой заглавной
	 * @param text наименование
	 * @return исходная строка, или пустая строка
	 * @sample АЛМАЛИНСКИЙ >> Алмалинский
	 */
	public static String upperWordToCaps(String text) {
		StringBuffer sb = new StringBuffer(text.substring(1, text.length()).toLowerCase());
		StringBuffer sb2 = new StringBuffer(text.substring(0, 1).toUpperCase());
		return sb2.append(sb).toString();
	}

	/**
	 * Преобразование действительного числа в строку указанного формата
	 * @param pattern формат
	 * @param value значение
	 * @return строка
	 */
    static public String getCustomNumberFormat(String pattern, double value) {
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(value);
    }

	/**
	 * Преобразование натурального числа от 1 до 9 
	 * в формат с ведущим нулем
	 * @param n целое число
	 * @return строка
	 */
    public static String formatDateNumber(int n) {
        return (n < 10) ? "0" + n : "" + n;
    }

	/**
	 * Заключение строки в апострофы
	 * @param s строка
	 * @return строка с апострофами
	 */
    public static String getQuotationString(String s) {
    	String qs = (s != null) ? s : ""; 
        return "'" + qs + "'";
    }

	/**
	 * Заключение строки в проценты и апострофы
	 * для использования в SQL-конструкции LIKE
	 * @param s строка
	 * @return строка с апострофами
	 */
    public static String getLikeString(String s) {
    	String qs = (s != null) ? s : ""; 
        return "'%" + qs + "%'";
    }

	/**
	 * Замена указанных символов в строке
	 * @param text строка
	 * @param oldSymbol символ, подлежащий замене
	 * @param newSymbol символ, которым следует заменить первоначальный символ
	 * @return измененная строка
	 * @sample 28/03/1997 >> 28.03.1997
	 */
	public static String replaceSymbols(String text, char oldSymbol, char newSymbol) {
		StringBuffer sb = new StringBuffer(text);
		for (int i = 0; i < sb.length(); i++)
			if (sb.charAt(i) == oldSymbol)
				sb.setCharAt(i, newSymbol);
		return sb.toString();
	}
	
	/**
	 * Удаляет в строке заданный символ
	 * @param text строка
	 * @param oldSymbol удаляемый символ
	 * @return строка без заданного символа
	 */
	public static String removeSymbols(String text, char oldSymbol) {
		StringBuffer sb = new StringBuffer(text);
		String res = ""; 
		for (int i = 0; i < sb.length(); i++)
			if (sb.charAt(i) != oldSymbol)
				res += sb.charAt(i);
		return res;
	}

    /**
     * Проверка, представляет ли указанная строка числовое значение
     * @param s строка
	 * @return <b>true</b> - строка содержит числовое значение, 
	 * <b>false</b> - строка не является числовым выражением  
     */
    public static boolean isNumber(String s) {
    	if (null == s || s.length() == 0) return false;
    	try {
    		Double.parseDouble(replaceSymbols(s, ',', '.'));
    		return true;
		} catch(NumberFormatException e) {
			return false;
		}
    }

	/**
	 * Преобразование всех слов строки
	 * из элементов, написанных заглавными буквами,
	 * в элементы со строчными буквами и первой заглавной
	 * @param text строка
	 * @return модифицированная строка
	 */
	public static String upperStringToCaps(String text) {
		if (null == text) return null;
		StringBuffer name = new StringBuffer("");
		StringTokenizer st = new StringTokenizer(text, " ");
		while (st.hasMoreTokens()) { 
			name.append(StringUtil.upperWordToCaps(st.nextToken()));
			name.append(" ");
		}
		return name.toString();
	}
	
	/**
	 * Конвертация строки из кодировки Unicode в кириллицу (метод для нубов :) ).
	 * Используется для корректировки русскоязычных текстовых 
	 * параметров, передаваемых в http-запросе
	 * @param value строковое значение
	 * @return преобразованная строка
	 */
    public static String convertUnicodeToCyrillic(String value) {
    	String result = "";
        StringBuffer s = null;
		try {
			s = new StringBuffer(new String(value.getBytes("Unicode")));
			System.out.println("unicode: " + s);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        for (int i = 0; i < value.length(); i++) {
        	if (i > 1 && i % 2 > 0)
        		result = result + s.charAt(i);
        }
        System.out.println("unicode to cyrillic: " + result);
        return result;
    }

    /**
     * Конвертация строки из кодировки ISO в юникод
     * @param isoString строковое значение
     * @return преобразовання строка
     */
    public String convertISOtoUTF(String isoString) {
	    String utf8String = null;
	    if (null != isoString && !isoString.equals("")) {
		    try {
			    byte[] stringBytesISO = isoString.getBytes("ISO-8859-1");
			    utf8String = new String(stringBytesISO, "UTF-8");
		    } catch(UnsupportedEncodingException e) {
			    // As we can't translate just send back the best guess.
			    System.out.println("UnsupportedEncodingException is: " + e.getMessage());
			    utf8String = isoString;
		    }
	    } else
	    	utf8String = isoString;
	    return utf8String;
    }

    /**
     * Проверка, является ли строка датой
     * @param date строковое представление даты
     * @return true - строка является датой
     * 
     * @author Blackfoot http://mycodepage.blogspot.com/
     */
    public static boolean isDate(CharSequence date) {
    	// some regular expression
    	String time = "(\\s(([01]?\\d)|(2[0123]))[:](([012345]\\d)|(60))"
    		+ "[:](([012345]\\d)|(60)))?"; // with a space before, zero or one time

    	// no check for leap years (Schaltjahr)
    	// and 31.02.2006 will also be correct
    	String day = "(([12]\\d)|(3[01])|(0?[1-9]))"; // 01 up to 31
    	String month = "((1[012])|(0\\d))"; // 01 up to 12
    	String year = "\\d{4}";

    	// define here all date format
    	ArrayList<Pattern> patterns = new ArrayList<Pattern>();
    	patterns.add(Pattern.compile(day + "[-.]" + month + "[-.]" + year + time));
    	patterns.add(Pattern.compile(year + "-" + month + "-" + day + time));
    	// here you can add more date formats if you want

    	// check dates
    	for (Pattern p : patterns)
    		if (p.matcher(date).matches())
    			return true;
    	return false;
    }
}
