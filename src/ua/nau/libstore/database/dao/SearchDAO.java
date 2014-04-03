package ua.nau.libstore.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import ua.nau.libstore.database.bean.LibraryBean;
import ua.nau.libstore.util.LanguageTypeEnum;


public class SearchDAO {
	
	public static final String SEARCH_IN_BOOLEAN_MODE = "SELECT *, " +
			"MATCH (name,short_description, full_description) AGAINST (? IN BOOLEAN MODE) as REL " +
			"FROM search_library " +
			"WHERE MATCH (name,short_description, full_description)  AGAINST (? IN BOOLEAN MODE) " +
			"ORDER BY REL";
	
	public static final String SEARCH_IN_NATURAL_MODE = "select * from search_library where " +
			"match (name, short_description, full_description) against (?) ";
	
	public static final String SEARCH_BY_NAME_AND_LANGUAGE = "select * from library where name=? and language=? order by version desc";
	
	public static final String SHOW_BY_LANGUAGE = "select * from library where language=?";
		
	Connection connection;

	public SearchDAO(Connection connection) {
		this.connection = connection;
	}

	/**
	 * Smart search in boolean(light) mode.
	 * You will get many and all possible recors
	 * You can use operators +, -, *, <>, ~ in queries
	 * @param query user query
	 * @return LibraryBean array of results
	 */
	public LibraryBean[] searchInBooleanMode(String query){
		LinkedList<LibraryBean> list = new LinkedList<>();
		try {
			PreparedStatement statement = connection.prepareStatement(SEARCH_IN_BOOLEAN_MODE);
			statement.setString(1, query);
			statement.setString(2, query);
			ResultSet resultSet = statement.executeQuery();
			
			LinkedList<Integer> listOfId = new LinkedList<>();
			while(resultSet.next() == true){
				Integer idLibrary = Integer.parseInt(resultSet.getString("idlibrary"));
				listOfId.add(idLibrary);
			}
			
			LibraryDAO libraryDAO = new LibraryDAO(connection);
			for (Integer current : listOfId) {
				list.add(libraryDAO.getLibrary(current));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		LibraryBean[] result = new LibraryBean[list.size()];
		return list.toArray(result);
	}
		
	/**
	 * Smart search in natural(hard) mode.
	 * You will get only main records.
	 * You can't use operators +, -, *, <>, ~ in queries
	 * @param query user query
	 * @return LibraryBean array of results
	 */
	public LibraryBean[] searchInNaturalMode(String query){
		LinkedList<LibraryBean> list = new LinkedList<>();
		try {
			PreparedStatement statement = connection.prepareStatement(SEARCH_IN_NATURAL_MODE);
			statement.setString(1, query);
			ResultSet resultSet = statement.executeQuery();
			
			LinkedList<Integer> listOfId = new LinkedList<>();
			while(resultSet.next() == true){
				Integer idLibrary = Integer.parseInt(resultSet.getString("idlibrary"));
				listOfId.add(idLibrary);
			}
			
			LibraryDAO libraryDAO = new LibraryDAO(connection);
			for (Integer current : listOfId) {
				list.add(libraryDAO.getLibrary(current));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		LibraryBean[] result = new LibraryBean[list.size()];
		return list.toArray(result);
	}

	public LibraryBean[] searchByNameAndLanguage(String name, LanguageTypeEnum language){
		LinkedList<LibraryBean> list = new LinkedList<>();
		try {
			PreparedStatement statement = connection.prepareStatement(SEARCH_BY_NAME_AND_LANGUAGE);
			statement.setString(1, name);
			statement.setString(2, language.name());
			
			ResultSet set = statement.executeQuery();
			while(set.next()){
				list.add(LibraryDAO.readLibrary(set));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		LibraryBean[] result = new LibraryBean[list.size()];
		return list.toArray(result);
	}
	
	public LibraryBean[] showAllLibrary(){
		LibraryDAO libraryDAO = new LibraryDAO(connection);
		return libraryDAO.getAllLibrary();
	}
	
	/**
	 * Show libraries by criteria programming language name
	 * @param language programming language for every library
	 * @return array of LibraryBean instance
	 */
	public LibraryBean[] showByLanguage(LanguageTypeEnum language){
		LinkedList<LibraryBean> list = new LinkedList<>();
		try {
			PreparedStatement statement = connection.prepareStatement(SHOW_BY_LANGUAGE);
			statement.setString(1, language.name());
			ResultSet set = statement.executeQuery();
			
			while(set.next() == true){
				list.add(LibraryDAO.readLibrary(set));
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		LibraryBean[] result = new LibraryBean[list.size()];
		return list.toArray(result);
	}
	
	/**
	 * Show libraries by criteria programming language name and array of tags
	 * @param language programming language for every library
	 * @param tags array of tags for every library
	 * @return array of LibraryBean instance
	 */
	public LibraryBean[] showByLanguageAndTags(LanguageTypeEnum language, String[] tags){
		LibraryBean[] beansByTags = showByTags(tags);
		
		Collection<LibraryBean> list = new LinkedList<>();
		for(int i = 0; i < beansByTags.length; i++){
			if(beansByTags[i].getLanguage() == language){
				list.add(beansByTags[i]);
			}
		}
		
		LibraryBean[] result = new LibraryBean[list.size()];
		return list.toArray(result);
	}

	/**
	 * Show libraries by tags with 50% threshold
	 * @param tags
	 * @return
	 */
	public LibraryBean[] showByTags(String[] tags){
		
		//Get all idlibrary that have tags
		LinkedList<Integer> list = new LinkedList<>(); 		
		TagListDAO tagListDAO = new TagListDAO(connection);
		for(int i = 0; i < tags.length; i++){		
			list.addAll(tagListDAO.getAllLibraryIdByTag(tags[i]));
		}
		Collections.sort(list);
		
		//Get all idlibrary if they was founded more that threshold times
		int threshold = (tags.length < 3 || list.size() < 4) ? 0 : (int)Math.ceil(tags.length * 0.5);	
		Integer[] array = new Integer[list.size()];
		list.toArray(array);
		
		LinkedList<Integer> libraryIdList = new LinkedList<>();
		int prev = -1;
		int count = 0;
		for(int i = 0; i < array.length; i++){
			if(array[i] != prev){
				if(count >= threshold) {
					libraryIdList.add(array[i]);
				}
				prev = array[i];
				count = 1;			//it's first time at this step
			} else {
				count++;
			}
		}
		
		//Get LibraryBean array objects from idlibrary array
		Integer[] libraryIdArray = new Integer[libraryIdList.size()];
		libraryIdList.toArray(libraryIdArray);		
		LinkedList<LibraryBean> resultList = new LinkedList<>();
		LibraryDAO libraryDAO = new LibraryDAO(connection);
		for(int i = 0; i < libraryIdArray.length; i++){
			LibraryBean bean = libraryDAO.getLibrary(libraryIdArray[i]);
			resultList.add(bean);
		}
		
		LibraryBean[] result = new LibraryBean[resultList.size()];
		return resultList.toArray(result);
	}
	
}
