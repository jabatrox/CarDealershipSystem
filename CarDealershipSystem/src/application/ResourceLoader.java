package application;

import java.io.InputStream;

final public class ResourceLoader {

	public ResourceLoader() {
		// TODO Auto-generated constructor stub
	}
	
	public static InputStream load(String path) {
		InputStream input = ResourceLoader.class.getResourceAsStream(path);
		System.out.println(path);
		if (input == null) {
			input = ResourceLoader.class.getResourceAsStream("/"+path);
		}
		System.out.println(path);
		return input;
	}

}
