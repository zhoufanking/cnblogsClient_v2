package org.CnBlogsClient.Storage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Iterator;
import java.util.Vector;

import org.CnBlogsClient.Common.ItemInfo;
import org.CnBlogsClient.Common.LinkList;
import org.CnBlogsClient.Common.Log;
import org.CnBlogsClient.Resource.Res;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

public class StoreCnBlogs implements Storage {

	private Document Doc;
	private Element root;
	private XMLOutputter XMLOut;

	public StoreCnBlogs() {
		root = new Element("ItemList");
		Doc = new Document(root);

		// xml related
		XMLOut = new XMLOutputter();
	}

	@Override
	public void init() {

		File fpRoot = new File(Res.getRootdir());
		if (!fpRoot.mkdir()) {
			Log.print("StoreCnBlogs::constructor", "create root dir fiald!");
			System.exit(-1);
		}

		File fpContent = new File(Res.getContentsDir());
		if (!fpContent.mkdir()) {
			Log.print("StoreCnBlogs::constructor", "create content dir fiald!");
			System.exit(-1);
		}

		File fpImgs = new File(Res.getImgDir());
		if (!fpImgs.mkdir()) {
			Log.print("StoreCnBlogs::constructor", "create image dir fiald!");
			System.exit(-1);
		}
	}

	private void addCfgItem(ItemInfo item) {
		Element ele = new Element("Item");

		if (!item.getTags().equals(null)) {
			String tagStr = null;
			Iterator<String> itor = item.getTags().iterator();
			while (itor.hasNext()) {
				tagStr += itor.next();
			}
			ele.addContent(new Element("Tags").setText(tagStr));
		}

		ele.addContent(new Element("Name").setText(item.getItemName()));
		ele.addContent(new Element("Summery").setText(item.getItemSummery()));
		ele.addContent(new Element("PostTime").setText(item.getPostTime()));
		ele.addContent(new Element("WebLink").setText(item.getItemLink()));
		ele.addContent(new Element("LocalPath").setText(item.getLocalPath()));

		root.addContent(ele);
	}

	@Override
	public void storeItem(ItemInfo item) {
		Vector<String> picPaths = new Vector<String>();
		//for each item fetch contents , fetch picslinks ,     ok
		//store contents, store pics, 						ok
		//modify the content's pic links  					ok
				
		//flush the cfg file
		/**
		 * 1.fetch pics and modify the pic web links in the content to the local path 
		 * 2.store the content to the local driver
		 * 3.insert a new item to the config file
		 */
		
		picPaths = StorePics(item);
		StoreContent(item);
		
		addCfgItem(item);
	}

	@Override
	public LinkList<ItemInfo> LoadCfgFile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void FlushCfg() {
		try {
			XMLOut.output(Doc, new FileOutputStream(Res.getCfgFilePath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void StoreContent(ItemInfo item) {
		// create the file according the item name	
		String cfPath = Res.getContentsDir() + File.separator+ item.getItemName() + ".html";
		File fp = new File(cfPath);
		
		
		PrintWriter output = null;

		try {
			output = new PrintWriter(fp);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		output.print(item.getContent());
		output.close();
		
		item.setLocalPath(cfPath);
	}

	private Vector<String> StorePics(ItemInfo item) {

		org.jsoup.nodes.Document adoc = Jsoup.parse(item.getContent());
		if(adoc.equals(null)){
			Log.print("SrorePics", "reade content file failed");
		}

		Elements Imgs = adoc.select("[src]");

		String imgLink = null;
		String imgPath = null;

		
		for (org.jsoup.nodes.Element src : Imgs) {

			if (src.tagName().equals("img")) {
				// obtain the image link
				imgLink = src.attr("abs:src");
				if (!imgLink.contains("?") && !imgLink.equals(null)) {

					// fetch the image and store it in a local dir
					imgPath = makeImg(imgLink, item.getItemName());
					Log.print("StorePics", imgPath);
				} else{
					
					imgPath = Res.getNullContentMsg();
				}
					
				// replace the image link in the post body from a web link to a
				// local link
				src.attr("src", imgPath);
			}
		}
		item.setContent(adoc.html());
		return null;
	}

	// 生成图片函数
	// return the image's local path
	private static String makeImg(String imgUrl, String DirName) {

		String imgDir = Res.getImgDir() + File.separator + DirName;
		File dirp = new File(imgDir);
		if(!dirp.exists() || dirp.isFile()){
			dirp.mkdir();
		}
		
		String path = null;

		if (imgUrl.equals(null) || imgDir.equals(null)) {
			path = "BADLINK OR DIRNAME";
			return path;
		}

		try {

			// 创建流
			BufferedInputStream in = null;
			try {
				in = new BufferedInputStream(new URL(imgUrl).openStream());
			} catch (Exception e) {
				path = "BADLINK";
				return path;
			} finally {
				if (in.equals(null)) {
					path = "BADLINK";
					return path;
				}
			}

			// 生成图片名
			int index = imgUrl.lastIndexOf("/");
			String sName = imgUrl.substring(index + 1, imgUrl.length());
			path = imgDir + File.separator + sName;
			System.out.println(sName);
			// 存放地址
			File img = new File(path);
			// 生成图片
			BufferedOutputStream out = new BufferedOutputStream(
					new FileOutputStream(img));
			byte[] buf = new byte[2048];
			int length = in.read(buf);
			while (length != -1) {
				out.write(buf, 0, length);
				length = in.read(buf);
			}
			in.close();
			out.close();
		} catch (Exception e) {
			// e.printStackTrace();
			return (path = "BADIMAGE");
		}

		return path;
	}

}
