package org.CnBlogsClient.Controller;

import org.CnBlogsClient.Common.*;
import org.CnBlogsClient.Fetch.Fetch;
import org.CnBlogsClient.Fetch.FetchFactory;
import org.CnBlogsClient.Storage.Storage;
import org.CnBlogsClient.Storage.StorageFactory;

public class Controller {

	private Fetch  Fetcher;
	private Storage st;
	
	private LinkList<ItemInfo> Items;
	
	
	public Controller(){
		Fetcher = FetchFactory.newInstance();
		st = StorageFactory.newInstance();
		Items = null;
	}
	
	public int Update(int ItemCntDemand){
		//for each item  store itme
				//flush the cfg file
				/**
				 * 1.get each item content
				 * 2.filter
				 * 3.store item
				 * 
				 */
		Items = Fetcher.getItems(ItemCntDemand);
		
		for( ItemInfo item : Items){
			Fetcher.getContent(item);
			Filter();
			st.storeItem(item);		
		}
		
		st.FlushCfg();
		
		return Items.Size();
	}
	
	//do some thing on the fetched items
	//throw out any duplicated data 
	public void Filter(){}

	public void print(){
		System.out.println(Items.Size());

		for(ItemInfo k : Items)
			System.out.println(k.toString());	
	}
	
}
