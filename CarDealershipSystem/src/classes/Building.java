package classes;

/**
 * --------------------------------------------------
 * @author Javier Soler, Borja González
 * Date: 11/09/2018
 * Lab: Final Project
 * File name: Building.java
 */
public class Building {
	
	static int buildingID; // (foreign key "buildingID" from "Concessionaire" or "FactoryDeposit")
	enum type{CONCESSIONAIRE, FACTORYDEPOSIT;};
	int carCapacity;


	/**
	 * 
	 */
	public Building(int buildingID, type buildingType) {
		Building.buildingID = buildingID;
	}

}
