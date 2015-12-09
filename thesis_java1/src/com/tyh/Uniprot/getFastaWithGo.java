package com.tyh.Uniprot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class getFastaWithGo {

	public static void main(String[] args) {
		File idFile = new File("files/id.txt");
		File fastaFile = new File("files/rawData_fasta.txt");
		
		String output = "files/data_fasta.txt";
		String fastaPairsFile = "files/fastaPairs.txt";
		
		BufferedReader idReader = null;
		BufferedReader fastaReader = null;
		
		int counter = 0;
		
		List<String> list = new ArrayList<String>();
		try{
			//get id and store them in set 
			idReader = new BufferedReader(new FileReader(idFile));
			String line = null;
			Set<String> idSet = new HashSet<String>();
			while((line = idReader.readLine()) != null){
				idSet.add(line);
			}
			//filter the raw fasta file
			FileWriter writer = new FileWriter(output);
			line = null;
			fastaReader = new BufferedReader(new FileReader(fastaFile));
			StringBuilder fasta = null;
			boolean hasGO = false;
			while((line = fastaReader.readLine()) != null){
				if(line.startsWith(">sp")){//fasta head
					if(fasta != null){
						writer.write(fasta.toString());
						list.add(fasta.toString());
						counter++;
						fasta = null;
						hasGO = false;
					}
					String[] tokens1 = line.split("\\|");
					//String[] tokens2 = tokens1[2].split("\\s+");
					String id = tokens1[1];
					//if this protein has GO terms
					if(idSet.contains(id)){
						fasta = new StringBuilder();
						hasGO = true;
						fasta.append(">" + id).append("\r\n");
						idSet.remove(id);
					}
				}
				else if(hasGO){
					fasta.append(line).append("\r\n");
				}
			}
			if(fasta != null){
				writer.write(fasta.toString());
				list.add(fasta.toString());
				counter++;
			}
			writer.flush();
			writer.close();
			System.out.println(counter);
			//write fasta pairs
			counter = 1;
			FileWriter pairsWriter = new FileWriter(fastaPairsFile);
			for(int i = 0; i < list.size()-1; i++){
				for(int j = i+1; j < list.size(); j++){
					pairsWriter.write("pair " + counter++ + "\r\n");
					pairsWriter.write(list.get(i));
					pairsWriter.write(list.get(j));
					pairsWriter.write("//\r\n");
				}
			}
			pairsWriter.flush();
			pairsWriter.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
