package kz.zvezdochet.core.service;

import kz.zvezdochet.core.bean.Model;

/**
 * Интерфейс управления моделями справочника
 * @author Natalie Didenko
 */
public interface IDictionaryService {
	/**
	 * Поиск значения справочника по коду
	 * @param code код
	 * @return модель
	 */
	public Model find(final String code) throws DataAccessException;
}