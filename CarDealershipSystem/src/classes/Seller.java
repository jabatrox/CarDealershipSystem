package classes;

import java.util.ArrayList;

/**
 * --------------------------------------------------
 * @author Javier Soler, Borja Gonz�lez
 * Date: 11/09/2018
 * Lab: Final Project
 * File name: Seller.java
 */
public class Seller extends Agent implements SellerOperations{
	
	int conID;
	
	
	/**
	 * @param firstName
	 * @param lastName
	 * @param conID
	 */
	public Seller(int sellerID, String firstName, String lastName, int conID) {
		super(agentID, firstName, lastName);
		this.conID = conID;
	}
	
	
	@Override
	public void buyCar(int carID) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void sellCar(int carID, boolean statusCar) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void rejectOperation(int bookingID) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void addCarExposed(int carID) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void deleteCarExposed(int carID) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public ArrayList<BookingDetails> checkPendigOperations(int conID) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the conID
	 */
	public int getConID() {
		return conID;
	}
	/**
	 * @param conId the conId to set
	 */
	public void setConId(int conID) {
		this.conID = conID;
	}
		

}
