package org.CnBlogsClient.Fetch;

public class FetchFactory {
	public static Fetch newInstance(){
		return new FetchCnBlogs();
	}
}
