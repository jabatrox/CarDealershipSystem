package classes;

import java.util.ArrayList;

public interface SellerOperations {
	
	public abstract void buyCar(int carId);
	public abstract void sellCar(int carId, boolean statusCar);
	public abstract void rejectOperation(int bookingId);
	public abstract void addCarExposed(int carId);
	public abstract void deleteCarExposed(int carId);
	public abstract ArrayList<BookingDetails> checkPendigOperations(int conId);
}
