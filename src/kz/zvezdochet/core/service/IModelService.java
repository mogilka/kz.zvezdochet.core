package kz.zvezdochet.core.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import kz.zvezdochet.core.bean.Model;


/**
 * Общий интерфейс сервиса по управлению сущностями предметной области в БД
 * @author Nataly Didenko
 */
public interface IModelService {
	/**
	 * Сохранение объекта в БД
	 * @param model объект
	 * @return Base сохраненный объект
	 */
	public Model save(Model model) throws DataAccessException;
	/**
	 * Удаление объекта из БД
	 * @param id идентификатор объекта
	 * @return результат выполнения операции
	 */
	public int delete(Long id) throws DataAccessException;
	/**
	 * Поиск объектов данного типа
	 * @return List<Base> список объектов
	 */
	public List<Model> getList() throws DataAccessException;
	/**
	 * Поиск объекта по идентификатору
	 * @param id идентификатор
	 * @return объект
	 */
	public Model find(Long id) throws DataAccessException;
	/**
	 * Инициализация найденного в БД объекта
	 * @param rs результирующий набор данных
	 * @return объект
	 * @throws DataAccessException
	 * @throws SQLException
	 */
	public Model init(ResultSet rs, Model base) throws DataAccessException, SQLException;
	/**
	 * Добавление объекта. Должно быть переопределено в наследниках
	 * @return объект
	 */
	public Model create();
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
