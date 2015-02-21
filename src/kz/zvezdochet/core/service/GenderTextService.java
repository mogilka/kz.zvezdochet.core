package kz.zvezdochet.core.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import kz.zvezdochet.core.bean.GenderText;
import kz.zvezdochet.core.bean.Model;
import kz.zvezdochet.core.tool.Connector;

/**
 * Сервис толкований для мужчин, женщин, детей
 * @author Nataly Didenko
 */
public class GenderTextService extends ModelService implements IDictionaryService {

	public GenderTextService() {
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
		GenderText dict = (GenderText)model;
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
	public GenderText init(ResultSet rs, Model model) throws DataAccessException, SQLException {
		GenderText genderText = (model != null) ? (GenderText)model : (GenderText)create();
		genderText.setId(rs.getLong("ID"));
		genderText.setText(rs.getString("Male"));
		genderText.setType(rs.getString("Female"));
		//TODO child
		return genderText;
	}

	@Override
	public Model create() {
		return new GenderText();
	}
}
