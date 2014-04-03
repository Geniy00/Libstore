package ua.nau.libstore.controller;

import java.sql.Connection;
import ua.nau.libstore.database.Connector;
import ua.nau.libstore.database.bean.LibraryBean;
import ua.nau.libstore.database.dao.LibraryDAO;
import ua.nau.libstore.database.dao.TagListDAO;
import ua.nau.libstore.util.LanguageTypeEnum;

public class LibraryController {
	
	/**
	 * Add new library record to DB
	 * @param libraryBean LibraryBean object for adding
	 * @param dirPath path to library's file or derectory
	 * @param imagePath path to library's image
	 * @return true - library was added; false - library wasn't added
	 */
	public boolean add(LibraryBean libraryBean, String dirPath, String imagePath, String tags){
		
		Connection connection = Connector.getInstance();
			
		boolean isRollback = false;
		
		//Library meta-data
		LibraryDAO libraryDAO = new LibraryDAO(connection);		
		int libraryID = libraryDAO.insertLibrary(libraryBean);		
		if(libraryID < 1){
			isRollback = true;
		}
		
		//Image
		if(isRollback == false)	{
			if(imagePath != null && imagePath.length() > 8){
				ImageController imageManager = new ImageController();
				if(imageManager.addImage(imagePath, libraryID) == false){
					isRollback = true;
				}
			}
		}
		
		//File
		if(isRollback == false)	{
			FileController fileController = new FileController();
			if(fileController.addFile(dirPath, libraryID) == false) isRollback = true;
		}		
		
		//Tags
		if(isRollback == false){
			String[] tagArray = DataController.parseTags(tags);
			TagListDAO tagListDAO = new TagListDAO(connection);
			for(int i = 0; i < tagArray.length && isRollback == false; i++)
			{
				if(tagListDAO.insertTagList(tagArray[i], libraryID) == false){
					isRollback = true;
				}
			}			
		}
		
		//Rollback
		if(isRollback == true){
			int id = libraryDAO.getIdByData(libraryBean.getName(), libraryBean.getShortDescription(), 
					libraryBean.getVersion(), libraryBean.getLanguage());
			libraryDAO.remove(id);
			return false;
		} else{
			return true;
		}	
	}
	
	/**
	 * Get LibraryBean object from table by ID
	 * @param id library's id
	 * @return LibraryBean object
	 */
	public LibraryBean getLibrary(int id){
		LibraryDAO libraryDAO = new LibraryDAO(Connector.getInstance());
		return libraryDAO.getLibrary(id);
	}
	
	/**
	 * Get LibraryBean instance from database by data
	 * @param name name of library
	 * @param shortDescription shortDescription of library
	 * @param version version value of library
	 * @param language language of library
	 * @return LibraryBean instance
	 */
	public LibraryBean getLibrary(String name, String shortDescription, String version, String language){
		LibraryDAO libraryDAO = new LibraryDAO(Connector.getInstance());		
		int id = libraryDAO.getIdByData(name, shortDescription, version, LanguageTypeEnum.valueOf(language));
		return libraryDAO.getLibrary(id);
	}
	
	public boolean edit(int id, LibraryBean libraryBean, String filePath, String imagePath){
		throw new UnsupportedOperationException();
	}
	
	
	public boolean remove(int id){
		throw new UnsupportedOperationException();
	}
}
