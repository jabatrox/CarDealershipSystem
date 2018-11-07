package classes;

public interface SellerOperations {
	
	public abstract void buyCar(int carId);
	public abstract void sellCar(int carId, boolean statusCar);
	public abstract void rejectOperation(int bookingId);
	
}
