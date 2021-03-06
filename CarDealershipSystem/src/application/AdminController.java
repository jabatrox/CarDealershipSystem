package application;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import classes.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.DBConnect;

public class AdminController implements Initializable {
	
	Connection conn = null;
	DBConnect openDBconn = new DBConnect();
	Statement stmt = null;
	
	private Admin admin;
	// boolean to fill the FactoryDeposit table the first time the windows shows and add the delete 
	// button to the FactoryDeposit table only one time (not every time an item is clicked).
	private boolean firstTimeRun = true;
	
	@FXML
	private Label adminWelcomeID, adminWelcomeName;
	
	@FXML
	private TextField factoryCapacity, concessionaireCapacity, newSellerFirstName, newSellerLastName, newSellerUsername;
	@FXML
	private PasswordField newSellerPassword;
	@FXML
	private ImageView addFactory, addConcessionaire, addSeller;
	
	@FXML
	private Text newSellerFirstNameLabel, newSellerLastNameLabel, newSellerUsernameLabel, newSellerPasswordLabel;
	
	@FXML
	private ComboBox<Integer> selectNewConcessionaireFactoryID, selectNewSellerConcessionaireID;
	
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
	private ObservableList<Concessionaire> concessionairesSalesHistoryTable_data = FXCollections.observableArrayList();
	
	@FXML
	private TableView<SalesHistoryBookingRow> bookingsHistoryFromConcessionaireTable;
	private ObservableList<SalesHistoryBookingRow> bookingsHistoryFromConcessionaireTable_data = FXCollections.observableArrayList();
	
	
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
		
		// Get all FactoryDeposits and add them to the combo box (choice box) in "Add new concessionaire".
		List<FactoryDeposit> allFactoryDeposits = admin.getAllFactoryDeposits();
		List<Integer> factoryDepositsID = new ArrayList<>();
		for (FactoryDeposit factoryDeposit : allFactoryDeposits) {
			factoryDepositsID.add(factoryDeposit.getFactID());
		}
		ObservableList<Integer> observableListOfFactoryDeposits = FXCollections.observableList(factoryDepositsID);
		selectNewConcessionaireFactoryID.setItems(observableListOfFactoryDeposits);
		selectNewConcessionaireFactoryID.getSelectionModel().selectFirst();
		selectNewConcessionaireFactoryID.setStyle("-fx-font: 12px \"Lucida Sans Unicode\";");
		
		// Get all Concessionaires and add them to the combo box (choice box) in "Add new seller".
		List<Concessionaire> allConcessionaires = admin.getAllConcessionaires();
		List<Integer> concessionairesID = new ArrayList<>();
		for (Concessionaire concessionaire : allConcessionaires) {
			concessionairesID.add(concessionaire.getConID());
		}
		ObservableList<Integer> observableListOfConcessionaires = FXCollections.observableList(concessionairesID);
		selectNewSellerConcessionaireID.setItems(observableListOfConcessionaires);
		selectNewSellerConcessionaireID.getSelectionModel().selectFirst();
		selectNewSellerConcessionaireID.setStyle("-fx-font: 12px \"Lucida Sans Unicode\";");
		
