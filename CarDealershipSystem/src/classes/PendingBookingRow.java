package classes;

public class PendingBookingRow {
	
	int bookingID;
	String bookingType;
	String custName;
	String brandModel;
	String carColor;
	int year;
	int kilometers;
	String engine;
	String horsepower;
	String paymentType;
	int amount;
	
	
	public PendingBookingRow(int bookingID, String bookingType, String custName, String brandModel, String carColor,
			int year, int kilometers, String engine, String horsepower, String paymentType, int amount) {
		this.bookingID = bookingID;
		this.bookingType = bookingType;
		this.custName = custName;
		this.brandModel = brandModel;
		this.carColor = carColor;
		this.year = year;
		this.kilometers = kilometers;
		this.engine = engine;
		this.horsepower = horsepower;
		this.paymentType = paymentType;
		this.amount = amount;
	}

	/**
	 * @return the bookingType
	 */
	public int getBookingID() {
		return bookingID;
	}

	/**
	 * @param bookingType the bookingType to set
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
	 * @return the custName
	 */
	public String getCustName() {
		return custName;
	}

	/**
	 * @param custName the custName to set
	 */
	public void setCustName(String custName) {
		this.custName = custName;
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
	 * @return the carColor
	 */
	public String getCarColor() {
		return carColor;
	}

	/**
	 * @param brandModel the carColor to set
	 */
	public void setCarColor(String carColor) {
		this.carColor = carColor;
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
	 * @return the engineHorsepower
	 */
	public String getEngine() {
		return engine;
	}

	/**
	 * @param engineHorsepower the engineHorsepower to set
	 */
	public void setEngine(String engine) {
		this.engine = engine;
	}
	
	/**
	 * @return the horsepower
	 */
	public String getHorsepower() {
		return horsepower;
	}

	/**
	 * @param engineHorsepower the horsepower to set
	 */
	public void setHorsepower(String horsepower) {
		this.horsepower = horsepower;
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

	public PendingBookingRow() {
		// TODO Auto-generated constructor stub
	}

}
