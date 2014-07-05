package kz.zvezdochet.core.tool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Класс, обеспечивающий соединение с базой данных
 * @author nataly
 *
 */
public class Connector {
	private static Connector instance;
	private static Connection connection;
	/**
	 * Признак того, что устанавливается соединение с удаленным сервером
	 */
	public static boolean remote = false;
	
	private Connector() {}
	
	public static Connector getInstance() {
		if (instance == null) {
			instance = new Connector();
			if (remote)
				setConnection();
			else
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

	/**
	 * Инициализация соединения с БД через контекст веб-сервера
	 */
	private static void setConnection() {
		String datasource = "jdbc/stargazer";
		try {
			Context c = (Context)new InitialContext().lookup("java:comp/env");
			DataSource ds = (DataSource)c.lookup(datasource);
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			System.out.println("Источних данных найден: java:comp/env/" + datasource);
			System.out.println("Соединение установлено");
		} catch (SQLException se) {
			se.printStackTrace();
			System.err.println("Соединение не установлено\t" + se.getMessage());
		} catch (NamingException ne) {
			//ne.printStackTrace();
			System.err.println("Источних данных не найден\t" + ne.getMessage());
		}
	}
}
