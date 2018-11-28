package application;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.DBConnect;

public class MainController {
	
	DBConnect conn = null;
	Statement stmt = null;

	
	@FXML
	private Label statusId;
	
	@FXML
	private TextField userId;
	
	@FXML
	private TextField passwordId;
	
	public void Login(ActionEvent event) {
		
		try {
			stmt = conn.connect().createStatement();
			System.out.print("Checking for user in the database");
			
			String sql = "SELECT userType FROM userDB WHERE userName="+userId;
			
			ResultSet rs = stmt.executeQuery(sql);
			if (rs == null) {
				statusId.setText("The username password combination is not correct");
				userId.setText("");
				passwordId.setText("");
			}
			String response = rs.getString("userType");
			switch(response) {
			
				case "c":
				{
					statusId.setText("Login is Correct...Redirecting to the customer interface");
				}
				case "s": 
				{
					statusId.setText("Login is Correct...Redirecting to the seller interface");
				}
				case "a":
				{
					statusId.setText("Login is Correct...Redirecting to the admin interface");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
