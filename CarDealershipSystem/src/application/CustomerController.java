package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import classes.*;
import classes.CarDetails.EngineType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.DBConnect;

public class CustomerController implements Initializable{
	
	Connection conn = null;
	DBConnect openDBconn = new DBConnect();
	Statement stmt = null;
	
	@FXML
	private Label customerWelcomeId;
	
	@FXML
	private String userID;
	
	@FXML
	private TextField brand,model,color,horsePower,price,miles,year,conID,factID,chassis;
	
	@FXML
	private ChoiceBox<String> engineTypes;
	
	@FXML
	private ChoiceBox<String> sellCarOptions;
	
	@FXML
	private ListView carListView;
	
	@FXML
	private Button requestButton;
	
	private Customer customer;
	
	
	public CustomerController(){
	
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
	}
	
	void initData(Agent customer1) {
		final Customer customer = (Customer) customer1;
		String name = "Welcome "+customer.getFirstName();
		customerWelcomeId.setText(name);
	 }
	 public void ListAvailableCars(ActionEvent event) throws Exception{
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/application/ListCars.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
	}
	 
	 public void SellCar(ActionEvent event) throws Exception{
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/application/Customer_Sell_Car.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			//String cLV = sellCarOptions.getSelectionModel().getSelectedItem();
			
			
			
	}
	 
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
			
			String sql = "SELECT * FROM carDetails WHERE carID='"+carId+"'";
			
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
				System.out.println("Helloooo");
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
				
			}
			else {
				new Alert(Alert.AlertType.ERROR, "The Chassis ID "+carId+" "
						+ "is incorrect. Only cars that were previously bought from us are accepted").show();
			}
			conn.close();
		 	} catch (SQLException e) {
		 		// TODO Auto-generated catch block
		 		e.printStackTrace();
		 	}
		 
	 }


	/**
	 * @return the userName
	 */
	public String getUserID() {
		return userID;
	}


	/**
	 * @param userName the userName to set
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}

}
