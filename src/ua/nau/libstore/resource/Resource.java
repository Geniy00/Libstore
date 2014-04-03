package ua.nau.libstore.resource;

import java.util.ResourceBundle;

/**
 * Get resources from resource property file by key string
 * @author Geniy
 *
 */
public class Resource {

	/**
	 * The path to property file
	 */
	public static final String RESOURCE_PATH = "ua.nau.libstore.resource.Resource";
	
	/**
	 * Resource object that was created by path to property file
	 */
	private static ResourceBundle resource = ResourceBundle.getBundle(RESOURCE_PATH);
	
	/**
	 * Get string by key string
	 * @param key key string for resource
	 * @return string by key 
	 */
	public static String getString(String key){
		return resource.getString(key);
	}
}
