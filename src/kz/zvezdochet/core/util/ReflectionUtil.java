package kz.zvezdochet.core.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Класс, предоставляющий методы для работы с метаданными Java-классов
 * @author Nataly Didenko
 */
public class ReflectionUtil {

	/**
	 * Динамическое создание экземпляра указанного класса
	 * @param c класс
	 * @param argtypes массив типов входных параметров конструктора
	 * @param arglist массив значений входных параметров конструктора
	 * @return объект класса
	 */
	@SuppressWarnings("unchecked")
	public static Object getObjectOfClass(Class c, Class argtypes[], Object arglist[]) {
		try {
			Constructor constructor = c.getConstructor(argtypes);
	        Object instance = constructor.newInstance(arglist);	   
	        return instance;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			//TODO данное исключение вызывается, потому что у конструктора нет параметров. как быть?
			//constructor.getParameterTypes(); ?
			//e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Метод, возвращающий generic тип поля класса.
	 * Используется для полей-коллекций
	 * @param field поле
	 * @return generic тип
	 */
	public static Type getGenericType(Field field) {
		try {
			Type genericType = field.getGenericType();
		    if (genericType instanceof ParameterizedType) {
		        ParameterizedType parameterizedType = (ParameterizedType)genericType;
		        Type[] args = parameterizedType.getActualTypeArguments();
		        return args[0];
		    }		
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Метод, возвращающий тип поля указанного класса
	 * @param clazz класс
	 * @param fieldName имя поля
	 * @return тип поля
	 */
	@SuppressWarnings("unchecked")
	public static Class getFieldTypeOfClass(Class clazz, String fieldName) {
		Field fieldlist[] = clazz.getDeclaredFields();
		for (Field field : fieldlist) 
			if (field.getName().equals(fieldName)) 
				return field.getType();
		return null;
	}

	/**
	 * Проверка, является ли класс наследником от указанного класса в иерархии
	 * @param clazz родительский класс
	 * @param subclass дочерний класс
	 * @return <true> - является, <false> - не является
	 */
	@SuppressWarnings("unchecked")
	public static boolean isInstanceOfClass(Class clazz, Class subclass) {
		Class superclass = subclass.getSuperclass();
		while (subclass.getSuperclass() != null) {
			if (subclass.equals(clazz))
				return true;
			subclass = superclass;
			superclass = subclass.getSuperclass();
		}
		return false;
	}
}
