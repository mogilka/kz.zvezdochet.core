package kz.zvezdochet.core.service;

import kz.zvezdochet.core.bean.Model;

/**
 * Общий интерфейс сервиса по управлению сущностями предметной области в БД
 * @author Nataly Didenko
 */
public interface IReferenceService extends IBaseService {
	/**
	 * Метод, возвращающий объект по коду
	 * @param code код
	 * @return объект-сущность
	 */
	public Model find(final String code) throws DataAccessException;
}