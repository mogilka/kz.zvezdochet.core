package kz.zvezdochet.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

/**
 * Утилита для работы с датами
 * @author Nataly Didenko
 */
public class DateUtil {
	
	/**
	 * Проверка диапазона дат
	 * @param from начальная дата
	 * @param to конечная дата
	 */
	public static boolean isDateRangeValid(Date from, Date to) {
		if (null == from || null == to) return true;
		return (from.after(to)) ? false : true;
	}

	/** Формат даты dd.MM.yyyy 
	 * @sample 13.09.2009 */
	public static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy"); //$NON-NLS-1$
	static SimpleDateFormat sdft = new SimpleDateFormat("dd.MM.yyyy HH:mm"); //$NON-NLS-1$
	/** Формат даты и времени dd.MM.yyyy HH:mm:ss 
	 * @sample 13.09.2009 20:24:52 */
	public static DateFormat dtf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"); //$NON-NLS-1$
	/** Формат времени HH:mm:ss 
	 * @sample 20:24:52 */
	public static SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss"); //$NON-NLS-1$
	/** Формат месяца года MMMMM yyyy 
	 * @sample Сентябрь 2009 */
	public static SimpleDateFormat sdfmy = new SimpleDateFormat("MMMMM yyyy"); //$NON-NLS-1$
	/** Формат даты d MMMMM yyyy г. 
	 * @sample 3 Сентябрь 2009 г. */
	public static String fulldf = "d MMMMM yyyy г."; //$NON-NLS-1$
	/** Формат даты yyyy MM 
	 * @sample 2009 03 */
	public static DateFormat dfym = new SimpleDateFormat("yyyy MM"); //$NON-NLS-1$
	/** Формат даты и времени, используемый в базе данных, yyyy-MM-dd HH:mm:ss 
	 * @sample 2010-05-14 14:14:14 */
	public static DateFormat dbdtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //$NON-NLS-1$
	/** Формат даты, используемый в базе данных, yyyy-MM-dd 
	 * @sample 2010-05-14 */
	public static DateFormat dbdf = new SimpleDateFormat("yyyy-MM-dd"); //$NON-NLS-1$
	/** Формат даты и времени EEE, d MMM yyyy HH:mm:ss Z 
	 * @sample воскресенье, 21 Июль 1985 21:18:51 +0600 */
	public static DateFormat fulldtf = new SimpleDateFormat("EEEE, d MMMM yyyy HH:mm:ss");

