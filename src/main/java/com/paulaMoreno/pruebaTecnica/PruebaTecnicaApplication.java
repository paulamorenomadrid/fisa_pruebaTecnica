package com.paulaMoreno.pruebaTecnica;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PruebaTecnicaApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(PruebaTecnicaApplication.class, args);
		
		Scanner in = new Scanner(System.in); 
    	List<talk> listOfTalks = new ArrayList<talk>();
    	
    	System.out.println("****BIENVENIDO****");
    	System.out.println("Ingrese el nombre de un archivo de texto que contenga las charlas que desea que se agreguen al programa de la conferencia: ");
    	String input = in.nextLine();  

    	listOfTalks = talkService.getTalksFromFile(input);
    	in.close(); 	
    	
    	sessionService.programarCharlas(listOfTalks);
	}

}
