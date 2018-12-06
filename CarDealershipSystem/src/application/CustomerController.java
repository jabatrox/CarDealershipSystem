package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import classes.*;
import classes.CarDetails.EngineType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.DBConnect;

public class CustomerController implements Initializable{

	Connection conn = null;
	DBConnect openDBconn = new DBConnect();
	Statement stmt = null;
	
	private Image buyButton;
	
	private Customer customer;

	@FXML
	private Label customerWelcomeName, customerWelcomeID;

//	@FXML
//	private String userID;

	@FXML
	private TextField brand, model, color, horsePower, price, miles, year, conID, factID, chassis;

	@FXML
	private ChoiceBox<String> engineTypes;

	@FXML
	private ComboBox<String> buyCarOptions;
	
	@FXML
	private Button requestButton;
	
	private boolean firstTimeRun = true;
	
	@FXML
	private TableView<CarDetails> carsExposedCustomerTable;
	
	private ObservableList<CarDetails> carsExposedTable_data = FXCollections.observableArrayList();


	public CustomerController(){

	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

	}

	void initData(Agent customer_logged_in) {
		customer = (Customer) customer_logged_in;
		String fullname = customer.getFirstName()+" "+customer.getLastName();
		customerWelcomeName.setText(fullname.toUpperCase());
		String cust_id = Integer.toString((customer.getAgentID()));
		customerWelcomeID.setText("CUSTOMER ID: "+cust_id);
		
		String option = buyCarOptions.getSelectionModel().getSelectedItem();
		System.out.println(option);
		carsExposedCustomerTable.getItems().clear();
		ArrayList<CarDetails> conCars = customer.checkCars(option);
		for (CarDetails conCar : conCars) {
			carsExposedTable_data.add(conCar);
		}
		carsExposedCustomerTable.setItems(carsExposedTable_data);
		
		if (!carsExposedCustomerTable.getItems().isEmpty() && firstTimeRun) {
			formatCarConditionInTable();
			addBuyButtonToTable();
		}
		
		
		firstTimeRun = false;
	}
	private void addBuyButtonToTable() {
		buyButton = new Image(getClass().getResourceAsStream("../../resources/buy_round.png"),30,30,false,false);
		TableColumn<CarDetails, Void> buyColBtn = new TableColumn<CarDetails, Void>("");
		Callback<TableColumn<CarDetails, Void>, TableCell<CarDetails, Void>> cellFactory = 
				new Callback<TableColumn<CarDetails, Void>, TableCell<CarDetails, Void>>() {
			@Override
			public TableCell<CarDetails, Void> call(final TableColumn<CarDetails, Void> param) {
				final TableCell<CarDetails, Void> cell = new TableCell<CarDetails, Void>() {
					
					private ImageView imageView = new ImageView();
					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							imageView.setImage(buyButton);
							setGraphic(imageView);
						}
						setOnMousePressed(new EventHandler<MouseEvent>() {
				            @Override
				            public void handle(MouseEvent event) {
				                getTableView().getItems().get(getIndex()).getCarID();
				            }            
				        });
					}
				};
				return cell;
			}
		};
		buyColBtn.setCellFactory(cellFactory);
		carsExposedCustomerTable.getColumns().add(buyColBtn);
		buyColBtn.setStyle("-fx-alignment: CENTER");
	}
	/*private void addBuyButtonToTable() {
		final ImageView buyButton;
		
		buyButton = new ImageView(new Image(getClass().getResourceAsStream("../../resources/buy_round.png"),20,20,false,false));
		
		TableColumn<CarDetails, Void> buyColBtn = new TableColumn<CarDetails, Void>("");

		Callback<TableColumn<CarDetails, Void>, TableCell<CarDetails, Void>> cellFactory = 
				new Callback<TableColumn<CarDetails, Void>, TableCell<CarDetails, Void>>() {
			@Override
			public TableCell<CarDetails, Void> call(final TableColumn<CarDetails, Void> param) {
				final TableCell<CarDetails, Void> cell = new TableCell<CarDetails, Void>() {
					
					//private final ImageView actionButton = new ImageView();
					{
						buyButton.setOnMouseClicked(event -> {
							System.out.println("Buy car");
				        });
						/*actionButton.setOnAction((ActionEvent event) -> {
							//int pendingBookingID = getTableView().getItems().get(getIndex()).getBookingID();
							//7String pendingBookingType = getTableView().getItems().get(getIndex()).getBookingType();
							System.out.println("Buy car");
							//acceptSaleCar(pendingBookingID, pendingBookingType);
						});
					}

					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(buyButton);
						}
					}
				};
				return cell;
			}
		};
		buyColBtn.setCellFactory(cellFactory);
		carsExposedCustomerTable.getColumns().add(buyColBtn);
		buyColBtn.setStyle("-fx-alignment: CENTER");
	}*/
	
	public void ListAvailableCars(ActionEvent event) throws Exception{
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/ListCars.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/*public void SellCar(ActionEvent event) throws Exception{
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/Customer_Sell_Car.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();

		//try {String cLV = sellCarOptions.getSelectionModel().getSelectedItem();} catch (Exception e) {};
	}*/

	public void CustomerPage (ActionEvent event) throws Exception {
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/Customer.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void RequestSellCar(ActionEvent event) {
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
			String chassisId = chassis.getText();

			System.out.println(chassisId);

			int carId = Integer.parseInt(chassisId);
			////////////////////// COMPROBAR SI SE HA PASADO UN CHASSID ID, SI NO PETA AL HACER SEND REQUEST //////////////////////////

			String sql = "SELECT * FROM carDetails WHERE carID='"+carId+"'";

			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
				CarDetails car = new CarDetails(carId,
						rs.getInt("conID"),
						rs.getInt("factID"),
						rs.getString("carBrand"),
						rs.getString("carModel"),
						co,
						EngineType.valueOf(rs.getString("engineType")),
						rs.getInt("horsePower"),
						Double.parseDouble(pr),
						Integer.parseInt(mi),
						rs.getBoolean("sold"),
						rs.getBoolean("exposed"),
						rs.getBoolean("carCondition"),
						rs.getInt("year"));
				if(customer.sellCar(car)) {
					new Alert(Alert.AlertType.INFORMATION, "Request Complete. Waiting for "
							+ "an employees approval").show();
				}

			} else {
				new Alert(Alert.AlertType.ERROR, "The Chassis ID "+carId+" "
						+ "is incorrect. Only cars that were previously bought from us are accepted").show();
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void formatCarConditionInTable() {
		TableColumn<CarDetails, Void> showCarCondition = new TableColumn<CarDetails, Void>("STATE");
		
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
		carsExposedCustomerTable.getColumns().add(showCarCondition);
		showCarCondition.setStyle("-fx-alignment: CENTER");
	}
	
	public void loadTable() {
		initData(customer);
	}
	


//	/**
//	 * @return the userName
//	 */
//	public String getUserID() {
//		return userID;
//	}
//
//
//	/**
//	 * @param userName the userName to set
//	 */
//	public void setUserID(String userID) {
//		this.userID = userID;
//	}

}