		// Fill the table of FactoryDeposits in the "factories and concessionaires" tab.
		fillFactoryDepositsTable();
		// Fill the table of Concessionaires in the "sales history" tab.
		fillConcessionairesSalesHistoryTable();
	}
	
	private void fillFactoryDepositsTable() {
		// Clear the table to avoid duplicates on update.
		factoryDepositsTable.getItems().clear();
		
		// Get the list of all FactoryDeposits, their capacity and the cars the have produced.
		ArrayList<AllFactoryDepositsInfoRow> allFactoryDeposits = admin.getAllFactoryDepositsRow();
		for (AllFactoryDepositsInfoRow factoryDeposit : allFactoryDeposits) {
			factoryDepositsTable_data.add(factoryDeposit);
		}
		factoryDepositsTable.setItems(factoryDepositsTable_data);
		
		// When a FactoryDeposit is selected, show the concessionaires it has on the table for it.
		if (!factoryDepositsTable.getItems().isEmpty() && firstTimeRun) {
			factoryDepositsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			    if (newSelection != null) {
			    	fillConcessionairesFromFactoriesTable(newSelection.getFactID());
			    }
			});
			// Add button to delete FactoryDeposit in an extra column.
			addButtonToFactoryDepositTable();
		}
		
		firstTimeRun = false;
	}
	
	private void fillConcessionairesFromFactoriesTable(int factID) {
		// Clear the table to avoid duplicates on update.
		concessionairesFromFactoriesTable.getItems().clear();
		
		// Get the list of all Concessionaires, their capacity and the number of sellers they have, for the selected FactoryDeposit.
		ArrayList<ConcessionairesFromFactoriesRow> allConcessionairesFromFactory = admin.getAllConcessionairesFromFactory(factID);
		for (ConcessionairesFromFactoriesRow concessionaireFromFactory : allConcessionairesFromFactory) {
			concessionairesFromFactoriesTable_data.add(concessionaireFromFactory);
		}
		concessionairesFromFactoriesTable.setItems(concessionairesFromFactoriesTable_data);
		
		firstTimeRun = false;
	}
	
	private void fillConcessionairesSalesHistoryTable() {
		// Clear the table to avoid duplicates on update.
		concessionairesSalesHistoryTable.getItems().clear();
		
		// Get the list of all Concessionaires (from all FactoryDeposits), their capacity and the number of sellers they have.
		ArrayList<Concessionaire> allConcessionaires = admin.getAllConcessionaires();
		for (Concessionaire concessionaire : allConcessionaires) {
			concessionairesSalesHistoryTable_data.add(concessionaire);
		}
		concessionairesSalesHistoryTable.setItems(concessionairesSalesHistoryTable_data);
		
		// When a Concessionaire is selected, show the bookingDetails it has on the table for it.
		if (!concessionairesSalesHistoryTable.getItems().isEmpty()) {
			concessionairesSalesHistoryTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			    if (newSelection != null) {
			    	fillBookingsHistoryFromConcessionairesSalesHistoryTable(newSelection.getConID());
			    }
			});
		}
		
		firstTimeRun = false;
	}
	
	private void fillBookingsHistoryFromConcessionairesSalesHistoryTable(int conID) {
		// Clear the table to avoid duplicates on update.
		bookingsHistoryFromConcessionaireTable.getItems().clear();
		
		// Get the list of all BookingDetails for the selected concessionaire.
		ArrayList<SalesHistoryBookingRow> allBookingsFromConcessionaires = admin.getSalesHistory(conID);
		for (SalesHistoryBookingRow concessionaireFromFactory : allBookingsFromConcessionaires) {
			bookingsHistoryFromConcessionaireTable_data.add(concessionaireFromFactory);
		}
		bookingsHistoryFromConcessionaireTable.setItems(bookingsHistoryFromConcessionaireTable_data);
		System.out.println("Concessionaire with conID="+conID+" has "+allBookingsFromConcessionaires.size()+" booking(s) in "
				+ "its sales' history.");
		
		firstTimeRun = false;
	}
	
	private void addButtonToFactoryDepositTable() {
//		final Image imageButton = new Image(getClass().getResourceAsStream("../../resources/Deny.png"),15,15,false,false);
		final Image imageButton = new Image(ResourceLoader.load("Deny.png"),15,15,false,false);


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
	public void addNewConcessionaire() {
		int newConcessionaireFactoryID = selectNewConcessionaireFactoryID.getSelectionModel().getSelectedItem();
		try {
			if (concessionaireCapacity.getText().isEmpty()) {
				concessionaireCapacity.setStyle("-fx-border-color: red;");
				new Alert(Alert.AlertType.ERROR, "Missing car capacity! Please select a car capacity.").show();
				throw new IllegalArgumentException(" ---> Missing car capacity! Please select a car capacity.");
			}
			if (concessionaireCapacity.getText().matches("\\d{1,}")) {
				int newFactoryCapacity = Integer.parseInt(concessionaireCapacity.getText());
				if (newFactoryCapacity <= 0) {
					concessionaireCapacity.setStyle("-fx-border-color: red;");
					new Alert(Alert.AlertType.ERROR, "Car capacity must be an integer greater that 0").show();
					throw new IllegalArgumentException(" ---> Car capacity must be an integer greater that 0.");
				}
			} else {
				concessionaireCapacity.setStyle("-fx-border-color: red;");
				new Alert(Alert.AlertType.ERROR, "Car capacity must be an integer greater that 0").show();
				throw new IllegalArgumentException(" ---> Car capacity must be an integer greater that 0.");
			}
			concessionaireCapacity.setStyle(null);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return;
		}
		admin.createConcessionaire(Integer.parseInt(concessionaireCapacity.getText()), newConcessionaireFactoryID);
		initData(admin);
	}
	
	@FXML
	public void addNewSeller() {
		int newSellerConID = selectNewSellerConcessionaireID.getSelectionModel().getSelectedItem();
		try {
			MainController.checkNamesAddress(newSellerFirstName, newSellerFirstNameLabel);
			MainController.checkNamesAddress(newSellerLastName, newSellerLastNameLabel);
			MainController.CheckUserName(newSellerUsername, newSellerUsernameLabel);
			MainController.checkPassword(newSellerPassword, newSellerPasswordLabel);
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			System.out.println(e1.getMessage());
			return;
		}
		try {
			admin.addSeller(newSellerConID,
					newSellerUsername.getText(),
					newSellerLastName.getText(),
					newSellerUsername.getText(),
					MainController.get_SecurePassword(newSellerPassword.getText()));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initData(admin);
	}
	
	@FXML
	public void logoutCloseWindowAction(ActionEvent event) {
		Stage stage = (Stage) closeButton.getScene().getWindow();
	    stage.close();
	    
	    try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Login.fxml"));
			Stage stage_login = new Stage();
			Region root = (Region) loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage_login.setScene(scene);
			stage_login.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
