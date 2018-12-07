package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import com.sun.javafx.tk.Toolkit;

import classes.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import models.DBConnect;

public class SellerController implements Initializable {

	Connection conn = null;
	DBConnect openDBconn = new DBConnect();
	Statement stmt = null;
	
	private Seller seller;
	private boolean firstTimeRun = true;
	private int freeSlots = 0;
	
	@FXML
	private Label sellerWelcomeID, sellerWelcomeName, concessionaireIDLabel, carsExposedLabel, freeSlotsLabel;
	
	@FXML
	private AnchorPane sellingAnchorPane;
//	@FXML
//	private SplitPane sellingSplitPane;
	
	@FXML
	private Button updateButton;
	
	@FXML
	private TableView<PendingBookingRow> pendingBookingsTable;
	@FXML
	private TableView<CarDetails> carsExposedTable;
	
	private ObservableList<PendingBookingRow> pendingBookingsTable_data = FXCollections.observableArrayList();
	private ObservableList<CarDetails> carsExposedTable_data = FXCollections.observableArrayList();
	
	public SellerController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		pendingBookingsTable.setPlaceholder(new Label("NO PENDING BOOKINGS. PLEASE UPDATE TO CHECK AGAIN"));
		carsExposedTable.setPlaceholder(new Label("THERE ARE NO CARS IN THIS CONCESSIONAIRE"));
	}
	
	void initData(Agent seller_logged_in) {
		seller = (Seller) seller_logged_in;
		String seller_name = seller.getFirstName()+" "+seller.getLastName();
		int seller_ID = seller.getAgentID();
		sellerWelcomeName.setText(seller_name.toUpperCase());
		sellerWelcomeID.setText("SELLER ID: "+seller_ID);
		concessionaireIDLabel.setText("Concessionaire: "+seller.getConID());
		concessionaireIDLabel.setLayoutX(sellingAnchorPane.getMaxWidth()/2 - 
				Math.ceil(Toolkit.getToolkit().getFontLoader().computeStringWidth(concessionaireIDLabel.getText(),
						concessionaireIDLabel.getFont()))/2);
		
		int conCapacity = 0;
		try {
			conn = openDBconn.connect();
			stmt = conn.createStatement();
			
			String sql_conCapacity = "SELECT carCapacity FROM concessionaire WHERE conID='"+seller.getConID()+"'";
			ResultSet rs_conCapacity = stmt.executeQuery(sql_conCapacity);
			if (!rs_conCapacity.next()) {
				System.out.println("No Data");
				return;
			}
			conCapacity = rs_conCapacity.getInt("carCapacity");
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		carsExposedTable.getItems().clear();
		ArrayList<CarDetails> conCars = seller.checkCarsConcessionaire(seller.getConID());
		int carsExposed = 0;
		for (CarDetails conCar : conCars) {
			if (conCar.isExposed()) {
				System.out.println("Car exposed "+conCar.getCarID()+" found!");
				carsExposed++;
			}
			carsExposedTable_data.add(conCar);
		}
		/*int */freeSlots = conCapacity-carsExposed;
		carsExposedLabel.setText("Cars Exposed: "+carsExposed);
		freeSlotsLabel.setText("Free Slots: "+freeSlots);
		carsExposedTable.setItems(carsExposedTable_data);
		
		if (!carsExposedTable.getItems().isEmpty() && firstTimeRun) {
			formatCarConditionInTable();
			addToggleButtonToTable();
		}
		
		pendingBookingsTable.getItems().clear();
		ArrayList<PendingBookingRow> conPendingBookings = seller.checkPendingOperations(seller.getConID());
		for (PendingBookingRow conPendingBooking : conPendingBookings) {
			pendingBookingsTable_data.add(conPendingBooking);
        }
		pendingBookingsTable.setItems(pendingBookingsTable_data);
		if (!pendingBookingsTable.getItems().isEmpty() && firstTimeRun) {
			addButtonToTable(true);
			addButtonToTable(false);
		}
		
		firstTimeRun = false;
	 }
	
	private void addButtonToTable(boolean buttonType) {
//		final ImageView imageButton;
		final Image imageButton;
		if (buttonType) {
//			imageButton = new ImageView(new Image(getClass().getResourceAsStream("../../resources/Accept.png"),15,15,false,false));
			imageButton = new Image(getClass().getResourceAsStream("../../resources/Accept.png"),15,15,false,false);
		} else {
//			imageButton = new ImageView(new Image(getClass().getResourceAsStream("../../resources/Deny.png"),15,15,false,false));
			imageButton = new Image(getClass().getResourceAsStream("../../resources/Deny.png"),15,15,false,false);
		}
		
		TableColumn<PendingBookingRow, Void> acceptDenyColBtn = new TableColumn<PendingBookingRow, Void>("");

		Callback<TableColumn<PendingBookingRow, Void>, TableCell<PendingBookingRow, Void>> cellFactory = 
				new Callback<TableColumn<PendingBookingRow, Void>, TableCell<PendingBookingRow, Void>>() {
			@Override
			public TableCell<PendingBookingRow, Void> call(final TableColumn<PendingBookingRow, Void> param) {
				final TableCell<PendingBookingRow, Void> cell = new TableCell<PendingBookingRow, Void>() {
					
//					private final Button actionButton = new Button("", imageButton);
					private ImageView imageView = new ImageView();
					
					/*{
						actionButton.setOnAction((ActionEvent event) -> {
							if (buttonType) {
								int pendingBookingID = getTableView().getItems().get(getIndex()).getBookingID();
								String pendingBookingType = getTableView().getItems().get(getIndex()).getBookingType();
								System.out.println("Accept pending booking with bookingID=" + pendingBookingID);
								acceptSaleCar(pendingBookingID, pendingBookingType);
							} else {
								int pendingBookingID = getTableView().getItems().get(getIndex()).getBookingID();
								System.out.println("Reject pending booking with bookingID=" + pendingBookingID);
								rejectSaleCar(pendingBookingID);
							}
						});
					}*/

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
//							setGraphic(actionButton);
							imageView.setImage(imageButton);
							setGraphic(imageView);
						}
						setOnMousePressed(new EventHandler<MouseEvent>() {
				            @Override
				            public void handle(MouseEvent event) {
				            	String alertConfirmDeny = "";
				            	if (buttonType) {
				            		alertConfirmDeny = "confirm";
								} else {
									alertConfirmDeny = "reject";
								}
				            	Alert alert = new Alert(AlertType.CONFIRMATION);
				            	alert.setTitle("Confirmation Dialog");
				            	alert.setHeaderText("Confirming operation");
				            	alert.setContentText("Are you sure you "+alertConfirmDeny+" this operation?");
				            	Optional<ButtonType> result = alert.showAndWait();
				            	if (result.get() == ButtonType.OK){
				            		if (buttonType) {
					            		int pendingBookingID = getTableView().getItems().get(getIndex()).getBookingID();
										String pendingBookingType = getTableView().getItems().get(getIndex()).getBookingType();
										System.out.println("Accept pending booking with bookingID=" + pendingBookingID);
										acceptSaleCar(pendingBookingID, pendingBookingType);
									} else {
										int pendingBookingID = getTableView().getItems().get(getIndex()).getBookingID();
										System.out.println("Reject pending booking with bookingID=" + pendingBookingID);
										rejectSaleCar(pendingBookingID);
									}
				            	} else {
				            		new Alert(Alert.AlertType.INFORMATION, "Operation cancelled");
				            	}

				            }            
				        });
//						actionButton.setOnAction((ActionEvent event) -> {
//							if (buttonType) {
//								int pendingBookingID = getTableView().getItems().get(getIndex()).getBookingID();
//								String pendingBookingType = getTableView().getItems().get(getIndex()).getBookingType();
//								System.out.println("Accept pending booking with bookingID=" + pendingBookingID);
//								acceptSaleCar(pendingBookingID, pendingBookingType);
//							} else {
//								int pendingBookingID = getTableView().getItems().get(getIndex()).getBookingID();
//								System.out.println("Reject pending booking with bookingID=" + pendingBookingID);
//								rejectSaleCar(pendingBookingID);
//							}
//						});
					}
				};
				return cell;
			}
		};
		acceptDenyColBtn.setCellFactory(cellFactory);
		pendingBookingsTable.getColumns().add(acceptDenyColBtn);
		acceptDenyColBtn.setStyle("-fx-alignment: CENTER");
	}
	
	private void formatCarConditionInTable() {
		TableColumn<CarDetails, Void> showCarCondition = new TableColumn<CarDetails, Void>("CAR CONDITION");
		
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
		carsExposedTable.getColumns().add(showCarCondition);
		showCarCondition.setStyle("-fx-alignment: CENTER");
	}
	
	private void addToggleButtonToTable() {
		TableColumn<CarDetails, Void> exposeColToggleBtn = new TableColumn<CarDetails, Void>("EXPOSE");
//		exposeColToggleBtn.setPrefWidth(140);

		Callback<TableColumn<CarDetails, Void>, TableCell<CarDetails, Void>> cellFactory = 
				new Callback<TableColumn<CarDetails, Void>, TableCell<CarDetails, Void>>() {
			@Override
			public TableCell<CarDetails, Void> call(final TableColumn<CarDetails, Void> param) {
				final TableCell<CarDetails, Void> cell = new TableCell<CarDetails, Void>() {
					
					private final CheckBox exposeToggleButton = new CheckBox();
					{
						exposeToggleButton.setOnAction((ActionEvent event) -> {
//							if (freeSlots != 0) {
								if(exposeToggleButton.isSelected()) {
									if (freeSlots != 0) {
										int exposedCarID = getTableView().getItems().get(getIndex()).getCarID();
										System.out.println("Car "+exposedCarID+" is now exposed!");
										exposeNewCar(exposedCarID);
									} else {
										exposeToggleButton.setSelected(false);
										new Alert(Alert.AlertType.ERROR, "The concessionaire is full, no more cars can be exposed.").show();
									}
	                            } else {
	                            	int exposedCarID = getTableView().getItems().get(getIndex()).getCarID();
									System.out.println("Car "+exposedCarID+" is no longer exposed!");
									deleteExposedCar(exposedCarID);
	                            }
						});
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(exposeToggleButton);
							if (getTableView().getItems().get(getIndex()).isExposed()) {
								exposeToggleButton.setSelected(true);
							} else {
								exposeToggleButton.setSelected(false);
							}
						}
					}
				};
				return cell;
			}
		};
		exposeColToggleBtn.setCellFactory(cellFactory);
		carsExposedTable.getColumns().add(exposeColToggleBtn);
		exposeColToggleBtn.setStyle("-fx-alignment: CENTER");

	}
	
	public void acceptSaleCar(int pendingBookingID, String pendingBookingType) {
		if (pendingBookingType.equals("BUYING")) {
			seller.buyCar(pendingBookingID);
		} else {
//			seller.sellCar(carID, statusCar);
			seller.sellCar(pendingBookingID); //////// IN PROGRESS
		}
		initData(seller);
	}
	
	public void rejectSaleCar(int pendingBookingID) {
		seller.rejectOperation(pendingBookingID);
		initData(seller);
	}
	
	private void exposeNewCar(int exposedCarID) {
		seller.addCarExposed(exposedCarID);
		initData(seller);
	}
	
	private void deleteExposedCar(int exposedCarID) {
		seller.deleteCarExposed(exposedCarID);
		initData(seller);
	}
	
	public void updateTableViews(ActionEvent event) {
		initData(seller);
	}

}
