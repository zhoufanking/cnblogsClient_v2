package org.CnBlogsClient.Controller;

import java.util.Iterator;

import org.CnBlogsClient.Common.*;
import org.CnBlogsClient.Fetch.Fetch;
import org.CnBlogsClient.Fetch.FetchFactory;

public class Controller {

	private Fetch  Fetcher;
//	private Storage Store;
	private LinkList<ItemInfo> Items;
	
	
	public Controller(){
		Fetcher = FetchFactory.newInstance();
		Items = null;
	}
	
	public int Update(int ItemCntDemand){
		
		Items = Fetcher.getItems(ItemCntDemand);
		
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
