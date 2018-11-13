package classes;

import java.util.Random;

import classes.CarDetails.EngineType;

/**
 * --------------------------------------------------
 * @author Javier Soler, Borja González
 * Date: 11/09/2018
 * Lab: Final Project
 * File name: FactoryDeposit.java
 */
public class FactoryDeposit {
	
	int factID;
	int carCapacity;
		
	/**
	 * @param factID
	 * @param carCapacity
	 */
	public FactoryDeposit(int factID, int carCapacity) {
		super();
		this.factID = factID;
		this.carCapacity = carCapacity;
	}
	
	public int produceCar(int conID, String carModel, String carType, String carColor,
			EngineType engineType, int horsePower, double price) {
		
		// Generate random ID (just for initial development)
		Random rand = new Random();
		int carID = rand.nextInt(6) + 5;
		
		CarDetails newCar = new CarDetails(
				carID,
				conID,
				FactoryDeposit.this.getFactID(),
				carModel,
				carType,
				carColor,
				engineType,
				horsePower,
				price,
				0,
				false,
				true,
				true
				);
		return newCar.getCarID();
		
	}
	
	/**
	 * @return the factID
	 */
	public int getFactID() {
		return factID;
	}
	/**
	 * @param factID the factID to set
	 */
	public void setFactID(int factID) {
		this.factID = factID;
	}
	/**
	 * @return the carCapacity
	 */
	public int getCarCapacity() {
		return carCapacity;
	}
	/**
	 * @param carCapacity the carCapacity to set
	 */
	public void setCarCapacity(int carCapacity) {
		this.carCapacity = carCapacity;
	}
	
}
