package com.tyh.foldData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.Set;

public class readFoldPairs {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File foldPairs = new File("F:/thesis/fold recognition dataset/foldprodataset");
		BufferedReader reader = null;
		
		String nameFile = "dataFiles/names.txt";
		try{
			int lineNo = 0;
			reader = new BufferedReader(new FileReader(foldPairs));
			String line = null;
			Set<String> nameSet = new HashSet<String>();
			while((line = reader.readLine()) != null && lineNo < 1951){
				lineNo++;
				if(lineNo % 2 == 1){//protein name pair line
					line = line.substring(1);
					String[] tokens = line.split("\\s+");
					nameSet.add(tokens[0]);
					nameSet.add(tokens[1]);
				}
			}
			System.out.println(nameSet.size());
			reader.close();
			//write name into file
			FileWriter writer = new FileWriter(nameFile);
			for(String name : nameSet){
				writer.write(name + "\r\n");
			}
			writer.flush();
			writer.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
