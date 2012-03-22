package org.CnBlogsClient;

import org.CnBlogsClient.Common.ItemInfo;
import org.CnBlogsClient.Common.LinkList;

public interface Storage {
	public void init(String CfgFilePath, String ContentPath, String  ImgFilePath);
	public void addCfgItem(ItemInfo item);
	public void storeItem(ItemInfo item);
	public LinkList<ItemInfo> LoadCfgFile();

}
