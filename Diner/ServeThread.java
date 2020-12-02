/*
 * Chris Huber <chuber2@mail.ccsf.edu>
 * CS211S, Jessica Masters
 * 11/22/2020
 * Assignment Twelve: Concurrency
 */

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class ServeThread implements Runnable {

	// declare properties
	protected BlockingQueue<Food> serveQueue = null;
	private int numItems;
	private int serveTimer = 0;
	
	public ServeThread(BlockingQueue<Food> serveQueue, int numItems) {
		// initialize values
		this.serveQueue = serveQueue;
		this.numItems = numItems;
	}
	
	@Override
	public void run() {
		System.out.println("SERVER READY");
		while (numItems > 0) {
			if (serveQueue.peek() != null) {
				try {
					Food itemUp = serveQueue.take();
					System.out.println("SERVER STARTING: " + itemUp.getName() + " (" + itemUp.getCookTime() + " TO COOK, " + itemUp.getServeTime() + " TO SERVE)");
	
					serveTimer = itemUp.getServeTime();
					while (serveTimer > 0) {
						Thread.sleep(1000);
						serveTimer--;
					}
					
					System.out.println("SERVER ENDING: " + itemUp.getName() + " (" + itemUp.getCookTime() + " TO COOK, " + itemUp.getServeTime() + " TO SERVE)");
					numItems--;
					System.out.println("SERVER READY");
				}
				catch (InterruptedException ex) { }
			}
		}
	}
}
