package ua.nau.libstore.database.bean;
/**
 * Describes image bean for image table
 * @author Geniy
 *
 */
public class ImageBean {
	
	int id;
	int libraryFK;
	String name;
	byte[] data;
	
	
	public ImageBean(int id, int libraryFK, String name, byte[] data) {
		super();
		this.id = id;
		this.libraryFK = libraryFK;
		this.name = name;
		this.data = data;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getLibraryFK() {
		return libraryFK;
	}
	public void setLibraryFK(int libraryFK) {
		this.libraryFK = libraryFK;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}	
}
