package ua.nau.libstore.database.bean;

/**
 * Describe taglist table in database, but it contains addition field tagName.
 * This bean is used for saving tags of each library.
 * @author Geniy
 *
 */
public class TagListBean {
	
	private int id;
	private int libraryFK;
	private int tagFK;
	private String tagName;
	
	public TagListBean(){
		id = -1;
		libraryFK = -1;
		tagFK = -1;
		tagName = "";
	}
	
	public TagListBean(int id, int libraryFK, int tagFK, String tagName) {
		super();
		this.id = id;
		this.libraryFK = libraryFK;
		this.tagFK = tagFK;
		this.tagName = tagName;
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
	
	public int getTagFK() {
		return tagFK;
	}
	public void setTagFK(int tagFK) {
		this.tagFK = tagFK;
	}
	
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	
}
