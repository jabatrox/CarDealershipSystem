package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import classes.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import models.DBConnect;

public class SellerController implements Initializable {

	Connection conn = null;
	DBConnect openDBconn = new DBConnect();
	Statement stmt = null;
	
	@FXML
	private Label sellerWelcomeID;
	@FXML
	private Label sellerWelcomeName;
	
	@FXML
	private Label carsExposedLabel;
	@FXML
	private Label freeSlotsLabel;
	
	@FXML
	private TableView<BookingDetails> carsTable_bookingDetails;
	@FXML
	private TableView<CarDetails> carsTable_Exposed;
	
	private ObservableList<BookingDetails> data;
	
	public SellerController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
//		carsTable_bookingDetails;
	}
	
	void initData(Agent seller1) {
		final Seller seller = (Seller) seller1;
		String seller_name = seller.getFirstName()+" "+seller.getLastName();
		int seller_ID = seller.getAgentID();
		sellerWelcomeName.setText(seller_name.toUpperCase());
		sellerWelcomeID.setText("SELLER ID: "+seller_ID);
		
		try {
			conn = openDBconn.connect();
			stmt = conn.createStatement();
			
			String sql_conCapacity = "SELECT carCapacity FROM concessionaire WHERE conID='"+seller.getConID()+"'";
			ResultSet rs_conCapacity = stmt.executeQuery(sql_conCapacity);
			if (!rs_conCapacity.next()) {
				System.out.println("No Data");
				return;
			}
			int conCapacity = rs_conCapacity.getInt("carCapacity");
			System.out.println(conCapacity);
			
			int carsExposed = 0;
			String sql_exposedCars = "SELECT * FROM carDetails WHERE conID='"+seller.getConID()+"' "
					+ "AND exposed='1'";
			ResultSet rs_exposedCars = stmt.executeQuery(sql_exposedCars);
			while(rs_exposedCars.next()) {
				System.out.println("There are cars");
				carsExposed++;
			}
			int freeSlots = conCapacity-carsExposed;
			carsExposedLabel.setText(carsExposedLabel.getText()+carsExposed);
			freeSlotsLabel.setText(freeSlotsLabel.getText()+freeSlots);
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			conn = openDBconn.connect();
			stmt = conn.createStatement();
			
			String sql_pending_bookings = "SELECT * FROM bookingDetails WHERE sellerID='"+seller.getConID()+"' "
					+ "AND bookingCompleted='0' ";
			ResultSet rs_pending_bookings = stmt.executeQuery(sql_pending_bookings);
			
			//if (rs_pending_bookings.next()) {
				System.out.println("Helloooo");
				while (rs_pending_bookings.next()) {
					data = FXCollections.observableArrayList(
			            new BookingDetails(rs_pending_bookings.getInt("bookingID"),
			            		rs_pending_bookings.getInt("bookingType"),
			            		rs_pending_bookings.getInt("custID"),
			            		rs_pending_bookings.getInt("sellerID"),
			            		rs_pending_bookings.getInt("carID"),
			            		rs_pending_bookings.getBoolean("bookingCompleted"),
			            		rs_pending_bookings.getInt("paymentType"),
			            		rs_pending_bookings.getInt("amount")));
				}
			//}
			
			carsTable_bookingDetails.setItems(data);
			conn.close();
			
			
		}catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }

}
