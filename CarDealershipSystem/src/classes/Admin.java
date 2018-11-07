package classes;

import classes.Building.BuildingType;

/**
 * --------------------------------------------------
 * @author Javier Soler, Borja González
 * Date: 11/09/2018
 * Lab: Final Project
 * File name: Admin.java
 */
public class Admin extends Agent implements AdminOperations {

	/**
	 * 
	 */
	public Admin() {
		super(agentID, firstName, lastName);
	}

	/* (non-Javadoc)
	 * @see classes.AdminOperations#createBuilding(classes.Building.BuildingType)
	 */
	@Override
	public void createBuilding(BuildingType buildingType) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see classes.AdminOperations#deleteBuilding(int)
	 */
	@Override
	public void deleteBuilding(int buildingID) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see classes.AdminOperations#getSalesHistory(int)
	 */
	@Override
	public BookingDetails getSalesHistory(int sellerID) {
		// TODO Auto-generated method stub
		return null;
	}

}
