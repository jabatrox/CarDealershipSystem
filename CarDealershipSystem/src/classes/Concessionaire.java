package classes;

import java.util.ArrayList;

/**
 * --------------------------------------------------
 * @author Javier Soler, Borja González
 * Date: 11/09/2018
 * Lab: Final Project
 * File name: Concessionaire.java
 */
public class Concessionaire extends Building {

	int conID; // (foreign key "buildingID" from "Concessionaire" or "FactoryDeposit")
//	ArrayList<Integer> sellers = new ArrayList<>();
	ArrayList<CarDetails> carsExposed;
	
	/**
	 * 
	 */
	public Concessionaire(int conID) {
		super(buildingID, BuildingType.CONCESSIONAIRE);
		this.conID = buildingID;
//		sellers.add(new Seller());
		carsExposed = new ArrayList<>();
	}

}
