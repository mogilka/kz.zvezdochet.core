package kz.zvezdochet.core.tool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Класс, обеспечивающий соединение с БД SQLite
 * @author Nataly Didenko
 *
 */
public class Connector {
	private static Connector instance;
	private static Connection connection;
	
	private Connector() {}
	
	public static Connector getInstance() {
		if (null == instance) {
			instance = new Connector();
			setLocalConnection();
		}
		return instance;
	}

	/**
	 * Метод, возвращающий соединение с базой данных
	 * @return соединение
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * Закрытие соединения с БД
	 */
	public synchronized void closeConnection() {
		try {
			if (connection != null) 
				connection.close();
			System.out.println("Connection closed");
		} catch (SQLException e) { 
			e.printStackTrace();	
		}
	}

	protected void finalize() {
		try {
//			closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Инициализация соединения с БД
	 * @todo вынести в конфиг + назначить местонахождение БД
	 */
	private static void setLocalConnection2() {
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:stargazer.db");
		} catch (ClassNotFoundException e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	

	/**
	 * Инициализация соединения с БД
	 * @todo вынести в конфиг
	 */
	private static void setLocalConnection() {
		try {
	    	String username = "root";
	    	String userpass = "dolphin";
	    	String dburl = "jdbc:mysql://localhost:3306/stargazer";
	    	String driverClassName = "com.mysql.jdbc.Driver";
	    	String characterEncoding = "utf8";
	    	String useUnicode = "true";
			Properties properties = new Properties();
			properties.put("user", username);
			properties.put("password", userpass);
			properties.put("characterEncoding", characterEncoding);
			properties.put("useUnicode", useUnicode);
			Class.forName(driverClassName);
			connection = (Connection)DriverManager.getConnection(dburl, properties);
		} catch (ClassNotFoundException e) {
			System.err.println("Unable to load driver: " + e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
}
