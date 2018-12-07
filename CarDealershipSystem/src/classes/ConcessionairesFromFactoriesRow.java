package classes;

public class ConcessionairesFromFactoriesRow {

	private int conID;
	private int carCapacity;
	private int numberOfSellers;
	
	
	/**
	 * @param conID
	 * @param carCapacity
	 * @param numberOfSellers
	 */
	public ConcessionairesFromFactoriesRow(int conID, int carCapacity, int numberOfSellers) {
		this.conID = conID;
		this.carCapacity = carCapacity;
		this.numberOfSellers = numberOfSellers;
	}
	
	/**
	 * 
	 */
	public ConcessionairesFromFactoriesRow() {
		// TODO Auto-generated constructor stub
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
	 * @return the numberOfSellers
	 */
	public int getNumberOfSellers() {
		return numberOfSellers;
	}

	/**
	 * @param numberOfSellers the numberOfSellers to set
	 */
	public void setNumberOfSellers(int numberOfSellers) {
		this.numberOfSellers = numberOfSellers;
	}

}
