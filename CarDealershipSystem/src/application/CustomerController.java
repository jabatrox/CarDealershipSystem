package application;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import classes.*;
import classes.CarDetails.EngineType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.DBConnect;

public class CustomerController implements Initializable{

	Connection conn = null;
	DBConnect openDBconn = new DBConnect();
	Statement stmt = null;
	
	private Customer customer;
	private boolean firstTimeRun = true;
	private Image buyButton;

	@FXML
	private Label customerWelcomeName, customerWelcomeID;

//	@FXML
//	private String userID;

	@FXML
	private TextField brand, model, color, horsePower, price, miles, year, conID, factID, chassis;

	@FXML
	private ChoiceBox<String> engineTypes;

	@FXML
	private ComboBox<String> buyCarOptions;
	
	@FXML
	private Button requestButton, closeButton;
	
	@FXML
	private TextField updateFirstName, updateLastName, updateAddress, updateEmail, updatePhone;
	
	@FXML
	private TextField oldPass, newPass1, newPass2;
	
	@FXML
	private Text newPassField;
	
	@FXML
	private Text firstNameUpdateLabel, lastNameUpdateLabel, addressUpdateLabel, emailUpdateLabel, phoneUpdateLabel, passwordUpdateLabel;
	
	@FXML
	private TableView<CarDetails> carsExposedCustomerTable;
	private ObservableList<CarDetails> carsExposedTable_data = FXCollections.observableArrayList();
	

	public CustomerController(){
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		buyCarOptions.setStyle("-fx-font: 12px \"Lucida Sans Unicode\";");
		carsExposedCustomerTable.setPlaceholder(new Label("THERE ARE NO CARS AVAILABLE FOR SELLING"));
	}

