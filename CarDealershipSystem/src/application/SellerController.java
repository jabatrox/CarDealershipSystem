package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

import classes.*;
import classes.CarDetails.EngineType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import models.DBConnect;

public class SellerController implements Initializable {

	Connection conn = null;
	DBConnect openDBconn = new DBConnect();
	Statement stmt = null;
	
	private Seller seller;
	
	@FXML
	private Label sellerWelcomeID;
	@FXML
	private Label sellerWelcomeName;
	
	@FXML
	private Label carsExposedLabel;
	@FXML
	private Label freeSlotsLabel;
	
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
	}
	
	void initData(Agent seller1) {
		seller = (Seller) seller1;
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
			while(rs_exposedCars.next()) {
				System.out.println("Car exposed found!");
				carsExposed++;
			}
			int freeSlots = conCapacity-carsExposed;
			carsExposedLabel.setText("Cars Exposed: "+carsExposed);
			freeSlotsLabel.setText("Free Slots: "+freeSlots);
			
			carsExposedTable.getItems().clear();
			String sql_conCars = "SELECT * FROM carDetails WHERE conID='"+seller.getConID()+"'";
			ResultSet rs_conCars = stmt.executeQuery(sql_conCars);
			ArrayList<CarDetails> conCars = new ArrayList<>();
			while(rs_conCars.next()) {
				System.out.println("Available car found!");
				conCars.add(new CarDetails(rs_conCars.getInt("carID"),
		            		rs_conCars.getInt("conID"),
		            		rs_conCars.getInt("factID"),
		            		rs_conCars.getString("carBrand"),
		            		rs_conCars.getString("carModel"),
		            		rs_conCars.getString("carColor"),
		            		EngineType.valueOf(rs_conCars.getString("engineType")),
		            		rs_conCars.getInt("horsePower"),
		            		rs_conCars.getDouble("price"),
		            		rs_conCars.getInt("kilometers"),
		            		rs_conCars.getBoolean("sold"),
		            		rs_conCars.getBoolean("exposed"),
		            		rs_conCars.getBoolean("carCondition"),
		            		rs_conCars.getInt("year")));
			}
			for (CarDetails conCar : conCars) {
				carsExposedTable_data.add(conCar);
	        }
			carsExposedTable.setItems(carsExposedTable_data);
			
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			conn = openDBconn.connect();
			stmt = conn.createStatement();
			
			pendingBookingsTable.getItems().clear();
			String sql_pendingBookings = "SELECT bookingDetails.bookingID, bookingDetails.bookingType, customer.firstName, "
					+ "customer.lastName, carDetails.carBrand, carDetails.carModel, carDetails.carColor, carDetails.year, "
					+ "carDetails.kilometers, carDetails.engineType, carDetails.horsePower, bookingDetails.paymentType, "
					+ "bookingDetails.amount "
					+ "FROM bookingDetails, customer, carDetails, seller "
					+ "WHERE bookingDetails.sellerID=seller.conID "
					+ "AND bookingDetails.bookingCompleted='0' "
					+ "AND bookingDetails.custID=customer.custID "
					+ "AND bookingDetails.carID=carDetails.carID";
			ResultSet rs_pendingBookings = stmt.executeQuery(sql_pendingBookings);
			ArrayList<PendingBookingRow> conPendingBookings = new ArrayList<>();
			while (rs_pendingBookings.next()) {
				System.out.println("Pending booking found!");
				String bookingType = "";
				if (rs_pendingBookings.getInt("bookingType") == 0) {
					bookingType = "BUYING";
				} else {
					bookingType = "SELLING";
				}
				String paymentType = "";
				if (rs_pendingBookings.getInt("paymentType") == 0) {
					paymentType = "UNIQUE";
				} else {
					paymentType = "INSTALLMENTS (24 MONTHS)";
				}
				conPendingBookings.add(new PendingBookingRow(rs_pendingBookings.getInt("bookingID"),
						bookingType,
						rs_pendingBookings.getString("firstName")+" "+rs_pendingBookings.getString("lastName"),
						rs_pendingBookings.getString("carBrand")+" / "+rs_pendingBookings.getString("carModel"),
						rs_pendingBookings.getString("carColor"),
						rs_pendingBookings.getInt("year"),
						rs_pendingBookings.getInt("kilometers"),
						rs_pendingBookings.getString("engineType"),
						rs_pendingBookings.getString("horsePower"),
						paymentType,
						rs_pendingBookings.getInt("amount")));
			}
			for (PendingBookingRow conPendingBooking : conPendingBookings) {
				pendingBookingsTable_data.add(conPendingBooking);
	        }
			pendingBookingsTable.setItems(pendingBookingsTable_data);
			if (!pendingBookingsTable.getItems().isEmpty()) {
				addButtonToTable(true);
				addButtonToTable(false);
			}
			conn.close();
		}catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	 }
	
	private void addButtonToTable(boolean buttonType) {
		final String buttonName;
		if (buttonType) {
			buttonName = "Accept";
		} else {
			buttonName = "Deny";
		}
		
		TableColumn<PendingBookingRow, Void> colBtn = new TableColumn("");

		Callback<TableColumn<PendingBookingRow, Void>, TableCell<PendingBookingRow, Void>> cellFactory = 
				new Callback<TableColumn<PendingBookingRow, Void>, TableCell<PendingBookingRow, Void>>() {
			@Override
			public TableCell<PendingBookingRow, Void> call(final TableColumn<PendingBookingRow, Void> param) {
				final TableCell<PendingBookingRow, Void> cell = new TableCell<PendingBookingRow, Void>() {
					
					private final Button actionButton = new Button(buttonName);
					{
						actionButton.setOnAction((ActionEvent event) -> {
							if (buttonType) {
								int pendingBookingID = getTableView().getItems().get(getIndex()).getBookingID();
								System.out.println("Accept pending booking with bookingID=" + pendingBookingID);
								acceptCustomerSellCar(pendingBookingID);
							} else {
								int pendingBookingID = getTableView().getItems().get(getIndex()).getBookingID();
								System.out.println("Reject pending booking with bookingID=" + pendingBookingID);
								rejectSaleCar(pendingBookingID);
							}
						});
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(actionButton);
						}
					}
				};
				return cell;
			}
		};

		colBtn.setCellFactory(cellFactory);

		pendingBookingsTable.getColumns().add(colBtn);

	}
	
	public void acceptCustomerSellCar(int pendingBookingID) {
		try {
			conn = openDBconn.connect();
			stmt = conn.createStatement();
			
			String sql_acceptBooking = "UPDATE bookingDetails SET bookingCompleted ='1' WHERE bookingID='"+pendingBookingID+"'";
			stmt.executeUpdate(sql_acceptBooking);
			// Pasar a Seller.buyCar(------------)
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initData(seller);
	}
	
	public void rejectSaleCar(int pendingBookingID) {
		try {
			conn = openDBconn.connect();
			stmt = conn.createStatement();
			
			String sql_deleteBooking = "DELETE FROM bookingDetails WHERE bookingID='"+pendingBookingID+"'";
			stmt.executeUpdate(sql_deleteBooking);
			// Pasar a Seller.rejectOperation(------------)
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initData(seller);
	}
	
	public void updateTableViews(ActionEvent event) {
		initData(seller);
	}

}
