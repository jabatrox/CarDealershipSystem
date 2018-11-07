package classes;

/**
 * --------------------------------------------------
 * @author Javier Soler, Borja González
 * Date: 11/09/2018
 * Lab: Final Project
 * File name: AdminOperations.java
 */
public interface AdminOperations {
	
	/**
	 * @param buildingType
	 */
	public abstract void createBuilding(Building.BuildingType buildingType);
	/**
	 * @param buildingID
	 */
	public abstract void deleteBuilding(int buildingID);
	/**
	 * @param sellerID
	 * @return
	 */
	public abstract BookingDetails getSalesHistory(int sellerID);

}
