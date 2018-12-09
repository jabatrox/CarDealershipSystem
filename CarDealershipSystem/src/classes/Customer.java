package classes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import classes.CarDetails.EngineType;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import models.DBConnect;

/**
 * --------------------------------------------------
 * @author Javier Soler, Borja Gonz�lez
 * Date: 11/09/2018
 * Lab: Final Project
 * File name: Customer.java
 */
public class Customer extends Agent implements CustomerOperations {
	
	private String address;
	private String email;
	private String phone;
	private int userDB_ID;
	
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
	public Customer(int custID, String firstName, String lastName, String address, String email, String phone, int userDB_ID) {
		super(custID, firstName, lastName);
		this.address = address;
		this.email = email;
		this.phone = phone;
		this.userDB_ID = userDB_ID;
	}

	@Override
	public ArrayList<CarDetails> listCars(int conID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void buyCar(CarDetails car, int customerID) {
		// TODO Auto-generated method stub
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Paymet Type");
		alert.setHeaderText("Choose betwwen a unique or an installment payment");
		alert.setContentText("Installment payments will be divided in 48 payments (2 years).");

		ButtonType buttonTypeOne = new ButtonType("Unique");
		ButtonType buttonTypeTwo = new ButtonType("Installment");

		alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
		int paymentType = 0;
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeOne){
		    paymentType = 0;
		} else if (result.get() == buttonTypeTwo) {
		    paymentType = 1;
		}
		
		
		List<String> choices = new ArrayList<>();
		choices.add("Black");
		choices.add("Blue");
		choices.add("Green");
		choices.add("Grey");
		choices.add("Purple");
		choices.add("Red");
		choices.add("White");
		choices.add("Yellow");

		ChoiceDialog<String> dialog = new ChoiceDialog<>("Black", choices);
		dialog.setTitle("Color Choice");
		dialog.setHeaderText("Ahead you can view all the colors available for this car");
		dialog.setContentText("Choose your color:");

		// Traditional way to get the response value.
		Optional<String> color = dialog.showAndWait();
		if (color.isPresent()){
		    System.out.println("Your choice: " + color.get());
		}

		Connection conn = null;
		DBConnect openDBconn = new DBConnect();
		Statement stmt = null;
		
		try {
			conn = openDBconn.connect();
			stmt = conn.createStatement();
			
			String sql_car = "INSERT INTO carDetails (conID, factID, "
					+ "carBrand, carModel, carColor, engineType, horsePower,"
					+ " price, kilometers, sold, exposed, carCondition, year)" 
					+ " VALUES ('"+car.getConID()+"','"+car.getFactID()+"','"+car.getCarBrand()+"', "
					+ "'"+car.getCarModel()+"', '"+color.get()+"', '"+car.getEngineType().toString()+"'"
					+ ",'"+car.getHorsePower()+"', '"+(int)car.getPrice()+"',"
					+ " '"+car.getKilometers()+"', '0', '0', '0', '"+Calendar.getInstance().get(Calendar.YEAR)+"');SELECT SCOPE_IDENTITY();";
			
			ResultSet rs = stmt.executeQuery(sql_car);
			String new_car_id = "";
			if(rs.next()) {
				new_car_id = rs.getString("");
			}
			else {
				System.out.print("No result");
				return;
			}
			
			long bookingTimeMillis = System.currentTimeMillis();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");    
			Date resultdate = new Date(bookingTimeMillis);
			String bookingTime = sdf.format(resultdate);
			
		
			String sql = "INSERT INTO bookingDetails (bookingType, custID, "
					+ "sellerID, carID, bookingCompleted, paymentType, amount, bookingTime) VALUES "
				+ "('0',"
				+ "'"+customerID+"',"
				+ "'"+car.getConID()+"',"
				+ "'"+new_car_id+"',"
				+ "'0',"
				+ "'"+paymentType+"',"
				+ "'"+(int) car.getPrice()+"',"
						+ "'"+bookingTime+"')";

		
			if(0==stmt.executeUpdate(sql)) {
				System.out.print("Car operation (buy) has been registered"); 
			}
			conn.close();
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

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
			long bookingTimeMillis = System.currentTimeMillis();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");    
			Date resultdate = new Date(bookingTimeMillis);
			String bookingTime = sdf.format(resultdate);
		
			String sql = "INSERT INTO bookingDetails (bookingType, "
					+ "custID, sellerID, carID, bookingCompleted, "
					+ "paymentType, amount, bookingTime) VALUES "
				+ "('1',"
				+ "'"+this.getAgentID()+"',"
				+ "'"+car.getConID()+"',"
				+ "'"+car.getCarID()+"',"
				+ "'0',"
				+ "'0',"
				+ "'"+(int)car.getPrice()+"',"
				+ "'"+bookingTime+"')";

		
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

	/**
	 * @return the userDB_ID
	 */
	public int getUserDB_ID() {
		return userDB_ID;
	}

	/**
	 * @param userDB_ID the userDB_ID to set
	 */
	public void setUserDB_ID(int userDB_ID) {
		this.userDB_ID = userDB_ID;
	}
	
	

}