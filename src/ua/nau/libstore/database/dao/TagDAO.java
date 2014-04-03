package ua.nau.libstore.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;



public class TagDAO {
	
	private static final String INSERT_TAG = "insert into tag (name) values (?)";
	private static final String TAG_IS_ALIVE = "select * from tag where name=?";
	private static final String TAG_IS_ALIVE_CONTINUE = " or name =?";
	private static final String COUNT_OF_TAGS = "select count(*) as 'count' from tag";
	private static final String GET_ID_BY_NAME = "select * from tag where name=?";
	private static final String GET_ALL_TAGS = "select name from tag";
	
	private Connection connection;
	private static String[] tagList = new String[0];

	public TagDAO(Connection connection) {
		super();
		this.connection = connection;
	}
	
	/**
	 * Get count of tags' records in database
	 * @return count of records
	 */
	public int countOfTags(){
		int result = -1;
		try {
			PreparedStatement statement = connection.prepareStatement(COUNT_OF_TAGS);
			ResultSet set = statement.executeQuery();
			if(set.next()) result = Integer.parseInt(set.getString("count"));
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return result;
	}
	
	/**
	 * Add tag to database by name
	 * @param tagName tag name
	 * @return id of tagName tag
	 */
	public int insertTag(String tagName){
		try {
			PreparedStatement statement = connection.prepareStatement(INSERT_TAG);
			statement.setString(1, tagName);
			statement.executeUpdate();							
		} catch (SQLException e) {
		}
		return getIdByName(tagName);	
	}
	
	/**
	 * Get tag ID by name
	 * @param tagName tag name
	 * @return id of tag
	 */
	public int getIdByName(String tagName){
		int result = -1;
		try {
			PreparedStatement statement = connection.prepareStatement(GET_ID_BY_NAME);
			statement.setString(1, tagName);			
			ResultSet set = statement.executeQuery();
			if(set.next()){
				result = Integer.parseInt(set.getString("idtag"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return result;
	}
		
	/**
	 * Get all tags from database
	 * @return array of tags
	 */
	public String[] getAllTags(){
		if(countOfTags() == tagList.length) return tagList;
		
		LinkedList<String> list = new LinkedList<>();
		try {
			PreparedStatement statement = connection.prepareStatement(GET_ALL_TAGS);
			ResultSet set = statement.executeQuery();
			while(set.next()){
				list.add(set.getString("name"));
			}
		} catch (SQLException e) {			
			e.printStackTrace();
			return null;
		}
		
		return list.toArray(tagList);
	}
		
	/**
	 * Get all alive tags in database, that is in the array of tags
	 * @param tagArray an array that has tags
	 * @return array of tags that is in array and database at the same time
	 */
	public String[] getAliveTags(String[] tagArray){
		LinkedList<String> list = new LinkedList<>();
		
		StringBuffer sqlRequest = new StringBuffer(TAG_IS_ALIVE);
		for(int i = 0; i < tagArray.length -1; i++){
			sqlRequest.append(TAG_IS_ALIVE_CONTINUE);
		}
		
		try {
			PreparedStatement statement = connection.prepareStatement(sqlRequest.toString());
			for(int i = 0; i < tagArray.length; i++){
				statement.setString(i+1, tagArray[i]);				
			}
			ResultSet set = statement.executeQuery();
			
			while(set.next() == true){
				list.add(set.getString("name"));
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
			return null;
		}
		
		String[] result = new String[list.size()];
		return list.toArray(result);
	}
	
	/**
	 * Check if a tag is alive
	 * @param tag tag name for checking
	 * @return true - tag is in database, false - tag wasn't found in database
	 */
	public boolean isAlliveTag(String tag){
		boolean result = false;
		try {
			PreparedStatement statement = connection.prepareStatement(TAG_IS_ALIVE);
			statement.setString(1, tag);
			ResultSet resultSet = statement.executeQuery();
			
			if(resultSet.next() == false) return false;		
			
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		
		return result;
	}
	
	
}
