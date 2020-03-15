package model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Turn implements Comparable<Turn>, Serializable{
	
	private String turn;
	private TypeTurn typeTurn;
	private FechaYHora requested;
	
	private boolean attented;
	private boolean isNotHere;
	boolean touch=false;
	
	float time;
	
	private User user;
	
	public Turn(String t) {
		
		this.turn=t;
		this.isNotHere=false;
		this.attented=false;
	}	
	
	public boolean isTouch() {
		return touch;
	}

	public void setTouch(boolean noTouch) {
		this.touch = noTouch;	
	}

	public String getTurn() {
		return turn;
	}

	public void setTurn(String turn) {
		this.turn = turn;
	}

	public boolean isAttented() {
		return attented;
	}

	public void setAttented(boolean attented) {
		this.attented = attented;
	}

	public boolean isNotHere() {
		return isNotHere;
	}

	public void setIsNottHere(boolean isNotHere) {
		this.isNotHere = isNotHere;
	}

	public User getUser() {
		return user;
	}

	public TypeTurn getType() {
		return typeTurn;
	}

	public void setType(TypeTurn type) {
		this.typeTurn = type;
	}

	public void setNotHere(boolean isNotHere) {
		this.isNotHere = isNotHere;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public FechaYHora getRequested() {
		return requested;
	}

	public void setRequested(FechaYHora requested) {
		this.requested = requested;
	}

	@Override
	public int compareTo(Turn otherTurn) {
		return turn.compareTo(otherTurn.getTurn());
	}




	
	

}
