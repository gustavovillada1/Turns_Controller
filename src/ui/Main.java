package ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Scanner;

import customExceptions.EmptyFieldException;
import customExceptions.IsNotRegisteredException;
import customExceptions.UserAlreadyHaveTurnException;
import model.DataBase;
import model.FechaYHora;
import model.TypeTurn;
import model.User;

public class Main {

	private static DataBase dataBase=new DataBase();
	
	public static void main(String[]args) throws EmptyFieldException, IOException, IsNotRegisteredException, UserAlreadyHaveTurnException, ClassNotFoundException {
		
		//HAY DOS SORTS EN GENERAR REPORTE DE TURNO Y UNO EN ORDENAR A LOS USUARIOS PARA LA BUSQUEDA BINARIA 
		

		
		Scanner input=new Scanner(System.in);
		boolean exit=false;
		int option=0;
		
		System.out.println("¿Do you want open the last program or start again?\n1)new Program 2)Last program saved");
		option=Integer.parseInt(input.nextLine());
		if(option==1) {
		ObjectInputStream openProgram=new ObjectInputStream(new FileInputStream("data/Turns-ControllerDefault.txt"));
		dataBase=(DataBase) openProgram.readObject();
		openProgram.close();}else if(option==2) {
			ObjectInputStream openProgram=new ObjectInputStream(new FileInputStream("data/Turns-ControllerSaved.txt"));
			dataBase=(DataBase) openProgram.readObject();
			openProgram.close();			
		}
		
		
		
		while(!exit) {
			try {
				
				dataBase.generateTurnsDay();
				dataBase.attend_turns();
				System.out.println("_________________________");
				LocalDateTime tm=LocalDateTime.now();
				dataBase.getFecha().refres(tm);
				System.out.println("|\t"+dataBase.getFecha().showDate()+"\n|\t"+dataBase.getFecha().showHour());				
			System.out.println("|  USER SERVICE SYSTEM  |");	
			System.out.println("|_______________________|");
			System.out.println("Please, choose a option in the screen\n1)Add new user to the system.\n2)Asigning a turn\n3)Attend turns\n4)New date \n5)Generate Users"
					+ "\n6)Generate Report by turn of user. \n7)Generate Report by all users with a turn\n8)Determine how many turns per day. \n9)EXIT");			
			option=input.nextInt();
			input.nextLine();
			switch(option) {
			case 1:
				User user = null;
				String name = null,lastName = null,direction = null;
				int id = 0;
				long phone = 0;
				boolean again=true;
				boolean again2=true;									
					while(again==true) {
					try{
				System.out.println("Enter the identification of user: \n");
				id=Integer.parseInt(input.nextLine());
				System.out.println("Enter the phone number of user: \n");
				phone=Long.parseLong(input.nextLine());
				again=false;
					} catch(InputMismatchException e) {
						input.nextLine();
						System.out.println("|||Please, enter only numbers|||");
						again = true;
					}			
					}
					while(again2==true) {
					try {
					System.out.println("Enter the name of user:\n");
					name=input.nextLine();
					System.out.println("Enter the last name of user:\n");
					lastName=input.nextLine();
					System.out.println("Enter the direction of user:\n");
					direction=input.nextLine();		
					System.out.println("Choose the type of user document:\n1)C.C\n2)C.E\n3)T.I\n4)R.C\n5)Passport");					
					int option2=0;
					user=new User(id,name,lastName,phone,direction);
					option2=input.nextInt();
					input.nextLine();
					user.chooseTypeDocument(option2);
					again2=false;
				    long t1 = System.currentTimeMillis( );
					metodo(name,lastName,direction,user);	
				    long t2 = System.currentTimeMillis( );
				    long r= t2 - t1; 
				    
				    System.out.println("Operation time: "+r+" milisegundos.");
				    
					}catch(EmptyFieldException e) {
						System.out.println(e.getMessage());
						again2=true;
					}
					}
				
				
				break;
				
				
			case 2:

				TypeTurn ty = null;
				
				if(dataBase.getTypeTurns().isEmpty()) {
					System.out.println("\n|THERE IS NOT TYPETURNS| Please add a type.\n Enter the name of the type.");					
					String n=input.nextLine();					
					System.out.println("Enter the time of duration in minutes.");
					float time=Float.parseFloat(input.nextLine());
					
				    long t1 = System.currentTimeMillis( );
					dataBase.addTypeTurn(n, time);
				    long t2 = System.currentTimeMillis( );
				    long r= t2 - t1; 	
				    
				    System.out.println("Operation time: "+r+" milisegundos.");
					ty=new TypeTurn(n, time);
				}else {
					System.out.println("Select a type.");
					System.out.println("______________________");
					for(int i=0;i<dataBase.getTypeTurns().size();i++) {
						System.out.println(i+")- "+dataBase.getTypeTurns().get(i).getName()+" | Duration: "+dataBase.getTypeTurns().get(i).getTime()+" minutes.");
					}
					System.out.println("¿You want to do?\n1)add New type.\n2)Use an existing one.");
					int op=Integer.parseInt(input.nextLine());
					switch(op){
					case 1:
						
						System.out.println("Please add a type.\n Enter the name of the type.");					
						String n=input.nextLine();					
						System.out.println("Enter the time of duration in seconds.");
						float t=Float.parseFloat(input.nextLine());
					    long t1 = System.currentTimeMillis( );
						dataBase.addTypeTurn(n, t);
					    long t2 = System.currentTimeMillis( );
					    long r= t2 - t1; 
					    System.out.println("Operation time: "+r+" milisegundos.");

						ty=new TypeTurn(n, t);
						break;
						
					case 2:
						
						System.out.println("Please enter the index of the typeTurn.");
						int indx=Integer.parseInt(input.nextLine());
						String nam=dataBase.getTypeTurns().get(indx).getName();
						float time=dataBase.getTypeTurns().get(indx).getTime();						
						ty=new TypeTurn(nam, time);
						
						break;
						
						
						default:
							System.out.println("Choose 1) or 2)");
							
							break;					
					}					
				}
				int userID=0;
				System.out.println("|  Assingnig a turn  | \n Please enter the user ID (Numbers) :");
				userID=Integer.parseInt(input.nextLine());
				try {

				    long t1 = System.currentTimeMillis( );
					dataBase.assingUserTurn(userID,ty,true);
				    long t2 = System.currentTimeMillis( );
				    long r= t2 - t1; 
				    System.out.println("Operation time: "+r+" milisegundos.");
				
				}catch(IsNotRegisteredException e) {
					System.out.println(e.getMessage());
				}catch(UserAlreadyHaveTurnException e) {
					System.out.println(e.getMessage());
				}
				break;
								
			case 3:
				System.out.println("Attend the turns.");
				dataBase.attend_turns();		
				
				break;
			
			case 4:
				
				System.out.println("Please enter your option, Update date: \n 1)By system date.\n 2)Manually");
				option=Integer.parseInt(input.nextLine());
				
					if(option==1) {
						
					    long t1 = System.currentTimeMillis( );
						dataBase.updateDateTime(false,0,0,0,0,0,0);
					    long t2 = System.currentTimeMillis( );
					    long r= t2 - t1; 
					    System.out.println("Operation time: "+r+" milisegundos.");
					    
					}else if(option==2) {
						
						int y,m,d,h,mi,s;
						System.out.println("YEAR");
						y=Integer.parseInt(input.nextLine());
						System.out.println("MONTH");
						m=Integer.parseInt(input.nextLine());
						System.out.println("DAY");
						d=Integer.parseInt(input.nextLine());
						System.out.println("HOUR");
						h=Integer.parseInt(input.nextLine());
						System.out.println("MINUTE");
						mi=Integer.parseInt(input.nextLine());
						System.out.println("SECOND");
						s=Integer.parseInt(input.nextLine());
						
					    long t1 = System.currentTimeMillis( );
						dataBase.updateDateTime(true,y,m,d,h,mi,s);
					    long t2 = System.currentTimeMillis( );
					    long r= t2 - t1; 
					    
					    System.out.println("Operation time: "+r+" milisegundos.");
					    
					}else {
						System.out.println("Please enter a correct option.");
					}
						System.out.println("The new date is: "+dataBase.getFecha().showDate()+" "+dataBase.getFecha().showHour());
				
				break;
				
				
			case 5:
				
				System.out.println("Please enter the numbers of users for generate.");
				int q=Integer.parseInt(input.nextLine());
				while(q>20000) {
					System.out.println("|||Please a number < 20 000|||");
					System.out.println("Please enter the numbers of users for generate.");
					q=Integer.parseInt(input.nextLine());
				}
				
			    long t1 = System.currentTimeMillis( );
				dataBase.generateUsers(q);
			    long t2 = System.currentTimeMillis( );
			    long r= t2 - t1; 
			    System.out.println("Operation time: "+r+" milisegundos.");

				break;
			
			case 6:
				System.out.println("Please enter the user id of which you want to generate a report.");
				int idReport=Integer.parseInt(input.nextLine());
				
				boolean ok=false;
				int sortBy=0;
				while(ok==false) {
				System.out.println("Choose how to order shifts: \n 1) By the letter |A|00 2) By number: A|00|");
				sortBy=Integer.parseInt(input.nextLine());
				ok=true;
				}
				ok=false;
				int n=0;
				while(ok==false) {
					System.out.println("Generate report in: \n1)Screen \n2)New file");
					 n=Integer.parseInt(input.nextLine());
					 ok=true;
				}
				
			     t1 = System.currentTimeMillis( );
				dataBase.generateReportByTurnUser(idReport,n,sortBy);
			     t2 = System.currentTimeMillis( );
			     r= t2 - t1; 
			    System.out.println("Operation time: "+r+" milisegundos.");
				
				break;
				
			case 7:
				System.out.println("Please enter the turn to find, example |A00|.");
				String tf=input.nextLine();
				
			     t1 = System.currentTimeMillis( );
					dataBase.generateReportPersonsWithATurnSpecific(tf);
			     t2 = System.currentTimeMillis( );
			     r= t2 - t1; 
			    System.out.println("Operation time: "+r+" milisegundos.");
				break;
				
				
			case 8:
				System.out.println("Enter the number of turns. ");
				int nt=Integer.parseInt(input.nextLine());
	
				System.out.println("For how many days. ");
				int nd=Integer.parseInt(input.nextLine());
				
			     t1 = System.currentTimeMillis( );
				dataBase.determine_turns_day(nt, nd);
			     t2 = System.currentTimeMillis( );
			     r= t2 - t1; 
			    System.out.println("Operation time: "+r+" milisegundos.");
				
				break;
				
			case 9:
				System.out.println("¿Do you want to save the changes??\n1)YES\n2)NO");
				int ext=Integer.parseInt(input.nextLine());
				while(ext<1&&ext>2) {
					System.out.println("¿Do you want to save the changes??\n1)YES\n2)NO");
					 ext=Integer.parseInt(input.nextLine());

				}
					if(ext==1) {
						 saveProgram();
						 input.close();
						exit=true;
					}else if(ext==2) {
						input.close();
						exit=true;
					}
					
				break;
			default:
				System.out.println("Sorry, must be a number between 1 and 8");
				break;
			
			}
			}catch(InputMismatchException e) {
				System.out.println("|||Please enter a number|||");
				input.nextLine();
			}
		}
	}
	
	private static void saveProgram() throws FileNotFoundException, IOException {
		ObjectOutputStream saving=new ObjectOutputStream(new FileOutputStream("data/Turns-ControllerSaved.txt"));
		saving.writeObject(dataBase);
		saving.close();
	}
	

	private static void metodo(String name,String lastName, String direction,User user) throws EmptyFieldException{
		if(name.isEmpty()||lastName.isEmpty()||direction.isEmpty()) {
		throw new EmptyFieldException(name,lastName,direction, 0, 0); 
	}else {
		System.out.println(user.getName()+" "+user.getLastName()+"\n|"+user.getTypeDocument()+"|- "+user.getId()+"  |has been added correctly.\n");
		dataBase.addUser(user);	
	}
	}
}
