package ua.nau.libstore.controller;

import java.util.LinkedList;

import ua.nau.libstore.database.Connector;
import ua.nau.libstore.database.bean.TagListBean;
import ua.nau.libstore.database.dao.TagDAO;
import ua.nau.libstore.database.dao.TagListDAO;

public class TagListController {

	/**
	 * Get all tag names
	 * @return return string array of tags
	 */
	public String[] getAllTagNames(){
		TagDAO tagDAO = new TagDAO(Connector.getInstance());
		return tagDAO.getAllTags();
	}
	
	public String getTagsByLibraryFK(int libraryFK){
		TagListDAO listDAO = new TagListDAO(Connector.getInstance());
		TagListBean[] tagListBeans = listDAO.getAllByLibraryFK(libraryFK);
		LinkedList<String> list = new LinkedList<>();
		for (int i = 0; i < tagListBeans.length; i++) {
			list.add(tagListBeans[i].getTagName());
		}
		String[] tags = new String[list.size()];
		list.toArray(tags);
		return DataController.formatTagsString(tags);
	}
	
}
