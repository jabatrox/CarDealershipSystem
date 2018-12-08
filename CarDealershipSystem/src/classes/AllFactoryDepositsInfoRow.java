package classes;

public class AllFactoryDepositsInfoRow {
	
	private int factID;
	private int carCapacity;
	private int carsProduced;
	
	
	/**
	 * @param factID
	 * @param carCapacity
	 * @param carsProduced
	 */
	public AllFactoryDepositsInfoRow(int factID, int carCapacity, int carsProduced) {
		this.factID = factID;
		this.carCapacity = carCapacity;
		this.carsProduced = carsProduced;
	}
	
	/**
	 * 
	 */
	public AllFactoryDepositsInfoRow() {
		// TODO Auto-generated constructor stub
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
	 * @return the carCapacity
	 */
	public int getCarCapacity() {
		return carCapacity;
	}

	/**
	 * @param carCapacity the carCapacity to set
	 */
	public void setCarCapacity(int carCapacity) {
		this.carCapacity = carCapacity;
	}

	/**
	 * @return the carsProduced
	 */
	public int getCarsProduced() {
		return carsProduced;
	}

	/**
	 * @param carsProduced the carsProduced to set
	 */
	public void setCarsProduced(int carsProduced) {
		this.carsProduced = carsProduced;
	}
	
	

}
