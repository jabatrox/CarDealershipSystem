package classes;

import java.util.ArrayList;

/**
 * --------------------------------------------------
 * @author Javier Soler, Borja González
 * Date: 11/09/2018
 * Lab: Final Project
 * File name: AdminOperations.java
 */
public interface AdminOperations {
	
	
	/**
	 * @return
	 */
	public ArrayList<AllFactoryDepositsInfoRow> getAllFactoryDeposits();
	/**
	 * 
	 */
	public abstract void createFactory(int carCapacity);
	/**
	 * @param factID
	 */
	public abstract void deleteFactory(int factID);
	/**
	 * @param factID
	 * @return
	 */
	public ArrayList<ConcessionairesFromFactoriesRow> getAllConcessionairesFromFactory(int factID);
	/**
	 * @param carCapacity
	 * @param factID
	 */
	public abstract void createConcessionaire(int carCapacity, int factID);
	/**
	 * @param conID
	 */
	public abstract void deleteConcessionaire(int conID);
	/**
	 * @param conID
	 */
	public abstract void addSeller(int conID);
	/**
	 * @param sellerID
	 */
	public abstract void deleteSeller(int sellerID);
	/**
	 * @param sellerID
	 * @return
	 */
	public abstract BookingDetails getSalesHistory(int sellerID);

}