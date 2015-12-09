package com.tyh.Uniprot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;

public class getGOAndSeq {

	public static void main(String[] args) {
		String fileName = "files/rawData.txt";
		
		String outputFileName = "files/data.txt";
		String entryFile = "files/entries.txt";
		String idFile = "files/id.txt";
		
		File file = new File(fileName);
		BufferedReader reader = null;
				
		int counter = 0;
		int size = 100;
		try {
			reader = new BufferedReader(new FileReader(file));
			FileWriter writer = new FileWriter(outputFileName);
			FileWriter entryWriter = new FileWriter(entryFile);
			FileWriter idWriter = new FileWriter(idFile);
			String line = "";
			while((line = reader.readLine()) != null && counter < size){
				if(line.startsWith("ID")){//a new protein
					StringBuilder protein = new StringBuilder();
					String[] tokens = line.split("\\s+");//split by any length of continuous whitespace
					String entry = tokens[1] + "\r\n";
					String id = null;
					boolean hasGO = false;
					while((line = reader.readLine()) != null && !line.equals("//")){
						//get id 
						if(line.startsWith("AC") && id == null){
							String[] ids = line.split("\\s+")[1].split(";");
							id = ids[0] + "\r\n";
							protein.append(id);
						}
						//get go term						
						if(line.startsWith("DR")){
							if(line.contains("GO;")){
								hasGO = true;
								String[] peaces = line.split("\\s+");
								protein.append(peaces[2]).append("\r\n");
							}
						}
						//get sequence
						if(line.startsWith("SQ") && hasGO){
							while((line = reader.readLine())!= null && !line.equals("//")){
								protein.append(line.trim()).append("\r\n");
							}
							protein.append("//").append("\r\n");
							writer.write(protein.toString());
							entryWriter.write(entry);
							idWriter.write(id);
							id = null;
							counter++;
							break;
						}
					}
				}				
			}
			System.out.println(counter);
			writer.flush();
			writer.close();
			entryWriter.flush();
			entryWriter.close();
			idWriter.flush();
			idWriter.close();
			reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
