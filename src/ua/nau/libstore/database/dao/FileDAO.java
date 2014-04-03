package ua.nau.libstore.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ua.nau.libstore.database.bean.FileBean;

public class FileDAO {

	private static final String INSERT_FILE = "insert into file (library_fk, name, data) value(?, ?, ?)";
	private static final String GET_FILE_BY_ID = "select * from file where idfile=?";
	private static final String GET_ID_BY_LIBRARY_FK = "select * from file where library_fk=?";
	private static final String REMOVE_BY_ID = "delete from file where idfile =?";
	
	private Connection connection;
	
	public FileDAO(Connection connection) {
		this.connection = connection;
	}
	
	/**
	 * Insert new file to DB
	 * @param fileBean FileBean object
	 * @return id of added file
	 */
	public int insertFile(FileBean fileBean){
		int result = -1;
		
		try {
			PreparedStatement statement = connection.prepareStatement(INSERT_FILE);
			statement.setString(1, new Integer(fileBean.getLibraryFK()).toString());
			statement.setString(2, fileBean.getName());
			statement.setObject(3, fileBean.getData());
			statement.executeUpdate();
			
			result = getIdByLibraryFK(fileBean.getLibraryFK());
		} catch (SQLException e) {			
			e.printStackTrace();
			result = -1;
		}
		return result;
	}
	
	/**
	 * Get file by id
	 * @param id id of file
	 * @return FileBean object
	 */
	public FileBean getFileBean(int id){
		
		FileBean fileBean = null;
		try {
			PreparedStatement statement = connection.prepareStatement(GET_FILE_BY_ID);
			statement.setString(1, new Integer(id).toString());
			ResultSet set = statement.executeQuery();
			
			if(set.next() == false) return null;
			
			id = Integer.parseInt(set.getString("idfile"));
			int libraryFK = Integer.parseInt(set.getString("library_fk"));
			String name = set.getString("name");
			byte[] data = (byte[])set.getObject("data");
			
			fileBean = new FileBean(id, libraryFK, name, data);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fileBean;
	}
	
	/**
	 * Get file by library foreign key
	 * @param libraryFK library fk of file
	 * @return id of file
	 */
	public int getIdByLibraryFK(int libraryFK){
		int result = -1;
		try {
			PreparedStatement statement = connection.prepareStatement(GET_ID_BY_LIBRARY_FK);
			statement.setString(1, new Integer(libraryFK).toString());
			ResultSet set = statement.executeQuery();
			
			if(set.next() == false) return -1;
			
			result = Integer.parseInt(set.getString("idfile"));
			
		} catch (SQLException e) {
			e.printStackTrace();
			result = -1;
		}
		return result;
	}
	
	/**
	 * Remove file from DB
	 * @param id id of the file
	 * @return true - file was removed; false - file wasn't removed
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