	void initData(Agent customer_logged_in) {
		customer = (Customer) customer_logged_in;
		String fullname = customer.getFirstName()+" "+customer.getLastName();
		customerWelcomeName.setText(fullname.toUpperCase());
		String cust_id = Integer.toString((customer.getAgentID()));
		customerWelcomeID.setText("CUSTOMER ID: "+cust_id);
		
		String option = buyCarOptions.getSelectionModel().getSelectedItem();
		System.out.println("Showing "+option);
		carsExposedCustomerTable.getItems().clear();
		ArrayList<CarDetails> conCars = customer.checkCars(option);
		for (CarDetails conCar : conCars) {
			carsExposedTable_data.add(conCar);
		}
		carsExposedCustomerTable.setItems(carsExposedTable_data);
		
		if (!carsExposedCustomerTable.getItems().isEmpty() && firstTimeRun) {
			formatCarConditionInTable();
			addBuyButtonToTable();
		}
		
		try {
			conn = openDBconn.connect();
			stmt = conn.createStatement();

			String sql = "SELECT * FROM customer WHERE custID='"+customer.getAgentID()+"'";
			
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				updateFirstName.setText(rs.getString("firstName"));
				updateLastName.setText(rs.getString("lastName"));
				updateAddress.setText(rs.getString("address"));
				updateEmail.setText(rs.getString("email"));
				updatePhone.setText(rs.getString("phone"));
			}
			
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		firstTimeRun = false;
	}
	private void addBuyButtonToTable() {
		buyButton = new Image(getClass().getResourceAsStream("../../resources/buy_round.png"),30,30,false,false);
		TableColumn<CarDetails, Void> buyColBtn = new TableColumn<CarDetails, Void>("");
		Callback<TableColumn<CarDetails, Void>, TableCell<CarDetails, Void>> cellFactory = 
				new Callback<TableColumn<CarDetails, Void>, TableCell<CarDetails, Void>>() {
			@Override
			public TableCell<CarDetails, Void> call(final TableColumn<CarDetails, Void> param) {
				final TableCell<CarDetails, Void> cell = new TableCell<CarDetails, Void>() {
					
					private ImageView imageView = new ImageView();
					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							imageView.setImage(buyButton);
							setGraphic(imageView);
						}
						setOnMousePressed(new EventHandler<MouseEvent>() {
				            @Override
				            public void handle(MouseEvent event) {
				            	Alert alert = new Alert(AlertType.CONFIRMATION);
				            	alert.setTitle("Confirmation Dialog");
				            	alert.setHeaderText("Confirming operation");
				            	alert.setContentText("Are you sure you want to buy this car?");
				            	 Optional<ButtonType> result = alert.showAndWait();
				            	 if (result.get() == ButtonType.OK){
				            	     customer.buyCar(getTableView().getItems().get(getIndex()), customer.getAgentID());
				            	 } else {
				            	     new Alert(Alert.AlertType.INFORMATION, "Operation cancelled");
				            	 }
				    
				            }            
				        });
					}
				};
				return cell;
			}
		};
		buyColBtn.setCellFactory(cellFactory);
		carsExposedCustomerTable.getColumns().add(buyColBtn);
		buyColBtn.setStyle("-fx-alignment: CENTER");
	}
	
	public void ListAvailableCars(ActionEvent event) throws Exception{
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/ListCars.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/*public void SellCar(ActionEvent event) throws Exception{
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/Customer_Sell_Car.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();

		//try {String cLV = sellCarOptions.getSelectionModel().getSelectedItem();} catch (Exception e) {};
	}*/

	public void CustomerPage (ActionEvent event) throws Exception {
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/Customer.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void RequestSellCar(ActionEvent event) {
		try {
			conn = openDBconn.connect();
			stmt = conn.createStatement();

			//Check data
			String br = brand.getText();
			String mo = model.getText();		
			String co = color.getText();
			String et = engineTypes.getSelectionModel().getSelectedItem();
			String hp = horsePower.getText();
			String pr = price.getText();
			String mi = miles.getText();
			String ye = year.getText();
			String ci = conID.getText();
			String fi = factID.getText();
			String chassisId = chassis.getText();

			System.out.println(chassisId);

			int carId = Integer.parseInt(chassisId);
			////////////////////// COMPROBAR SI SE HA PASADO UN CHASSID ID, SI NO PETA AL HACER SEND REQUEST //////////////////////////

			String sql = "SELECT * FROM carDetails WHERE carID='"+carId+"'";

			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
				CarDetails car = new CarDetails(carId,
						rs.getInt("conID"),
						rs.getInt("factID"),
						rs.getString("carBrand"),
						rs.getString("carModel"),
						co,
						EngineType.valueOf(rs.getString("engineType")),
						rs.getInt("horsePower"),
						Double.parseDouble(pr),
						Integer.parseInt(mi),
						rs.getBoolean("sold"),
						rs.getBoolean("exposed"),
						rs.getBoolean("carCondition"),
						rs.getInt("year"));
				if(customer.sellCar(car)) {
					new Alert(Alert.AlertType.INFORMATION, "Request Complete. Waiting for "
							+ "an employees approval").show();
				}

			} else {
				new Alert(Alert.AlertType.ERROR, "The Chassis ID "+carId+" "
						+ "is incorrect. Only cars that were previously bought from us are accepted").show();
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void formatCarConditionInTable() {
		TableColumn<CarDetails, Void> showCarCondition = new TableColumn<CarDetails, Void>("STATE");
		
		Callback<TableColumn<CarDetails, Void>, TableCell<CarDetails, Void>> cellFactory = 
				new Callback<TableColumn<CarDetails, Void>, TableCell<CarDetails, Void>>() {
			@Override
			public TableCell<CarDetails, Void> call(final TableColumn<CarDetails, Void> param) {
				final TableCell<CarDetails, Void> cell = new TableCell<CarDetails, Void>() {
					
					private final Label carCondition = new Label();
					{
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(carCondition);
							if (getTableView().getItems().get(getIndex()).isCarCondition()) {
								carCondition.setText("NEW");
							} else {
								carCondition.setText("USED");
							}
						}
					}
				};
				return cell;
			}
		};
		showCarCondition.setCellFactory(cellFactory);
		carsExposedCustomerTable.getColumns().add(showCarCondition);
		showCarCondition.setStyle("-fx-alignment: CENTER");
	}
	
	public void loadTable() {
		initData(customer);
	}
	
	public void updateUserInfo() {
		System.out.println("\nUpdating new user...");
		
		try {
			conn = openDBconn.connect();
			stmt = conn.createStatement();
			
			// Check if all fields have content
			System.out.println("\\___Checking fields...");
			System.out.printf("    \\___Checking firstname... ");
			MainController.checkNamesAddress(updateFirstName, firstNameUpdateLabel);
			System.out.printf("    \\___Checking lastname... ");
			MainController.checkNamesAddress(updateLastName, lastNameUpdateLabel);
			System.out.printf("    \\___Checking address... ");
			MainController.checkNamesAddress(updateAddress, addressUpdateLabel);
			System.out.printf("    \\___Checking email... ");
			MainController.checkEmail(updateEmail, emailUpdateLabel);
			System.out.printf("    \\___Checking phone... ");
			MainController.checkPhone(updatePhone, phoneUpdateLabel);

				
			String sql_usersDB = "UPDATE customer SET firstName="
					+ "'"+updateFirstName.getText()+"',lastName="
					+ "'"+updateLastName.getText()+"',address="
					+ "'"+updateAddress.getText()+"',email="
					+ "'"+updateEmail.getText()+"',phone="
					+ "'"+updatePhone.getText()+"' WHERE custID="
					+ "'"+customer.getAgentID()+"'";
			if (1==stmt.executeUpdate(sql_usersDB)){
				new Alert(Alert.AlertType.INFORMATION, "Your new information has been updated").show();
				
			}
			else {
				new Alert(Alert.AlertType.INFORMATION, "There was an error while trying to update your profile").show();
			}
			
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return; 
		}
		System.out.println("User updated.");
		
	}
	
	public void updateUserPassword() {
		if (oldPass.getText().equals("")) {
			new Alert(Alert.AlertType.ERROR, "Please introduce your current password").show();
		}else {
			String oldPassword="";
			try {
				oldPassword = MainController.get_SecurePassword(oldPass.getText());
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				conn = openDBconn.connect();
				stmt = conn.createStatement();
				
				
				String sql3 = "SELECT hashPass FROM usersDB WHERE userID='"+customer.getUserDB_ID()+"'";
				

				ResultSet rs = stmt.executeQuery(sql3);
				
				if (rs.next()) {
					System.out.printf("HashPass: "+rs.getString("hashPass")+"\n");

					System.out.println("Old password: "+oldPassword);
					if (rs.getString("hashPass").equals(oldPassword)) {
						System.out.printf("    \\___Checking password... ");
						if (newPass1.getText().equals(newPass2.getText())) {
							if(checkPassword(newPass1, newPassField)) {
								try {
									String new_pass = MainController.get_SecurePassword(newPass1.getText());
									String sql_new = "UPDATE usersDB SET hashPass='"+new_pass+"' WHERE userID='"+customer.getUserDB_ID()+"'";
									if(1==stmt.executeUpdate(sql_new)){
										new Alert(Alert.AlertType.INFORMATION, "Your new password has been set").show();
									}
									else {
										new Alert(Alert.AlertType.INFORMATION, "There was an error while trying to update your password").show();
									}
									
								} catch (UnsupportedEncodingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
							}
						}else {
							new Alert(Alert.AlertType.ERROR, "The confirmed password does not match the new password").show();;
						}
					} else {
						new Alert(Alert.AlertType.ERROR, "The password you introduced does not match your current password").show();;
					}
				}
				conn.close();
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public boolean checkPassword(TextField input, Text inputLabel) throws IllegalArgumentException {
		Boolean x = false;
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
				MainController.get_SecurePassword(password);
				x = true;
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
		return x;
	}
	
	@FXML
	public void logoutCloseWindowAction(ActionEvent event) {
	    Stage stage = (Stage) closeButton.getScene().getWindow();
	    stage.close();
	}
	


//	/**
//	 * @return the userName
//	 */
//	public String getUserID() {
//		return userID;
//	}
//
//
//	/**
//	 * @param userName the userName to set
//	 */
//	public void setUserID(String userID) {
//		this.userID = userID;
//	}

}
