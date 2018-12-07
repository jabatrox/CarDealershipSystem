package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
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
	@FXML
	private TableView<Concessionaire> concessionairesFromFactoriesTable;
	
	private ObservableList<FactoryDeposit> factoryDepositsTable_data = FXCollections.observableArrayList();
	private ObservableList<Concessionaire> concessionairesFromFactoriesTable_data = FXCollections.observableArrayList();
	
	@FXML
	private TableView<Concessionaire> concessionairesSalesHistoryTable;
	@FXML
	private TableView<PendingBookingRow> bookingsHistoryFromConcessionaireTable;
	
	private ObservableList<FactoryDeposit> concessionairesSalesHistoryTable_data = FXCollections.observableArrayList();
	private ObservableList<PendingBookingRow> bookingsHistoryFromConcessionaireTable_data = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		Label factoryDepositsTableLabel = new Label("THERE ARE NO FACTORIES CURRENTLY.\n\nPLEASE ADD A NEW ONE");
		factoryDepositsTableLabel.setAlignment(Pos.CENTER);
		factoryDepositsTable.setPlaceholder(factoryDepositsTableLabel);
		Label concessionairesFromFactoriesTableLabel = new Label("THERE ARE NO CONCESSIONAIRES FOR THIS FACTORY CURRENTLY."
				+ "\n\nPLEASE ADD A NEW ONE");
		concessionairesFromFactoriesTableLabel.setAlignment(Pos.CENTER);
		concessionairesFromFactoriesTable.setPlaceholder(concessionairesFromFactoriesTableLabel);
		Label concessionairesSalesHistoryTableLabel = new Label("THERE ARE NO CONCESSIONAIRES CURRENTLY.\n\nPLEASE ADD A NEW ONE");
		concessionairesSalesHistoryTableLabel.setAlignment(Pos.CENTER);
		concessionairesSalesHistoryTable.setPlaceholder(concessionairesSalesHistoryTableLabel);
		Label bookingsHistoryFromConcessionaireTableLabel = new Label("THERE ARE NO BOOKINGS FOR THIS CONCESSIONAIRE CURRENTLY."
				+ "\n\nPLEASE ADD A NEW ONE");
		bookingsHistoryFromConcessionaireTableLabel.setAlignment(Pos.CENTER);
		bookingsHistoryFromConcessionaireTable.setPlaceholder(bookingsHistoryFromConcessionaireTableLabel);
	}
	
	void initData(Agent admin_logged_in) {
		admin = (Admin) admin_logged_in;
		String admin_name = admin.getFirstName()+" "+admin.getLastName();
		int admin_ID = admin.getAgentID();
		adminWelcomeName.setText(admin_name.toUpperCase());
		adminWelcomeID.setText("ADMIN ID: "+admin_ID);
	}

}
