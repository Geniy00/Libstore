package ua.nau.libstore.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ua.nau.libstore.database.bean.ImageBean;

/**
 * Data access object to image table
 * @author Geniy
 *
 */
public class ImageDAO {
	
	private static final String INSERT_IMAGE = "insert into image (library_fk, name, data) value (?, ?, ?)";
	private static final String GET_IMAGE_BY_ID = "select * from image where idimage =?";
	private static final String GET_ID_BY_LIBRARY_FK = "select * from image where library_fk=?";
	private static final String REMOVE_BY_ID = "delete from image where idimage =?";
	
	private Connection connection;

	public ImageDAO(Connection connection) {
		this.connection = connection;
	}
	
	/**
	 * Store image in database
	 * @param image ImageBean object
	 * @return id of image
	 */
	public int insertImage(ImageBean image){
		int result = -1;
		
		try {
			PreparedStatement statement = connection.prepareStatement(INSERT_IMAGE);
			statement.setString(1, new Integer(image.getLibraryFK()).toString());
			statement.setString(2, image.getName());
			statement.setObject(3, image.getData());
			statement.executeUpdate();
			
			result = getIdByLibraryFK(image.getLibraryFK());
		} catch (SQLException e) {
			e.printStackTrace();
			result = -1;
		}
		return result;
	}
	
	/**
	 * Read ImageBean object from DB
	 * @param idimage shearch images that have this id
	 * @return ImageBean object
	 */
	public ImageBean getImage(int idimage){
		
		ImageBean imageBean = null;
		
		try {
			PreparedStatement statement = connection.prepareStatement(GET_IMAGE_BY_ID);
			statement.setString(1, new Integer(idimage).toString());
			ResultSet resultSet = statement.executeQuery();
			
			if(resultSet.next() == false) return null;
			
			idimage = Integer.parseInt(resultSet.getString("idimage"));
			int libraryFK = Integer.parseInt(resultSet.getString("library_fk"));
			String name = resultSet.getString("name");
			byte[] data = (byte[])resultSet.getObject("data");
			
			imageBean = new ImageBean(idimage, libraryFK, name, data);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		return imageBean;
	}
	
	/**
	 * Get id of image by library foreign key
	 * @param libraryFK id of foreign library
	 * @return id number
	 */
	public int getIdByLibraryFK(int libraryFK){
		int result = -1;
		try {
			PreparedStatement statement = connection.prepareStatement(GET_ID_BY_LIBRARY_FK);
			statement.setString(1, new Integer(libraryFK).toString());
			ResultSet set = statement.executeQuery();
			
			if(set.next()){
				result = Integer.parseInt(set.getString("idimage"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			result = -1;
		}
		return result;
	}
	
	/**
	 * Remove image by ID
	 * @param id id of image
	 * @return true - image was deleted; false - image wasn't deleted
	 */
	public boolean revome(int id){
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
