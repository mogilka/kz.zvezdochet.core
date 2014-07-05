package kz.zvezdochet.core.service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import kz.zvezdochet.core.bean.BaseEntity;
import kz.zvezdochet.core.tool.Connector;


/**
 * Реализация интерфейса сервиса управления сущностями
 * @see IEntityService Интерфейс сервиса управления объектами на уровне БД  
 */
public abstract class EntityService implements IEntityService {
	/**
	 * Название таблицы БД, представляющей сущность 
	 */
	protected String tableName = "";
	
	@Override
	public int removeEntity(Long id) {
		if (id == null) return -1;
		int result = -1;
        PreparedStatement ps = null;
		try {
			updateDictionary();
			String query = "delete from " + tableName + " where id = ?";
			ps = Connector.getInstance().getConnection().prepareStatement(query);
			ps.setLong(1, id);
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)	ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}	

	/**
	 * Метод, преобразующий массив в строку значений,
	 * которая будет использоваться в sql-запросе
	 * в сочетании с операндом IN
	 * @param array строковый массив
	 * @return строка SQL-запроса для оператора IN
	 */
	protected String arrayToString(Object[] array) {
		if (array == null || array.length == 0)
			return null;
		StringBuffer s = new StringBuffer("");
		for (Object value : array) {
			if (value != null) {
				if (value instanceof String)
					value = "'" + value + "'";
				else if (value instanceof Long)
					value = value.toString();
				s.append((s.length() == 0) ? value : ", " + value);
			}
		}
		//System.out.println("arrayString = " + s);
		return s.toString();
	}

	/**
	 * Кэшированный список часто используемых объектов сущности
	 */
	protected List<BaseEntity> list = null;

	@Override
	public List<BaseEntity> getList() throws DataAccessException {
		if (null == list || 0 == list.size())
			list = getOrderedEntities();
		return list;
	}

	@Override
	public void updateDictionary() {
		list = null;
	}
}
