package classes;

/**
 * --------------------------------------------------
 * @author Javier Soler, Borja Gonz�lez
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
