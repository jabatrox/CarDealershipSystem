package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.sql.Connection;

import java.sql.DriverManager;


public class DaoModel {
	
	//Declare DB objects 
	DBConnect conn = null;
	Statement stmt = null;

		// constructor
	public DaoModel() { //create db object instance
		 conn = new DBConnect();
	}
	
	// CREATE TABLE METHOD
	public void createTable() {
	    try {
	   
	    // Open a connection
	    System.out.println("Connecting to database to create Table...");
	    stmt = conn.connect().createStatement();
	    System.out.println("Connected database successfully...");
	   
	    // Execute create query
	    
	    System.out.println("Creating table in given database...");
	    //String schema = conn.getSchema();
	 
	   
	    String sql = "CREATE TABLE concessionaire (\n" + 
	    		"  conID INTEGER NOT NULL,\n" + 
	    		"  carCapacity INTEGER NOT NULL,\n" + 
	    		"  factID INTEGER NOT NULL REFERENCES factoryDeposit(factID),\n" + 
	    		"  PRIMARY KEY(conID)\n" + 
	    		");\n" + 
	    		"\n" + 
	    		"CREATE TABLE factoryDeposit (\n" + 
	    		"  factID INTEGER NOT NULL,\n" + 
	    		"  carCapacity INTEGER NOT NULL,\n" + 
	    		"  PRIMARY KEY(factID)\n" + 
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
	    conn.connect().close(); //close db connection 
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
