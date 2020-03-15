package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import customExceptions.IsNotRegisteredException;
import customExceptions.UserAlreadyHaveTurnException;

public class DataBase implements Serializable{
	
	private ArrayList<User> users;
	private ArrayList<Turn> turns;
	private ArrayList<TypeTurn> typeTurns;
	private int turnsGenerateDay;
	private int how_many_days;
	private FechaYHora dayStartGeneratedTurns;

	
	private FechaYHora fecha;
	
	
	public DataBase() {
		
		users=new ArrayList<User>();
		turns=new ArrayList<Turn>();
		typeTurns=new ArrayList<TypeTurn>();
		fecha=new FechaYHora();
		turnsGenerateDay=0;
		how_many_days=0;
		dayStartGeneratedTurns=new FechaYHora();
	}



	
	
	public void generateTurnsDay() throws IsNotRegisteredException, UserAlreadyHaveTurnException {		
		int totalGenerated=0;
		int days=0;
		
		if(dayStartGeneratedTurns.getCurrentDate().isBefore(fecha.getCurrentDate())){
			long daysD=Duration.between(dayStartGeneratedTurns.getCurrentDate(), fecha.getCurrentDate()).getSeconds();
			days=(int) (daysD/86400);
			
			if(days>how_many_days) {
				for(int i=0;i<how_many_days*turnsGenerateDay;i++) {
					
					boolean done=false;
					User u=null;
					for(int j=0;j<users.size()&&done==false;j++) {

						if(users.get(j).getTurn().isTouch()==true) {
							u=users.get(j);
							TypeTurn ty=generateRandomTypeTurn();
							assingUserTurn(u.getId(), ty, true);
							totalGenerated++;
							done=true;

						}
					}
					done=false;
				}
			}else if(days<how_many_days) {
				long d=Duration.between(dayStartGeneratedTurns.getCurrentDate(), fecha.getCurrentDate()).getSeconds();
				days=(int) (daysD/86400);
				
				how_many_days=how_many_days-days;
				for(int i=0;i<days*turnsGenerateDay;i++) {
				boolean done=false;
				User u=null;
				for(int j=0;j<users.size()&&done==false;j++) {

					if(users.get(j).getTurn().isTouch()==true) {
						u=users.get(j);
						TypeTurn ty=generateRandomTypeTurn();
						assingUserTurn(u.getId(), ty, true);
						totalGenerated++;
						done=true;

					}
				}
				done=false;
				}
			}
			
				how_many_days--;
	}

			
		System.out.println("Has been generated "+totalGenerated+" turns later of "+days+" days.");
	}
	
	
	public void determine_turns_day(int qT,int days) throws IsNotRegisteredException, UserAlreadyHaveTurnException {
		
		this.turnsGenerateDay=qT;
		this.how_many_days=days;
		this.dayStartGeneratedTurns=fecha;
		generateTurnsDay();
	}
	
	
	private User[] usersWithATurnSpecific(String t1) {
		ArrayList<User> us= new ArrayList<User>();
		User u=null;
		
		for(int i=1;i<turns.size();i++) {
			Turn t=turns.get(i);
			if(t.getTurn().equals(t1)){
				u=t.getUser();
				us.add(u);
			}
		}
		
		User[] us1=new User[us.size()];
		for(int i=0;i<us1.length;i++) {
			us1[i]=us.get(i);
		}
		return us1;		
	}
	
	public void generateReportPersonsWithATurnSpecific(String turn) throws FileNotFoundException {
		User[] usrs=usersWithATurnSpecific(turn);
				
		String path="data/reports/usersWith-"+turn+"-turn.txt";			
		PrintWriter pw1=new PrintWriter(path);
		pw1.println("TURN "+turn+" REQUESTED BY THE USERS");
		pw1.println("=====================================");	
		
		for(int i=0;i<usrs.length;i++) {
			User u=usrs[i];
			pw1.println((i+1)+") | "+u.getName()+" "+u.getLastName()+"\t | ID: "+u.getId()+" \t|");
		}
		pw1.println("\n "+fecha.showDate()+" "+fecha.showHour());
		pw1.close();
	}


