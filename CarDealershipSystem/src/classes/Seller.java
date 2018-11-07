package classes;

import java.util.ArrayList;

public class Seller implements SellerOperations{
	
	int sellerId;
	String firstName;
	String lastName;
	int conId;
	
	
	/**
	 * @param sellerId
	 * @param firstName
	 * @param lastName
	 * @param conId
	 */
	public Seller(int sellerId, String firstName, String lastName, int conId) {
		super();
		this.sellerId = sellerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.conId = conId;
	}
	
	
	
	/* (non-Javadoc)
	 * @see classes.SellerOperations#buyCar(int)
	 */
	@Override
	public void buyCar(int carId) {
		// TODO Auto-generated method stub
		
	}



	/* (non-Javadoc)
	 * @see classes.SellerOperations#sellCar(int, boolean)
	 */
	@Override
	public void sellCar(int carId, boolean statusCar) {
		// TODO Auto-generated method stub
		
	}



	/* (non-Javadoc)
	 * @see classes.SellerOperations#rejectOperation(int)
	 */
	@Override
	public void rejectOperation(int bookingId) {
		// TODO Auto-generated method stub
		
	}



	/* (non-Javadoc)
	 * @see classes.SellerOperations#addCarExposed(int)
	 */
	@Override
	public void addCarExposed(int carId) {
		// TODO Auto-generated method stub
		
	}



	/* (non-Javadoc)
	 * @see classes.SellerOperations#deleteCarExposed(int)
	 */
	@Override
	public void deleteCarExposed(int carId) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public ArrayList<BookingDetails> checkPendigOperations(int conId) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the sellerId
	 */
	public int getSellerId() {
		return sellerId;
	}
	/**
	 * @param sellerId the sellerId to set
	 */
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the conId
	 */
	public int getConId() {
		return conId;
	}
	/**
	 * @param conId the conId to set
	 */
	public void setConId(int conId) {
		this.conId = conId;
	}
		

}
