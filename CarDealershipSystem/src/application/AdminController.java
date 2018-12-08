package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

import classes.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;
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
	private Button closeButton;
	
	@FXML
	private TableView<AllFactoryDepositsInfoRow> factoryDepositsTable;
	private ObservableList<AllFactoryDepositsInfoRow> factoryDepositsTable_data = FXCollections.observableArrayList();
	
	@FXML
	private TableView<ConcessionairesFromFactoriesRow> concessionairesFromFactoriesTable;
	private ObservableList<ConcessionairesFromFactoriesRow> concessionairesFromFactoriesTable_data = FXCollections.observableArrayList();
	
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
		
		fillFactoryDepositsTable();
//		fillConcessionairesFromFactoriesTable();
	}
	
	private void fillFactoryDepositsTable() {
		factoryDepositsTable.getItems().clear();
		ArrayList<AllFactoryDepositsInfoRow> allFactoryDeposits = admin.getAllFactoryDeposits();
		for (AllFactoryDepositsInfoRow factoryDeposit : allFactoryDeposits) {
			factoryDepositsTable_data.add(factoryDeposit);
		}
		factoryDepositsTable.setItems(factoryDepositsTable_data);
		
		if (!factoryDepositsTable.getItems().isEmpty() && firstTimeRun) {
			factoryDepositsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			    if (newSelection != null) {
			    	fillConcessionairesFromFactoriesTable(newSelection.getFactID());
			    }
			});
//			fillConcessionairesFromFactoriesTable();
//			addBuyButtonToTable();
		}
		
		firstTimeRun = false;
	}
	
	private void fillConcessionairesFromFactoriesTable(int factID) {
		concessionairesFromFactoriesTable.getItems().clear();
		ArrayList<ConcessionairesFromFactoriesRow> allConcessionairesFromFactory = admin.getAllConcessionairesFromFactory(factID);
		for (ConcessionairesFromFactoriesRow concessionaireFromFactory : allConcessionairesFromFactory) {
			concessionairesFromFactoriesTable_data.add(concessionaireFromFactory);
		}
		concessionairesFromFactoriesTable.setItems(concessionairesFromFactoriesTable_data);
		
//		if (!carsExposedCustomerTable.getItems().isEmpty() && firstTimeRun) {
//			formatCarConditionInTable();
//			addBuyButtonToTable();
//		}
		
		
		/*Callback<TableColumn<CarDetails, Void>, TableCell<CarDetails, Void>> cellFactory = 
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
		};*/
		
	}
	
	@FXML
	public void logoutCloseWindowAction(ActionEvent event) {
	    Stage stage = (Stage) closeButton.getScene().getWindow();
	    stage.close();
	}

}
