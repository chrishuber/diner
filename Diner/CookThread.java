/*
 * Chris Huber <chuber2@mail.ccsf.edu>
 * CS211S, Jessica Masters
 * 11/22/2020
 * Assignment Twelve: Concurrency
 */

import java.util.*;

public class CookThread implements Runnable {

	// declare properties
	private List<Food> foodList;
	
	

	public CookThread(List<Food> foodList) {
		// initialize values
		this.foodList = foodList;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < 3; i++) {
			synchronized (hotPass) {
				while (hotPass.isFilled()) {
					try {
						System.out.println("Chef waiting on server...");
						hotPass.wait();
					}
					catch (InterruptedException ex) { }
				}
				// cook item
				System.out.println("Chef adding item to hot pass");
				hotPass.notify();
			}
			try {
				System.out.println("Chef sleeping...");
				Thread.sleep(1000);
				System.out.println("Chef awake");
			}
			catch (InterruptedException ex) { }
		}
		System.out.println(this.foodList);
	}
}
