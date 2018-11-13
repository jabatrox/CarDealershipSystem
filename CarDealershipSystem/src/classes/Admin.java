package classes;

/**
 * --------------------------------------------------
 * @author Javier Soler, Borja González
 * Date: 11/09/2018
 * Lab: Final Project
 * File name: Admin.java
 */
public class Admin extends Agent implements AdminOperations {

	/**
	 * @param agentID
	 * @param firstName
	 * @param lastName
	 */
	public Admin(int adminID, String firstName, String lastName) {
		super(adminID, firstName, lastName);
	}

	@Override
	public void createFactory() {
		// TODO Auto-generated method stub
		// ID por autoincrement y elegir carCapacity
		FactoryDeposit fact = new FactoryDeposit(0, 40);
		createConcessionaire(40, fact.getFactID());
		
	}

	@Override
	public void deleteFactory(int factID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createConcessionaire(int carCapacity, int factID) {
		// TODO Auto-generated method stub
		Concessionaire con = new Concessionaire(0, carCapacity, factID);
		addSeller(con.getConID());
		
	}

	@Override
	public void deleteConcessionaire(int conID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addSeller(int conID) {
		// TODO Auto-generated method stub
		// Elegir ID, nombre y apellido por la interfaz
		Seller seller = new Seller(0, null, null, conID);
	}

	@Override
	public void deleteSeller(int sellerID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BookingDetails getSalesHistory(int sellerID) {
		// TODO Auto-generated method stub
		return null;
	}

}
