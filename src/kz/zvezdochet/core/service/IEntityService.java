package kz.zvezdochet.core.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import kz.zvezdochet.core.bean.BaseEntity;


/**
 * Общий интерфейс сервиса по управлению сущностями предметной области в БД
 * @author nataly
 */
public interface IEntityService {
	/**
	 * Сохранение объекта в БД
	 * @param element объект-сущность
	 * @return BaseEntity сохраненный объект-сущность
	 */
	public BaseEntity saveEntity(BaseEntity element) throws DataAccessException;
	/**
	 * Удаление объекта из БД
	 * @param element объект-сущность
	 * @return результат выполнения операции
	 */
	public int removeEntity(Long id) throws DataAccessException;
	/**
	 * Метод, возвращающий список всех объектов конкретного класса,
	 * упорядоченный по имени
	 * @return List<BaseEntity> список объектов
	 */
	public List<BaseEntity> getOrderedEntities() throws DataAccessException;
	/**
	 * Поиск объекта по идентификатору
	 * @param id идентификатор
	 * @return объект-сущность
	 */
	public BaseEntity getEntityById(Long id) throws DataAccessException;
	/**
	 * Инициализация объекта, реляционные данные которого получены из БД
	 * @param rs результирующий набор данных
	 * @return объект
	 * @throws DataAccessException
	 * @throws SQLException
	 */
	public BaseEntity initEntity(ResultSet rs) throws DataAccessException, SQLException;
	/**
	 * Создание объекта.
	 * Данный метод должен быть обязательно переопределен в наследниках
	 * @return объект
	 */
	public BaseEntity createEntity();
	/**
	 * Обновление справочника после модификации данных
	 */
	public void updateDictionary();
	/**
	 * Возвращает список объектов сущности
	 * @return список объектов сущности
	 * @throws DataAccessException
	 */
	public List<BaseEntity> getList() throws DataAccessException;
}