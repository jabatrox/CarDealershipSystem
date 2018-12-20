package application;

import java.io.InputStream;

/**
 * This class is used to load resources (images, etc.) in the project. It is necessary for the JAR file compiler to load them and
 * add the to the JAR file while generating it.
 * @author Javier Soler, Borja González
 * Date: 12/08/2018
 * Lab: Final Project
 * File name: ResourceLoader.java
 */
final public class ResourceLoader {

	public ResourceLoader() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param path
	 * @return
	 */
	public static InputStream load(String path) {
		InputStream input = ResourceLoader.class.getResourceAsStream(path);
		if (input == null) {
			input = ResourceLoader.class.getResourceAsStream("/"+path);
		}
		return input;
	}

}
