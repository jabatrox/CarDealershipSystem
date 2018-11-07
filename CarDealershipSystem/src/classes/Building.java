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
	enum BuildingType{CONCESSIONAIRE, FACTORYDEPOSIT;};
	int carCapacity;


	/**
	 * 
	 */
	public Building(int buildingID, BuildingType buildingType) {
		Building.buildingID = buildingID;
	}

}
