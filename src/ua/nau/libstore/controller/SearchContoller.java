package ua.nau.libstore.controller;



import ua.nau.libstore.database.Connector;
import ua.nau.libstore.database.bean.LibraryBean;
import ua.nau.libstore.database.dao.SearchDAO;
import ua.nau.libstore.util.LanguageTypeEnum;

/**
 * Search controller
 * @author Geniy
 *
 */
public class SearchContoller {
	
	/**
	 * Smart search in natural(hard) mode.
	 * You will get only main records.
	 * You can't use operators +, -, *, <>, ~ in queries
	 * @param query user query
	 * @return LibraryBean array of results
	 */
	public LibraryBean[] searchInNaturalMode(String query){
		if(query == null || query.length() < 3) return null;
		
		SearchDAO searchDAO = new SearchDAO(Connector.getInstance());
		return searchDAO.searchInNaturalMode(query);
	}
	
	/**
	 * Smart search in boolean(light) mode.
	 * You will get many and all possible recors
	 * You can use operators +, -, *, <>, ~ in queries
	 * @param query user query
	 * @return LibraryBean array of results
	 */
	public LibraryBean[] searchInBooleanMode(String query){
		if(query == null || query.length() < 3) return null;
		
		SearchDAO searchDAO = new SearchDAO(Connector.getInstance());
		return searchDAO.searchInBooleanMode(query);
	}
	
	/**
	 * Show libraries by data: language and tags
	 * @param language language of library
	 * @param tags tags of library
	 * @return LibraryBean[] instance
	 */
	public LibraryBean[] showByData(LanguageTypeEnum language, String tags) {
		String[] tagsArray = DataController.parseTags(tags);
		SearchDAO searchDAO = new SearchDAO(Connector.getInstance());

		if (language != LanguageTypeEnum.UNDEFINED && tags.length() > 0) {
			return searchDAO.showByLanguageAndTags(language, tagsArray);
		} else if (language != LanguageTypeEnum.UNDEFINED && tags.length() == 0) {
			return searchDAO.showByLanguage(language);
		} else if (language == LanguageTypeEnum.UNDEFINED && tags.length() > 0) {
			return searchDAO.showByTags(tagsArray);
		} else
			return searchDAO.showAllLibrary();
	}

	public LibraryBean[] searchOtherVersion(String name, LanguageTypeEnum language){
		SearchDAO searchDAO = new SearchDAO(Connector.getInstance());
		return searchDAO.searchByNameAndLanguage(name, language);
	}
	
	public LibraryBean[] searchRelated(String tags){
		SearchDAO searchDAO = new SearchDAO(Connector.getInstance());
		String[] tagArray = DataController.parseTags(tags);
		return searchDAO.showByTags(tagArray);
	}
}
