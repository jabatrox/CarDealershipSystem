package classes;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import classes.CarDetails.EngineType;
import models.DBConnect;

/**
 * --------------------------------------------------
 * @author Javier Soler, Borja González
 * Date: 11/09/2018
 * Lab: Final Project
 * File name: Seller.java
 */
public class Seller extends Agent implements SellerOperations{
	
	Connection conn = null;
	DBConnect openDBconn = new DBConnect();
	Statement stmt = null;
	
	private int conID;
	
	
	/**
	 * Generates a new seller, and associates it to a Concessionaire
	 * @param sellerID
	 * @param firstName
	 * @param lastName
	 * @param conID
	 */
	public Seller(int sellerID, String firstName, String lastName, int conID) {
		super(sellerID, firstName, lastName);
		this.conID = conID;
	}
	
	
	@Override
	public ArrayList<PendingBookingRow> checkPendingOperations(int conID) {
		// TODO Auto-generated method stub
		ArrayList<PendingBookingRow> conPendingBookings = new ArrayList<>();
		try {
			conn = openDBconn.connect();
			stmt = conn.createStatement();
			
			
			String sql_pendingBookings = "SELECT bookingDetails.bookingID, bookingDetails.bookingType, customer.firstName, "
					+ "customer.lastName, carDetails.carBrand, carDetails.carModel, carDetails.carColor, carDetails.year, "
					+ "carDetails.kilometers, carDetails.engineType, carDetails.horsePower, bookingDetails.paymentType, "
					+ "bookingDetails.amount "
					+ "FROM bookingDetails, customer, carDetails, seller "
					+ "WHERE bookingDetails.sellerID=seller.conID "
					+ "AND bookingDetails.bookingCompleted='0' "
					+ "AND bookingDetails.custID=customer.custID "
					+ "AND bookingDetails.carID=carDetails.carID";
			ResultSet rs_pendingBookings = stmt.executeQuery(sql_pendingBookings);
			while (rs_pendingBookings.next()) {
				System.out.println("Pending booking "+rs_pendingBookings.getInt("bookingID")+" found!");
				String bookingType = "";
				if (rs_pendingBookings.getInt("bookingType") == 0) {
					bookingType = "SELLING";
				} else {
					bookingType = "BUYING";
				}
				String paymentType = "";
				if (rs_pendingBookings.getInt("paymentType") == 0) {
					paymentType = "UNIQUE";
				} else {
					paymentType = "INSTALLMENTS\n  (24 MONTHS)";
				}
				conPendingBookings.add(new PendingBookingRow(rs_pendingBookings.getInt("bookingID"),
						bookingType,
						rs_pendingBookings.getString("firstName")+" "+rs_pendingBookings.getString("lastName"),
						rs_pendingBookings.getString("carBrand")+" / "+rs_pendingBookings.getString("carModel"),
						rs_pendingBookings.getString("carColor").toUpperCase(),
						rs_pendingBookings.getInt("year"),
						rs_pendingBookings.getInt("kilometers"),
						rs_pendingBookings.getString("engineType"),
						rs_pendingBookings.getString("horsePower"),
						paymentType,
						rs_pendingBookings.getInt("amount")));
			}
			conn.close();
		}catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conPendingBookings;
	}
	
	
	@Override
	public void buyCar(int bookingID) {
		// TODO Auto-generated method stub
		long bookingTimeMillis = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");    
		Date resultdate = new Date(bookingTimeMillis);
		String bookingTime = sdf.format(resultdate);
		try {
			conn = openDBconn.connect();
			stmt = conn.createStatement();
			String sql_acceptBookingBuyCar = "UPDATE bookingDetails SET bookingCompleted='1', bookingTime='"+bookingTime+"' "
					+  "WHERE bookingID='"+bookingID+"'";
			stmt.executeUpdate(sql_acceptBookingBuyCar);
			String sql_getBookingCarID = "SELECT bookingDetails.carID FROM bookingDetails WHERE bookingID='"+bookingID+"'";
			ResultSet rs_getBookingCarID = stmt.executeQuery(sql_getBookingCarID);
			if (!rs_getBookingCarID.next()) {
				System.out.println("No Data");
				return;
			}
			
			String sql_setCarNotSold = "UPDATE CarDetails SET sold='1', exposed='0' "
					+ "WHERE carID='"+rs_getBookingCarID.getString("carID")+"'";
			stmt.executeUpdate(sql_setCarNotSold);
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Booking accepted on: "+bookingTime);
	}

	
	@Override
//	public void sellCar(int carID, boolean statusCar) {
	public void sellCar(int bookingID) {
		// TODO Auto-generated method stub
		long bookingTimeMillis = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");    
		Date resultdate = new Date(bookingTimeMillis);
		String bookingTime = sdf.format(resultdate);
		try {
			conn = openDBconn.connect();
			stmt = conn.createStatement();
			
			String sql_getBookingCarID = "SELECT bookingDetails.carID FROM bookingDetails WHERE bookingID='"+bookingID+"'";
			ResultSet rs_getBookingCarID = stmt.executeQuery(sql_getBookingCarID);
			if (!rs_getBookingCarID.next()) {
				System.out.println("No Data");
				return;
			}
			int sellCarID = rs_getBookingCarID.getInt("carID");
			
			String sql_bookingCarDetail = "SELECT * FROM carDetails WHERE carID='"+sellCarID+"'";
			ResultSet rs_bookingCarDetail = stmt.executeQuery(sql_bookingCarDetail);
			if (!rs_bookingCarDetail.next()) {
				System.out.println("No Data");
				return;
			}
			CarDetails bookingCarDetail = new CarDetails(rs_bookingCarDetail.getInt("carID"),
            		rs_bookingCarDetail.getInt("conID"),
            		rs_bookingCarDetail.getInt("factID"),
            		rs_bookingCarDetail.getString("carBrand").toUpperCase(),
            		rs_bookingCarDetail.getString("carModel").toUpperCase(),
            		rs_bookingCarDetail.getString("carColor").toUpperCase(),
            		EngineType.valueOf(rs_bookingCarDetail.getString("engineType")),
            		rs_bookingCarDetail.getInt("horsePower"),
            		rs_bookingCarDetail.getDouble("price"),
            		rs_bookingCarDetail.getInt("kilometers"),
            		rs_bookingCarDetail.getBoolean("sold"),
            		rs_bookingCarDetail.getBoolean("exposed"),
            		rs_bookingCarDetail.getBoolean("carCondition"),
            		rs_bookingCarDetail.getInt("year"));
			if (bookingCarDetail.isCarCondition()) {
//				FactoryDeposit.
//				String sql_bookingCarDetail = "SELECT * FROM carDetails WHERE carID='"+sellCarID+"'";
//				ResultSet rs_bookingCarDetail = stmt.executeQuery(sql_bookingCarDetail);
//				if (!rs_bookingCarDetail.next()) {
//					System.out.println("No Data");
//					return;
//				}
				
			} else {
				bookingCarDetail.setSold(true);
				bookingCarDetail.setExposed(false);
				String sql_acceptBookingSellCar = "UPDATE bookingDetails SET bookingCompleted='1', bookingTime='"+bookingTime+"' "
						+  "WHERE bookingID='"+bookingID+"'";
				stmt.executeUpdate(sql_acceptBookingSellCar);
			}
			
			
			String sql_acceptBookingSellCar = "UPDATE bookingDetails SET bookingCompleted ='1' WHERE bookingID='"+bookingID+"'";
			stmt.executeUpdate(sql_acceptBookingSellCar);
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		boolean accepted = true; // Take it from the DB
//		if (accepted && statusCar) {
//			int factID = conID;//.getFact();
////			FactoryDeposit.produceCar(carID, null, null, null, null, carID, carID);
//		}
		System.out.println("Booking accepted on: "+bookingTime);
	}

	
	@Override
	public void rejectOperation(int bookingID) {
		// TODO Auto-generated method stub
		try {
			conn = openDBconn.connect();
			stmt = conn.createStatement();
			String sql_deleteBooking = "DELETE FROM bookingDetails WHERE bookingID='"+bookingID+"'";
			stmt.executeUpdate(sql_deleteBooking);
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public ArrayList<CarDetails> checkCarsConcessionaire(int conID) {
		// TODO Auto-generated method stub
		ArrayList<CarDetails> conCars = new ArrayList<>();
		try {
			conn = openDBconn.connect();
			stmt = conn.createStatement();

			String sql_conCars = "SELECT * FROM carDetails WHERE conID='"+conID+"' AND sold='0'";
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

	
	@Override
	public void addCarExposed(int carID) {
		// TODO Auto-generated method stub
		try {
			conn = openDBconn.connect();
			stmt = conn.createStatement();
			String sql_acceptBooking = "UPDATE CarDetails SET exposed ='1' WHERE carID='"+carID+"'";
			stmt.executeUpdate(sql_acceptBooking);
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void deleteCarExposed(int carID) {
		// TODO Auto-generated method stub
		try {
			conn = openDBconn.connect();
			stmt = conn.createStatement();
			String sql_acceptBooking = "UPDATE CarDetails SET exposed ='0' WHERE carID='"+carID+"'";
			stmt.executeUpdate(sql_acceptBooking);
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return the conID
	 */
	public int getConID() {
		return conID;
	}
	/**
	 * @param conId the conId to set
	 */
	public void setConId(int conID) {
		this.conID = conID;
	}

}