package kz.zvezdochet.core.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import kz.zvezdochet.core.bean.Base;


/**
 * Общий интерфейс сервиса по управлению сущностями предметной области в БД
 * @author Nataly Didenko
 */
public interface IBaseService {
	/**
	 * Сохранение объекта в БД
	 * @param element объект
	 * @return Base сохраненный объект
	 */
	public Base save(Base element) throws DataAccessException;
	/**
	 * Удаление объекта из БД
	 * @param element объект
	 * @return результат выполнения операции
	 */
	public int delete(Long id) throws DataAccessException;
	/**
	 * Поиск объектов данного типа
	 * @return List<Base> список объектов
	 */
	public List<Base> getList() throws DataAccessException;
	/**
	 * Поиск объекта по идентификатору
	 * @param id идентификатор
	 * @return объект
	 */
	public Base find(Long id) throws DataAccessException;
	/**
	 * Инициализация найденного в БД объекта
	 * @param rs результирующий набор данных
	 * @return объект
	 * @throws DataAccessException
	 * @throws SQLException
	 */
	public Base init(ResultSet rs) throws DataAccessException, SQLException;
	/**
	 * Добавление объекта. Должно быть переопределено в наследниках
	 * @return объект
	 */
	public Base create();
	/**
	 * Обновление справочника после модификации данных
	 */
	public void update();
	/**
	 * Возвращает имя ТБД
	 * @return имя таблицы БД
	 */
	public String getTableName();
}
