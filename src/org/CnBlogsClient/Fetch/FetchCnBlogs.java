package org.CnBlogsClient.Fetch;


import java.io.IOException;
import java.util.Vector;

import org.CnBlogsClient.Common.ItemInfo;
import org.CnBlogsClient.Common.LinkList;
import org.CnBlogsClient.Common.Log;
import org.CnBlogsClient.Resource.Res;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FetchCnBlogs implements Fetch {

	private Vector<String> ItemName;
	private Vector<String> ItemSummery;
	private Vector<String> ItemPostTime;
	private Vector<String> ItemLink;
	private int 		   pages;
	private Document	   doc;
	private LinkList<ItemInfo>	   ItemData;
	
	
	public FetchCnBlogs(){
		ItemName 		= new Vector<String>();
		ItemSummery 	= new Vector<String>();
		ItemPostTime 	= new Vector<String>();
		ItemLink 		= new Vector<String>();
	
		pages			= 2;
		doc				= null;
		ItemData		= new LinkList<ItemInfo>();		
	}
	
	@Override
	public int getItemNum() {
		return ItemName.size();
	}

	@Override
	public LinkList<ItemInfo> getItems(int count) {
		int retry = 0;
		if(Connect(Res.getBaseAddress())){
			parser();
		}
		else{
			System.out.println(Res.getConFailueMsg());
		}

		
		while(ItemName.size() < count || retry >= Res.getRetrytimes()){
			if(Connect(Res.getFollowingAddress()+pages)){
				parser();
				pages++;
			}else
				retry++;
		}
		
		for(int lp = 0; lp< count; lp++){
			ItemInfo aItem = new ItemInfo();
			aItem.setItemName(ItemName.get(lp));
			aItem.setItemSummery(ItemSummery.get(lp));
			aItem.setPostTime(ItemPostTime.get(lp));
			aItem.setItemLink(ItemLink.get(lp));	
			
			ItemData.add(aItem);
		}
		return ItemData;
	}

	
	private boolean Connect(String Address) {
	
		for(int Retry = 0 ; (doc == null)&&(Retry < Res.getRetrytimes()); Retry++){
			try {
				
				doc = Jsoup.connect(Address).userAgent("Mozilla").timeout(Res.geTimeOut()).get();
				
			} catch (IOException e) {
					
				Log.print("FetchCnblogs::Connect", Res.getConRetryMsg()+"第"+(Retry+1)+"次");			
			}
		}
		
		if(doc == null)
			return false;
		else
			return true;
	}

	/**
	 * get post body and fetch the image links
	 */
	@Override
	public void getContent(ItemInfo item) {
		Document  adoc= null;
		for (int j = 0; adoc == null&&j < Res.getRetrytimes(); j++) {
				System.out.println("Startting fetching contets of"+ item.getItemName());
				try {
					adoc = Jsoup.connect(item.getItemLink())
							.userAgent("Mozilla").timeout(Res.geTimeOut()).get();
				} catch (IOException e) {
					
					System.out.println(Res.getConRetryMsg());
				}
			}
			if(adoc == null){
				System.out.println("fetch tips error, adoc is null");
				item.setContent(Res.getFileNotExistMsg());
			}
			else{
				item.setContent(adoc.getElementsByClass("post").html());
			}
		}	

	private void parser(){
		getItemName();
		getSummery();
		getPostTime();
		getPostLink();
	}
	
	private void getItemName(){
		
		Elements news = null;
		news = doc.getElementsByClass("titlelnk");

		for (Element item : news) {
			ItemName.add(item.text().replace("\u2014", "-")); // \u2014 unicode涓��绌烘�
			}
		}

	private void getSummery(){
		Elements summeries = null;
		
		summeries = doc.getElementsByClass("post_item_summary");
		
		if (summeries != null) {
			// System.out.println(news.text());

			for (Element summery : summeries) {
				ItemSummery.add(summery.text().replace("\u2014", "-"));
			}
		}
	}

	private void getPostTime(){
		String tempDate = null;
		Elements pstimes = null;
		
		pstimes = doc.getElementsByClass("post_item_foot");


		for (Element pstime : pstimes) {
			
			tempDate = pstime.text();
			
			int startPos = tempDate.lastIndexOf("发布于")+4;
			ItemPostTime.add(tempDate.substring(startPos, startPos+10));
		}
	}

	private void getPostLink(){
		Elements news = null;
		news = doc.getElementsByClass("titlelnk");

		for (Element item : news) {
			ItemLink.add(item.absUrl("href"));
			}
		}

//	
//	private Vector<String> getPicsLinks(Document  ContentDoc) {
//		Elements Imgs = ContentDoc.select("[src]");
//		
//		String imgLink = null;
//		
//		Vector<String> imgs = new Vector<String>();
//		
//		 for (Element src : Imgs) {
//			 
//			 if(src.tagName().equals("img")){
//				 //obtain the image link
//				 imgLink = src.attr("abs:src");
//				 if(!imgLink.contains("?")&&!imgLink.equals(null))
//					 //fetch the image and store it in a local dir
//					 //imgPath = makeImg(imgLink,Res.getImgDir()+File.separator);
//					 imgs.add(imgLink);					 
//				 else
//					 imgs.add(Res.getNullContentMsg());	
//				 //replace the image link in the post body from a web link to a local link
//				//src.attr("src", imgPath);
//		}
//	}
//		return imgs;
//	}
//	
	
	
}



	

