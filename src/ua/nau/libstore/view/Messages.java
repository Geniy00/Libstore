package ua.nau.libstore.view;

import java.beans.Beans;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
	////////////////////////////////////////////////////////////////////////////
	//
	// Constructor
	//
	////////////////////////////////////////////////////////////////////////////
	private Messages() {
		// do not instantiate
	}
	////////////////////////////////////////////////////////////////////////////
	//
	// Bundle access
	//
	////////////////////////////////////////////////////////////////////////////
	private static final String BUNDLE_NAME = "ua.nau.libstore.view.messages"; //$NON-NLS-1$
	private static final String BUNDLE_NAME_EN = "ua.nau.libstore.view.messages_en_US";
	private static final String BUNDLE_NAME_UK = "ua.nau.libstore.view.messages_uk_UA";
	private static ResourceBundle RESOURCE_BUNDLE = loadBundle();
	
	private static ResourceBundle loadBundle() {
		return ResourceBundle.getBundle(BUNDLE_NAME);
	}
	
	public static void setDefaultLanguage(){
		RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
	}
	
	public static void setEnglishLanguage(){
		RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME_EN);
	}
	
	public static void setUkraineLanguage(){
		RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME_UK);
	}
	
	////////////////////////////////////////////////////////////////////////////
	//
	// Strings access
	//
	////////////////////////////////////////////////////////////////////////////
	public static String getString(String key, String defaultValue) {
		try {
//			ResourceBundle bundle = Beans.isDesignTime() ? loadBundle() : RESOURCE_BUNDLE;
			ResourceBundle bundle = RESOURCE_BUNDLE;
			return bundle.getString(key);
		} catch (MissingResourceException e) {
			return defaultValue;
		}
	}
}
