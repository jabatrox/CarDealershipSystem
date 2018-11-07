package classes;

/**
 * --------------------------------------------------
 * @author Javier Soler, Borja Gonz�lez
 * Date: 11/09/2018
 * Lab: Final Project
 * File name: Admin.java
 */
public class Admin extends Agent implements AdminOperations {

	public Admin(int agentID, String firstName, String lastName) {
		super(agentID, firstName, lastName);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see classes.AdminOperations#createFactory(classes.Factory)
	 */
	@Override
	public void createFactory() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see classes.AdminOperations#deleteFactory(int)
	 */
	@Override
	public void deleteFactory(int factID) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see classes.AdminOperations#createConcessionaire(classes.Concessionaire)
	 */
	@Override
	public void createConcessionaire() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see classes.AdminOperations#deleteConcessionaire(int)
	 */
	@Override
	public void deleteConcessionaire(int conID) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see classes.AdminOperations#getSalesHistory(int)
	 */
	@Override
	public BookingDetails getSalesHistory(int sellerID) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
