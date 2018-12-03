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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
	private TextField brand,model,color,horsePower,price,miles,year,conID,factID;
	
	@FXML
	private ChoiceBox<String> engineTypes;
	
	@FXML
	private ChoiceBox<String> sellCarOptions;
	
	@FXML
	private ListView carListView;
	
	
	public CustomerController(){
	
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
	}
	
	void initData(Agent customer) {

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
			
			String cLV = sellCarOptions.getSelectionModel().getSelectedItem();
			
			
			
	}
	 
	 public void CustomerPage (ActionEvent event) throws Exception {
		 Stage primaryStage = new Stage();
		 Parent root = FXMLLoader.load(getClass().getResource("/application/Customer.fxml"));
		 Scene scene = new Scene(root);
		 scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		 primaryStage.setScene(scene);
		 primaryStage.show();
	 }
	 
	 public void RequestSellCar() {
		 
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
			
			stmt.executeUpdate("INSERT INTO carDetails (conID,factID,carBrand,carModel"
					+ ",carColor,engineType,horsePower,price,kilometers,sold,exposed,"
					+ "carCondition,year) VALUES ('"+ci+"','"+fi+"','"+br+"','"+mo+"','"+co+"',"
					+ "'"+et+"','"+hp+"','"+pr+"','"+mi+"','"+0+"','"+0+"','"+0+"','"+ye+"')");
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
