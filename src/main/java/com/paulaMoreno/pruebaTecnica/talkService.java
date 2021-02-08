package com.paulaMoreno.pruebaTecnica;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class talkService {
	
	/**
     * Listar las charlas desde un archivo de texto
     * @param input 
     * @return
     */
	public static List<talk> getTalksFromFile(String input){
		        List<talk> talks = new ArrayList<talk>();
		        try{
		          FileInputStream fstream = new FileInputStream(input);
		          DataInputStream in = new DataInputStream(fstream);
		          BufferedReader br = new BufferedReader(new InputStreamReader(in));
		          String strLine = br.readLine();
		          while (strLine != null)   {
		        	talk aux = getTalkfromString(strLine);
		        	if (aux != null) {
		            talks.add(aux);
		            }
		        	strLine = br.readLine();
		          }
		          in.close();
		          br.close();
		        }catch (Exception e){
		          System.err.println("Error: " + e.getMessage());
		        }       
		        return talks;
		    }
	
	/**
     * Convertir string de datos en objetos talk
     * @param strLine
     * @return
     */	
	private static talk getTalkfromString(String strLine) throws Exception {
		String title = getTitle(strLine);
		if(title != null) {
			int time = getTime(strLine);
			talk talk = new talk(title, time);
			return talk;
		}
		return null;
	}
	
	/**
     * Obtener el titulo de la charla a partir de un string de datos
     * @param strLine
     * @return
     */	
	private static String getTitle(String strLine) {
		int lastSpaceIndex = strLine.lastIndexOf(" ");
		String title = null;
		if (lastSpaceIndex != -1) {
		title = strLine.substring(0, lastSpaceIndex);
		}
		return title;
	}

	/**
     * Obtener la duracion de la charla a partir de un string de datos
     * @param strLine
     * @return
     */	
	public static int getTime(String strLine) throws Exception {
		String minStr = "min";
		String lightningStr = "lightning";
		int time = 0;
		
		int lastSpaceIndex = strLine.lastIndexOf(" ");
		String timeStr= strLine.substring(lastSpaceIndex + 1);
		
		try{
            if(timeStr.endsWith(minStr)) {
                 time = Integer.parseInt(timeStr.substring(0, timeStr.indexOf(minStr)));
             }
             else if(timeStr.endsWith(lightningStr)) {
                 String lightningTime = timeStr.substring(0, timeStr.indexOf(lightningStr));
                 if("".equals(lightningTime))
                     time = 5;
                 else
                     time = Integer.parseInt(lightningTime) * 5;
             }
            
         }catch(NumberFormatException nfe) {
             throw new Exception("No se reconoce el tiempo de la charla en este formato: " + timeStr);
         }
		
		return time;
		
	}
	
	/**
     * Ordernar la lista de talks
     * @param talks
     * @return
     */	
	 public static List<talk> sortTalks(List<talk> talks) { 
	           
	        List<talk> talksList = new ArrayList<talk>(); 
	        talksList.addAll(talks);
	        Collections.sort(talksList, Collections.reverseOrder());
	        return talksList;
	 }
	 
	/**
     * Obtener a cantidad de dias posibles
     * @param talks
     * @return
     */		        
	 public static int numberOfDays(List<talk> talks) {
		int perDayMinTime = 6 * 60;
	    int totalTalksTime = getTotalTalksTime(talks);
	    return totalTalksTime/perDayMinTime;
	 }
	
	/**
     * Obtener el total de duracion de todas las charlas
     * @param talks
     * @return
     */	
	public static int getTotalTalksTime(List<talk> list) {
        if(list == null || list.isEmpty())
            return 0;
        
        int totalTime = 0;
        for(talk talk : list) {
            totalTime += talk.getLength();
        }
        return totalTime;
	}
		
}