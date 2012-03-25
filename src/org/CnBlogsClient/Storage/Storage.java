package org.CnBlogsClient.Storage;

import org.CnBlogsClient.Common.ItemInfo;
import org.CnBlogsClient.Common.LinkList;

public interface Storage {
	public void init();
	//public void addCfgItem(ItemInfo item);
	public void storeItem(ItemInfo item);
	public LinkList<ItemInfo> LoadCfgFile();
	public void FlushCfg();

}
