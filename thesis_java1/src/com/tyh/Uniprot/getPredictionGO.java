package com.tyh.Uniprot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class getPredictionGO {

	public static void main(String[] args) {
		File prediction = new File("files/prediction100.txt");
		String predictionGO_method1 = "files/predictionGO_method1.txt";
		
		try{
			BufferedReader reader = new BufferedReader(new FileReader(prediction));
			FileWriter writer1 = new FileWriter(predictionGO_method1);
			String line = null;
			StringBuilder fasta = new StringBuilder();
			String id = "";
			int counter = 0;
			int method = 0;
			while((line = reader.readLine()) != null){
				if(line.startsWith(">Fasta")){
					if(fasta.length() != 0){
						writer1.write(fasta.toString());
					}
					//find a new fasta result
					fasta = new StringBuilder();
					String[] idTokens = line.split(">");
					id = idTokens[2].trim();
					fasta.append(id).append("\r\n");
				}
				//current method we are reading
				if(line.startsWith("Method 1")){
					method = 1;
				}
				if(line.startsWith("Method 2")){
					method = 2;
				}
				if(line.startsWith("Method 3")){
					method = 3;
				}
				if(line.startsWith(id)){
					if(method == 1){
						String[] GOTokens = line.split("\\s+");
						fasta.append(GOTokens[1]).append("\r\n");
					}
				}
			}
			if(fasta.length() != 0){
				writer1.write(fasta.toString());
			}
			reader.close();
			writer1.flush();
			writer1.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
