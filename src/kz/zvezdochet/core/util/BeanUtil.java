package kz.zvezdochet.core.util;

import java.util.List;

import kz.zvezdochet.core.bean.BaseEntity;
import kz.zvezdochet.core.bean.Reference;


/**
 * Класс, предоставляющий вспомогательные методы для работы с сущностями
 * @author nataly
 *
 */
public class BeanUtil {
	/**
	 * Поиск объекта справочника по коду
	 * @param code код объекта
	 * @return объект справочника
	 */
	public static BaseEntity getReferenceByCode(List<BaseEntity> list, String code) {
		for (BaseEntity entity : list)
			if (((Reference)entity).getCode().equals(code))
				return entity;
		return null;
	}
}