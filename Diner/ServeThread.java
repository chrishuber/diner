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
	private boolean isServing = false;
	private int serveTimer = 0;
	public static int totalServeTime = 0;
	
	public ServeThread(BlockingQueue<Food> serveQueue) {
		// initialize values
		this.serveQueue = serveQueue;
	}
	
	@Override
	public void run() {
		// if (isServing == false) {
		//	System.out.println("SERVER READY");
		// }
		while (serveQueue.peek() != null) {
			try {
				Food itemUp = serveQueue.take();
				isServing = true;
				System.out.println("SERVER STARTING: " + itemUp.getName() + " (" + itemUp.getCookTime() + " TO COOK, " + itemUp.getServeTime() + " TO SERVE)");

				serveTimer = itemUp.getServeTime();
				while (serveTimer > 0) {
					Thread.sleep(1000);
					serveTimer--;
					totalServeTime++;
				}
				
				System.out.println("SERVER ENDING: " + itemUp.getName() + " (" + itemUp.getCookTime() + " TO COOK, " + itemUp.getServeTime() + " TO SERVE)");
				isServing = false;
				System.out.println("SERVER READY");
			}
			catch (InterruptedException ex) { }
		}
	}
}
