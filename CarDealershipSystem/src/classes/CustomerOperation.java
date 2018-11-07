package classes;

import java.util.ArrayList;

public interface CustomerOperation {
	
	public abstract ArrayList<CarDetails> listCars(int conId);
	public abstract void buyCar(int carId);
	public abstract void sellCar(int CarId); 

}
