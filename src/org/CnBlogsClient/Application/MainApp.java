package org.CnBlogsClient.Application;

import org.CnBlogsClient.Controller.Controller;

public class MainApp {

	public static void main(String[] args) {
		Controller ctrl = new Controller();
		ctrl.Update(10);
		ctrl.print();
	}
}