	public void generateReportByTurnUser(int id, int option, int sortby) throws FileNotFoundException {
		boolean found=searchUser(id);
		int poss=getIndexUser(id);	
		User u=users.get(poss);
		ArrayList<Turn> tUser=u.getTurnSolicited();		
		if(sortby==1) {
			Collections.sort(tUser);
		}else if(sortby==2) {
			Collections.sort(tUser,new CompareByNumbers());
		}
		
		if(found==true) {
			
			if(option==2) {
			String path="data/reports/userID-"+u.getId()+"-turns.txt";			
		PrintWriter pw1=new PrintWriter(path);
		pw1.println("TURNS REQUESTED BY THE USER "+u.getName()+" "+u.getLastName());
		pw1.println("================================================");				
		for(int i=0;i<tUser.size();i++) {
			Turn tur=tUser.get(i);
			String attended = "NO";
			String wasHere = "NOT ATTENDED";		
			if(tur.isTouch()==true) {
				attended="YES";				
				if(tur.isNotHere()==true){
					wasHere="NO";
				}else {
					wasHere="YES";
				}
			}		
				pw1.println(i+")\t |"+tur.getTurn()+"|\t Attend: "+attended+"    The user was present: "+wasHere);				
		}		
		pw1.println(fecha.showDate()+" "+fecha.showHour());
		pw1.close();

			}else if(option==1) {
				System.out.println("TURNS REQUESTED BY THE USER "+u.getName()+" "+u.getLastName());
				System.out.println("================================================");				
				for(int i=0;i<tUser.size();i++) {
					Turn tur=tUser.get(i);
					String attended = "NO";
					String wasHere = "NOT ATTENDED";		
					if(tur.isTouch()==true) {
						attended="YES";				
						if(tur.isNotHere()==true){
							wasHere="NO";
						}else {
							wasHere="YES";
						}
					}		
					System.out.println(i+")\t |"+tur.getTurn()+"|\t Attend: "+attended+"    The user was present: "+wasHere);				
				}	
			}		
		}else {
			System.out.println("The user is not registered.");
		}
	}

	public boolean suspend_User(User u) {
		boolean s=false;
		ArrayList<Turn> ts=u.getTurnSolicited();
		
		if(ts.size()>2) {
			Turn t1=ts.get(ts.size()-2);
			Turn t2=ts.get(ts.size()-1);
			if(t1.isNotHere()==true&&t2.isNotHere()==true) {
				u.setSuspended(true);
				s=true;
				System.out.println(u.getName()+" Has been suspended until day "+u.getDateSuspention().showDate()+" "+u.getDateSuspention().showHour()+" \nbecause has not been in the last two turns.");
			}			
		}		
		return s;
	}
	

	public void attend_turns() {
		
		boolean first=false;
		FechaYHora start=null;
		int attendeds=0;
		float nextTurn=(float)0.25;
		for(int i=1;i<turns.size();i++) {
			if(turns.get(i).isTouch()==false) {				
				if(first==false) {
				first=true;
				start=turns.get(i).getRequested();
				}				

				float ti=turns.get(i).getType().getTime();
				long time=(long) ((ti+nextTurn)*60);
					FechaYHora hour_when_terminated_turns=start;
					LocalDateTime hwt=hour_when_terminated_turns.getCurrentDate();
					hwt = hwt.plus(time, ChronoUnit.SECONDS); //sumar segudbnos

				
					Turn t=turns.get(i);
					if(hwt.isBefore(fecha.getCurrentDate())) {		
						
						double x= Math.random();				
						int random=(int)(x*2);
						if(random==1) {
						t.setIsNottHere(true);
						t.setAttented(false);
						t.setTouch(true);
						int idSearch=t.getUser().getId();												
						int poss=getIndexUser(idSearch);
						User u=users.get(poss);	
						suspend_User(u);
						start.setCurrentDate(hwt);;
						
						attendeds++;				
						}else if(random==2) {
							turns.get(i).setIsNottHere(false);
							turns.get(i).setAttented(true);
							turns.get(i).setTouch(true);
							start.setCurrentDate(hwt);;

							attendeds++;
						}						
					}				
					LocalDateTime tm=LocalDateTime.now();
						fecha.refres(tm);										
			}			
		}
		System.out.println(attendeds+" shifts have been attended");
	}
	
	


	
	
	public void updateDateTime(boolean manualUpdate,int y,int m, int d,int h,int min,int s) {		
		if(manualUpdate==false) {
			fecha=new FechaYHora();
		}else {
			LocalDateTime compare=LocalDateTime.now();
			LocalDateTime c=LocalDateTime.now();
			Month mo=Month.of(m);
			c=c.of(y, mo, d, h, min,s);


			if(c.isAfter(compare)) {
				System.out.println("entra");

				fecha=new FechaYHora(true,c);

			}else if(c.isBefore(compare)) {
				System.out.println("Please do not add a date from the past");
			}
		}
		
		
	}
	
	
	public void addTypeTurn(String n, float t) {
		TypeTurn tp=new TypeTurn(n,t);
		typeTurns.add(tp);
	}
	
	
																																																																int id=390212;

