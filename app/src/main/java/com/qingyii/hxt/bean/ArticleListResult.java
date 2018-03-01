package com.qingyii.hxt.bean;

import com.andbase.library.http.model.AbResult;

import java.util.List;
//import com.ab.model.AbResult;

/**
 * 
 *
 */
public class ArticleListResult extends AbResult {

	private List<Article> items;

	public List<Article> getItems() {
		return items;
	}

	public void setItems(List<Article> items) {
		this.items = items;
	}
	
	

}
