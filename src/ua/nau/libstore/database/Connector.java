package ua.nau.libstore.database;

import java.sql.Connection;
import java.sql.DriverManager;
import ua.nau.libstore.resource.Resource;
import ua.nau.libstore.util.Database;

public class Connector {
	
	public static final String dbDriver = 	Resource.getString("db.driver");
	public static final String dbUrl = 		Resource.getString("db.url");
	public static final String dbTable = 	Resource.getString("db.table");
	public static final String dbLogin = 	Resource.getString("db.login");
	public static final String dbPassword =	Resource.getString("db.password");
	
	private static Connection connection;
	
	private Connector(){ }
	
	
	private static synchronized Connection getConnection(){
		if(connection != null) return connection;		
		try{			
			Class.forName(dbDriver).newInstance();			
			connection = DriverManager.getConnection(dbUrl+dbTable, dbLogin, dbPassword);			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return connection;
	}
	
	public static Connection getInstance(){
				
		if(connection != null 
				&& Database.conectionIsValid(connection) == false) connection = null;
				
		if(connection != null) return connection;
		
		synchronized (Connector.class) {
			return getConnection();
		}
	}
	
}
