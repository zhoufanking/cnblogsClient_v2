package org.CnBlogsClient.Resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.CnBlogsClient.Common.Log;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//test the dir and file create -----------start-------------
		File fpRoot = new File(Res.getRootdir());
		fpRoot.mkdir();
		
		Log.setLogState(true);
		Log.print("ResTest", Res.getRootdir()+"is created");
		
		System.out.println(Res.getRootdir());
		
		System.out.println(Res.getContentsDir());
		File fpContetn = new File(Res.getContentsDir());
		fpContetn.mkdir();
		Log.print("ResTest", Res.getContentsDir()+"is created");
		
		File fpPics = new File(Res.getImgDir());
		fpPics.mkdir();
		Log.print("ResTest", Res.getImgDir()+"is created");
		
		File fpcfg = new File(Res.getCfgFilePath());
		
		PrintWriter out = null;
		try {
			out = new PrintWriter(fpcfg);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i = 10; i>0; i--){
			out.println(i);
		}
		
		out.close();
		//test the dir and file create -----------end-------------
		

	}

}
