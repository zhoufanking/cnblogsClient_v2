package org.CnBlogsClient.Application;

import org.CnBlogsClient.Common.Log;
import org.CnBlogsClient.Controller.Controller;

public class MainApp {

	public static void main(String[] args) {
		
		Controller ctrl = new Controller();
		ctrl.Update(5);
				
		ctrl.print();
	}
}
