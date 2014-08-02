package kz.zvezdochet.core.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kz.zvezdochet.core.bean.Model;
import kz.zvezdochet.core.bean.Reference;
import kz.zvezdochet.core.tool.Connector;

/**
 * Реализация сервиса справочников
 * @author Nataly Didenko
 *
 * @see ModelService Реализация интерфейса сервиса управления объектами на уровне БД  
 * @see IReferenceService Интерфейс управления справочниками на уровне БД  
 */
public abstract class ReferenceService extends ModelService implements IReferenceService {

	@Override
	public Model find(String code) throws DataAccessException {
        Reference type = new Reference();
        PreparedStatement ps = null;
        ResultSet rs = null;
		try {
			String query = "select * from " + tableName + " where code like ?";
			ps = Connector.getInstance().getConnection().prepareStatement(query);
			ps.setString(1, code);
			rs = ps.executeQuery();
			if (rs.next())
				type = init(rs, null);
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
			String query = "select * from " + tableName + " order by name";
			ps = Connector.getInstance().getConnection().prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				Model type = init(rs, null);
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
	public Model save(Model element) throws DataAccessException {
		Reference reference = (Reference)element;
		int result = -1;
        PreparedStatement ps = null;
		try {
			String query;
			if (element.getId() == null) 
				query = "insert into " + tableName + "(code, name, description) values(?,?,?)";
			else
				query = "update " + tableName + " set " +
					"code = ?, " +
					"name = ?, " +
					"description = ? " +
					"where id = " + reference.getId();
			ps = Connector.getInstance().getConnection().prepareStatement(query);
			ps.setString(1, reference.getCode());
			ps.setString(2, reference.getName());
			ps.setString(3, reference.getDescription());
			result = ps.executeUpdate();
			if (result == 1) {
				if (element.getId() == null) { 
					Long autoIncKeyFromApi = -1L;
					ResultSet rsid = ps.getGeneratedKeys();
					if (rsid.next()) {
				        autoIncKeyFromApi = rsid.getLong(1);
				        element.setId(autoIncKeyFromApi);
//					    System.out.println("inserted " + tableName + "\t" + autoIncKeyFromApi);
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
		return reference;
	}

	@Override
	public Reference init(ResultSet rs, Model model) throws DataAccessException, SQLException {
		Reference type = null;
		if (model != null && model instanceof Reference)
			type = (Reference)model;
		else
			type = new Reference();
		type.setId(Long.parseLong(rs.getString("ID")));
		type.setCode(rs.getString("Code"));
		type.setName(rs.getString("Name"));
		type.setDescription(rs.getString("Description"));
		return type;
	}
}
