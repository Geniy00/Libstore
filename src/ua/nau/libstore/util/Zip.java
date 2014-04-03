package ua.nau.libstore.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Archive and extract files and directories
 * @author Geniy
 *
 */
public class Zip {
	
	/**
	 * Archive a directory to zip-archive
	 * @param dirPath path to directory for archiving
	 * @param zipFilePath path to an archive will be created
	 * @return true - action was archived successfully; false - archive wasn't created
	 */
	public static boolean archiveDir(String dirPath, String zipFilePath) {
		boolean result = false;
		
		File dirFile = new File(dirPath);
		ZipOutputStream out;
		try {
			out = new ZipOutputStream(new FileOutputStream(zipFilePath));
			String prefix = dirFile.getAbsolutePath().replaceAll("\\\\", "\\\\\\\\");
			addDir(dirFile, out, prefix);
			out.close();
			result = true;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return result;
	}

	/**
	 * Add new dir to the list for every iteration
	 * @param dirFile an object that may be a file or a directory
	 * @param out ZipOutputStream object
	 * @throws IOException
	 */
	private static void addDir(File dirFile, ZipOutputStream out, String prefix) throws IOException {
		File[] files = dirFile.listFiles();
		
		for (int i = 0; i < files.length; i++) {
			
			if (files[i].isDirectory()) {
				addDir(files[i], out, prefix);
				continue;
			}
			
			FileInputStream in = new FileInputStream(files[i].getAbsolutePath());
			
			String absolutePath = files[i].getAbsolutePath();
			String path = absolutePath.replaceFirst(prefix, "");		
			out.putNextEntry(new ZipEntry(path));
			
			int len;
			byte[] tmpBuf = new byte[1024];
			while ((len = in.read(tmpBuf)) > 0) {
				out.write(tmpBuf, 0, len);
			}
			
			out.closeEntry();
			in.close();
		}
	}
	
	/**
	 * Extract zip file to directory
	 * @param zipFilePath path to zip file
	 * @param destinationPath path to directory of extracting
	 * @return true - zip was extracted successfully, false - archive wasn't extracted
	 */
	public static boolean extractFile(String zipFilePath, String destinationPath){
		boolean result = false;
		 
		try{
			ZipInputStream in = new ZipInputStream(new FileInputStream(zipFilePath));			
			extract(in, destinationPath);
			in.close();
			
			result = true;
		} catch(IOException e){
			e.printStackTrace();
		} 
		
		return result;
	}
	
	/**
	 * Extract zip file to directory
	 * @param in ZipInputStream object from there extracting was
	 * @param destinationPath a directory where files will be extracted
	 * @throws IOException
	 */
	private static void extract(ZipInputStream in, String destinationPath) throws IOException {
				
		ZipEntry zipEntry = in.getNextEntry();
		while (zipEntry != null) {
			String entryName = zipEntry.getName();

			File newFile = new File(entryName);
			String directory = newFile.getParent();

			if (directory == null) {
				if (newFile.isDirectory())
					break;
			}
			
			new File(destinationPath + directory.replaceAll("\\\\", "/")).mkdirs();			
			FileOutputStream fos = new FileOutputStream(destinationPath + entryName);

			int len;
			byte[] buf = new byte[1024];
			while ((len = in.read(buf, 0, 1024)) > -1) {
				fos.write(buf, 0, len);
			}
			fos.close();
			in.closeEntry();
			zipEntry = in.getNextEntry();
		}
		in.close();
	}
	
}
