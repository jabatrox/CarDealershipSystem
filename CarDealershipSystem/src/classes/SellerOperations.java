package classes;

import java.util.ArrayList;

/**
 * --------------------------------------------------
 * @author Javier Soler, Borja González
 * Date: 11/09/2018
 * Lab: Final Project
 * File name: SellerOperations.java
 */
public interface SellerOperations {
	
	/**
	 * @param carId
	 */
	public abstract void buyCar(int carId);
	/**
	 * @param carId
	 * @param statusCar
	 */
	public abstract void sellCar(int carId, boolean statusCar);
	/**
	 * @param bookingId
	 */
	public abstract void rejectOperation(int bookingId);
	/**
	 * @param carId
	 */
	public abstract void addCarExposed(int carId);
	/**
	 * @param carId
	 */
	public abstract void deleteCarExposed(int carId);
	/**
	 * @param conId
	 * @return
	 */
	public abstract ArrayList<BookingDetails> checkPendingOperations(int conId);

}
