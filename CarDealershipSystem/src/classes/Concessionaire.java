package classes;

/**
 * --------------------------------------------------
 * @author Javier Soler, Borja González
 * Date: 11/09/2018
 * Lab: Final Project
 * File name: Concessionaire.java
 */
public class Concessionaire {

	private int conID; // (foreign key "buildingID" from "Concessionaire" or "FactoryDeposit")
	//ArrayList<Integer> sellers = new ArrayList<>();
	private int carCapacity;
	//ArrayList<CarDetails> carsExposed;
	private int factID;
	
	/**
	 * Generates a new Concessionaire with a certain ID and capacity.
	 * @param conID
	 * @param carCapacity
	 * @param factID
	 */
	public Concessionaire(int conID, int carCapacity, int factID) {
		this.conID = conID;
		this.carCapacity = carCapacity;
		this.factID = factID;
		//sellers.add(new Seller());
		//carsExposed = new ArrayList<>();
	}

	/**
	 * @return the conID
	 */
	public int getConID() {
		return conID;
	}

	/**
	 * @param conID the conID to set
	 */
	public void setConID(int conID) {
		this.conID = conID;
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

}