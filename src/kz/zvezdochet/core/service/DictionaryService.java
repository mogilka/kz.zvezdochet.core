package kz.zvezdochet.core.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kz.zvezdochet.core.bean.Dictionary;
import kz.zvezdochet.core.bean.Model;
import kz.zvezdochet.core.tool.Connector;

/**
 * Прототип сервиса модификации справочника
 * @author Nataly Didenko
 */
public abstract class DictionaryService extends ModelService implements IDictionaryService {

	@Override
	public Model find(String code) throws DataAccessException {
        Dictionary type = (Dictionary)create();
        PreparedStatement ps = null;
        ResultSet rs = null;
		try {
			String sql = "select * from " + tableName + " where code like ?";
			ps = Connector.getInstance().getConnection().prepareStatement(sql);
			ps.setString(1, code);
			rs = ps.executeQuery();
			if (rs.next())
				type = init(rs, type);
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
		return type;
	}

	@Override
	public List<Model> getList() throws DataAccessException {
        List<Model> list = new ArrayList<Model>();
        PreparedStatement ps = null;
        ResultSet rs = null;
		try {
			String sql = "select * from " + tableName + " order by name";
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
	public Model save(Model model) throws DataAccessException {
		Dictionary dict = (Dictionary)model;
		int result = -1;
        PreparedStatement ps = null;
		try {
			String sql;
			if (null == model.getId()) 
				sql = "insert into " + tableName + "(code, name, description) values(?,?,?)";
			else
				sql = "update " + tableName + " set " +
					"code = ?, " +
					"name = ?, " +
					"description = ? " +
					"where id = " + dict.getId();
			ps = Connector.getInstance().getConnection().prepareStatement(sql);
			ps.setString(1, dict.getCode());
			ps.setString(2, dict.getName());
			ps.setString(3, dict.getDescription());
			result = ps.executeUpdate();
			if (result == 1) {
				if (null == model.getId()) { 
					Long autoIncKeyFromApi = -1L;
					ResultSet rsid = ps.getGeneratedKeys();
					if (rsid.next()) {
				        autoIncKeyFromApi = rsid.getLong(1);
				        model.setId(autoIncKeyFromApi);
					}
					if (rsid != null) rsid.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)	ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			update();
		}
		return dict;
	}

	@Override
	public Dictionary init(ResultSet rs, Model model) throws DataAccessException, SQLException {
		Dictionary type = null;
		if (model != null && model instanceof Dictionary)
			type = (Dictionary)model;
		else
			type = (Dictionary)create();
		type.setId(rs.getLong("ID"));
		type.setCode(rs.getString("Code"));
		type.setName(rs.getString("Name"));
		type.setDescription(rs.getString("Description"));
		return type;
	}

	@Override
	public Model create() {
		return new Dictionary();
	}
}
