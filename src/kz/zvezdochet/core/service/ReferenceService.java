package kz.zvezdochet.core.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kz.zvezdochet.core.bean.Base;
import kz.zvezdochet.core.bean.Reference;
import kz.zvezdochet.core.tool.Connector;

/**
 * Реализация сервиса справочников
 * @author Nataly Didenko
 *
 * @see BaseService Реализация интерфейса сервиса управления объектами на уровне БД  
 * @see IReferenceService Интерфейс управления справочниками на уровне БД  
 */
public abstract class ReferenceService extends BaseService implements IReferenceService {

	@Override
	public Base find(String code) throws DataAccessException {
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
	public Base find(Long id) throws DataAccessException {
		if (id == null) return null;
        Reference type = (Reference)create();
        PreparedStatement ps = null;
        ResultSet rs = null;
		try {
			String query = "select * from " + tableName + " where id = ?";
			ps = Connector.getInstance().getConnection().prepareStatement(query);
			ps.setLong(1, id);
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
	public List<Base> getList() throws DataAccessException {
        List<Base> list = new ArrayList<Base>();
        PreparedStatement ps = null;
        ResultSet rs = null;
		try {
			String query = "select * from " + tableName + " order by name";
			ps = Connector.getInstance().getConnection().prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				Reference type = (Reference)create();
				type = init(rs, type);
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
	public Base save(Base element) throws DataAccessException {
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
	public Reference init(ResultSet rs, Base base) throws DataAccessException, SQLException {
		Reference type = null;
		if (base != null && base instanceof Reference)
			type = (Reference)base;
		else
			type = new Reference();
		type.setId(Long.parseLong(rs.getString("ID")));
		type.setCode(rs.getString("Code"));
		type.setName(rs.getString("Name"));
		type.setDescription(rs.getString("Description"));
		return type;
	}
}
