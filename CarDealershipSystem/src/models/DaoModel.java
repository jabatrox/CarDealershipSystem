package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;


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

/*			// Open a connection
			System.out.println("Connecting to database to create Tables...");
			stmt = conn.connect().createStatement();
			System.out.println("Connected database successfully...");

			// Execute create query

			System.out.println("Creating table in given database...");
			//String schema = conn.getSchema();


			String sql = "CREATE TABLE factoryDeposit (\n" + 
					"  factID INTEGER NOT NULL,\n" + 
					"  carCapacity INTEGER NOT NULL,\n" + 
					"  PRIMARY KEY(factID)\n" + 
					");\n" + 
					"\n" + 
					"CREATE TABLE concessionaire (\n" + 
					"  conID INTEGER NOT NULL auto_increment,\n" + 
					"  carCapacity INTEGER NOT NULL,\n" + 
					"  factID INTEGER NOT NULL REFERENCES factoryDeposit(factID),\n" + 
					"  PRIMARY KEY(conID)\n" + 
					");\n" + 
					"\n" + 
					"CREATE TABLE carDetails (\n" + 
					"  carID INTEGER NOT NULL,\n" + 
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
					");\n" + 
					"\n" + 
					"CREATE TABLE customer (\n" + 
					"  custID INTEGER NOT NULL,\n" + 
					"  firstName VARCHAR(50) NOT NULL,\n" + 
					"  lastName VARCHAR(50) NOT NULL,\n" + 
					"  address VARCHAR(100) NOT NULL,\n" + 
					"  email VARCHAR(50) NOT NULL,\n" + 
					"  phone VARCHAR(20) NOT NULL,\n" + 
					"  PRIMARY KEY(custID)\n" + 
					");\n" + 
					"\n" + 
					"CREATE TABLE usersDB (\n" + 
					"  userID INTEGER NOT NULL ,\n" + 
					"  username VARCHAR(50) NOT NULL,\n" + 
					"  userType CHAR(1) NOT NULL\n" + 
					"  PRIMARY KEY(userID)\n" + 
					");\n" + 
					"\n" + 
					"CREATE TABLE admin (\n" + 
					"  adminID INTEGER NOT NULL ,\n" + 
					"  firstName VARCHAR(50) NOT NULL,\n" + 
					"  lastName VARCHAR(50) NOT NULL,\n" + 
					"  hashPass CHAR(64) NOT NULL,\n" + 
					"  PRIMARY KEY(adminID)\n" + 
					");\n" + 
					"\n" + 
					"CREATE TABLE seller (\n" + 
					"  sellerID INTEGER NOT NULL ,\n" + 
					"  firstName VARCHAR(50) NOT NULL,\n" + 
					"  lastName VARCHAR(50) NOT NULL,\n" + 
					"  conID INTEGER NOT NULL REFERENCES concessionaire(conID),\n" + 
					"  PRIMARY KEY(sellerID)\n" + 
					");\n" + 
					"\n" + 
					"CREATE TABLE bookingDetails (\n" + 
					"  bookingID INTEGER NOT NULL ,\n" + 
					"  bookingType INTEGER NOT NULL,\n" + 
					"  custID INTEGER NOT NULL REFERENCES customer(custID),\n" + 
					"  sellerID INTEGER NOT NULL REFERENCES seller(sellerID),\n" + 
					"  carID INTEGER NOT NULL REFERENCES carDetails(carID),\n" + 
					"  bookingCompleted TINYINT NOT NULL,\n" + 
					"  paymentType INTEGER NOT NULL,\n" + 
					"  amount INTEGER NOT NULL,\n" + 
					"  PRIMARY KEY(bookingID)\n" + 
					");";
			stmt.executeUpdate(sql);
			System.out.println("Created table in given database...");
			conn.connect().close(); //close db connection */
		}catch (SQLException se) { // Handle errors for JDBC
			se.printStackTrace();
		}
	}

	// INSERT INTO METHOD
	/*public void insertRecords(BankRecords[] robjs) {
        try {
        // Execute a query
        System.out.println("Inserting records into the table...");
        //stmt = conn.connect().createStatement();
        String sql = null;

        // Include all object data to the database table
        for (int i = 0; i < robjs.length; ++i) {
        	// finish string assignment to insert all object data 
        	// (id, income, pep) into your database table

        	sql = "INSERT INTO F_GONZ6_tab(id, income, pep) VALUES (?,?,?)";
        	PreparedStatement st = conn.connect().prepareStatement(sql);
        	st.setString(1, robjs[i].getId());
        	st.setDouble(2, robjs[i].getIncome());
        	st.setString(3, robjs[i].getPep());
            st.executeUpdate();
            st.close();
        }
        System.out.println("Records Inserted!");
        conn.connect().close();
        } catch (SQLException se) { se.printStackTrace();  }
   }// INSERT INTO METHOD

	public ResultSet retrieveRecords() throws SQLException {		
	    ResultSet rs = null;
	    try {
	    	System.out.println("Retrieving records from the database...");
		    stmt = conn.connect().createStatement();
		    String sql = "SELECT * from F_GONZ6_tab";
		    rs = stmt.executeQuery(sql);
		    String sql1 = "SELECT pid, id,income, pep FROM F_GONZ6_tab ORDER BY pep DESC";
		    rs = stmt.executeQuery(sql1);
		    System.out.println("Records retrieved");
		    conn.connect().close();
		}catch (SQLException se) { se.printStackTrace();  }
		return rs;
	}

	public void loanReport(BankRecords[] robjs) {
		double total = 0;
		double max = 0;
		double min = robjs[0].getIncome();
		for (int i = 0; i < robjs.length; ++i) {
			total += robjs[i].getIncome();
			if(robjs[i].getIncome() > max) {
				max = robjs[i].getIncome();
			}
			if(robjs[i].getIncome() < min) {
				min  = robjs[i].getIncome();
			}
		}
		DecimalFormat df = new DecimalFormat(".##"); 
		double avg = total/(robjs.length);
		System.out.println("\n\n");
		System.out.println("Loan Analysis Report:\n");
		System.out.printf("Average income of records: "+df.format(avg)+"\n");
		System.out.printf("Maximum income of records: "+df.format(max)+"\n");
		System.out.printf("Minimum income of records: "+df.format(min));
	}*/
}
