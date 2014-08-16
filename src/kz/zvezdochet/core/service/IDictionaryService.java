package kz.zvezdochet.core.service;

import kz.zvezdochet.core.bean.Model;

/**
 * Интерфейс управления моделями справочника
 * @author Nataly Didenko
 */
public interface IDictionaryService extends IModelService {
	/**
	 * Поиск значения справочника по коду
	 * @param code код
	 * @return модель
	 */
	public Model find(final String code) throws DataAccessException;
}