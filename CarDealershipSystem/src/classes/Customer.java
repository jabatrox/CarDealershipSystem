package classes;

import java.util.ArrayList;

/**
 * --------------------------------------------------
 * @author Javier Soler, Borja González
 * Date: 11/09/2018
 * Lab: Final Project
 * File name: Customer.java
 */
public class Customer extends Agent implements CustomerOperation {
	
	String address;
	String email;
	String phone;
	
	/**
	 * Generates a new Customer.
	 * @param custID
	 * @param firstName
	 * @param lastName
	 * @param address
	 * @param email
	 * @param phone
	 */
	public Customer(int custID, String firstName, String lastName, String address, String email, String phone) {
		super(custID, firstName, lastName);
		this.address = address;
		this.email = email;
		this.phone = phone;
	}

	@Override
	public ArrayList<CarDetails> listCars(int conID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void buyCar(int carID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sellCar(int CarID) {
		// TODO Auto-generated method stub

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