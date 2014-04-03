package ua.nau.libstore.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import ua.nau.libstore.database.Connector;
import ua.nau.libstore.database.bean.ImageBean;
import ua.nau.libstore.database.dao.ImageDAO;

/**
 * Image manager class
 * @author Geniy
 *
 */
public class ImageController {

	/**
	 * Get byte array from image
	 * @param imagePath path to image on the hard disk
	 * @return
	 */
	public byte[] readImageAsByteArray(String imagePath){
		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream(1000);
			BufferedImage img  = ImageIO.read(new File(imagePath));
			ImageIO.write(img, "jpg", baos);   ///XXX change *.jpg
			baos.flush();
			
			return baos.toByteArray();
			
		}catch(IOException e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Add image to DB
	 * @param imagePath path to an image on the hard disk
	 * @param libraryFK id of library this file belongs
	 * @return true - image was added; false - image wasn't added
	 */
	public boolean addImage(String imagePath, int libraryFK){
				
		byte[] data = readImageAsByteArray(imagePath);
		ImageBean imageBean = new ImageBean(0, libraryFK, imagePath, data);
		
		ImageDAO imageDAO = new ImageDAO(Connector.getInstance());
		int imageFK = imageDAO.insertImage(imageBean);
		
		if(imageFK > 0) return true;
		else return false;		
	}
	
	/**
	 * Get image by libraryFK id
	 * @param libraryFK id of image
	 * @return ImageBean object
	 */
	public ImageBean getImageBean(int libraryFK){
		
		ImageDAO imageDAO = new ImageDAO(Connector.getInstance());		
		int imageID =  imageDAO.getIdByLibraryFK(libraryFK);
		return imageDAO.getImage(imageID);
	}
	
	/*
	public BufferedImage getImage(int id){
		ImageBean bean = getImageBean(id);
		
		BufferedImage bufferedImage = null;
		try {
			
			bufferedImage = ImageIO.read(new ByteArrayInputStream(bean.getData()));
			//bufferedImage = bufferedImage.getScaledInstance(100, 100, 1);
		} catch (IOException e) {
			e.printStackTrace();
			bufferedImage = null;
		}
		return bufferedImage;
	}
	*/
}
