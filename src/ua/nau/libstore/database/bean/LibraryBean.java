package ua.nau.libstore.database.bean;

import java.util.Date;

import ua.nau.libstore.util.LanguageTypeEnum;

/**
 * Describe library table in database
 * @author Geniy
 *
 */
public class LibraryBean {
	
	private int id;
	private String name;
	private String shortDescription;
	private String fullDescription;	
	/**
	 * Value must have view as "1.2". Two numbers is divided by dot
	 */
	private String version;	
	private LanguageTypeEnum language;	
	/**
	 * Value must be between 0 and 5;
	 */
	private float raiting;
	/**
	 * Value must be between 0 and 10. The value 0 means that this library wasn't tested
	 */
	private int testRaiting;
	private String comment;
	private Date date;
	private String require;
	
	
	public LibraryBean(){
		id = -1;
		name = "";
		shortDescription = "";
		fullDescription = "";
		version = "0.0";
		language = LanguageTypeEnum.UNDEFINED;
		raiting = 0;
		testRaiting = 0;
		comment = "";
		date = new Date(0);
		require = "";
	}
	
	public LibraryBean(int id, String name, String shortDescription, String fullDescription,
			String version, LanguageTypeEnum language, float raiting, int testRaiting,
			String comment, Date date, String require) {
		super();
		this.id = id;
		this.name = name;
		this.shortDescription = shortDescription;
		this.fullDescription = fullDescription;
		this.version = version;
		this.language = language;
		this.raiting = raiting;
		this.testRaiting = testRaiting;
		this.comment = comment;
		this.date = date;
		this.require = require;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	
	public String getFullDescription() {
		return fullDescription;
	}
	public void setFullDescription(String fullDescription) {
		this.fullDescription = fullDescription;
	}
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	public LanguageTypeEnum getLanguage() {
		return language;
	}
	public void setLanguage(LanguageTypeEnum language) {
		this.language = language;
	}
	
	public float getRaiting() {
		return raiting;
	}
	public void setRaiting(float raiting) {
		this.raiting = raiting;
	}
	
	public int getTestRaiting() {
		return testRaiting;
	}
	public void setTestRaiting(int testRaiting) {
		this.testRaiting = testRaiting;
	}
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public Date getDate() {
		return date;	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getRequire() {
		return require;
	}
	public void setRequire(String require) {
		this.require = require;
	}
	
}
