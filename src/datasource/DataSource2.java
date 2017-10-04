package datasource;

import java.util.HashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DataSource2 implements DataSourceApi {

	@Override
	public HashMap<String, String> getData() {
		BufferedReader br = null;
		FileReader fr = null;
		String fileName = "." + File.separator + "src" + File.separator + "datasource" + File.separator
				+ "readData.txt";
		HashMap<String, String> cityTemp = new HashMap<String, String>();
		String[] splt;
		try {
			fr = new FileReader(fileName);
		} catch (FileNotFoundException e) {
			System.out.print("Could not find the file");
			return null;
		}
		br = new BufferedReader(fr);
		String sCurrentLine;
		try {
			while ((sCurrentLine = br.readLine()) != null) {
				sCurrentLine = sCurrentLine.trim();
				splt = sCurrentLine.split(" ");
				cityTemp.put(splt[0], splt[1]);
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.print("IO Exception encountered");
			return null;
		}
		return cityTemp;
	}

}
