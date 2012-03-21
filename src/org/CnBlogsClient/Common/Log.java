package org.CnBlogsClient.Common;

public class Log {
	private static boolean LogState =false;
	
	public static void setLogState(boolean state){
		LogState = state;
	}
	public static void print(String FuncName,String Text){
		if(LogState)
			System.out.println(FuncName+" "+Text);
	}
	
}
