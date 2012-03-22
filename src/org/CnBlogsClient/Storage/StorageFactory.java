package org.CnBlogsClient.Storage;

public class StorageFactory {

	public static Storage newInstance(){
		return new StoreCnBlogs();
	}
}
