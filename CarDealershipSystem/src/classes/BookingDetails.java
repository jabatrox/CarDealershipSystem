/**
 * 
 */
package classes;

/**
 * @author Borja
 *
 */
public class BookingDetails {
	
	int bookingId;
	int bookingType;
	int custId;
	int sellerId;
	int carId;
	boolean bookingCompleted;
	int paymentType;
	int amount;
	/**
	 * @return the bookingId
	 */
	public int getBookingId() {
		return bookingId;
	}
	/**
	 * @param bookingId the bookingId to set
	 */
	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
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
	 * @return the custId
	 */
	public int getCustId() {
		return custId;
	}
	/**
	 * @param custId the custId to set
	 */
	public void setCustId(int custId) {
		this.custId = custId;
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
	 * @return the carId
	 */
	public int getCarId() {
		return carId;
	}
	/**
	 * @param carId the carId to set
	 */
	public void setCarId(int carId) {
		this.carId = carId;
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
