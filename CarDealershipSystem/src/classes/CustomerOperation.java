package classes;

import java.util.ArrayList;

/**
 * --------------------------------------------------
 * @author Javier Soler, Borja Gonz�lez
 * Date: 11/09/2018
 * Lab: Final Project
 * File name: CustomerOperation.java
 */
public interface CustomerOperation {
	
	/**
	 * @param conId
	 * @return
	 */
	public abstract ArrayList<CarDetails> listCars(int conId);
	/**
	 * @param carId
	 */
	public abstract void buyCar(int carId);
	/**
	 * @param CarId
	 */
	public abstract void sellCar(int CarId); 

}
