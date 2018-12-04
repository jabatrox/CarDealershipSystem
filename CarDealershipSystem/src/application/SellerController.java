package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import classes.*;
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
	private TableView<CarDetails> carsTable_bookingDetails;
	@FXML
	private TableView<CarDetails> carsTable_Exposed;
	
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
			
			int carsExposed = 0;
			String sql_exposedCars = "SELECT * FROM carDetails WHERE conID='"+seller.getConID()+"' "
					+ "AND exposed='1'";
			ResultSet rs_exposedCars = stmt.executeQuery(sql_exposedCars);
			if (!rs_exposedCars.next()) {
				System.out.println("No data");
				carsExposed = 0;
			} else {
				while(rs_exposedCars.next()) {
					carsExposed++;
				}
			}
			int freeSlots = conCapacity-carsExposed;
			carsExposedLabel.setText(carsExposedLabel.getText()+carsExposed);
			freeSlotsLabel.setText(freeSlotsLabel.getText()+freeSlots);
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	 }

}
