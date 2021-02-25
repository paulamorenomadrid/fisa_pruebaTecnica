package com.paulaMoreno.pruebaTecnica;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class sessionService {
	
	/**
     * Encontrar las posibles sesiones
     * @param talks
     * @param numberOfDays
     * @param morningSession
     * @return
     */
	 private static List<session> findProbablySessions(List<talk> talks, boolean morningSession, int numberOfDays)
	    {
	        int minSessionTimeLimit = 180;
	        int maxSessionTimeLimit = 240;
	                
	        
	        if(morningSession) 
	            maxSessionTimeLimit = minSessionTimeLimit;
	       
	        List<session> possibleListOfSession = new ArrayList<session>();
	        int possibleCombinationCount = 0;
	        
	        List<talk> sortTalks = new ArrayList<talk>();
	        sortTalks = talkService.sortTalks(talks);
	        
	        int talksSize = talks.size();
	        
	        for(int count = 0 ; count < talksSize; count++) {
	            int start = count;
	            int total = 0;
	            session possibleSession = new session();
	            
	            while(start != talksSize) {
	                int currentCount = start;
	                start++;
	                talk currentTalk = sortTalks.get(currentCount);
	                int talkTime = currentTalk.getLength();
	                if(currentTalk.isScheduled())
	                    continue;
	                if(talkTime > maxSessionTimeLimit || talkTime + total > maxSessionTimeLimit) {
	                    continue;
	                }
	                
	                possibleSession.getTalks().add(currentTalk);
	                total += talkTime;
	                
	                if(morningSession) {
	                	possibleSession.setMorningSession(true);
	                    if(total == maxSessionTimeLimit)
	                        break;
	                }else if(total >= minSessionTimeLimit)
	                    break;
	            }
	       	
	            boolean validSession = false;
	            if(morningSession)
	                validSession = (talkService.getTotalTalksTime(possibleSession.getTalks()) == maxSessionTimeLimit);
	            else
	                validSession = (total >= minSessionTimeLimit && total <= maxSessionTimeLimit);
	            
	            if(validSession) {
	            	possibleListOfSession.add(possibleSession);
	                for(talk talk : possibleSession.getTalks()){
	                    talk.setScheduled(true);
	                }
	                possibleCombinationCount++;
	                if(possibleCombinationCount == numberOfDays)
	                    break;
	            }
	        }
	        
	        return possibleListOfSession;
	    }
	 	
		/**
	     * Programar las charlas 
	     * @param talks
	     * @return
	     */
		public static List<session> programarCharlas(List<talk> talks) throws Exception {
			
			int numberOfDays= talkService.numberOfDays(talks);
			
			List<session> morningSessions = findProbablySessions(talks, true, numberOfDays);
			
		    for(session session : morningSessions) {
		    	talks.removeAll(session.getTalks());
		    }
		        
		    List<session> eveningSessions = findProbablySessions(talks, false, numberOfDays);     
		        
		    for(session session : eveningSessions) {
		         talks.removeAll(session.getTalks());
		    }
		      
		        int maxSessionTimeLimit = 240;
		        if(!talks.isEmpty()) {
		            List<talk> scheduledTalkList = new ArrayList<talk>();
		            for(session session : eveningSessions) {
		                int total = talkService.getTotalTalksTime(session.getTalks());
		                
		                for(talk talk : talks) {
		                    int talkTime = talk.getLength();
		                    
		                    if(total + talkTime <= maxSessionTimeLimit) {
		                        talk.setScheduled(true);
					session.add(talk);
		                        scheduledTalkList.add(talk);
		                        total = total + talkTime;
		                    }
		                }
		                talks.removeAll(scheduledTalkList);
		                if(talks.isEmpty())
		                    break;
		            }
		        }
		        
		     
		        if(!talks.isEmpty())
		        {
		            throw new Exception("No se puede programar esta conferencia.");
		        }
		        
		        return getPrograma(morningSessions, eveningSessions);
		    }

	
		/**
	     * Imprimir programa en consola 
	     * @param morningSessions
	     * @param eveningSessions
	     * @return
	     */
		private static List<session> getPrograma(List<session> morningSessions, List<session> eveningSessions){
		
		        List<session> program = new ArrayList<session>();
		        int totalPossibleDays = eveningSessions.size();
		        
		        for(int dayCount = 0; dayCount < totalPossibleDays; dayCount++) {
		            List<talk> talkList = new ArrayList<talk>();
		            
		            Calendar date = Calendar.getInstance();
		            SimpleDateFormat dateFormat = new SimpleDateFormat ("hh:mm");
		            date.set(Calendar.HOUR, 9); 
		            date.set(Calendar.MINUTE, 0); 
		            date.set(Calendar.SECOND, 0); 

		            int trackCount = dayCount + 1;
		            String scheduledTime = dateFormat.format(date.getTime());
		            
		            
		            System.out.println("Track " + trackCount + ":");
		            
		            // Morning Session - set the scheduled time in the talk and get the next time using time duration of current talk.
		            session morningSessionTalkList = morningSessions.get(dayCount);
		            for(talk talk : morningSessionTalkList.getTalks()) {
		                System.out.println(scheduledTime + " " + talk.getTitle());
		                date.add(Calendar.MINUTE, talk.getLength());
		                scheduledTime = dateFormat.format(date.getTime());
		                talkList.add(talk);
		            }
		           
		        
		            talk lunchTalk = new talk("Lunch", 60);
		            talkList.add(lunchTalk);
		            System.out.println(scheduledTime + " " + "Lunch");
		            
		            date.add(Calendar.MINUTE, lunchTalk.getLength());
	                scheduledTime = dateFormat.format(date.getTime());
		            session eveningSessionTalkList = eveningSessions.get(dayCount);
		            for(talk talk : eveningSessionTalkList.getTalks()) {
		                talkList.add(talk);
		                System.out.println(scheduledTime + " " +  talk.getTitle());
		                date.add(Calendar.MINUTE, talk.getLength());
		                scheduledTime = dateFormat.format(date.getTime());
		            }
		           
		            talk networking = new talk("Networking Event", 60);
		            talkList.add(networking);
		            System.out.println(scheduledTime + " " + "Networking Event\n");
		            session day = new session();
		            day.setTalks(talkList);
		            program.add(day);
		        }
		        
		        return program;
		    }
}
