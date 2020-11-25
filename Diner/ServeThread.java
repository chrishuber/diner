/*
 * Chris Huber <chuber2@mail.ccsf.edu>
 * CS211S, Jessica Masters
 * 11/22/2020
 * Assignment Twelve: Concurrency
 */

import java.util.List;

public class ServeThread implements Runnable {
	// declare properties
	private List<Food> foodList;

	public ServeThread(List<Food> foodList) {
		// initialize values
		this.foodList = foodList;
	}
	
	@Override
	public void run() {
		System.out.println(this.foodList);
	}
}
