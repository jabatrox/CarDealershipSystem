package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;


public class DaoModel {

	//Declare DB objects 
	DBConnect openDBconn = null;
	Connection conn = null;
	Statement stmt = null;
	
	private String[] DBtables = {
			"factoryDeposit",
			"concessionaire",
			"carDetails",
			"usersDB",
			"customer",
			"admin",
			"seller",
			"bookingDetails"
			};

	// constructor
	public DaoModel() { //create db object instance
		openDBconn = new DBConnect();
	}

	// CREATE TABLE METHOD
	public void createTable() {
		try {

			// Connect to DB and check existing tables.
			System.out.println("Connecting to the DB...");
			conn = openDBconn.connect();
			System.out.println("\\___DB connection successful.");
			System.out.println("\nChecking for existing tables...");
			DatabaseMetaData dbm = conn.getMetaData();
			for (int i = 0; i < DBtables.length; i++) {
				ResultSet rs = dbm.getTables(null, null, DBtables[i], null);
				if (rs.next()) {
					System.out.println("\\___Table "+DBtables[i]+" already exists.");
					continue;
				} else {
					System.out.println("\\___Table "+DBtables[i]+" does not exist, creating it.");
					
					// Create and execute SQL query to create missing tables.
					stmt = conn.createStatement();
					
					String sql = "";
			        switch (DBtables[i]) {
			            case "factoryDeposit":
			            	sql = "CREATE TABLE factoryDeposit (\n" + 
			    					"  factID INTEGER NOT NULL IDENTITY,\n" + 
			    					"  carCapacity INTEGER NOT NULL,\n" + 
			    					"  PRIMARY KEY(factID)\n" + 
			    					");";
			                break;
			            case "concessionaire":
			            	sql = "CREATE TABLE concessionaire (\n" + 
			    					"  conID INTEGER NOT NULL IDENTITY,\n" + 
			    					"  carCapacity INTEGER NOT NULL,\n" + 
			    					"  factID INTEGER NOT NULL REFERENCES factoryDeposit(factID),\n" + 
			    					"  PRIMARY KEY(conID)\n" + 
			    					");";
			                break;
			            case "carDetails":
			            	sql = "CREATE TABLE carDetails (\n" + 
			    					"  carID INTEGER NOT NULL IDENTITY,\n" + 
			    					"  conID INTEGER NOT NULL REFERENCES concessionaire(conID),\n" + 
			    					"  factID INTEGER NOT NULL REFERENCES factoryDeposit(factID),\n" + 
			    					"  carBrand VARCHAR(30) NOT NULL,\n" + 
			    					"  carModel VARCHAR(30) NOT NULL,\n" + 
			    					"  carColor VARCHAR(30) NOT NULL,\n" + 
			    					"  engineType VARCHAR(30) NOT NULL,\n" + 
			    					"  horsePower INTEGER NOT NULL,\n" + 
			    					"  price FLOAT(10) NOT NULL,\n" + 
			    					"  kilometers INTEGER NOT NULL,\n" + 
			    					"  sold TINYINT NOT NULL,\n" + 
			    					"  exposed TINYINT NOT NULL,\n" + 
			    					"  carCondition TINYINT NOT NULL,\n" + 
			    					"  PRIMARY KEY(carID)\n" + 
			    					");";
			                break;
			            case "usersDB":
			            	sql = "CREATE TABLE usersDB (\n" + 
			    					"  userID INTEGER NOT NULL IDENTITY,\n" + 
			    					"  username VARCHAR(50) NOT NULL,\n" + 
			    					"  userType CHAR(1) NOT NULL,\n" + 
			    					"  hashPass CHAR(64) NOT NULL,\n" + 
			    					"  PRIMARY KEY(userID)\n" + 
			    					");";
			                break;
			            case "customer":
			            	sql = "CREATE TABLE customer (\n" + 
			    					"  custID INTEGER NOT NULL IDENTITY,\n" + 
			    					"  firstName VARCHAR(50) NOT NULL,\n" + 
			    					"  lastName VARCHAR(50) NOT NULL,\n" + 
			    					"  address VARCHAR(100) NOT NULL,\n" + 
			    					"  email VARCHAR(50) NOT NULL,\n" + 
			    					"  phone VARCHAR(20) NOT NULL,\n" + 
			    					"  userDB_ID INTEGER NOT NULL REFERENCES usersDB(userID),\n" +
			    					"  PRIMARY KEY(custID)\n" + 
			    					");";
			                break;
			            case "admin":
			            	sql = "CREATE TABLE admin (\n" + 
			    					"  adminID INTEGER NOT NULL IDENTITY,\n" + 
			    					"  firstName VARCHAR(50) NOT NULL,\n" + 
			    					"  lastName VARCHAR(50) NOT NULL,\n" + 
			    					"  userDB_ID INTEGER NOT NULL REFERENCES usersDB(userID),\n" +
			    					"  PRIMARY KEY(adminID)\n" + 
			    					");";
			                break;
			            case "seller":
			            	sql = "CREATE TABLE seller (\n" + 
			    					"  sellerID INTEGER NOT NULL IDENTITY,\n" + 
			    					"  firstName VARCHAR(50) NOT NULL,\n" + 
			    					"  lastName VARCHAR(50) NOT NULL,\n" + 
			    					"  conID INTEGER NOT NULL REFERENCES concessionaire(conID),\n" + 
			    					"  userDB_ID INTEGER NOT NULL REFERENCES usersDB(userID),\n" +
			    					"  PRIMARY KEY(sellerID)\n" + 
			    					");";
			                break;
			            case "bookingDetails":
			            	sql = "CREATE TABLE bookingDetails (\n" + 
			    					"  bookingID INTEGER NOT NULL IDENTITY,\n" + 
			    					"  bookingType INTEGER NOT NULL,\n" + 
			    					"  custID INTEGER NOT NULL REFERENCES customer(custID),\n" + 
			    					"  sellerID INTEGER NOT NULL REFERENCES seller(sellerID),\n" + 
			    					"  carID INTEGER NOT NULL REFERENCES carDetails(carID),\n" + 
			    					"  bookingCompleted TINYINT NOT NULL,\n" + 
			    					"  paymentType INTEGER NOT NULL,\n" + 
			    					"  amount INTEGER NOT NULL,\n" +
			    					"  bookingTime VARCHAR(50) NOT NULL,\n" +
			    					"  PRIMARY KEY(bookingID)\n" + 
			    					");";
			                break;
			            default:
			                break;
			        }
			        stmt.executeUpdate(sql);
				}
			}
			conn.close(); //close db connection 
			System.out.println("==> All tables exist.");
		}catch (SQLException se) { // Handle errors for JDBC
			se.printStackTrace();
		}
	}

}
