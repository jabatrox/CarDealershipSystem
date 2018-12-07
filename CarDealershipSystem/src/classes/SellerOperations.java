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
	 * @param conId
	 * @return
	 */
	public abstract ArrayList<PendingBookingRow> checkPendingOperations(int conID);
	/**
	 * @param pendingBookingID
	 */
	public abstract void buyCar(int bookingID);
	/**
	 * @param carId
	 * @param statusCar
	 */
//	public abstract void sellCar(int carID, boolean statusCar);
	public abstract void sellCar(int bookingID);
	/**
	 * @param bookingId
	 */
	public abstract void rejectOperation(int bookingID, String bookingType);
	/**
	 * @param conId
	 * @return
	 */
	public abstract ArrayList<CarDetails> checkCarsConcessionaire(int conID);
	/**
	 * @param carId
	 */
	public abstract void addCarExposed(int carID);
	/**
	 * @param carId
	 */
	public abstract void deleteCarExposed(int carID);

}