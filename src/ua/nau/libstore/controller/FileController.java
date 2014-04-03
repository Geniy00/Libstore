package ua.nau.libstore.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import ua.nau.libstore.database.Connector;
import ua.nau.libstore.database.bean.FileBean;
import ua.nau.libstore.database.dao.FileDAO;
import ua.nau.libstore.resource.Resource;
import ua.nau.libstore.util.Zip;

public class FileController {

	public byte[] readFileAsByteArray(String pathDir){
		byte[] result = null;
		try {
			File file = new File(pathDir);
			FileInputStream fis = new FileInputStream(file);
			
			if(file.length() > Integer.MAX_VALUE){
				System.err.println("File too long");
				return null;
			}
			
			byte[] fileContent = new byte[(int)file.length()];
			fis.read(fileContent);
			fis.close();
			result = fileContent;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Add new file to DB
	 * @param dirPath path to file
	 * @param libraryFK id of library that this file is belonged
	 * @return true - library was added; false - library wasn't added
	 */
	public boolean addFile(String dirPath, int libraryFK){
		
		//Creating paths
		String archiveFileName = new File(dirPath).getName() + ".zip";
		String archiveDirectoryPath = Resource.getString("path.temp.zip") + libraryFK + "/";
		new File(archiveDirectoryPath).mkdirs(); 		
		String zipFileArchivePath = archiveDirectoryPath + archiveFileName;
		
		//Archiving directory(file)
		boolean isArchived = Zip.archiveDir(dirPath, zipFileArchivePath);
		if(isArchived == false){
			System.err.println("File wasn't archive");
			return false;
		}
					
		//Read archive file
		byte[] data = readFileAsByteArray(zipFileArchivePath);		
		if(data == null){
			System.err.println("Data wasn't read");
			return false;
		}
		
		FileBean fileBean = new FileBean(-1, libraryFK, libraryFK + "_"+ archiveFileName, data);
		FileDAO fileDAO = new FileDAO(Connector.getInstance());
		int id = fileDAO.insertFile(fileBean);
		
		if(id > 0) return true;
		else return false;
	}
	
	/**
	 * Get FileBean object by ID
	 * @param libraryFK id of file
	 * @return FileBean object
	 */
	public FileBean getFileBean(int libraryFK){
		FileDAO fileDAO = new FileDAO(Connector.getInstance());
		int id = fileDAO.getIdByLibraryFK(libraryFK);
		return fileDAO.getFileBean(id);
	}
	
	/**
	 * Save archive file from database on the hard disk
	 * @param libraryFK id of file
	 * @param pathDir path for saving
	 * @return true - file was saved; false - file wasn't saved
	 */
	public boolean saveFile(int libraryFK, String pathDir){				
		boolean result = false;
		pathDir = pathDir.replace("\\", "/");
		new File(pathDir).mkdirs();
		
		FileBean fileBean = getFileBean(libraryFK);
		if(fileBean == null) return false;
		
		try {
			File file = new File(pathDir  + "/" + fileBean.getName());
			file.createNewFile();
			
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(fileBean.getData());
			fos.close();
			
			result = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Extract an archive in directory
	 * @param libraryFK of file
	 * @param destinationDir destination directory for extracting
	 * @return true - file was extracted; false - file wasn't extracted
	 */
	public boolean extractFile(int libraryFK, String destinationDir){
		destinationDir = destinationDir.replace("\\", "/") + "/";		
		
		FileBean fileBean = getFileBean(libraryFK);
		if(fileBean == null) return false;
		
		boolean isSaved = saveFile(libraryFK, Resource.getString("path.temp.zip"));
		if(isSaved == false) return false;
		
		String zipFilePath = Resource.getString("path.temp.zip") + fileBean.getName();
		/**
		 * REMOVE THIS LINE!!!!!
		 */
		//System.err.println("Destination dir for extracting is static");
		//destinationDir = Resource.getString("path.temp.unzip") + fileBean.getLibraryFK();
		
		new File(destinationDir).mkdirs();
		
		boolean extracted = Zip.extractFile(zipFilePath, destinationDir);
		if(extracted == false) return false;
		
		return true;
	}
	
}
