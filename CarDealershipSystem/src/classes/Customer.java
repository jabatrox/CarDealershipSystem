package classes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import classes.CarDetails.EngineType;
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
	
	Connection conn = null;
	DBConnect openDBconn = new DBConnect();
	Statement stmt = null;
	
	
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
		boolean x = false;
		
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
				x = true;
			else
				x = false;
			
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return x;
	}
	
	@Override
	public ArrayList<CarDetails> checkCars(String option) {
		// TODO Auto-generated method stub
		ArrayList<CarDetails> conCars = new ArrayList<>();
		try {
			conn = openDBconn.connect();
			stmt = conn.createStatement();
			String sql_conCars;
			if (option.equals("All Cars")) {
				sql_conCars = "SELECT * FROM carDetails WHERE sold='0'";
			}
			else if (option.equals("New Cars")) {
				sql_conCars = "SELECT * FROM carDetails WHERE sold='0' AND carCondition='1'";
			}
			else {
				sql_conCars = "SELECT * FROM carDetails WHERE sold='0' AND carCondition='0'";
			}
			ResultSet rs_conCars = stmt.executeQuery(sql_conCars);
			while(rs_conCars.next()) {
				System.out.println("Available car "+rs_conCars.getInt("carID")+" found!");
				conCars.add(new CarDetails(rs_conCars.getInt("carID"),
		            		rs_conCars.getInt("conID"),
		            		rs_conCars.getInt("factID"),
		            		rs_conCars.getString("carBrand").toUpperCase(),
		            		rs_conCars.getString("carModel").toUpperCase(),
		            		rs_conCars.getString("carColor").toUpperCase(),
		            		EngineType.valueOf(rs_conCars.getString("engineType")),
		            		rs_conCars.getInt("horsePower"),
		            		rs_conCars.getDouble("price"),
		            		rs_conCars.getInt("kilometers"),
		            		rs_conCars.getBoolean("sold"),
		            		rs_conCars.getBoolean("exposed"),
		            		rs_conCars.getBoolean("carCondition"),
		            		rs_conCars.getInt("year")));
			}		
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conCars;
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