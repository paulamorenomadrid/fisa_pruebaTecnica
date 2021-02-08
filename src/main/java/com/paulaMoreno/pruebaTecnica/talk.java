package com.paulaMoreno.pruebaTecnica;

public class talk implements Comparable< talk >{
	
	private int id;
	private String title;
	private Integer length;
	private boolean scheduled;
	

	public talk(String title, int length) {
		super();
		this.title = title;
		this.length = length;
	}
	
	@Override
	public String toString() {
		return title + " " + length + "min";
	}
	
	@Override
	public int compareTo(talk talk) {
		 return this.getLength().compareTo(talk.getLength());
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	
	public boolean isScheduled() {
		return scheduled;
	}

	public void setScheduled(boolean schedule) {
		this.scheduled = schedule;
	}
}