	/**
	 * 
	 * @param q
	 * @throws IOException 
	 * @throws UserAlreadyHaveTurnException 
	 * @throws IsNotRegisteredException 
	 */
	public void generateUsers(int q) throws IOException, IsNotRegisteredException, UserAlreadyHaveTurnException {
		Collections.sort(users, Collections.reverseOrder(new CompareByID()));
		User[] usersGenerated=new User[q];	
		String[] names=importFilesForUser("data/names.txt");
		String[] lastNames=importFilesForUser("data/lastNames.txt");
		String[] directions= {"Valle de Lili","Bochalema","Ciudad 2000","Refugio","Capri","Distrito Aguablanca","Siloe", "Tequendama","Ciudad Jardin","Pance","Buitrera","La selva","Primitivo Crespo","Nuevo Latir","Ciudad Cordoba"};
		String[] typeDocuments= {"C.C","T.I","C.E","R.C","PassPort"};
		for(int i=0;i<usersGenerated.length;i++) {			
			String name=names[(int)(Math.random()*names.length)];
			String lastName=lastNames[(int)(Math.random()*lastNames.length)];
			String direction=directions[(int)(Math.random()*directions.length)];
			String typeDocument=typeDocuments[(int)Math.random()*typeDocuments.length];
			if(users.isEmpty()==true) {
				 id= 787652;
			}else {
			id++;
			}		
			long phone=0;			
			if(users.isEmpty()==true) {
				 phone= 31387643;
			}else {
			 phone= users.get(users.size()-1).getPhone()+1;
			}		
			User user=new User(id,name,lastName,phone,direction);
			user.setTypeDocument(typeDocument);				
		
			TypeTurn tp=generateRandomTypeTurn();			
			users.add(user);
			assingUserTurn(id,tp,false);

		}			
	}

	private TypeTurn generateRandomTypeTurn() {
		String[] names= {"CONSULTA","TRANSACCION","ALMORZAR","COMER","CORRER","BAILAR","VIAJAR","PROGRAMAR","MANEJAR"};
		String name=names[(int)(Math.random()*names.length-1)];
	
		int poss=0;
		TypeTurn tp1=null;
		float lastTime=(float)2.5;
		float time=0;
		
		if(typeTurns.isEmpty()==true) {
			 time=lastTime;
		}else {
			poss=typeTurns.size()-1;
			tp1=typeTurns.get(poss);
			lastTime=tp1.getTime();
			time=lastTime;
		}
		
		int attempts=0;
		if(addTypeTurnGenerate(name, time)==true&&attempts<3) {
			attempts++;	
			name=names[(int)(Math.random()*names.length-1)];
		}else if(addTypeTurnGenerate(name, time)==true){
			addTypeTurn(name,time);
		}else {
			name=names[(int)(Math.random()*names.length-1)];
		}
		
		TypeTurn tp=new TypeTurn(name,time);	

		return tp;
		
	}
	
	private boolean addTypeTurnGenerate(String n, float t) {
		TypeTurn tp=new TypeTurn(n,t);
		boolean exist=false;
		
		for(int i=0;i<typeTurns.size();i++) {
			if(n.equals(typeTurns.get(i).getName())) {
				exist=true;
			}
		}
		if(exist==false) {
		typeTurns.add(tp);
		}
		return exist;
	}
		
				
	
	
	public String[] importFilesForUser(String path) throws IOException {
		ArrayList<String> total=new ArrayList<String>();
		File file=new File(path);
		FileReader fr=new FileReader(file);
		BufferedReader br=new BufferedReader(fr);		
		String line=br.readLine();			
		while(line!=null) {		
			total.add(line);
			line=br.readLine();
		}
		String[] rt=new String[total.size()];
		for(int i=0;i<total.size();i++) {
			rt[i]=total.get(i);
		}		
		fr.close();
		br.close();				
		return rt;
	}
	
	
	
	
	/**
	 * This method is responsible for allowing to see the current turn.
	 * @return The String with the current turn.
	 */
	public Turn actualTurn() {
		Turn turnActual=null;		
		boolean ok=false;
		for(int i=0;i<turns.size()&&ok==false;i++) {
			if(turns.get(i).isAttented()==false&&turns.get(i).isNotHere()==false) {
				turnActual=turns.get(i);
				ok=true;
			}
		}
		return turnActual;
	}
	
