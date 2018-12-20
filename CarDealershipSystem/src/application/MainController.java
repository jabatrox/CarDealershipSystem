package application;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import classes.*;
import models.DaoModel;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
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
		DaoModel dao = new DaoModel();
		dao.createTable();
		launch(args);
	}

	static Connection conn = null;
	static DBConnect openDBconn = new DBConnect();
	static Statement stmt = null;
	
	static String nL = System.getProperty("line.separator");
	
	@FXML
	private Label statusLoginID;
	@FXML
	private TextField usernameLoginID, passwordLoginID;
	
	@FXML
	private TextField firstNameRegisterID, lastNameRegisterID, addressRegisterID, emailRegisterID;
	@FXML
	private TextField phoneRegisterID, usernameRegisterID, passwordRegisterID;
	@FXML
	private Text firstNameRegisterLabel, lastNameRegisterLabel, addressRegisterLabel, emailRegisterLabel;
	@FXML
	private Text phoneRegisterLabel, usernameRegisterLabel, passwordRegisterLabel;

	@FXML
	private Button loginButton, registerButton;
	
	
	public void Login (ActionEvent event) throws Exception{

		try {
			conn = openDBconn.connect();
			stmt = conn.createStatement();
			System.out.println("\nChecking for user in the database...");

			String sql_login = "SELECT * FROM usersDB WHERE userName='"+usernameLoginID.getText()+"' "
					+ "COLLATE SQL_Latin1_General_CP1_CS_AS "
					+ "AND hashPass='"+get_SecurePassword(passwordLoginID.getText())+"'";
			ResultSet rs_login = stmt.executeQuery(sql_login);

			//conn.close();
			if (!rs_login.next()) {
				System.out.println("\\___Login failed: wrong username/password");
				passwordLoginID.setText("");
				statusLoginID.setText("Login failed: wrong username/password");
			}
			else {
				System.out.println("User \""+rs_login.getString("username")+"\" (ID: "+rs_login.getString("userID")+") found!\n");
				String response = rs_login.getString("userType");
								
				switch(response) {
					case "c":
					case "C":
					{
						statusLoginID.setText("");
						String sql_customer = "SELECT * FROM customer WHERE userDB_ID='"+rs_login.getString("userID")+"'";
						ResultSet rs_customer = stmt.executeQuery(sql_customer);
						if (!rs_customer.next()) {
							System.out.println("No Data");
							return;
						}
						Agent customer = new Customer(Integer.parseInt(rs_customer.getString("custID")),
								rs_customer.getString("firstName"),
								rs_customer.getString("lastName"),
								rs_customer.getString("address"),
								rs_customer.getString("email"),
								rs_customer.getString("phone"),
								rs_customer.getInt("userDB_ID"));

						FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Customer.fxml"));
						Stage stage_customer = new Stage();
						Region root = (Region) loader.load();
						CustomerController cController = loader.<CustomerController>getController();
						cController.initData(customer);
						Stage stage = (Stage) loginButton.getScene().getWindow();
					    stage.close();

						Scene scene = new Scene(root);
						scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
						stage_customer.setScene(scene);
						stage_customer.show();
						break;
					}
					case "s":
					case "S":
					{
						statusLoginID.setText("");
						String sql_seller = "SELECT * FROM seller WHERE userDB_ID='"+rs_login.getString("userID")+"'";
						ResultSet rs_seller = stmt.executeQuery(sql_seller);
						if (!rs_seller.next()) {
							System.out.println("No Data");
							return;
						}
						Agent seller = new Seller(Integer.parseInt(rs_seller.getString("sellerID")),
								rs_seller.getString("firstName"),
								rs_seller.getString("lastName"),
								Integer.parseInt(rs_seller.getString("conID")));
						
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Seller.fxml"));
						Stage stage_seller = new Stage();
						Region root = (Region) loader.load();
						SellerController sController = loader.<SellerController>getController();
						sController.initData(seller);
						Stage stage = (Stage) loginButton.getScene().getWindow();
					    stage.close();
						
						Scene scene = new Scene(root);
						scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
						stage_seller.setScene(scene);
						stage_seller.show();
						break;
					}
					case "a":
					case "A":
					{
						statusLoginID.setText("");
						String sql_admin = "SELECT * FROM admin WHERE userDB_ID='"+rs_login.getString("userID")+"'";
						ResultSet rs_admin = stmt.executeQuery(sql_admin);
						if (!rs_admin.next()) {
							System.out.println("No Data");
							return;
						}
						Agent admin = new Admin(Integer.parseInt(rs_admin.getString("adminID")),
								rs_admin.getString("firstName"),
								rs_admin.getString("lastName"));
						
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Admin.fxml"));
						Stage stage_admin = new Stage();
						Region root = (Region) loader.load();
						AdminController aController = loader.<AdminController>getController();
						aController.initData(admin);
						Stage stage = (Stage) loginButton.getScene().getWindow();
					    stage.close();
						
						Scene scene = new Scene(root);
						scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
						stage_admin.setScene(scene);
						stage_admin.show();
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
		Scene scene = new Scene(root);
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
			checkNamesAddress(firstNameRegisterID, firstNameRegisterLabel);
			System.out.printf("    \\___Checking lastname... ");
			checkNamesAddress(lastNameRegisterID, lastNameRegisterLabel);
			System.out.printf("    \\___Checking address... ");
			checkNamesAddress(addressRegisterID, addressRegisterLabel);
			System.out.printf("    \\___Checking email... ");
			checkEmail(emailRegisterID, emailRegisterLabel);
			System.out.printf("    \\___Checking phone... ");
			checkPhone(phoneRegisterID, phoneRegisterLabel);
			System.out.printf("    \\___Checking username... ");
			CheckUserName(usernameRegisterID, usernameRegisterLabel);
			System.out.printf("    \\___Checking password... ");
			checkPassword(passwordRegisterID, passwordRegisterLabel);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return;
		}
		try {
			conn = openDBconn.connect();
			stmt = conn.createStatement();
			
			String sql_usersDB = "INSERT INTO usersDB (username, userType, hashPass) VALUES "
							+ "('"+usernameRegisterID.getText()+"',"
							+ "'C',"
							+ "'"+get_SecurePassword(passwordRegisterID.getText())+"');";
			stmt.executeUpdate(sql_usersDB);
			
//			String sql_getUserID = "SELECT * FROM usersDB WHERE userName='"+usernameRegisterId.getText()+"' "
//					+ "AND hashPass='"+get_SecurePassword(passId.getText())+"'";
			String sql_getUserID = "SELECT * FROM usersDB WHERE userName='"+usernameRegisterID.getText()+"'";
			ResultSet rs = stmt.executeQuery(sql_getUserID);
			if (!rs.next()) {
				System.out.println("Sin resultados");
				return;
			}
			String response = rs.getString("userID");
			
			String sql_customer = "INSERT INTO customer (firstName, lastName, address, email, phone, userDB_ID) VALUES "
							+ "('"+firstNameRegisterID.getText()+"',"
							+ "'"+lastNameRegisterID.getText()+"',"
							+ "'"+addressRegisterID.getText()+"',"
							+ "'"+emailRegisterID.getText()+"',"
							+ "'"+phoneRegisterID.getText()+"',"
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
	
	public static void checkNamesAddress(TextField input, Text inputLabel) 
			throws IllegalArgumentException {
		if (input.getText().isEmpty()) {
			input.setStyle("-fx-border-color: red;");
			new Alert(Alert.AlertType.ERROR, "Missing field "
			+inputLabel.getText()+"! "
					+ "All fields must be filled").show();
			throw new IllegalArgumentException(" ---> Missing field "
					+inputLabel.getText()+"! All fields must be filled.");
		}
		input.setStyle(null);
		System.out.println("OK");
	}
	
	public static void checkEmail(TextField input, Text inputLabel) 
			throws IllegalArgumentException {
		if (input.getText().isEmpty()) {
			input.setStyle("-fx-border-color: red;");
			new Alert(Alert.AlertType.ERROR, "Missing field "+inputLabel.getText()+"! "
					+ "All fields must be filled").show();
			throw new IllegalArgumentException(" ---> Missing field "+inputLabel.getText()
			+"! All fields must be filled.");
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
	
	public static void checkPhone(TextField input, Text inputLabel) 
			throws IllegalArgumentException {
		if (input.getText().isEmpty()) {
			input.setStyle("-fx-border-color: red;");
			new Alert(Alert.AlertType.ERROR, "Missing field "
			+inputLabel.getText()+"! "
					+ "All fields must be filled").show();
			throw new IllegalArgumentException(" ---> Missing field "
					+inputLabel.getText()+"! All fields must be filled.");
		}
		if (!input.getText().matches("\\d{9,13}")) {
			if (input.getText().length() < 9 || input.getText().length() > 13) {
				input.setStyle("-fx-border-color: red;");
				new Alert(Alert.AlertType.ERROR, "Incorrect phone format: "
						+ "phone length is not valid").show();
				throw new IllegalArgumentException(" ---> Incorrect phone "
						+ "format: phone length is not valid.");
			}
			input.setStyle("-fx-border-color: red;");
			new Alert(Alert.AlertType.ERROR, "Incorrect phone format: must "
					+ "be numbers 0-9").show();
			throw new IllegalArgumentException(" ---> Incorrect phone "
					+ "format: must be numbers 0-9.");
		}
		input.setStyle(null);
		System.out.println("OK");
	}

	public static void CheckUserName(TextField input, Text inputLabel) throws IllegalArgumentException {
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
			
			String sql = "SELECT username FROM usersDB WHERE userName='"+username+"' COLLATE SQL_Latin1_General_CP1_CS_AS";
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
	
	public static void checkPassword(TextField input, Text inputLabel) throws IllegalArgumentException {
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
		firstNameRegisterID.setText("");
		firstNameRegisterID.setStyle(null);
		
		lastNameRegisterID.setText("");
		lastNameRegisterID.setStyle(null);
		
		addressRegisterID.setText("");
		addressRegisterID.setStyle(null);
		
		emailRegisterID.setText("");
		emailRegisterID.setStyle(null);
		
		phoneRegisterID.setText("");
		phoneRegisterID.setStyle(null);
		
		usernameRegisterID.setText("");
		usernameRegisterID.setStyle(null);
		
		passwordRegisterID.setText("");
		passwordRegisterID.setStyle(null);
		System.out.println("\nAll fields cleared!");
	}

}