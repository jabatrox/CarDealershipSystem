package application;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.DBConnect;

public class MainController {

	Connection conn = null;
	DBConnect openDBconn = new DBConnect();
	Statement stmt = null;
	
	static String nL = System.getProperty("line.separator");


	@FXML
	private Label statusId;

	@FXML
	private TextField userId;

	@FXML
	private TextField userNameId;

	@FXML
	private TextField passwordId;

	@FXML
	private TextField firstNameId;
	@FXML
	private Label firstNameLabel;

	@FXML
	private TextField lastNameId;	
	@FXML
	private Label lastNameLabel;

	@FXML
	private TextField addressId;
	@FXML
	private Label addressLabel;

	@FXML
	private TextField emailId;
	@FXML
	private Label emailLabel;

	@FXML
	private TextField phoneId;
	@FXML
	private Label phoneLabel;

	@FXML
	private TextField usernameRegisterId;
	@FXML
	private Label usernameRegisterLabel;

	@FXML
	private TextField passId;
	@FXML
	private Label passLabel;

	public void Login (ActionEvent event) throws Exception{

		try {
			conn = openDBconn.connect();
			stmt = conn.createStatement();
			System.out.print("Checking for user in the database");

			String sql = "SELECT userType FROM usersDB WHERE userName='"+userId.getText()+"'";

			ResultSet rs = stmt.executeQuery(sql);
//			conn.close();
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
						statusId.setText("Correct login... Redirecting to the customer interface");
						Stage primaryStage = new Stage();
						Parent root = FXMLLoader.load(getClass().getResource("/application/Customer.fxml"));
						Scene scene = new Scene(root,600,400);
						scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
						primaryStage.setScene(scene);
						primaryStage.show();
					}
					case "s": 
					{
						statusId.setText("Correct login... Redirecting to the seller interface");
						Stage primaryStage = new Stage();
						Parent root = FXMLLoader.load(getClass().getResource("/application/Seller.fxml"));
						Scene scene = new Scene(root,600,400);
						scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
						primaryStage.setScene(scene);
						primaryStage.show();
					}
					case "a":
					{
						statusId.setText("Correct login... Redirecting to the admin interface");
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

	public void RegisterPage (ActionEvent event) throws Exception{
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/Register.fxml"));
		Scene scene = new Scene(root,600,400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void RegisterUser () {
		System.out.println("\nAdding new user...");
		
		try {
			// Check if all fields have content
			System.out.println("\\___Checking fields...");
			System.out.printf("    \\___Checking firstname... ");
			checkNamesAddress(firstNameId, firstNameLabel);
			System.out.printf("    \\___Checking lastname... ");
			checkNamesAddress(lastNameId, lastNameLabel);
			System.out.printf("    \\___Checking address... ");
			checkNamesAddress(addressId, addressLabel);
			System.out.printf("    \\___Checking email... ");
			checkEmail(emailId, emailLabel);
			System.out.printf("    \\___Checking phone... ");
			checkPhone(phoneId, phoneLabel);
			System.out.printf("    \\___Checking username...");
			CheckUserName(usernameRegisterId, usernameRegisterLabel);
	//		passId.getText().isEmpty();
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return;
		}
		System.out.println("New user added.");
	}
	
	public static boolean checkNamesAddress(TextField input, Label inputLabel) throws IllegalArgumentException {
		if (input.getText().isEmpty()) {
			throw new IllegalArgumentException(" ---> Missing field "+inputLabel.getText()+"! All fields must be filled.");
		}
		System.out.println("OK");
		return true;
	}
	
	public static boolean checkEmail(TextField input, Label inputLabel) throws IllegalArgumentException {
		if (input.getText().isEmpty()) {
			throw new IllegalArgumentException(" ---> Missing field "+inputLabel.getText()+"! All fields must be filled.");
		}
		// Regex pattern to valid email address
	    String EMAIL_REGEX="^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
	    Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(input.getText());
	    if (!matcher.matches()) {
	    	throw new IllegalArgumentException(" ---> Incorrect email format.");
	    }
	    System.out.println("OK");
	    return matcher.matches();
	}
	
	public static boolean checkPhone(TextField input, Label inputLabel) throws IllegalArgumentException {
		if (input.getText().isEmpty()) {
			throw new IllegalArgumentException(" ---> Missing field "+inputLabel.getText()+"! All fields must be filled.");
		}
		if (!input.getText().matches("\\d{9,13}")) {
			if (input.getText().length() < 9 || input.getText().length() > 13) {
				throw new IllegalArgumentException(" ---> Incorrect phone format: phone length is not valid.");
			}
			throw new IllegalArgumentException(" ---> Incorrect phone format: must be numbers 0-9.");
		}
		System.out.println("OK");
		return true;
	}

	public void CheckUserName(TextField input, Label inputLabel) throws IllegalArgumentException {
		if (input.getText().isEmpty()) {
			throw new IllegalArgumentException(" ---> Missing field "+inputLabel.getText()+"! All fields must be filled.");
		}
		try {
			conn = openDBconn.connect();
			stmt = conn.createStatement();

			String sql = "SELECT username FROM usersDB WHERE userName='"+usernameRegisterId.getText()+"'";
			ResultSet rs = stmt.executeQuery(sql);
			System.out.printf(nL+"        \\___Checking if username \""+usernameRegisterId.getText()+"\" is available... ");
			if (rs.next()) {
				System.out.println("---> Username \""+usernameRegisterId.getText()+"\" already exits,"
						+ " please select a different one.");
				usernameRegisterId.setStyle("-fx-border-color: red;");
				new Alert(Alert.AlertType.WARNING, "Username "+usernameRegisterId.getText()
						+" already exits, please select a different one").showAndWait();
			} else {
				System.out.println("OK");
				usernameRegisterId.setStyle(null);
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void ClearAll() {
		firstNameId.setText("");
		lastNameId.setText("");
		addressId.setText("");
		emailId.setText("");
		phoneId.setText("");
		usernameRegisterId.setText("");
		usernameRegisterId.setStyle(null);
		passId.setText("");
		System.out.println("\nAll fields cleared!");
	}
}
