package org.CnBlogsClient.Fetch;

import org.CnBlogsClient.Common.ItemInfo;
import org.CnBlogsClient.Common.LinkList;


public interface Fetch {
	public int getItemNum();
	public LinkList<ItemInfo> getItems(int ItemNums);//Number of Items fetch form the web 
	//public boolean Connect(String Address);
	
	public void getContent(ItemInfo item);
}
