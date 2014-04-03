package ua.nau.libstore.database.bean;

import ua.nau.libstore.util.UserTypeEnum;

/**
 * Describe user table in database
 * @author Geniy
 *
 */
public class UserBean {
	
	private int id;
	private String login;
	private UserTypeEnum permission;
	
	
	public UserBean() {}
	
	public UserBean(int id, String login, UserTypeEnum permission) {
		super();
		this.id = id;
		this.login = login;
		this.permission = permission;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	
	public UserTypeEnum getPermission() {
		return permission;
	}
	public void setPermission(UserTypeEnum permission) {
		this.permission = permission;
	}
	
}
