package classes;

public class SalesHistoryBookingRow {
	
	private int bookingID;
	private String bookingType;
	private String brandModel;
	private int year;
	private int kilometers;
	private String paymentType;
	private int amount;
	private String bookingCompleted;
	
	
	/**
	 * @param bookingID
	 * @param bookingType
	 * @param brandModel
	 * @param year
	 * @param kilometers
	 * @param paymentType
	 * @param amount
	 * @param bookingCompleted
	 */
	public SalesHistoryBookingRow(int bookingID, String bookingType, String brandModel, int year, int kilometers, 
			String paymentType, int amount, String bookingCompleted) {
		this.bookingID = bookingID;
		this.bookingType = bookingType;
		this.brandModel = brandModel;
		this.year = year;
		this.kilometers = kilometers;
		this.paymentType = paymentType;
		this.amount = amount;
		this.bookingCompleted = bookingCompleted;
	}

	/**
	 * @return the bookingID
	 */
	public int getBookingID() {
		return bookingID;
	}

	/**
	 * @param bookingID the bookingID to set
	 */
	public void setBookingID(int bookingID) {
		this.bookingID = bookingID;
	}

	/**
	 * @return the bookingType
	 */
	public String getBookingType() {
		return bookingType;
	}

	/**
	 * @param bookingType the bookingType to set
	 */
	public void setBookingType(String bookingType) {
		this.bookingType = bookingType;
	}

	/**
	 * @return the brandModel
	 */
	public String getBrandModel() {
		return brandModel;
	}

	/**
	 * @param brandModel the brandModel to set
	 */
	public void setBrandModel(String brandModel) {
		this.brandModel = brandModel;
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * @return the kilometers
	 */
	public int getKilometers() {
		return kilometers;
	}

	/**
	 * @param kilometers the kilometers to set
	 */
	public void setKilometers(int kilometers) {
		this.kilometers = kilometers;
	}

	/**
	 * @return the paymentType
	 */
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * @param paymentType the paymentType to set
	 */
	public void setPaymentType(String paymentType) {
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

	/**
	 * @return the bookingCompleted
	 */
	public String getBookingCompleted() {
		return bookingCompleted;
	}

	/**
	 * @param bookingCompleted the bookingCompleted to set
	 */
	public void setBookingCompleted(String bookingCompleted) {
		this.bookingCompleted = bookingCompleted;
	}

	public SalesHistoryBookingRow() {
		// TODO Auto-generated constructor stub
	}

}
