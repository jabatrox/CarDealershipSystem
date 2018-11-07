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
	 * 
	 */
	public Admin() {
		super(agentID, firstName, lastName);
	}

	@Override
	public void createFactory() {
		// TODO Auto-generated method stub
//		Factory fact = new Factory();
		
	}

	@Override
	public void deleteFactory(int factID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createConcessionaire() {
		// TODO Auto-generated method stub
//		Concessionaire con = new Concessionaire();
		
	}

	@Override
	public void deleteConcessionaire(int conID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BookingDetails getSalesHistory(int sellerID) {
		// TODO Auto-generated method stub
		return null;
	}

}
