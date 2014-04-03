package ua.nau.libstore.controller;

import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.StringTokenizer;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import ua.nau.libstore.database.bean.LibraryBean;

public class DataController {
	
	/**
	 * Column names in result tables
	 */
	public static final Object[] columnIdentifiers = {"Name", "Short description", "Language", "Version", "Raiting",  "Date" };
	
	
	/**
	 * Get LibraryBean instance from TableModel by row index
	 * @param model model for getting data
	 * @param rowIndex row number where important data is
	 * @return LibraryBean object
	 */
	public static LibraryBean getLibraryBeanByModel(TableModel model, int rowIndex){
		LinkedHashMap<String, String> map = new LinkedHashMap<>();  //<columnName, cellValue>
		for(int i = 0; i < model.getColumnCount(); i++){			
			map.put(model.getColumnName(i), model.getValueAt(rowIndex, i).toString());
		}
		
		String name = map.get("Name");
		String shortDescription = map.get("Short description");
		String version = map.get("Version");
		String language = map.get("Language");
		Manager manager = Manager.getInstance();
		return manager.Library.getLibrary(name, shortDescription, version, language);
	}
	
	/**
	 * Fill TableModel by LibraryBean object
	 * @param model filled table model
	 * @param libraryBeans library that will be filled
	 */
	public static void fillLibraryBeanToModel(DefaultTableModel model, LibraryBean[] libraryBeans){
		model.getDataVector().removeAllElements();
		
		if(libraryBeans == null || libraryBeans.length == 0){
			model.setColumnIdentifiers(new String[]{"Nothing was found"});
			return;
		}					
		model.setColumnIdentifiers(columnIdentifiers);
		
		for(int i = 0; i < libraryBeans.length; i++){
			Object[] row = new Object[columnIdentifiers.length];
			row[0] = libraryBeans[i].getName();
			row[1] = libraryBeans[i].getShortDescription();
			row[2] = libraryBeans[i].getLanguage().name();
			row[3] = libraryBeans[i].getVersion();
			row[4] = String.format("%.1f", libraryBeans[i].getRaiting());
			row[5] = new SimpleDateFormat("dd.MM.yyyy").format(libraryBeans[i].getDate());
			
			model.insertRow(i, row);
		}
	}
	
	/**
	 * Parse string that consists of tags and build String[]
	 * "tag1, tag2; tag3." ==> new Sting[]{"tag1","tag2","tag3"}
	 * @param tags string that consists of tags
	 * @return String array
	 */
	public static String[] parseTags(String tags){
		LinkedList<String> list = new LinkedList<>();
		
		StringTokenizer st = new StringTokenizer(tags, " ,.;|");
		while(st.hasMoreTokens()){
			String str = st.nextToken();
			if(list.contains(str) == false) {
				list.add(str);
			}
		}
		String[] result = new String[list.size()];
		return list.toArray(result);
	}
	
	/**
	 * Format tags array to well-formatted string
	 * @param tags string that consists of tags
	 * @return well-formated string
	 */
	public static String formatTagsString(String[] tags){
		
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < tags.length; i++) {
			sb.append(tags[i] + "; ");
		}		
		return sb.toString().trim();
	}
	
	/**
	 * Format string to well-formatted view
	 * "tag1, tag2; tag3." ==> "tag1; tag2; tag3;"
	 * @param tags string that consists of tags
	 * @return well-formated string
	 */
	public static String formatTagsString(String tags){
		String[] array = parseTags(tags);
		
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			sb.append(array[i] + "; ");
		}		
		return sb.toString().trim();
	}

}
