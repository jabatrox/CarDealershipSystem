package classes;

import java.util.ArrayList;

public interface CustomerOperation {
	
	/**
	 * @param conId
	 * @return
	 */
	public abstract ArrayList<CarDetails> listCars(int conId);
	/**
	 * @param carId
	 */
	public abstract void buyCar(int carId);
	/**
	 * @param CarId
	 */
	public abstract void sellCar(int CarId); 

}
