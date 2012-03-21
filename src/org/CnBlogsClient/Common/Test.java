package org.CnBlogsClient.Common;

import java.util.Iterator;

public class Test {
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Link list test section----start
		Log.setLogState(true);
		LinkList<Integer> list = new LinkList<Integer>();
		for(int i = 0; i<10; i++)
			list.add(i);
		Iterator<Integer> aIterator = list.iterator();
		
		while(aIterator.hasNext())
			Log.print("LListTest", aIterator.next()+"");

		for(int k : list)
			System.out.println(k);	
		//Link list test section----end
		
		LinkList<ItemInfo> items = new LinkList<ItemInfo>();
		for(int i = 0; i<10; i++){
			ItemInfo item = new ItemInfo();
			item.setItemName("name"+i);
			item.setItemSummery("summ"+i);
			items.add(item);
		}
		for(ItemInfo k : items)
			System.out.println(k.toString());	
		
	}
}
