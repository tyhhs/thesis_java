package com.tyh.Uniprot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class getProteinPairs {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File file = new File("files/data.txt");
		String output = "files/proteinPairs.txt";
		BufferedReader reader = null;
		List<String> proteins = new ArrayList<String>();
		try{
			//put all proteins in a map
			reader = new BufferedReader(new FileReader(file));
			String line = null;
			StringBuilder protein = null;
			while((line = reader. readLine()) != null){
				if(line.startsWith("ID")){
					//found a new protein
					protein = new StringBuilder();
					protein.append(line).append("\r\n");
				}
				else if(!line.equals("//")){
					protein.append(line).append("\r\n");
				}
				else{
					proteins.add(protein.toString());
				}
			}
			System.out.println(proteins.size());
			//build protein pairs form the list
			FileWriter writer = new FileWriter(output);
			int counter = 1;
			for(int i = 0; i < proteins.size(); i++){
				for(int j = i+1; j < proteins.size(); j++){
					writer.write("pair" + counter++ + "\r\n");
					writer.write(proteins.get(i));
					writer.write(proteins.get(j));
					writer.write("//\r\n");
				}
			}
			writer.flush();
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
