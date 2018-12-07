package classes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Random;

import classes.CarDetails.EngineType;
import models.DBConnect;

/**
 * --------------------------------------------------
 * @author Javier Soler, Borja Gonzï¿½lez
 * Date: 11/09/2018
 * Lab: Final Project
 * File name: FactoryDeposit.java
 */
public class FactoryDeposit {
	
	static Connection conn = null;
	static DBConnect openDBconn = new DBConnect();
	static Statement stmt = null;
	
	private int factID;
	private int carCapacity;
		
	/**
	 * @param factID
	 * @param carCapacity
	 */
	public FactoryDeposit(int factID, int carCapacity) {
		this.factID = factID;
		this.carCapacity = carCapacity;
	}
	
	public static CarDetails produceCar(CarDetails bookingCarDetail) {
		/*INSERT INTO carDetails (conID, factID, carBrand, carModel, carColor, engineType, horsePower, price, kilometers, sold, exposed, carCondition, year)
	    VALUES ('1', '1', 'Citroën', 'C-Zero', 'Black', 'ELECTRIC', '67', '2000', '50000', '0', '0', '0', '2018');*/
		CarDetails newBookingCarDetail = null;
		try {
			conn = openDBconn.connect();
			stmt = conn.createStatement();
			String sql_newCarDetail = "INSERT INTO carDetails "
					+ "(conID, factID, carBrand, carModel, carColor, engineType,"
					+ "horsePower, price, kilometers, sold, exposed, carCondition, year)"
					+ "VALUES ("
					+ "'"+bookingCarDetail.getConID()+"',"
					+ "'"+bookingCarDetail.getFactID()+"',"
					+ "'"+bookingCarDetail.getCarBrand()+"',"
					+ "'"+bookingCarDetail.getCarModel()+"',"
					+ "'"+bookingCarDetail.getCarColor()+"',"
					+ "'"+bookingCarDetail.getEngineType()+"',"
					+ "'"+bookingCarDetail.getHorsePower()+"',"
					+ "'"+bookingCarDetail.getPrice()+"',"
					+ "'"+bookingCarDetail.getKilometers()+"',"
					+ "'0',"
					+ "'0',"
					+ "'0',"
					+ "'"+Calendar.getInstance().get(Calendar.YEAR)+"');"
					+ "\n"
					+ "SELECT SCOPE_IDENTITY();";
			ResultSet rs_newCarDetailID = stmt.executeQuery(sql_newCarDetail);
			if (!rs_newCarDetailID.next()) {
				System.out.println("No Data");
				return bookingCarDetail;
			}
			String sql_getNewCarDetail = "SELECT * FROM carDetails WHERE carID='"+rs_newCarDetailID.getString("")+"'";
			ResultSet rs_getNewCarDetail = stmt.executeQuery(sql_getNewCarDetail);
			if (!rs_getNewCarDetail.next()) {
				System.out.println("No Data");
				return bookingCarDetail;
			}
			newBookingCarDetail = new CarDetails(rs_getNewCarDetail.getInt("carID"),
					rs_getNewCarDetail.getInt("conID"),
					rs_getNewCarDetail.getInt("factID"),
					rs_getNewCarDetail.getString("carBrand").toUpperCase(),
					rs_getNewCarDetail.getString("carModel").toUpperCase(),
					rs_getNewCarDetail.getString("carColor").toUpperCase(),
            		EngineType.valueOf(rs_getNewCarDetail.getString("engineType")),
            		rs_getNewCarDetail.getInt("horsePower"),
            		rs_getNewCarDetail.getDouble("price"),
            		rs_getNewCarDetail.getInt("kilometers"),
            		rs_getNewCarDetail.getBoolean("sold"),
            		rs_getNewCarDetail.getBoolean("exposed"),
            		rs_getNewCarDetail.getBoolean("carCondition"),
            		rs_getNewCarDetail.getInt("year"));
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newBookingCarDetail;
	}
	
	/**
	 * @return the factID
	 */
	public int getFactID() {
		return factID;
	}
	/**
	 * @param factID the factID to set
	 */
	public void setFactID(int factID) {
		this.factID = factID;
	}
	/**
	 * @return the carCapacity
	 */
	public int getCarCapacity() {
		return carCapacity;
	}
	/**
	 * @param carCapacity the carCapacity to set
	 */
	public void setCarCapacity(int carCapacity) {
		this.carCapacity = carCapacity;
	}
	
}