package classes;

import java.util.ArrayList;

/**
 * --------------------------------------------------
 * @author Javier Soler, Borja Gonz�lez
 * Date: 11/09/2018
 * Lab: Final Project
 * File name: Concessionaire.java
 */
public class Concessionaire {

	int conID; // (foreign key "buildingID" from "Concessionaire" or "FactoryDeposit")
	//ArrayList<Integer> sellers = new ArrayList<>();
	int carCapacity;
	//ArrayList<CarDetails> carsExposed;
	
	/**
	 * @param conID
	 * @param carCapacity
	 */
	public Concessionaire(int conID, int carCapacity) {
		this.conID = conID;
		this.carCapacity = carCapacity;
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
	
	

}
