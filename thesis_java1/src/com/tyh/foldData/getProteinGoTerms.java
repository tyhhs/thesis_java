package com.tyh.foldData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class getProteinGoTerms {

	public static void main(String[] args) {
		getProteinGoTerms instance = new getProteinGoTerms();
		File nameFile = new File("dataFiles/names.txt");
		BufferedReader reader = null;
		try{
			reader = new BufferedReader(new FileReader(nameFile));
			String line = null;
			while((line = reader.readLine()) != null){
				//line is a protein name
				//name format is PDB+chain - SCOPdomain
				String[] nameTokens = line.split("-");
				String scop = nameTokens[1];
				scop = scop.replace("_", "a");
				//System.out.println(scop);
			}
			System.out.println(instance.getGoTerm("d1asu"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//search Go terms in the online tool
	//URL: http://pdg.cnb.csic.es/scop2go/search.jsp?search=scop
	private String getGoTerm(String scop){
		//input: scop name
		//output: protein GO terms
		String url = "http://pdg.cnb.csic.es/scop2go/search.jsp?search=" + scop;
		String html = getHtml(url);
		System.out.println(html);
		return html;
	}
	
	private String getHtml(String url){
		StringBuffer html = new StringBuffer();
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			
			// optional default is GET
			con.setRequestMethod("GET");
			
			con.setConnectTimeout(5 * 1000);
			
			InputStreamReader isr = new InputStreamReader(con.getInputStream());
			BufferedReader reader = new BufferedReader(isr);
			String line = null;
			while((line = reader.readLine()) != null){
				html.append(line).append("\r\n");
			}
			reader.close();
			isr.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return html.toString();
	}

}
