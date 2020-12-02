/*
 * Chris Huber <chuber2@mail.ccsf.edu>
 * CS211S, Jessica Masters
 * 11/28/2020
 * Assignment Eleven: Concurrency
 */

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Stream;

public class FoodTester {

	public static void main(String[] args) {
		List<Food> foodList = new ArrayList<>();
		foodList.add(new Food("Spinach Dip", 2, 1));
		foodList.add(new Food("Burger", 5, 1));
		foodList.add(new Food("Pasta", 4, 3));
		foodList.add(new Food("Baked Alaska", 6, 20));
		foodList.add(new Food("Salad", 1, 1));
		foodList.add(new Food("Bruchetta", 3, 1));
		foodList.add(new Food("Bread", 1, 1));
		foodList.add(new Food("Fried Green Tomatoes", 2, 1));
		
		Stream<Food> cookStream =  foodList.stream();
		int totalCookTime = cookStream.mapToInt(Food::getCookTime).sum();

		Stream<Food> serveStream =  foodList.stream();
		int totalServeTime = serveStream.mapToInt(Food::getServeTime).sum();
		
		BlockingQueue<Food> foodQueue = new ArrayBlockingQueue<>(100);
		BlockingQueue<Food> serveQueue = new ArrayBlockingQueue<>(3);
		
		// INITIALIZE AND START YOUR THREADS HERE!		
		try {
			while (foodList.size() > 0) {
				foodQueue.put(foodList.get(0));
				foodList.remove(0);
			}
		}
		catch (InterruptedException ie) {
			ie.printStackTrace();
		}
		
		Thread cooker = new Thread(new CookThread(foodQueue, serveQueue));
		cooker.start();
		
      // IMPORTANT NOTE!!! 
		// Some IDEs have more than one thread active. You might need to 
		// change the >1 to >2 or similar in order to get the tester to
		// work in your IDE.
		int programTimeCounter=0;
		while(Thread.activeCount()>1) {
			System.out.println("TIME " + programTimeCounter);
			programTimeCounter++;
			try {
				Thread.sleep(1000);
			} catch(InterruptedException ex) {
				
			}
		}
		
		// USE STREAMS HERE ON THE INITIAL LIST! 
		// NOTE: THIS PART HAS NOTHING TO DO WITH THE THREADS- JUST MORE STREAM PRACTICE! :)
		// USE METHOD REFERENCES!
		System.out.println("Total Cook Time = " + totalCookTime);
		System.out.println("Total Serve Time = " + totalServeTime);
		System.out.println("Program Time = " + programTimeCounter);
		
	}

}
