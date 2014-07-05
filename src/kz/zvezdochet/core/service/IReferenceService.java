package kz.zvezdochet.core.service;

import kz.zvezdochet.core.bean.BaseEntity;

/**
 * Общий интерфейс сервиса по управлению сущностями предметной области в БД
 * @author nataly
 */
public interface IReferenceService extends IEntityService {
	/**
	 * Метод, возвращающий объект по коду
	 * @param code код
	 * @return объект-сущность
	 */
	public BaseEntity getEntityByCode(final String code) throws DataAccessException;
}