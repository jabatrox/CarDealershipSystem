package classes;

import java.util.ArrayList;

public class Seller extends Agent implements SellerOperations{
	
	int sellerID;
	int conID;
	
	
	/**
	 * @param sellerID
	 * @param firstName
	 * @param lastName
	 * @param conID
	 */
	public Seller(int sellerID, String firstName, String lastName, int conID) {
		super(agentID, firstName, lastName);
		this.sellerID = agentID;
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
	 * @return the sellerID
	 */
	public int getSellerID() {
		return sellerID;
	}
	/**
	 * @param sellerID the sellerId to set
	 */
	public void setSellerID(int sellerID) {
		this.sellerID = sellerID;
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
