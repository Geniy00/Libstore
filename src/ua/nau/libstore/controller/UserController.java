package ua.nau.libstore.controller;

import ua.nau.libstore.database.Connector;
import ua.nau.libstore.database.bean.UserBean;
import ua.nau.libstore.database.dao.UserDAO;

/**
 * 
 * @author Geniy
 *
 */
public class UserController {
	
	public UserBean login(String login, String password){
		if(login == null || password == null || login.length() == 0 || password.length() == 0){
			return null;
		}
		
		UserDAO userDAO = new UserDAO(Connector.getInstance());
		return userDAO.getUserByLoginPassword(login, password);
	}
	
}
