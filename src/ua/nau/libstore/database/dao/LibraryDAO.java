/**
 * 
 */
package ua.nau.libstore.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import ua.nau.libstore.database.bean.LibraryBean;
import ua.nau.libstore.util.LanguageTypeEnum;

/**
 * @author Geniy
 *
 */
public class LibraryDAO {
	
	private static final String INSERT_LIBRARY = 
			"insert into library (name, short_description, full_description,  version, language, " +
			"raiting, test_raiting, comment, date, library.require) " +
			"value (?, ?, ?, ?, ?, ?, ?, ?, CURDATE(), ?)";	
	/**
	 * Get id by group unique
	 */
	private static final String GET_ALL_LIBRARY = "select * from library";
	private static final String GET_LIBRARY_BY_ID = "select * from library where idlibrary =?";
	private static final String GET_ID_BY_DATA = "select * from library where name=? and short_description=? and version=? and language=?";
	private static final String REMOVE_BY_ID = "delete from library where idlibrary =?";
	
	private Connection connection;

	public LibraryDAO(Connection connection) {
		super();
		this.connection = connection;
	}
	
	/**
	 * Insert new library to DB
	 * @param bean library bean for adding
	 * @return id of library
	 */
	public int insertLibrary(LibraryBean bean){
		int result = -1;
		try {
			PreparedStatement statement = connection.prepareStatement(INSERT_LIBRARY);
			statement.setString(1, bean.getName());
			statement.setString(2, bean.getShortDescription());
			statement.setString(3, bean.getFullDescription());
			statement.setString(4, bean.getVersion());
			statement.setString(5, bean.getLanguage().name());
			statement.setString(6, new Float(3f).toString());
			statement.setString(7, new Integer(bean.getTestRaiting()).toString());
			statement.setString(8, bean.getComment());
			statement.setString(9, bean.getRequire());			
			statement.executeUpdate();
			
			result = getIdByData(bean.getName(), bean.getShortDescription(), 
					bean.getVersion(), bean.getLanguage());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Get library's id by data
	 * @param name name of library
	 * @param shortDescription short description of library
	 * @param version version of library
	 * @param language language of library
	 * @return id of library
	 */
	public int getIdByData(String name, String shortDescription, String version, 
			LanguageTypeEnum language){
		
		int result = -1;
		try {
			PreparedStatement statement = connection.prepareStatement(GET_ID_BY_DATA);
			statement.setString(1, name);
			statement.setString(2, shortDescription);
			statement.setString(3, version);
			statement.setString(4, language.name());
			ResultSet set = statement.executeQuery();
			
			if(set.next() == false) return -1;
			
			result = Integer.parseInt(set.getString("idlibrary"));		
			
			if(set.next() == true) {
				throw new IllegalArgumentException("Database has more that one libraries by these data");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public LibraryBean[] getAllLibrary(){
		LinkedList<LibraryBean> list = new LinkedList<>();
		
		try {
			PreparedStatement statement = connection.prepareStatement(GET_ALL_LIBRARY);
			ResultSet set = statement.executeQuery();			
			while(set.next()){
				list.add(readLibrary(set));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		LibraryBean[] result = new LibraryBean[list.size()];
		return list.toArray(result);
	}
	
	/**
	 * Get library by id
	 * @param id id of library
	 * @return LibraryBean object
	 */
	public LibraryBean getLibrary(int id){
		LibraryBean bean = null;
		
		try {
			PreparedStatement statement = connection.prepareStatement(GET_LIBRARY_BY_ID);
			statement.setString(1, new Integer(id).toString());			
			ResultSet resultSet = statement.executeQuery();
			
			if(resultSet.next()) bean = readLibrary(resultSet);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	/**
	 * Read LibraryBean from ResultSet object
	 * @param set ResultSet object
	 * @return LibraryBean object
	 * @throws NumberFormatException
	 * @throws SQLException
	 */
	public static LibraryBean readLibrary(ResultSet set) throws NumberFormatException, SQLException{

		int id = Integer.parseInt(set.getString("idlibrary"));
		String name = set.getString("name");
		String shortDescription = set.getString("short_description");
		String fullDescription = set.getString("full_description");
		String version = set.getString("version");
		LanguageTypeEnum language = LanguageTypeEnum.valueOf(set.getString("language"));
		float raiting = Float.parseFloat(set.getString("raiting"));
		int testRaiting = Integer.parseInt(set.getString("test_raiting")); //try to change to getInteger method
		String comment = set.getString("comment");
		java.util.Date date = new java.util.Date(set.getDate("date").getTime()); //convert sql.Date to util.Date
		String require = set.getString("require");
		
		return new LibraryBean(id, name, shortDescription, fullDescription, version, 
				language, raiting, testRaiting, comment, date, require);
	}
	
	/**
	 * Remove library from DB
	 * @param id id of the library
	 * @return true - library was removed; false - library wasn't removed
	 */
	public boolean remove(int id){
		boolean result = false;
		try {
			PreparedStatement statement = connection.prepareStatement(REMOVE_BY_ID);
			statement.setString(1, new Integer(id).toString());
			statement.executeUpdate();
			
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
