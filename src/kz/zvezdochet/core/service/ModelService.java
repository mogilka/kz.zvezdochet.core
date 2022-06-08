package kz.zvezdochet.core.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kz.zvezdochet.core.bean.Model;
import kz.zvezdochet.core.tool.Connector;
import kz.zvezdochet.core.util.DateUtil;

/**
 * Сервис управления моделями на уровне БД
 * @author Natalie Didenko
 */
public abstract class ModelService implements IModelService {
	/**
	 * Название таблицы БД, представляющей сущность 
	 */
	protected String tableName = "";
	
	@Override
	public int delete(Long id) {
		if (null == id) return -1;
		int result = -1;
        PreparedStatement ps = null;
		try {
			afterSave();
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
		if (null == array || array.length == 0)
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
	 * Кэшированный список всех объектов модели
	 */
	protected List<Model> list = null;

	@Override
	public void afterSave() {
		list = null;
	}
	
	@Override
	public String getTableName() {
		return tableName;
	}

	@Override
	public List<Model> getList() throws DataAccessException {
        List<Model> list = new ArrayList<Model>();
        PreparedStatement ps = null;
        ResultSet rs = null;
		try {
			String sql = "select * from " + tableName;
			ps = Connector.getInstance().getConnection().prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				Model type = init(rs, create());
				list.add(type);
			}
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
		return list;
	}

	@Override
	public Model find(Long id) throws DataAccessException {
		if (null == id) return null;
        PreparedStatement ps = null;
        ResultSet rs = null;
		try {
			String sql = "select * from " + tableName + " where id = ?";
			ps = Connector.getInstance().getConnection().prepareStatement(sql);
			ps.setLong(1, id);
			rs = ps.executeQuery();
			if (rs.next()) 
				return init(rs, create());
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
		return null;
	}

	@Override
	public int update(Long id, List<Object> params) {
		if (null == id) return -1;
		int result = -1;
        PreparedStatement ps = null;
		try {
			int count = params.size();
			String sql = "update " + tableName + " set ";
			int i = 0;
			for (Object object : params) {
				Object[] vals = (Object[])object;
				sql += vals[0] + " = ?";
				if (++i < count)
					sql += ", ";
			}
			sql += " where id = ?";
			ps = Connector.getInstance().getConnection().prepareStatement(sql);
			i = 0;
			for (Object object : params) {
				Object[] vals = (Object[])object;
				Object val = vals[2];
				String type = vals[1].toString();
				if (type.equals("String"))
					ps.setString(++i, val.toString());
			}
			ps.setLong(count + 1, id);
//			System.out.println(ps);
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

	@Override
	public Date getLastModified() {
        PreparedStatement ps = null;
        ResultSet rs = null;
		try {
			String sql = "select max(date) from " + tableName;
			ps = Connector.getInstance().getConnection().prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
				return DateUtil.dbdtf.parse(rs.getString(0));
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
		return null;
	}

	/**
	 * Возвращает данные справочника в виде карты
	 * @return массив данных с ключом в виде идентификатора
	 */
	public Map<Long, Model> getMap() throws DataAccessException {
		if (null == map) {
			map = new HashMap<>();
	        PreparedStatement ps = null;
	        ResultSet rs = null;
			String sql;
			try {
				sql = "select * from " + tableName + " order by id";
				ps = Connector.getInstance().getConnection().prepareStatement(sql);
				rs = ps.executeQuery();
				while (rs.next()) {
					Model type = init(rs, create());
					map.put(type.getId(), type);
				}
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
		}
		return map;
	}

	/**
	 * Кэшированная карта всех объектов модели
	 */
	protected Map<Long, Model> map = null;
}
