package models;

import java.sql.Connection;
import java.sql.DriverManager; 
import java.sql.SQLException;

public class DBConnect {

	// Code database URL
	static final String DB_URL = "jdbc:sqlserver://dealershipserver.database.windows.net:1433;database=DealershipDB;user=root123;password={Contraseņa123};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30";
	// Database credentials
	static final String USER = "root123", PASS = "Contraseņa123";

	public Connection connect() throws SQLException {

		return DriverManager.getConnection(DB_URL, USER, PASS);

	}
}