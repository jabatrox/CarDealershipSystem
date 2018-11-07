package classes;

/**
 * --------------------------------------------------
 * @author Javier Soler, Borja Gonz�lez
 * Date: 11/09/2018
 * Lab: Final Project
 * File name: AdminOperations.java
 */
public interface AdminOperations {
	
	/**
	 * @param Factory
	 */
	public abstract void createFactory(Factory fact);
	/**
	 * @param factID
	 */
	public abstract void deleteFactory(int factID);
	/**
	 * @param Concessionaire
	 */
	public abstract void createConcessionaire(Concessionaire con);
	/**
	 * @param conID
	 */
	public abstract void deleteConcessionaire(int conID);
	/**
	 * @param sellerID
	 * @return
	 */
	public abstract BookingDetails getSalesHistory(int sellerID);

}