	/**
	 * Возвращает дату первого дня месяца
	 */
	public static Date getDateOfFirstDayOfMonth(int year, int month) {
		try {
			return dtf.parse("01." + month + "." + year + " 00:00:00"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Приведение даты к обычному формату 01.01.2009
	 * @param date дата
	 * @return отформатированная строка со значением даты
	 */
	public static String formatDate(Date date) {
		return (date != null) ? sdf.format(date) : ""; //$NON-NLS-1$
	}

	/**
	 * Приведение времени к обычному формату 00:00:00
	 * @param date время
	 * @return отформатированная строка со значением времени
	 */
	public static String formatTime(Date date) {
		return (date != null) ? stf.format(date) : ""; //$NON-NLS-1$
	}

	/**
	 * Приведение даты и времени к обычному формату 01.01.2009 00:00:00
	 * @param date дата и время
	 * @return отформатированная строка со значением даты и времени
	 */
	public static String formatDateTime(Date date) {
		return (date != null) ? dtf.format(date) : ""; //$NON-NLS-1$
	}
	
	/**
	 * Приведение даты к указанному формату
	 * @param date дата и время
	 * @param pattern шаблон формата
	 * @return отформатированная строка со значением даты и времени
	 */
	public static String formatCustomDateTime(Date date, String pattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return (date != null) ? simpleDateFormat.format(date) : ""; //$NON-NLS-1$
	}
	
	/**
	 * Метод предназначен для сравнения значений дат, переданных в параметрах
	 * @param date1 первая дата
	 * @param date2 вторая дата
	 * @return
	 * 0 - если даты равны,<br> 
	 * -1 - если вторая дата позже, чем первая<br>
	 * 1 - если вторая дата раньше, чем первая  
	 */
	public static int compareDate(Date date1, Date date2) {
//		assert(date1 != null);
//		assert(date2 != null);
		if (null == date1)
			if (null == date2)
				return 0;
			else
				return -1;
		if (null == date2)
			return 1;
		Calendar calendar1 = new GregorianCalendar();
		calendar1.setTime(date1);
		Calendar calendar2 = new GregorianCalendar();
		calendar2.setTime(date2);
		return calendar1.compareTo(calendar2);	  
	}

	/**
	 * Преобразование строки в значение даты, формат "dd.MM.yyyy". 
	 * Метод вернет нуловое значение, в случае ошибки
	 * @param dateStr - строка
	 * @return дата
	 * @throws ParseException исключение преобразования строки
	 */
	public static Date getDate(String dateStr) {
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	/**
	 * Преобразование строки в значение времени
	 * @param string строка
	 * @return время
	 */
	public static Date getTime(String string) {
		try {
			return stf.parse(string);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Метод, преобразующий число дня/месяца 
	 * в фомат с ведущим нулем, если это необходимо
	 * @param n целое число
	 * @return строка
	 */
    public static String formatDateNumber(int n) {
        return (n < 10) ? "0" + n : "" + n; //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Конвертация даты формата "13.04.2009" в "2009-04-13 00:00:00.000"
     * @param d строковое значение даты
     * @return преобразованное строковое значение
     */
    public static String formatDatabaseDate(String d) {
        String formattedDate =
        	d.substring(6,10) + "-" + //$NON-NLS-1$
            d.substring(3,5) + "-" + //$NON-NLS-1$
            d.substring(0,2);
        return formattedDate + " 00:00:00.000"; //$NON-NLS-1$
    }

    /**
     * Конвертация даты формата "25.12.2008 12:37:55" в "2009-04-13 22:37:55.000" 
     * @param d строковое значение даты
     * @return преобразованное строковое значение
     */
    public static String formatDatabaseDateTime(String d) {
    	if (d.length() < 19) {
    		System.out.println("Date length less than 19\t" + d);
    		return null;
    	}
        String formattedDate =
        	d.substring(6,10) + "-" + //$NON-NLS-1$
            d.substring(3,5) + "-" + //$NON-NLS-1$
            d.substring(0,2) + " " + //$NON-NLS-1$
            d.substring(11,19);
        return formattedDate + ".000"; //$NON-NLS-1$
    }
    
	/**
	 * Конвертация длинного целого представления даты в строку
	 * @param longTime длинное целое
	 * @param строковое значение даты
	 */
	public static String getDateStringFromLong(Long longTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(longTime);
		return String.valueOf(calendar.getTime());
	}

	/**
	 * Конвертация даты в длинное целое
	 * @param date дата
	 * @return целое число
	 */
	public static Long getLongFromDate(Date date) {
		return date.getTime();
	}

	/**
	 * Метод, возвращающий начальную и конечную дату месяца указанной даты
	 * @param date дата
	 * @return массив, содержащий:<br>
	 * - начальную дату месяца<br>
	 * - начальную дату следующего месяца
	 */
	public static Date[] getMonthPeriodByDate(Date date) {
		if (null == date) return null;
		
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int monthFrom = calendar.get(Calendar.MONTH);
		int yearFrom = calendar.get(Calendar.YEAR);
		int monthTo, yearTo;
		if (monthFrom == 11) {
			monthTo = 0;
			yearTo = calendar.get(Calendar.YEAR) + 1;
		} else {
			monthTo = calendar.get(Calendar.MONTH) + 1;
			yearTo = calendar.get(Calendar.YEAR);
		}
		calendar.set(yearFrom, monthFrom, 1);
		Date from = calendar.getTime();
		calendar.set(yearTo, monthTo, 1);
		Date to = calendar.getTime();
		return new Date[] {from, to};
	}

	/**
	 * Проверка вхождения даты в указанный диапазон
	 * @param date дата
	 * @param from начальная дата
	 * @param to конечная дата
	 * @return <b>true</b> - если период включает дату,
	 * <b>false</b> - если не включает 
	 */
	public static boolean isInPeriod(Date date, Date from, Date to) {
		/*
		 * добавляем секунду к проверяемой дате,
		 * потому что если прошедшая или будущая дата равны проверяемой,
		 * нет полной уверенности, что Java расценит их как идентичные.
		 * На практике выявлено, что миллисекунды могут произвольно
		 * добавляться в диапазонные значения
		 */ 
		Date dateTime = new Date(date.getTime() + 1000); 
		return (dateTime.getTime() >= from.getTime() && dateTime.getTime() <= to.getTime());
	}
	/**
	 * Возвращает время конца месяца
	 */
	public static Date getDateOfLastDayOfMonth(int year, int month) {
		int yearNext = year;
		int monthNext = month + 1;
		if (monthNext > 12) {
			monthNext = 1;
			yearNext += 1;
		}
		Date date = getDateOfFirstDayOfMonth(yearNext, monthNext);
		return new Date(date.getTime() - 1000);
	}
	/**
	 * Возвращает признак пересечения двух периодов
	 * @param rang1FirstDay
	 * @param rang1LastDay
	 * @param rang2FirstDay
	 * @param rang2LastDay
	 * @return
	 */
	public boolean checkRangesConnected(Date rang1FirstDay, Date rang1LastDay,
			Date rang2FirstDay, Date rang2LastDay) {
		if (null == rang1FirstDay || null == rang1LastDay || null == rang2FirstDay)
			return false;
		
		return (null == rang2LastDay || (rang1FirstDay.before(rang2LastDay)) 
				&& rang1LastDay.after(rang2FirstDay)) ;
	}
	
	/**
	 * Возвращает количество дней по числу миллисекунд
	 * @param l
	 * @return
	 */
	public Long getDayCount(long l) {
		double d = l /1000.0 / 60.0 / 60.0 / 24.0;
		double c = Math.ceil(d);
		long r = Math.round(c);
		return r;
	}
	/**Возвращает название месяца по его номеру (1 - Январь, ...)*/
	public static String getMonthName(int month) {
		switch (month) {
		case 1:
			return Messages.getString("DateUtil.JUN"); //$NON-NLS-1$
		case 2:
			return Messages.getString("DateUtil.FEB"); //$NON-NLS-1$
		case 3:
			return Messages.getString("DateUtil.MAR"); //$NON-NLS-1$
		case 4:
			return Messages.getString("DateUtil.APR"); //$NON-NLS-1$
		case 5:
			return Messages.getString("DateUtil.MAY"); //$NON-NLS-1$
		case 6:
			return Messages.getString("DateUtil.JUNE"); //$NON-NLS-1$
		case 7:
			return Messages.getString("DateUtil.JUL"); //$NON-NLS-1$
		case 8:
			return Messages.getString("DateUtil.AVG"); //$NON-NLS-1$
		case 9:
			return Messages.getString("DateUtil.SEP"); //$NON-NLS-1$
		case 10:
			return Messages.getString("DateUtil.OCT"); //$NON-NLS-1$
		case 11:
			return Messages.getString("DateUtil.NOV"); //$NON-NLS-1$
		case 12:
			return Messages.getString("DateUtil.DEC"); //$NON-NLS-1$
		default:
			return null;
		}
	}

	/**
	 * Определение начала дня, т.е. даты с нулевым временем
	 * @param date дата
	 * @return дата без времени
	 */
	public static Date getBeginOfDate(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	/**
	 * Определение конца дня, т.е. даты с временем 23:59
	 * @param date дата
	 * @return дата без последней минуты
	 */
	public static Date getEndOfDate(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}
	
	/**
	 * Метод возвращает год указанной даты
	 * @param date - дата
	 * @return int
	 */
	public static int getYearFromDate(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * Метод возвращает месяц указанной даты (начиная с 0)
	 * @param date - дата
	 * @return int
	 */
	public static int getMonthFromDate(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH);
	}

	/**
	 * Метод возвращает день указанной даты
	 * @param date - дата
	 * @return int
	 */
	public static int getDateFromDate(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return calendar.get(Calendar.DATE);
	}

	/**
	 * Преобразование строки в значение даты, формат "dd.MM.yyyy HH:mm:ss".
	 * Метод вернет нуловое значение, в случае ошибки
	 * @param dateStr - строковое представление даты
	 * @return Date
	 * @throws ParseException исключение преобразования строки
	 */
	public static Date getDateTime(String dateStr) {
		try {
			return dtf.parse(dateStr);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	/**
	 * Преобразование строки в значение даты, формат "yyyy-MM-dd HH:mm:ss".
	 * @param dateStr строковое представление даты
	 * @return Date дата
	 * @throws ParseException исключение преобразования строки
	 */
	public static Date getDatabaseDateTime(String dateStr) {
		try {
			return dbdtf.parse(dateStr);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	/**
	 * Метод, возвращающий номер годового квартала по дате
	 * @param date - конец периода
	 * @return - квартал
	 */
	public static int getQuarterByDate(Date date) {
		int quart = 0;
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH);
		if (month >= 0 && month <= 2) quart = 1;
		if (month >= 3 && month <= 5) quart = 2;
		if (month >= 6 && month <= 8) quart = 3;
		if (month >= 9 && month <= 11) quart = 4;
		return quart;
	}

	 
	/**
	 * Вычисление периода между двумя моментами времени
	 * @param from начальный момент времени
	 * @param to конечный момент времени
	 * @return строковое представление времени вида HH:mm:ss
	 */
    public static String getTimePeriodAsString(Date from, Date to) {
//    	System.out.println(from);
//    	System.out.println(to);
        long difference = to.getTime() - from.getTime();
        long seconds = difference / 1000;
        long hours = seconds / 3600;
        seconds = seconds - (hours * 3600);
        long minutes = seconds / 60;
        seconds = seconds - (minutes * 60);
//        System.out.println("hours = " + hours + ", minutes = " + minutes + ", seconds = " + seconds);
        String text = formatDateNumber((int)hours) + ":" +
			        	formatDateNumber((int)minutes) + ":" +
			        	formatDateNumber((int)seconds);
//        System.out.println(text);
        return text;
    }

    public static boolean isDaily(Date date) {
  	  	String time = formatCustomDateTime(date, new SimpleDateFormat("H").toPattern());
    	return Integer.valueOf(time) < 12;
    }

    /**
     * Get a diff between two dates
     * @param date1 the oldest date
     * @param date2 the newest date
     * @param timeUnit the unit in which you want the diff
     * @return the diff value, in the provided unit
     * @author Sebastien Lorber
     * @see http://stackoverflow.com/questions/1555262/calculating-the-difference-between-two-java-date-instances
     */
    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    /**
     * Проверка, является ли год високосным
     * @param year номер года
     * @return признак високосного года
     * @author cletus
     * @see http://stackoverflow.com/questions/1021324/java-code-for-calculating-leap-year
     */
    public static boolean isLeapYear(int year) {
    	Calendar cal = Calendar.getInstance();
    	cal.set(Calendar.YEAR, year);
    	return cal.getActualMaximum(Calendar.DAY_OF_YEAR) > 365;
	}
}
