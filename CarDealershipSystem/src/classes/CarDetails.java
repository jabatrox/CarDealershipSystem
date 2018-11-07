package classes;

public class CarDetails {
	
	int carID;
	int conID; 
	int factID;
	String carModel;
//	enum CarType{ELECTRIC, HYBRID, GASOLINE, DIESEL;};
	String carType;
	String carColor;
	String engineType;
	int horsePower;
	double price;
	int kilometers;
	boolean sold;
	boolean exposed;
	boolean condition;
	
	public CarDetails(int carID, int conID, int factID, String carModel, String carType/*CarType carType*/, 
			String carColor, String engineType, int horsePower, double price, 
			int kilometers, boolean sold, boolean exposed, boolean condition) {
		
		this.carID = carID;
		this.conID = conID; 
		this.factID = factID;
		this.carModel = carModel;
		this.carType = carType;
		this.carColor = carColor;
		this.engineType = engineType;
		this.horsePower = horsePower;
		this.price = price;
		this.kilometers = kilometers;
		this.sold = sold;
		this.exposed = exposed;
		this.condition = condition;
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
	 * @return the carType
	 */
	public String getCarType() {
		return carType;
	}

	/**
	 * @param carType the carType to set
	 */
	public void setCarType(String carType) {
		this.carType = carType;
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
	public String getEngineType() {
		return engineType;
	}

	/**
	 * @param engineType the engineType to set
	 */
	public void setEngineType(String engineType) {
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
	public boolean isCondition() {
		return condition;
	}

	/**
	 * @param condition the condition to set
	 */
	public void setCondition(boolean condition) {
		this.condition = condition;
	}
	
	
	
	
}
