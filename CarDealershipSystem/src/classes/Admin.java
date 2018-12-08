package classes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import classes.CarDetails.EngineType;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import models.DBConnect;

/**
 * --------------------------------------------------
 * @author Javier Soler, Borja González
 * Date: 11/09/2018
 * Lab: Final Project
 * File name: Admin.java
 */
public class Admin extends Agent implements AdminOperations {

	Connection conn = null;
	DBConnect openDBconn = new DBConnect();
	Statement stmt = null;
	
	/**
	 * @param agentID
	 * @param firstName
	 * @param lastName
	 */
	public Admin(int adminID, String firstName, String lastName) {
		super(adminID, firstName, lastName);
	}
	
	@Override
	public ArrayList<AllFactoryDepositsInfoRow> getAllFactoryDeposits() {
		// TODO Auto-generated method stub
		ArrayList<AllFactoryDepositsInfoRow> allFactoryDeposits = new ArrayList<>();
		try {
			conn = openDBconn.connect();
			stmt = conn.createStatement();
			
			String sql_factoryDeposits = "SELECT * FROM factoryDeposit";
			ResultSet rs_factoryDeposits = stmt.executeQuery(sql_factoryDeposits);
			
			while(rs_factoryDeposits.next()) {
				System.out.println("FactoryDeposit with factID="+rs_factoryDeposits.getInt("factID")+" found!");
				allFactoryDeposits.add(new AllFactoryDepositsInfoRow(rs_factoryDeposits.getInt("factID"),
						rs_factoryDeposits.getInt("carCapacity"), 0));
			}
			for (AllFactoryDepositsInfoRow factoryDepositInfo : allFactoryDeposits) {
				String sql_factoryDepositsInfo = "SELECT COUNT(*) AS totalCarsProduced "
						+ "FROM factoryDeposit, carDetails "
						+ "WHERE carDetails.factID='"+factoryDepositInfo.getFactID()+"'";
				ResultSet rs_factoryDepositsInfo = stmt.executeQuery(sql_factoryDepositsInfo);
				int carsProduced = 0;
				while (rs_factoryDepositsInfo.next()) {
					System.out.println("\\___having produced "+rs_factoryDepositsInfo.getString("totalCarsProduced")+" cars.");
					carsProduced = rs_factoryDepositsInfo.getInt("totalCarsProduced");
				}
				factoryDepositInfo.setCarsProduced(carsProduced);
			}
			conn.close();
		}catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return allFactoryDeposits;
	}