	/**
	 * This method is in charge of generate a new turn.
	 * @return the turn generated.
	 */
	public String generateTurn() {
		Turn turn=new Turn("");
		char[] letters={ 'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
		if(turns.isEmpty()) {
			turn.setTurn("A00");
			turns.add(turn);
		}else {
				char c1;
				String c2,c3;
				c1=turns.get(turns.size()-1).getTurn().charAt(0);	c2=turns.get(turns.size()-1).getTurn().charAt(1)+"";	c3=turns.get(turns.size()-1).getTurn().charAt(2)+"";				
				int c22,c33;				
				c22=Integer.parseInt(c2); c33=Integer.parseInt(c3);				
				if(c33==9) {
					c33=0;					
					if(c22==9) {
						c22=0; 			
						boolean nextLetter=false;
						for(int j=0;j<letters.length;j++) {							
							if(letters[j]==c1&&c1!=letters[letters.length-1]&&nextLetter==false) {
								c1=letters[j+1];
								nextLetter=true;								
							}else if(c1=='Z'){
								c1=letters[0];
							}
						}						
					}else {
						c22=c22+1;
					}
				}else {
					c33=c33+1;
				}
				String t1,t2,t3;
				t1=c1+""; t2=c22+""; t3=c33+"";
				turn.setTurn(t1+t2+t3);
		}		
		return turn.getTurn();
	}
	
	
	//SECUENCIAL SEARCH
	/**
	 * This method is in charge of get the index in the arrayList "users" of a user that have the same user ID than the one enters by parameter.
	 * @param id The user ID that we want to know what the index is within the array.
	 * @return The possition where is the user within the arrayList.
	 */
	public int getIndexUser(int id) {
		int poss=0;		
		for(int i=0;i<users.size();i++) {
			if(id==users.get(i).getId()) {
				poss=i;
			}			
		}		
		return poss;
	}
		
/**
 * 
 * @param id
 * @param ty
 * @param screen
 * @throws IsNotRegisteredException
 * @throws UserAlreadyHaveTurnException
 */
	public void assingUserTurn(int id, TypeTurn ty, boolean screen) throws IsNotRegisteredException, UserAlreadyHaveTurnException {
		Collections.sort(users, new CompareByID());
		boolean screen1=screen;
		
		boolean registered=false;
		registered=searchUser(id);
		if(registered==true) {
			int poss=getIndexUser(id);
			User u=users.get(poss);
			if(u.isSuspended()==false) {
			
			if(u.getTurn()==null) {
				
			
				if(screen1==true) {
			System.out.println("User Dates");
			System.out.println("|Name  |"+users.get(poss).getName()+" "+users.get(poss).getLastName());
			System.out.println("|ID    |"+users.get(poss).getId());
				}
			
			Turn turn=new Turn(generateTurn());

			turn.setUser(users.get(poss));
			turn.setType(ty);			
			
			LocalDateTime dt=LocalDateTime.now();
			fecha.refres(dt);
			
			turn.setRequested(fecha);
			turns.add(turn);
			users.get(poss).setTurn(turn);
			users.get(poss).getTurnSolicited().add(turn);
			
			if(screen1==true) {
			System.out.println("The turn assigned to "+users.get(poss).getName()+" is |"+turn.getTurn()+"| Type "+turn.getType().getName()+" requested at: "+turn.getRequested().showHour());
			}
			
			}else if(users.get(poss).getTurn().isTouch()==false){
				throw new UserAlreadyHaveTurnException(users.get(poss).getName(),users.get(poss).getTurn().getTurn());

			}	
		}else{
			System.out.println("The user: "+u.getName()+" is suspended, the suspention will finish at:" );
		}
		}else {
			throw new IsNotRegisteredException();			
		}	
	}
	
	/**
	 * This method is in charge of add an user to the Data Base of users. 
	 * @param user Is the user that will be add to the data base.
	 */
	public boolean addUser(User user) {
		boolean added=false;
		boolean found=searchUser(user.getId());
		if(found==false) {
		users.add(user);
		added=true;
	}else {
		System.out.println(user.getName()+" Is already registered");
	}	
		return added;
	}
	
	/**
	 * This method is responsible for finding a user ID and then say if it is found or not
	 * @param id The user ID that we want search. 
	 * @return boolean that say if it is found or not.  
	 */
	public boolean searchUser1(int id) {

		
		boolean found=false;		
		for(int i=0;i<users.size();i++) {
			if(id==users.get(i).getId()) {				
				found=true;
			}			
		}		
		return found;
	}
	
	
	//BINARY SEARCH
	public boolean searchUser(int id) {	
		boolean found=false;		
		Collections.sort(users,new CompareByID());
		
		int inicio=0;		
		int fin=users.size()-1;				
		while(inicio<=fin&&!found) {
			int medio=(inicio+fin)/2;
			if(users.get(medio).getId()==id) {
				found=true;				
			}else if(users.get(medio).getId()>id) {
				fin=medio-1;				
			}else {
				inicio=medio+1;
			}
		}	

		return found;
	}	


	public ArrayList<User> getUsers() {
		return users;
	}


	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}

	public ArrayList<Turn> getTurns() {
		return turns;
	}

	public void setTurns(ArrayList<Turn> turns) {
		this.turns = turns;
	}

	public ArrayList<TypeTurn> getTypeTurns() {
		return typeTurns;
	}

	public void setTypeTurns(ArrayList<TypeTurn> typeTurns) {
		this.typeTurns = typeTurns;
	}


	public FechaYHora getFecha() {
		return fecha;
	}


	public void setFecha(FechaYHora fecha) {
		this.fecha = fecha;
	}




	
	
	

}
