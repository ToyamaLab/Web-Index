package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import core.data.Product;

public class ShareMethods {
	
	public static String readSampleFile(String fileName) {
		StringBuffer output = new StringBuffer();
		BufferedReader br;
		try {
			 br = new BufferedReader(new FileReader("src/test/resources/sample/" + fileName));
			 String line = "";
			 while ((line = br.readLine()) != null) {
				 output.append(new String(line.getBytes("UTF-8"), "UTF-8") + System.getProperty("line.separator"));
			 }
			 br.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return output.toString();
	}
	
	public static void createSampleFile(String fileName, String target) {
		FileWriter fw;
		try {
			File file = new File("src/test/resources/sample/" + fileName);
			fw = new FileWriter(file);
			fw.write(new String(target.getBytes("UTF-8"), "UTF-8"));
			fw.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void createObjectFile (String fileName, List<Product> input) 
			throws FileNotFoundException, IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/test/resources/sample/" + fileName));
		oos.writeObject(input);
		oos.close();
	}
	
	public static List<Product> readObjectFile (String fileName) 
			throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("src/test/resources/sample/" + fileName));
		List<?> readObject = (List<?>) ois.readObject();
		ois.close();
		List<Product> result = new ArrayList<Product>();
		for (int i = 0; i < readObject.size(); i++) {
			result.add((Product) readObject.get(i));
		}
		return result;
	}
}
