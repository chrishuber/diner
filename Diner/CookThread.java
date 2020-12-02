/*
 * Chris Huber <chuber2@mail.ccsf.edu>
 * CS211S, Jessica Masters
 * 11/22/2020
 * Assignment Twelve: Concurrency
 */

import java.util.*;
import java.util.concurrent.*;

public class CookThread implements Runnable {

	// declare properties
	protected BlockingQueue<Food> cookQueue = null;
	protected BlockingQueue<Food> serveQueue = null;
	private boolean isCooking = false;
	private int cookTimer = 0;
	public static int totalCookTime = 0;
	
	public CookThread(BlockingQueue<Food> cookQueue, BlockingQueue<Food> serveQueue) {
		// initialize values
		this.cookQueue = cookQueue;
		this.serveQueue = serveQueue;
	}
	
	@Override
	public void run() {
		if (isCooking == false) {
			System.out.println("COOK READY");
		}
		while (cookQueue.peek() != null) {
			try {
				if (serveQueue.size() < 3) {
					Food itemOn = cookQueue.take();
					isCooking = true;
					System.out.println("COOK STARTING: " + itemOn.getName() + " (" + itemOn.getCookTime() + " TO COOK, " + itemOn.getServeTime() + " TO SERVE)");
	
					cookTimer = itemOn.getCookTime();
					while (cookTimer > 0) {
						Thread.sleep(1000);
						cookTimer--;
						totalCookTime++;
					}
					
					System.out.println("COOK ENDING: " + itemOn.getName() + " (" + itemOn.getCookTime() + " TO COOK, " + itemOn.getServeTime() + " TO SERVE)");
					serveQueue.add(itemOn);
					
					Thread server = new Thread(new ServeThread(serveQueue));
					server.start();
					
					isCooking = false;
				}
			}
			catch (InterruptedException ex) { }
		}
				
		/*
		synchronized (hotPass) {
			while (hotPass.isFilled()) {
				try {
					System.out.println("Chef waiting on server...");
					hotPass.wait();
				}
				catch (InterruptedException ex) { }
			}
			// cook item
			// System.out.println("Chef adding item to hot pass");
			System.out.println("Chef starting: ");
			hotPass.notify();
		}
		try {
			System.out.println("Chef sleeping...");
			Thread.sleep(1000);
			System.out.println("Chef awake");
		}
		catch (InterruptedException ex) { }
		System.out.println(this.foodList);
		*/
	}
}
