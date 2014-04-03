package ua.nau.libstore.controller;

import ua.nau.libstore.database.bean.UserBean;

public class Manager {
	
	public final LibraryController Library = new LibraryController();
	public final ImageController Image = new ImageController();
	public final FileController File = new FileController();
	public final SearchContoller Search = new SearchContoller();
	public final UserController User = new UserController();
	public final TagListController TagList = new TagListController();
	
	private UserBean userBean;	
	private static Manager manager;
	
	private Manager(){
		
	}
	
	public static Manager getInstance(){
		if(manager == null)
		{
			synchronized (Manager.class) {
				if(manager == null) manager = new Manager();
			}
		}
		return manager;
	}

	public UserBean getUserBean() {
		return userBean;
	}
	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	
	
}
