package kz.zvezdochet.core.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import kz.zvezdochet.core.bean.Model;
import kz.zvezdochet.core.tool.Connector;

/**
 * Сервис управления моделями на уровне БД
 */
public abstract class ModelService implements IModelService {
	/**
	 * Название таблицы БД, представляющей сущность 
	 */
	protected String tableName = "";
	
	@Override
	public int delete(Long id) {
		if (id == null) return -1;
		int result = -1;
        PreparedStatement ps = null;
		try {
			update();
			String sql = "delete from " + tableName + " where id = ?";
			ps = Connector.getInstance().getConnection().prepareStatement(sql);
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
	protected List<Model> list = null;

	@Override
	public void update() {
		list = null;
	}
	
	@Override
	public String getTableName() {
		return tableName;
	}

	@Override
	public List<Model> getList() throws DataAccessException {
		return list;
	}

	@Override
	public Model find(Long id) throws DataAccessException {
		if (id == null) return null;
		Model model = create();
        PreparedStatement ps = null;
        ResultSet rs = null;
		try {
			String sql = "select * from " + tableName + " where id = ?";
			ps = Connector.getInstance().getConnection().prepareStatement(sql);
			ps.setLong(1, id);
			rs = ps.executeQuery();
			if (rs.next()) 
				model = init(rs, model);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { 
				if (rs != null) rs.close();
				if (ps != null) ps.close();
			} catch (SQLException e) { 
				e.printStackTrace(); 
			}
		}
		return model;
	}
}
