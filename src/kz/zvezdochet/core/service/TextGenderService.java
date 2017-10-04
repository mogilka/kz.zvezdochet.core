package kz.zvezdochet.core.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kz.zvezdochet.core.bean.Model;
import kz.zvezdochet.core.bean.TextGender;
import kz.zvezdochet.core.tool.Connector;

/**
 * Сервис толкований для гендерных и возрастных групп
 * @author Nataly Didenko
 */
public class TextGenderService extends ModelService implements IDictionaryService {

	public TextGenderService() {
		tableName = "textgender";
	}

	@Override
	public Model find(String code) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Model> getList() throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model save(Model model) throws DataAccessException {
		TextGender dict = (TextGender)model;
		int result = -1;
        PreparedStatement ps = null;
		try {
			String sql;
			if (null == model.getId()) 
				sql = "insert into " + tableName + "(text, type, objectid, objectype) values(?,?,?,?)";
			else
				sql = "update " + tableName + " set " +
					"text = ?, " +
					"type = ?, " +
					"objectid = ?, " +
					"objectype = ? " +
					"where id = ?";
			ps = Connector.getInstance().getConnection().prepareStatement(sql);
			ps.setString(1, dict.getText());
			ps.setString(2, dict.getType());
			ps.setLong(3, dict.getObjectId());
			ps.setString(4, dict.getObjectType());
			if (model.getId() != null)
				ps.setLong(5, model.getId());
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
		}
		return dict;
	}

	@Override
	public TextGender init(ResultSet rs, Model model) throws DataAccessException, SQLException {
		TextGender genderText = (model != null) ? (TextGender)model : (TextGender)create();
		genderText.setId(rs.getLong("ID"));
		genderText.setText(rs.getString("text"));
		genderText.setType(rs.getString("type"));
		return genderText;
	}

	@Override
	public Model create() {
		return new TextGender();
	}

	/**
	 * Поиск гендерных связей модели
	 * @param model модель справочника
	 * @param female true|false женский|мужской
	 * @param child true|false детский|взрослый
	 * @return список гендерных моделей
	 * @throws DataAccessException
	 */
	public List<TextGender> find(Model model, boolean female, boolean child) throws DataAccessException {
        List<TextGender> list = new ArrayList<TextGender>();
        String types = "'health'";
        if (female)
        	types += ", 'female'";
        else
        	types += ", 'male'";
        
        if (child)
        	types += ",'child'";

        PreparedStatement ps = null;
        ResultSet rs = null;
		try {
			String sql = "select * from " + tableName + 
				" where objectid = ? " +
					"and objectype = ? " +
					"and type in (" + types + ")";
			ps = Connector.getInstance().getConnection().prepareStatement(sql);
			ps.setLong(1, model.getId());
			ps.setString(2, model.getService().getTableName());
			//System.out.println(ps);
			rs = ps.executeQuery();
			while (rs.next()) {
				TextGender type = init(rs, create());
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

	/**
	 * Поиск гендерных связей модели для ребёнка
	 * @param model модель справочника
	 * @return гендерное толкование
	 * @throws DataAccessException
	 */
	public TextGender findChild(Model model) throws DataAccessException {
        TextGender type = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
		try {
			String sql = "select * from " + tableName + 
				" where objectid = ? " +
					"and objectype = ? " +
					"and type = 'child'";
			ps = Connector.getInstance().getConnection().prepareStatement(sql);
			ps.setLong(1, model.getId());
			ps.setString(2, model.getService().getTableName());
			rs = ps.executeQuery();
			if (rs.next())
				type = init(rs, create());
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

	/**
	 * Поиск гендерных связей модели по типу
	 * @param model модель справочника
	 * @param type love|family|deal любовный|семейный|партнёрский
	 * @return гендерное толкование
	 * @throws DataAccessException
	 */
	public TextGender find(Model model, String type) throws DataAccessException {
        PreparedStatement ps = null;
        ResultSet rs = null;
		try {
			String sql = "select * from " + tableName + 
				" where objectid = ? " +
					"and objectype = ? " +
					"and type = ?";
			ps = Connector.getInstance().getConnection().prepareStatement(sql);
			ps.setLong(1, model.getId());
			ps.setString(2, model.getService().getTableName());
			ps.setString(3, type);
			//System.out.println(ps);
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
}
