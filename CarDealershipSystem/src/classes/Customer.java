package classes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import models.DBConnect;

/**
 * --------------------------------------------------
 * @author Javier Soler, Borja Gonz�lez
 * Date: 11/09/2018
 * Lab: Final Project
 * File name: Customer.java
 */
public class Customer extends Agent implements CustomerOperations {
	
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
	public void buyCar(CarDetails car) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean sellCar(CarDetails car) {
		// TODO Auto-generated method stub
		
		Connection conn = null;
		DBConnect openDBconn = new DBConnect();
		Statement stmt = null;
		
		try {
			conn = openDBconn.connect();
			stmt = conn.createStatement();
		
			//BookingDetails bookD = new BookingDetails(car.getPrice(),);
		
			String sql = "INSERT INTO bookingDetails () VALUES "
				+ "('1',"
				+ "'"+this.getAgentID()+"',"
				+ "'"+car.getConID()+"',"
				+ "'"+car.getCarID()+"',"
				+ "'0',"
				+ "'0',"
				+ "'"+car.getPrice()+"')";

		
			if(0==stmt.executeUpdate(sql)) 
				return true;
			else
				return false;
			
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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