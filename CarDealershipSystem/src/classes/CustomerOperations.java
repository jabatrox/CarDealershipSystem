package classes;

import java.util.ArrayList;

/**
 * --------------------------------------------------
 * @author Javier Soler, Borja Gonz�lez
 * Date: 11/09/2018
 * Lab: Final Project
 * File name: CustomerOperation.java
 */
public interface CustomerOperations {
	
	/**
	 * @param conId
	 * @return
	 */
	public abstract ArrayList<CarDetails> listCars(int conId);
	/**
	 * @param carId
	 */
	public abstract void buyCar(CarDetails car, int custID);
	/**
	 * @param CarId
	 */
	public abstract boolean sellCar(CarDetails car); 
	
	/**
	 * @param conId
	 * @return
	 */
	public abstract ArrayList<CarDetails> checkCars(String option);

}