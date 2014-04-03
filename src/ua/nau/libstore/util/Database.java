package ua.nau.libstore.util;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Class util for database
 * @author Geniy
 *
 */
public class Database {
	
	/**
	 * Check if connection is valid at this time
	 * @param connection a connection for checking
	 * @return true - a connection is valid, false - a connection is wrong
	 */
	public static boolean conectionIsValid(Connection connection){
		try {
			connection.prepareStatement("select 1").execute();
		} catch (SQLException e) {			
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}