	@Override
	public void createFactory(int carCapacity) {
		// TODO Auto-generated method stub
		// ID por autoincrement y elegir carCapacity
		System.out.println("Adding new factory with carCapacity="+carCapacity);
		try {
			conn = openDBconn.connect();
			stmt = conn.createStatement();
			String sql_newFactoryDeposit = "INSERT INTO factoryDeposit (carCapacity) VALUES ('"+carCapacity+"')";
			stmt.executeUpdate(sql_newFactoryDeposit);
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("\\___Added");
	}

	@Override
	public void deleteFactory(int factID) {
		// TODO Auto-generated method stub
		try {
			conn = openDBconn.connect();
			stmt = conn.createStatement();
			
			String sql_numberOfFactoryDeposits = "SELECT COUNT(*) AS numberOfFactoryDeposits FROM factoryDeposit";
			ResultSet rs_numberOfFactoryDeposits = stmt.executeQuery(sql_numberOfFactoryDeposits);
			int numberOfFactoryDeposits = 0;
			while (rs_numberOfFactoryDeposits.next()) {
				numberOfFactoryDeposits = rs_numberOfFactoryDeposits.getInt("numberOfFactoryDeposits");
			}
			if (numberOfFactoryDeposits == 1) {
				new Alert(Alert.AlertType.ERROR, "There is only one factory left, you can't delete it!").show();
			} else {
				List<Integer> factoryDepositsID = new ArrayList<>();
				String sql_factoryDeposits = "SELECT * FROM factoryDeposit WHERE factID<>'"+factID+"'";
				ResultSet rs_factoryDeposits = stmt.executeQuery(sql_factoryDeposits);
				while(rs_factoryDeposits.next()) {
					System.out.println("FactoryDeposit with factID="+rs_factoryDeposits.getInt("factID")+" found!");
					factoryDepositsID.add(rs_factoryDeposits.getInt("factID"));
				}

				ChoiceDialog<Integer> dialog = new ChoiceDialog<Integer>(factoryDepositsID.get(0), factoryDepositsID);
				dialog.setTitle("Factory Choice");
				dialog.setHeaderText("Select a factory from the list to move all concessionaires and cars to it");
				dialog.setContentText("Factory:");

				// Get the response value.
				Optional<Integer> factoryID = dialog.showAndWait();
				if (factoryID.isPresent()){
				    System.out.println("Your choice: " + factoryID.get());
				    // Update concessionaires and carDetails to the new factoryDeposit
					String sql_changeConcessionairesToNewFactory = "UPDATE concessionaire SET factID='"+factoryID+"' "
							+ "WHERE factID='"+factID+"';"
							+ "\n"
							+ "UPDATE carDetails SET factID='"+factoryID+"' "
							+ "WHERE factID='"+factID+"';";
					stmt.executeUpdate(sql_changeConcessionairesToNewFactory);
				}
				String sql_deleteFactoryDeposit = "DELETE FROM factoryDeposit WHERE factID='"+factID+"'";
				stmt.executeUpdate(sql_deleteFactoryDeposit);
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public ArrayList<ConcessionairesFromFactoriesRow> getAllConcessionairesFromFactory(int factID) {
		// TODO Auto-generated method stub
		ArrayList<ConcessionairesFromFactoriesRow> allConcessionairesFromFactory = new ArrayList<>();
		try {
			conn = openDBconn.connect();
			stmt = conn.createStatement();
			
			String sql_concessionaires = "SELECT * FROM concessionaire WHERE factID='"+factID+"'";
			ResultSet rs_concessionaires = stmt.executeQuery(sql_concessionaires);
			
			while(rs_concessionaires.next()) {
				System.out.println("Concessionaire with conID="+rs_concessionaires.getInt("conID")+" found!");
				System.out.println("\\___belonging to factID="+factID+".");
				allConcessionairesFromFactory.add(new ConcessionairesFromFactoriesRow(rs_concessionaires.getInt("conID"),
						rs_concessionaires.getInt("carCapacity"), 0));
			}
			for (ConcessionairesFromFactoriesRow concessionaireFromFactory : allConcessionairesFromFactory) {
				String sql_concessionaireFromFactoryInfo = "SELECT COUNT(*) AS totalSellersInConcessionaire "
						+ "FROM seller, concessionaire "
						+ "WHERE seller.conID='"+concessionaireFromFactory.getConID()+"'";
				ResultSet rs_concessionaireFromFactoryInfo = stmt.executeQuery(sql_concessionaireFromFactoryInfo);
				int numberOfSellers = 0;
				while (rs_concessionaireFromFactoryInfo.next()) {
					System.out.println("\\___and having "+rs_concessionaireFromFactoryInfo.getString("totalSellersInConcessionaire")+" sellers.");
					numberOfSellers = rs_concessionaireFromFactoryInfo.getInt("totalSellersInConcessionaire");
				}
				concessionaireFromFactory.setNumberOfSellers(numberOfSellers);
			}
			conn.close();
		}catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return allConcessionairesFromFactory;
	}

	@Override
	public void createConcessionaire(int carCapacity, int factID) {
		// TODO Auto-generated method stub
		Concessionaire con = new Concessionaire(0, carCapacity, factID);
		addSeller(con.getConID());
		
	}

	@Override
	public void deleteConcessionaire(int conID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addSeller(int conID) {
		// TODO Auto-generated method stub
		// Elegir ID, nombre y apellido por la interfaz
		Seller seller = new Seller(0, null, null, conID);
	}

	@Override
	public void deleteSeller(int sellerID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BookingDetails getSalesHistory(int sellerID) {
		// TODO Auto-generated method stub
		return null;
	}

}