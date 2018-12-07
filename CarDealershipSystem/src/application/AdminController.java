package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

import classes.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import models.DBConnect;

public class AdminController implements Initializable {
	
	Connection conn = null;
	DBConnect openDBconn = new DBConnect();
	Statement stmt = null;
	
	private Admin admin;
	private boolean firstTimeRun = true;
	
	@FXML
	private Label adminWelcomeID, adminWelcomeName;
	
	@FXML
	private TableView<FactoryDeposit> factoryDepositsTable;
	private ObservableList<FactoryDeposit> factoryDepositsTable_data = FXCollections.observableArrayList();
	
	@FXML
	private TableView<Concessionaire> concessionairesFromFactoriesTable;
	private ObservableList<Concessionaire> concessionairesFromFactoriesTable_data = FXCollections.observableArrayList();
	
	@FXML
	private TableView<Concessionaire> concessionairesSalesHistoryTable;
	private ObservableList<FactoryDeposit> concessionairesSalesHistoryTable_data = FXCollections.observableArrayList();
	
	@FXML
	private TableView<PendingBookingRow> bookingsHistoryFromConcessionaireTable;
	private ObservableList<PendingBookingRow> bookingsHistoryFromConcessionaireTable_data = FXCollections.observableArrayList();
	
	
	public AdminController() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		factoryDepositsTable.setPlaceholder(new Label("THERE ARE NO FACTORIES CURRENTLY"));
		concessionairesFromFactoriesTable.setPlaceholder(new Label("THERE ARE NO CONCESSIONAIRES FOR THIS FACTORY CURRENTLY"));
		concessionairesSalesHistoryTable.setPlaceholder(new Label("THERE ARE NO CONCESSIONAIRES CURRENTLY"));
		bookingsHistoryFromConcessionaireTable.setPlaceholder(new Label("THERE ARE NO BOOKINGS FOR THIS CONCESSIONAIRE CURRENTLY"));
	}
	
	void initData(Agent admin_logged_in) {
		admin = (Admin) admin_logged_in;
		String admin_name = admin.getFirstName()+" "+admin.getLastName();
		int admin_ID = admin.getAgentID();
		adminWelcomeName.setText(admin_name.toUpperCase());
		adminWelcomeID.setText("ADMIN ID: "+admin_ID);
		
		factoryDepositsTable.getItems().clear();
//		ArrayList<CarDetails> conCars = customer.checkCars(option);
//		for (CarDetails conCar : conCars) {
//			carsExposedTable_data.add(conCar);
//		}
		factoryDepositsTable.setItems(factoryDepositsTable_data);
		
//		if (!carsExposedCustomerTable.getItems().isEmpty() && firstTimeRun) {
//			formatCarConditionInTable();
//			addBuyButtonToTable();
//		}
		
		
	}

}
