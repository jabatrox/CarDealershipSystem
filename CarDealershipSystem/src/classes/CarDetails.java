package classes;

/**
 * --------------------------------------------------
 * @author Javier Soler, Borja Gonz�lez
 * Date: 11/09/2018
 * Lab: Final Project
 * File name: CarDetails.java
 */
public class CarDetails {
	
	int carID;
	int conID; 
	int factID;
	String carBrand;
	String carModel;
	String carColor;
	public enum EngineType{ELECTRIC, HYBRID, GASOLINE, DIESEL};
	EngineType engineType;
	int horsePower;
	double price;
	int kilometers;
	boolean sold;
	boolean exposed;
	boolean carCondition;
	int year;
	
	/**
	 * @param carID
	 * @param conID
	 * @param factID
	 * @param carModel
	 * @param carType
	 * @param carColor
	 * @param engineType
	 * @param horsePower
	 * @param price
	 * @param kilometers
	 * @param sold
	 * @param exposed
	 * @param carCondition
	 */
	public CarDetails(int carID, int conID, int factID, String carBrand, String carModel, 
			String carColor, EngineType engineType /*String engineType*/, int horsePower, double price, 
			int kilometers, boolean sold, boolean exposed, boolean carCondition, int year) {
		
		this.carID = carID;
		this.conID = conID; 
		this.factID = factID;
		this.carBrand = carBrand;
		this.carModel = carModel;
		this.carColor = carColor;
		this.engineType = engineType;
		this.horsePower = horsePower;
		this.price = price;
		this.kilometers = kilometers;
		this.sold = sold;
		this.exposed = exposed;
		this.carCondition = carCondition;
		this.year = year;
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
	public void setCarID(int carID) {
		this.carID = carID;
	}

	/**
	 * @return the conID
	 */
	public int getConID() {
		return conID;
	}

	/**
	 * @param conID the conID to set
	 */
	public void setConID(int conID) {
		this.conID = conID;
	}

	/**
	 * @return the factID
	 */
	public int getFactID() {
		return factID;
	}

	/**
	 * @param factID the factID to set
	 */
	public void setFactID(int factID) {
		this.factID = factID;
	}

	/**
	 * @return the carBrand
	 */
	public String getCarBrand() {
		return carBrand;
	}

	/**
	 * @param carBrand the carBrand to set
	 */
	public void setCarBrand(String carBrand) {
		this.carBrand = carBrand;
	}

	/**
	 * @return the carModel
	 */
	public String getCarModel() {
		return carModel;
	}

	/**
	 * @param carModel the carModel to set
	 */
	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	/**
	 * @return the carColor
	 */
	public String getCarColor() {
		return carColor;
	}

	/**
	 * @param carColor the carColor to set
	 */
	public void setCarColor(String carColor) {
		this.carColor = carColor;
	}

	/**
	 * @return the engineType
	 */
	public EngineType getEngineType() {
		return engineType;
	}

	/**
	 * @param engineType the engineType to set
	 */
	public void setEngineType(EngineType engineType) {
		this.engineType = engineType;
	}

	/**
	 * @return the horsePower
	 */
	public int getHorsePower() {
		return horsePower;
	}

	/**
	 * @param horsePower the horsePower to set
	 */
	public void setHorsePower(int horsePower) {
		this.horsePower = horsePower;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
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
	 * @return the sold
	 */
	public boolean isSold() {
		return sold;
	}

	/**
	 * @param sold the sold to set
	 */
	public void setSold(boolean sold) {
		this.sold = sold;
	}

	/**
	 * @return the exposed
	 */
	public boolean isExposed() {
		return exposed;
	}

	/**
	 * @param exposed the exposed to set
	 */
	public void setExposed(boolean exposed) {
		this.exposed = exposed;
	}

	/**
	 * @return the condition
	 */
	public boolean isCarCondition() {
		return carCondition;
	}

	/**
	 * @param condition the condition to set
	 */
	public void setCarCondition(boolean condition) {
		this.carCondition = condition;
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
	
	
	
}