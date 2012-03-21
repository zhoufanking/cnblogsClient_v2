package org.CnBlogsClient.Common;

import java.util.Vector;

public class ItemInfo {

	public ItemInfo() {
		
		ItemName = null;
		ItemSummery = null;
		PostTime = null;
		Content = null;
		Tags = new Vector<String>();
	}
	
	public String getItemName() {
		return ItemName;
	}
	public void setItemName(String itemName) {
		ItemName = itemName;
	}
	public String getItemSummery() {
		return ItemSummery;
	}
	public void setItemSummery(String itemSummery) {
		ItemSummery = itemSummery;
	}
	public String getPostTime() {
		return PostTime;
	}
	public void setPostTime(String postTime) {
		PostTime = postTime;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public Vector<String> getTags() {
		return Tags;
	}
	public void setTags(Vector<String> tags) {
		Tags = tags;
	}
	public String getItemLink() {
		return ItemLink;
	}

	public void setItemLink(String itemLink) {
		ItemLink = itemLink;
	}
	
	public String toString(){
		String str = ItemName + "\n"
					+ItemSummery+ "\n"
					+PostTime + "\n"
					+ItemLink;
		return str;
	}
	
	private	String			ItemName;
	private	String			ItemSummery;
	private	String			PostTime;
	private	String			Content;
	private String 			ItemLink;
	


	private	Vector<String>	Tags;
	
	
	
	
}
