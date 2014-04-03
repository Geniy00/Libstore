package ua.nau.libstore.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import ua.nau.libstore.database.bean.UserBean;
import ua.nau.libstore.util.UserTypeEnum;

/**
 * Data access object to user table
 * @author Geniy
 *
 */
public class UserDAO {
	
	private static final String INSERT_USER = "insert into libstore.user (login, password, permission) value (?, password(?), ?)";
	private static final String GET_USER_BY_LOGIN_PASSWORD = "select * from libstore.user where login = ? and password = password(?)";
	/**
	 * Connection to database
	 */
	private Connection connection;
		
	public UserDAO(Connection connection) {
		this.connection = connection;
	}

	/**
	 * Add new record to database
	 * @param user user bean for addition
	 * @param password a password for a new user
	 * @return count of users in table
	 */
	public int addUser(UserBean user, String password){
		
		int result = -1;
		try {
			PreparedStatement statement = connection.prepareStatement(INSERT_USER);
			statement.setString(1, user.getLogin());
			statement.setString(2, password);
			statement.setString(3, user.getPermission().name());
			result = statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Login user by login and password
	 * @param login a login of user
	 * @param password a password of user
	 * @return user bean object
	 */
	public UserBean getUserByLoginPassword(String login, String password){
		
		UserBean result = null;
		try{
			PreparedStatement statement = connection.prepareStatement(GET_USER_BY_LOGIN_PASSWORD);
			statement.setString(1, login);
			statement.setString(2, password);
			ResultSet resultSet = statement.executeQuery();
			
			if(resultSet.next() == false) return null;
			
			int id = Integer.parseInt(resultSet.getString("iduser"));
			UserTypeEnum permission = UserTypeEnum.valueOf(resultSet.getString("permission"));
			
			result = new UserBean(id, login, permission);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result = null;
		}
		
		return result;
	}
	
}
