package model;

import java.io.Serializable;

public class TypeTurn implements Serializable{

	private String name;
	private float time;	
	
	public TypeTurn(String name, float time) {
		this.name = name;
		this.time = time;
	}
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public float getTime() {
		return time;
	}
	
	public void setTime(float time) {
		this.time = time;
	}
	
	

	
	
}
