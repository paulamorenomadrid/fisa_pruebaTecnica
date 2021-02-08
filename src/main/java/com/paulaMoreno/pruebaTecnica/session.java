package com.paulaMoreno.pruebaTecnica;

import java.util.ArrayList;
import java.util.List;

public class session {
	
	public session() {
		super();
		List<talk> list = new ArrayList<talk>();
		this.setTalks(list);
	}
	
	private int id;
	private boolean morningSession;
	private List<talk> talks;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isMorningSession() {
		return morningSession;
	}
	public void setMorningSession(boolean morningSession) {
		this.morningSession = morningSession;
	}
	public List<talk> getTalks() {
		return talks;
	}
	public void setTalks(List<talk> talks) {
		this.talks = talks;
	}
}

