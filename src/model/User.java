package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.InputMismatchException;

public class User implements Serializable{
	//-----------------------------------------------------
	// ATRIBUTOS
	//-----------------------------------------------------
	private int id;
	private String name;
	private String lastName;
	private long phone;
	private String direction;
	private String typeDocument;
	private ArrayList<Turn> turnSolicited;
	private boolean suspended;
	private FechaYHora dateSuspention;
	
	private Turn turn;

	private String C_C="C.C";
	private String T_I="T.I";
	private String C_E="C.E";
	private String R_C="R.C";
	private String Pass="Passport";	
	
	
	public User(int id, String name, String lastName, long phone, String direction) {
		super();
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.phone = phone;
		this.direction = direction;
		this.suspended=false;
		this.turnSolicited=new ArrayList<Turn>();
	}
	
	/**
	 * This method is responsible for giving the user the option to choose what type of document he has.
	 * @param option2 the index of the type of document you have chosen
	 * @throws InputMismatchException Throw the exception if you enter a letter instead of a number
	 */
	public void chooseTypeDocument(int option2) throws InputMismatchException{
	
		try {
		switch(option2) {
		case 1:
			typeDocument=C_C;
			break;
			
		case 2:
			typeDocument=C_E;
			break;
			
		case 3:
			typeDocument=T_I;
			break;
			
		case 4:
			typeDocument=R_C;
			break;
			
		case 5:
			typeDocument=Pass;
			break;	
		default:
			System.out.println("Choose a number between 1 to 5");

			break;
		}	
		}catch(InputMismatchException e) {
			System.out.println("Please enter a number.");
		}
		
		
		
	}
	

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public long getPhone() {
		return phone;
	}
	public void setPhone(int phone) {
		this.phone = phone;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public Turn getTurn() {
		return turn;
	}
	public void setTurn(Turn turn) {
		this.turn = turn;
	}

	public String getTypeDocument() {
		return typeDocument;
	}

	public void setTypeDocument(String typeDocument) {
		this.typeDocument = typeDocument;
	}

	public ArrayList<Turn> getTurnSolicited() {
		return turnSolicited;
	}

	public void setTurnSolicited(ArrayList<Turn> turnSolicited) {
		this.turnSolicited = turnSolicited;
	}

	public boolean isSuspended() {
		return suspended;
	}

	public void setSuspended(boolean suspended) {
		this.suspended = suspended;
	}

	public FechaYHora getDateSuspention() {
		return dateSuspention;
	}

	public void setDateSuspention(FechaYHora dateSuspention) {
		this.dateSuspention = dateSuspention;
	}

	


	
	
	
	
}
