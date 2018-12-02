package application;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import models.DBConnect;

public class MainController extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/application/Login.fxml"));
			Scene scene = new Scene(root,300,260);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	Connection conn = null;
	DBConnect openDBconn = new DBConnect();
	Statement stmt = null;
	
	static String nL = System.getProperty("line.separator");
	
	@FXML
	private Label statusId;

	@FXML
	private TextField userId;

//	@FXML
//	private TextField userNameId;

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
			System.out.println("Checking for user in the database...");

			String sql = "SELECT * FROM usersDB WHERE userName='"+userId.getText()+"' AND hashPass='"+get_SecurePassword(passwordId.getText())+"'";

			ResultSet rs = stmt.executeQuery(sql);
//			conn.close();
			if (!rs.next()) {
				System.out.println("\\___Login failed: wrong username/password");
				passwordId.setText("");
				statusId.setText("Login failed: wrong username/password");
			}
			else {
				System.out.println("User found!");
				String response = rs.getString("userType");
				switch(response) {
					case "c":
					{
						statusId.setText("");
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Customer.fxml"));
						Stage stage = new Stage();
						Region root = (Region) loader.load();
						CustomerController cController = loader.<CustomerController>getController();
						cController.initData(rs.getString("userID"));

						Scene scene = new Scene(root,600,400);
						scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
						stage.setScene(scene);
	
						stage.show();
						break;
					}
					case "s": 
					{
						statusId.setText("");
						Stage primaryStage = new Stage();
						Parent root = FXMLLoader.load(getClass().getResource("/application/Seller.fxml"));
						Scene scene = new Scene(root,600,400);
						scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
						primaryStage.setScene(scene);
						primaryStage.show();
						break;
					}
					case "a":
					{
						statusId.setText("");
						Stage primaryStage = new Stage();
						Parent root = FXMLLoader.load(getClass().getResource("/application/Admin.fxml"));
						Scene scene = new Scene(root,600,400);
						scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
						primaryStage.setScene(scene);
						primaryStage.show();
						break;
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
			System.out.printf("    \\___Checking username... ");
			CheckUserName(usernameRegisterId, usernameRegisterLabel);
			System.out.printf("    \\___Checking password... ");
			checkPassword(passId, passLabel);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return;
		}
		try {
			conn = openDBconn.connect();
			stmt = conn.createStatement();
			
			String sql_usersDB = "INSERT INTO usersDB (username, userType, hashPass) VALUES "
							+ "('"+usernameRegisterId.getText()+"',"
							+ "'C',"
							+ "'"+get_SecurePassword(passId.getText())+"');";
			stmt.executeUpdate(sql_usersDB);
			
//			String sql_getUserID = "SELECT * FROM usersDB WHERE userName='"+usernameRegisterId.getText()+"' "
//					+ "AND hashPass='"+get_SecurePassword(passId.getText())+"'";
			String sql_getUserID = "SELECT * FROM usersDB WHERE userName='"+usernameRegisterId.getText()+"'";
			ResultSet rs = stmt.executeQuery(sql_getUserID);
			if (!rs.next()) {
				System.out.println("Sin resultados");
				return;
			}
			String response = rs.getString("userID");
			
			String sql_customer = "INSERT INTO customer (firstName, lastName, address, email, phone, userDB_ID) VALUES "
							+ "('"+firstNameId.getText()+"',"
							+ "'"+lastNameId.getText()+"',"
							+ "'"+addressId.getText()+"',"
							+ "'"+emailId.getText()+"',"
							+ "'"+phoneId.getText()+"',"
							+ "'"+response+"')";
			stmt.executeUpdate(sql_customer);
			conn.close();
		} catch (SQLException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return; 
		}
		System.out.println("New user added.");
	}
	
	public void checkNamesAddress(TextField input, Label inputLabel) throws IllegalArgumentException {
		if (input.getText().isEmpty()) {
			input.setStyle("-fx-border-color: red;");
			new Alert(Alert.AlertType.ERROR, "Missing field "+inputLabel.getText()+"! "
					+ "All fields must be filled").show();
			throw new IllegalArgumentException(" ---> Missing field "+inputLabel.getText()+"! All fields must be filled.");
		}
		input.setStyle(null);
		System.out.println("OK");
	}
	
	public void checkEmail(TextField input, Label inputLabel) throws IllegalArgumentException {
		if (input.getText().isEmpty()) {
			input.setStyle("-fx-border-color: red;");
			new Alert(Alert.AlertType.ERROR, "Missing field "+inputLabel.getText()+"! "
					+ "All fields must be filled").show();
			throw new IllegalArgumentException(" ---> Missing field "+inputLabel.getText()+"! All fields must be filled.");
		}
		// Regex pattern to valid email address
	    String EMAIL_REGEX="^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
	    Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(input.getText());
	    if (!matcher.matches()) {
	    	input.setStyle("-fx-border-color: red;");
			new Alert(Alert.AlertType.ERROR, "Incorrect email format").show();
	    	throw new IllegalArgumentException(" ---> Incorrect email format.");
	    }
	    input.setStyle(null);
	    System.out.println("OK");
	}
	
	public void checkPhone(TextField input, Label inputLabel) throws IllegalArgumentException {
		if (input.getText().isEmpty()) {
			input.setStyle("-fx-border-color: red;");
			new Alert(Alert.AlertType.ERROR, "Missing field "+inputLabel.getText()+"! "
					+ "All fields must be filled").show();
			throw new IllegalArgumentException(" ---> Missing field "+inputLabel.getText()+"! All fields must be filled.");
		}
		if (!input.getText().matches("\\d{9,13}")) {
			if (input.getText().length() < 9 || input.getText().length() > 13) {
				input.setStyle("-fx-border-color: red;");
				new Alert(Alert.AlertType.ERROR, "Incorrect phone format: phone length is not valid").show();
				throw new IllegalArgumentException(" ---> Incorrect phone format: phone length is not valid.");
			}
			input.setStyle("-fx-border-color: red;");
			new Alert(Alert.AlertType.ERROR, "Incorrect phone format: must be numbers 0-9").show();
			throw new IllegalArgumentException(" ---> Incorrect phone format: must be numbers 0-9.");
		}
		input.setStyle(null);
		System.out.println("OK");
	}

	public void CheckUserName(TextField input, Label inputLabel) throws IllegalArgumentException {
		if (input.getText().isEmpty()) {
			input.setStyle("-fx-border-color: red;");
			new Alert(Alert.AlertType.ERROR, "Missing field "+inputLabel.getText()+"! "
					+ "All fields must be filled").show();
			throw new IllegalArgumentException(" ---> Missing field "+inputLabel.getText()+"! All fields must be filled.");
		}
		String username = input.getText();
		try {
			System.out.printf("checking if \""+username+"\" is available... ");
			conn = openDBconn.connect();
			stmt = conn.createStatement();
			
			String sql = "SELECT username FROM usersDB WHERE userName='"+username+"'";
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				input.setStyle("-fx-border-color: red;");
				new Alert(Alert.AlertType.ERROR, "Username "+username
						+" already exits, please select a different one").show();
				throw new IllegalArgumentException("Username "+username+" already exits, please select a different one.");
			}
			input.setStyle(null);
			System.out.println("OK");
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void checkPassword(TextField input, Label inputLabel) throws IllegalArgumentException {
		if (input.getText().isEmpty()) {
			input.setStyle("-fx-border-color: red;");
			new Alert(Alert.AlertType.ERROR, "Missing field "+inputLabel.getText()+"! "
					+ "All fields must be filled").show();
			throw new IllegalArgumentException(" ---> Missing field "+inputLabel.getText()+"! All fields must be filled.");
		}
		String password = input.getText();
		boolean isAtLeast8   = password.length() >= 8; //Checks for at least 8 characters
		boolean hasUppercase = !password.equals(password.toLowerCase()); //Checks for at least one uppercase characters
		boolean hasLowercase = !password.equals(password.toUpperCase()); //Checks for at least one lowercase characters
		boolean hasSpecial   = !password.matches("[A-Za-z0-9 ]*"); //Checks for at least non-alphanumeric character
		boolean noConditions = !(password.contains("AND ") || password.contains("NOT ")); //Check that it doesn't contain AND or NOT
		
		if (isAtLeast8 && hasUppercase && hasLowercase && hasSpecial && noConditions) {
			try {
				get_SecurePassword(password);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			input.setStyle("-fx-border-color: red;");
			new Alert(Alert.AlertType.ERROR, "Invalid password: must have at least 8 characters, with one uppercase, "
					+ "one lowercase, and one non-alphanumeric character").show();
			throw new IllegalArgumentException(" ---> Invalid password: must have at least 8 characters, with one uppercase, "
					+ "one lowercase, and one non-alphanumeric character.");
		}
		input.setStyle(null);
		System.out.println("OK");
	}

	// Password hashing function
	public static String get_SecurePassword(String passwordToHash) throws UnsupportedEncodingException {
		String generatedPassword = passwordToHash;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");

			byte[] bytes = md.digest(passwordToHash.getBytes("UTF-8"));
			StringBuilder sb = new StringBuilder();
			for(int i=0; i< bytes.length ;i++){
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		} 
		catch (NoSuchAlgorithmException e){
			e.printStackTrace();
		}
		return generatedPassword;

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