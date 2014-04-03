/**
 * 
 */
package ua.nau.libstore.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import ua.nau.libstore.database.bean.TagListBean;

/**
 * Data access object to taglist and tag tables
 * @author Geniy
 *
 */
public class TagListDAO {
	
	private static final String INSERT_TAGLIST = "insert into taglist (library_fk, tag_fk) values (?, ?)";
	private static final String GET_ALL_BY_LIBRARY_FK = "select * from taglist, tag where tag_fk =idtag and library_fk =?";
	private static final String GET_IDLIBRARY_BY_TAG = "select library_fk from taglist, tag where idtag=tag_fk and name=?";
	
	
	private Connection connection;

	public TagListDAO(Connection connection) {
		super();
		this.connection = connection;
	}
	
	/**
	 * Insert taglist record in database and name of tag in TAG database
	 * @param tagName  name of tag
	 * @param libraryFK foreign key of library what taglist is adding for
	 * @return true - tagList was added, false - tagList wasn't added
	 */
	public boolean insertTagList(String tagName, int libraryFK){
		TagDAO tagDAO = new TagDAO(connection);
		int tagFK = tagDAO.insertTag(tagName);
		if(tagFK < 1) return false;
		
		boolean result = false;
		try {
			PreparedStatement statement = connection.prepareStatement(INSERT_TAGLIST);
			statement.setString(1, new Integer(libraryFK).toString());
			statement.setString(2, new Integer(tagFK).toString());
			statement.executeUpdate();
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}	
	
	/**
	 * Get all tags by library id
	 * @param libraryFK id of library
	 * @return array of TagListBean objects
	 */
	public TagListBean[] getAllByLibraryFK(int libraryFK){
		LinkedList<TagListBean> list = new LinkedList<>();		
		
		try{
			PreparedStatement statement = connection.prepareStatement(GET_ALL_BY_LIBRARY_FK);
			statement.setString(1, new Integer(libraryFK).toString());
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()){
				list.add(readTagListBean(resultSet));
			}
		} catch (Exception e) {			
			e.printStackTrace();
			return null;
		}
		
		TagListBean[] result = new TagListBean[list.size()];
		return list.toArray(result);
	}
	
	public Collection<Integer> getAllLibraryIdByTag(String tag){
		Collection<Integer> list = new LinkedList<>();
		
		try {
			PreparedStatement statement = connection.prepareStatement(GET_IDLIBRARY_BY_TAG);
			statement.setString(1, tag);
			ResultSet set = statement.executeQuery();
			while(set.next()){
				list.add(Integer.parseInt(set.getString("library_fk")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * Read TagListBean from resultSet of a request
	 * @param set ResultSet object
	 * @return TagListBean objects
	 * @throws SQLException result set is bad
	 */
	private TagListBean readTagListBean(ResultSet set) throws SQLException{		
		int id = Integer.parseInt(set.getString("idtaglist"));
		int libraryFK = Integer.parseInt(set.getString("library_fk"));
		int tagFK = Integer.parseInt(set.getString("tag_fk"));
		String name = set.getString("name");
		return new TagListBean(id, libraryFK, tagFK, name);					
	}
	
}
