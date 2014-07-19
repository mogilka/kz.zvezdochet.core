package kz.zvezdochet.core.util;

import java.util.List;

import kz.zvezdochet.core.bean.Model;
import kz.zvezdochet.core.bean.Reference;


/**
 * Класс, предоставляющий вспомогательные методы для работы с сущностями
 * @author Nataly Didenko
 *
 */
public class BeanUtil {
	/**
	 * Поиск объекта справочника по коду
	 * @param code код объекта
	 * @return объект справочника
	 */
	public static Model getReferenceByCode(List<Model> list, String code) {
		for (Model entity : list)
			if (((Reference)entity).getCode().equals(code))
				return entity;
		return null;
	}
}