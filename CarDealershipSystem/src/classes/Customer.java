/**
 * 
 */
package classes;

import java.util.ArrayList;

/**
 * @author Borja
 *
 */
public class Customer extends Agent implements CustomerOperation {
	
	int custId;
	String address;
	String email;
	String phone;
	
	
	
	
	/**
	 * @param custId
	 * @param firstName
	 * @param lastName
	 * @param address
	 * @param email
	 * @param phone
	 */
	public Customer(int custId, String firstName, String lastName, String address, String email, String phone) {
		super(agentID, firstName, lastName);
		this.custId = agentID;
		this.address = address;
		this.email = email;
		this.phone = phone;
	}

	/* (non-Javadoc)
	 * @see classes.CustomerOperation#listCars(int)
	 */
	@Override
	public ArrayList<CarDetails> listCars(int conId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see classes.CustomerOperation#buyCar(int)
	 */
	@Override
	public void buyCar(int carId) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see classes.CustomerOperation#sellCar(int)
	 */
	@Override
	public void sellCar(int CarId) {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the custId
	 */
	public int getCustId() {
		return custId;
	}

	/**
	 * @param custId the custId to set
	 */
	public void setCustId(int custId) {
		this.custId = custId;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	

}
