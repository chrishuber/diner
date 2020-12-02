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
	protected Thread server;
	private int cookTimer = 0;
	
	public CookThread(BlockingQueue<Food> cookQueue, BlockingQueue<Food> serveQueue) {
		// initialize values
		this.cookQueue = cookQueue;
		this.serveQueue = serveQueue;
		
		server = new Thread(new ServeThread(serveQueue, cookQueue.size()));
		server.start();
	}
	
	@Override
	public void run() {
		System.out.println("COOK READY");
		while (cookQueue.peek() != null) {
			try {
				if (serveQueue.size() < 3) {
					Food itemOn = cookQueue.take();

					System.out.println("COOK STARTING: " + itemOn.getName() + " (" + itemOn.getCookTime() + " TO COOK, " + itemOn.getServeTime() + " TO SERVE)");
	
					cookTimer = itemOn.getCookTime();
					while (cookTimer > 0) {
						Thread.sleep(1000);
						cookTimer--;
					}
					
					System.out.println("COOK ENDING: " + itemOn.getName() + " (" + itemOn.getCookTime() + " TO COOK, " + itemOn.getServeTime() + " TO SERVE)");
					serveQueue.add(itemOn);
					System.out.println("COOK READY");
				}
			}
			catch (InterruptedException ex) { }
		}
	}
}
