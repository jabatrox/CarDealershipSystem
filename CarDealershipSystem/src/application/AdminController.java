package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import classes.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
	private TextField factoryCapacity, concessionaireCapacity, concessionaireFactoryID;
	@FXML
	private ImageView addFactory, addConcessionaire;
	
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
		concessionairesFromFactoriesTable.setPlaceholder(new Label("NO FACTORY SELECTED OR THERE ARE NO\nCONCESSIONAIRES FOR THIS FACTORY CURRENTLY"));
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
//		fillConcessionairesFromFactoriesTable(1);
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
			addButtonToFactoryDepositTable();
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
		
		firstTimeRun = false;
	}
	
	private void addButtonToFactoryDepositTable() {
		final Image imageButton = new Image(getClass().getResourceAsStream("../../resources/Deny.png"),15,15,false,false);

		TableColumn<AllFactoryDepositsInfoRow, Void> deleteFactoryDepositColBtn = new TableColumn<AllFactoryDepositsInfoRow, Void>("");

		Callback<TableColumn<AllFactoryDepositsInfoRow, Void>, TableCell<AllFactoryDepositsInfoRow, Void>> cellFactory = 
				new Callback<TableColumn<AllFactoryDepositsInfoRow, Void>, TableCell<AllFactoryDepositsInfoRow, Void>>() {
			@Override
			public TableCell<AllFactoryDepositsInfoRow, Void> call(final TableColumn<AllFactoryDepositsInfoRow, Void> param) {
				final TableCell<AllFactoryDepositsInfoRow, Void> cell = new TableCell<AllFactoryDepositsInfoRow, Void>() {
					
					private ImageView imageView = new ImageView();

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							imageView.setImage(imageButton);
							setGraphic(imageView);
						}
						setOnMousePressed(new EventHandler<MouseEvent>() {
				            @Override
				            public void handle(MouseEvent event) {
				            	Alert alert = new Alert(AlertType.CONFIRMATION);
				            	alert.setTitle("Confirmation Dialog");
				            	alert.setHeaderText("Confirming operation");
				            	alert.setContentText("Are you sure you want to delete this factory?");
				            	Optional<ButtonType> result = alert.showAndWait();
				            	if (result.get() == ButtonType.OK){
				            		int factoryDepositID = getTableView().getItems().get(getIndex()).getFactID();
									System.out.println("Deleting factory with factoryID=" +factoryDepositID);
									admin.deleteFactory(factoryDepositID);
									initData(admin);
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
		deleteFactoryDepositColBtn.setCellFactory(cellFactory);
		factoryDepositsTable.getColumns().add(deleteFactoryDepositColBtn);
		deleteFactoryDepositColBtn.setStyle("-fx-alignment: CENTER");
	}
	
	@FXML
	public void addNewFactory() throws IllegalArgumentException {
		try {
			if (factoryCapacity.getText().isEmpty()) {
		    	factoryCapacity.setStyle("-fx-border-color: red;");
				new Alert(Alert.AlertType.ERROR, "Missing car capacity! Please select a car capacity.").show();
				throw new IllegalArgumentException(" ---> Missing car capacity! Please select a car capacity.");
			}
			if (factoryCapacity.getText().matches("\\d{1,}")) {
				int newFactoryCapacity = Integer.parseInt(factoryCapacity.getText());
				if (newFactoryCapacity <= 0) {
					factoryCapacity.setStyle("-fx-border-color: red;");
					new Alert(Alert.AlertType.ERROR, "Car capacity must be an integer greater that 0").show();
					throw new IllegalArgumentException(" ---> Car capacity must be an integer greater that 0.");
				}
			} else {
				factoryCapacity.setStyle("-fx-border-color: red;");
				new Alert(Alert.AlertType.ERROR, "Car capacity must be an integer greater that 0").show();
				throw new IllegalArgumentException(" ---> Car capacity must be an integer greater that 0.");
			}
			factoryCapacity.setStyle(null);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return;
		}
		admin.createFactory(Integer.parseInt(factoryCapacity.getText()));
		initData(admin);
	}
	
	@FXML
	public void addNewConcessionaire(ActionEvent event) {
		
		try {
			if (concessionaireFactoryID.getText().isEmpty()) {
		    	factoryCapacity.setStyle("-fx-border-color: red;");
//				new Alert(Alert.AlertType.ERROR, "Missing car capacity! Please select a car capacity.").show();
//				throw new IllegalArgumentException(" ---> Missing car capacity! Please select a car capacity.");
			}
			if (factoryCapacity.getText().matches("\\d{1,}")) {
				int newFactoryCapacity = Integer.parseInt(factoryCapacity.getText());
				if (newFactoryCapacity <= 0) {
					factoryCapacity.setStyle("-fx-border-color: red;");
					new Alert(Alert.AlertType.ERROR, "Car capacity must be an integer greater that 0").show();
					throw new IllegalArgumentException(" ---> Car capacity must be an integer greater that 0.");
				}
			} else {
				factoryCapacity.setStyle("-fx-border-color: red;");
				new Alert(Alert.AlertType.ERROR, "Car capacity must be an integer greater that 0").show();
				throw new IllegalArgumentException(" ---> Car capacity must be an integer greater that 0.");
			}
			factoryCapacity.setStyle(null);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return;
		}
		
		
		try {
			if (factoryCapacity.getText().isEmpty()) {
		    	factoryCapacity.setStyle("-fx-border-color: red;");
				new Alert(Alert.AlertType.ERROR, "Missing car capacity! Please select a car capacity.").show();
				throw new IllegalArgumentException(" ---> Missing car capacity! Please select a car capacity.");
			}
			if (factoryCapacity.getText().matches("\\d{1,}")) {
				int newFactoryCapacity = Integer.parseInt(factoryCapacity.getText());
				if (newFactoryCapacity <= 0) {
					factoryCapacity.setStyle("-fx-border-color: red;");
					new Alert(Alert.AlertType.ERROR, "Car capacity must be an integer greater that 0").show();
					throw new IllegalArgumentException(" ---> Car capacity must be an integer greater that 0.");
				}
			} else {
				factoryCapacity.setStyle("-fx-border-color: red;");
				new Alert(Alert.AlertType.ERROR, "Car capacity must be an integer greater that 0").show();
				throw new IllegalArgumentException(" ---> Car capacity must be an integer greater that 0.");
			}
			factoryCapacity.setStyle(null);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return;
		}
		admin.createFactory(Integer.parseInt(factoryCapacity.getText()));
/*		try {
			conn = openDBconn.connect();
			stmt = conn.createStatement();

			String chassisId = chassis.getText();

			System.out.println(chassisId);
			
			int carId = Integer.parseInt(chassisId);

			if (chassisId.isEmpty()) {
				new Alert(Alert.AlertType.WARNING, "Please input your Chassis Id").show();
				return;
			}
			else {
				String sql = "SELECT * FROM carDetails WHERE carID='"+carId+"' AND sold='1'";
	
				rs_carFound = stmt.executeQuery(sql);
	
				if (rs_carFound.next()) {
					carFound = true;
					
					brand.setText(rs_carFound.getString("carBrand"));
					model.setText(rs_carFound.getString("carModel"));
					color.setText(rs_carFound.getString("carColor"));
					engineTypes.setText(rs_carFound.getString("engineType"));
					horsePower.setText(rs_carFound.getString("horsePower"));
					year.setText(rs_carFound.getString("year"));
					conID.setText(rs_carFound.getString("conID"));
					factID.setText(rs_carFound.getString("factID"));
				} else {
					new Alert(Alert.AlertType.ERROR, "The Chassis ID "+carId+" "
							+ "is incorrect. Only cars that were previously in our dealership are eligible").show();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	@FXML
	public void logoutCloseWindowAction(ActionEvent event) {
	    Stage stage = (Stage) closeButton.getScene().getWindow();
	    stage.close();
	}

}
