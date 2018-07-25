package kz.zvezdochet.core.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kz.zvezdochet.core.bean.Dictionary;
import kz.zvezdochet.core.bean.Model;
import kz.zvezdochet.core.bean.TextGenderDictionary;
import kz.zvezdochet.core.tool.Connector;

/**
 * Прототип сервиса гендерного справочника
 * @author Nataly Didenko
 */
public abstract class TextGenderDictionaryService extends DictionaryService {

	@Override
	public Model save(Model model) throws DataAccessException {
		TextGenderDictionary dict = (TextGenderDictionary)model;
		//TODO реализовать сохранение гендерных значений перед сохранением модели
		//dict.setGenderText((TextGender)new TextGenderService().save(dict.getGenderTexts()));
		int result = -1;
        PreparedStatement ps = null;
		try {
			String sql;
			if (null == model.getId()) 
				sql = "insert into " + tableName + 
					"(text, code, name, description) values(?,?,?,?)";
			else
				sql = "update " + tableName + " set " +
					"text = ?, " +
					"code = ?, " +
					"name = ?, " +
					"description = ? " +
					"where id = " + dict.getId();
			ps = Connector.getInstance().getConnection().prepareStatement(sql);
			ps.setString(1, dict.getText());
			ps.setString(2, dict.getCode());
			ps.setString(3, dict.getName());
			ps.setString(4, dict.getDescription());
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
			afterSave();
		}
		return dict;
	}

	@Override
	public TextGenderDictionary init(ResultSet rs, Model model) throws DataAccessException, SQLException {
		TextGenderDictionary type = (model != null) ? (TextGenderDictionary)model : (TextGenderDictionary)create();
		if (type instanceof Dictionary)
			super.init(rs, model);
		type.setText(rs.getString("Text"));
//		if (rs.getString("GenderID") != null) {
//			TextGender genderText = (TextGender)new TextGenderService().find(rs.getLong("GenderID"));
//			if (genderText != null)
//				type.setGenderText(genderText);
//		}
		return type;
	}
}
