package classes;

/**
 * --------------------------------------------------
 * @author Javier Soler, Borja González
 * Date: 11/09/2018
 * Lab: Final Project
 * File name: BookingDetails.java
 */
public class BookingDetails {
	
	int bookingID;
	int bookingType;
	int custID;
	int sellerID;
	int carID;
	boolean bookingCompleted;
	int paymentType;
	int amount;
	
	/**
	 * @param bookingID
	 * @param bookingType
	 * @param custID
	 * @param sellerID
	 * @param carID
	 * @param bookingCompleted
	 * @param paymentType
	 * @param amount
	 */
	public BookingDetails(int bookingID, int bookingType, int custID, int sellerID,
			int carID, boolean bookingCompleted, int paymentType, int amount) {
		
		this.bookingID = bookingID;
		this.bookingType = bookingType;
		this.custID = custID;
		this.sellerID = sellerID;
		this.carID = carID;
		this.bookingCompleted = bookingCompleted;
		this.paymentType = paymentType;
		this.amount = amount;
		
	}
	
	/**
	 * @return the bookingID
	 */
	public int getBookingID() {
		return bookingID;
	}
	/**
	 * @param bookingID the bookingId to set
	 */
	public void setBookingID(int bookingID) {
		this.bookingID = bookingID;
	}
	/**
	 * @return the bookingType
	 */
	public int getBookingType() {
		return bookingType;
	}
	/**
	 * @param bookingType the bookingType to set
	 */
	public void setBookingType(int bookingType) {
		this.bookingType = bookingType;
	}
	/**
	 * @return the custID
	 */
	public int getCustID() {
		return custID;
	}
	/**
	 * @param custID the custID to set
	 */
	public void setCustID(int custID) {
		this.custID = custID;
	}
	/**
	 * @return the sellerID
	 */
	public int getSellerID() {
		return sellerID;
	}
	/**
	 * @param sellerID the sellerID to set
	 */
	public void setSellerID(int sellerID) {
		this.sellerID = sellerID;
	}
	/**
	 * @return the carID
	 */
	public int getCarID() {
		return carID;
	}
	/**
	 * @param carID the carID to set
	 */
	public void setCarId(int carID) {
		this.carID = carID;
	}
	/**
	 * @return the bookingCompleted
	 */
	public boolean isBookingCompleted() {
		return bookingCompleted;
	}
	/**
	 * @param bookingCompleted the bookingCompleted to set
	 */
	public void setBookingCompleted(boolean bookingCompleted) {
		this.bookingCompleted = bookingCompleted;
	}
	/**
	 * @return the paymentType
	 */
	public int getPaymentType() {
		return paymentType;
	}
	/**
	 * @param paymentType the paymentType to set
	 */
	public void setPaymentType(int paymentType) {
		this.paymentType = paymentType;
	}
	/**
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
}