package com.tyh.Uniprot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class getTotalProteins {

	public static void main(String[] args) {
		String fileName = "files/rawData.txt";
		File file = new File(fileName);
		BufferedReader reader = null;
		int counter = 0;
		
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = null;
			while((line = reader.readLine()) != null){
				if(line.startsWith("ID")){
					counter++;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(counter);
	}

}
