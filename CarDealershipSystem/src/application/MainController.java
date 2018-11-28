package application;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.DBConnect;

public class MainController {
	
	Connection conn = null;
	DBConnect openDBconn = null;
	Statement stmt = null;

	
	@FXML
	private Label statusId;
	
	@FXML
	private TextField userId;
	
	@FXML
	private TextField passwordId;
	
	public void Login(ActionEvent event) throws Exception{
		
		try {
			openDBconn = new DBConnect();
			stmt = openDBconn.connect().createStatement();
			System.out.print("Checking for user in the database");
			
			String sql = "SELECT userType FROM usersDB WHERE userName='"+userId.getText()+"'";
			
			ResultSet rs = stmt.executeQuery(sql);
			if (!rs.next()) {
				userId.setText("");
				passwordId.setText("");
				statusId.setText("The username/password combination is not correct");
			}
			else {
			String response = rs.getString("userType");
			switch(response) {
			
				case "c":
				{
					statusId.setText("Login is Correct...Redirecting to the customer interface");
					Stage primaryStage = new Stage();
					Parent root = FXMLLoader.load(getClass().getResource("/application/Customer.fxml"));
					Scene scene = new Scene(root,400,400);
					scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
					primaryStage.setScene(scene);
					primaryStage.show();
				}
				case "s": 
				{
					statusId.setText("Login is Correct...Redirecting to the seller interface");
					Stage primaryStage = new Stage();
					Parent root = FXMLLoader.load(getClass().getResource("/application/Seller.fxml"));
					Scene scene = new Scene(root,600,400);
					scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
					primaryStage.setScene(scene);
					primaryStage.show();
				}
				case "a":
				{
					statusId.setText("Login is Correct...Redirecting to the admin interface");
					Stage primaryStage = new Stage();
					Parent root = FXMLLoader.load(getClass().getResource("/application/Admin.fxml"));
					Scene scene = new Scene(root,600,400);
					scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
					primaryStage.setScene(scene);
					primaryStage.show();
				}
			}
		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void Register(ActionEvent event) throws Exception{
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/Register.fxml"));
		Scene scene = new Scene(root,400,400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
