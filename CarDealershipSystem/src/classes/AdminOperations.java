package classes;

import java.util.ArrayList;

import javafx.scene.control.TextField;

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
	public ArrayList<FactoryDeposit> getAllFactoryDeposits();
	/**
	 * @return
	 */
	public ArrayList<AllFactoryDepositsInfoRow> getAllFactoryDepositsRow();
	/**
	 * 
	 */
	public abstract void createFactory(int carCapacity);
	/**
	 * @param factID
	 */
	public abstract void deleteFactory(int factID);
	/**
	 * @return
	 */
	public ArrayList<Concessionaire> getAllConcessionaires();
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
	 * @param newSellerFirstNameString
	 * @param newSellerLastNameString
	 * @param newSellerUsernameString
	 * @param passwordHash
	 */
	public abstract void addSeller(int conID, String newSellerFirstNameString, String newSellerLastNameString,
			String newSellerUsernameString, String passwordHash);
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