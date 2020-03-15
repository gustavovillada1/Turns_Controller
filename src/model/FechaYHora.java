package model;
import java.io.Serializable;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
public class FechaYHora implements Serializable{
	
	
	private LocalDateTime currentDate;
	private LocalDateTime lastActualDate;
	private boolean isManual;
			
	public FechaYHora() {

		currentDate=LocalDateTime.now();
		currentDate = LocalDateTime.of(currentDate.getYear(), currentDate.getMonthValue(), currentDate.getDayOfMonth(), currentDate.getHour(), currentDate.getMinute(), currentDate.getSecond(), 0);

		this.isManual=false;
		
	}
	
	public FechaYHora(boolean manual,LocalDateTime c) {

		currentDate=c;
		lastActualDate=LocalDateTime.now();

		this.isManual=manual;
		
	}
	
	
	public void refres(LocalDateTime dt) {
		LocalDateTime now=LocalDateTime.now();
		
		if(isManual==true) {
			Duration d=Duration.between(lastActualDate, now);
			
			long seconds = d.getSeconds();
			
			currentDate=currentDate.plusSeconds(seconds);
		}else {
			currentDate=LocalDateTime.now();
		}
		lastActualDate=dt;

	}
	
	
	
	public String showDate() {
		String date;
		
		String year=currentDate.getYear()+"";
		String month=currentDate.getMonth()+"";
		String day=currentDate.getDayOfMonth()+"";
		date=day+"/"+month+"/"+year;	
		
		return date;
	}
	
	public String showHour() {
		String h;
		
		String seconds=currentDate.getSecond()+"";
		String minutes=currentDate.getMinute()+"";
		String hour=currentDate.getHour()+"";
		
		String amPM;
		if(currentDate.getHour()>12) {
			amPM="PM";
		}else {
			amPM="AM";
		}
			
		if(seconds.length()==1) {
			seconds=0+seconds;
		}
		if(minutes.length()==1) {
			minutes=0+minutes;
		}
		if(hour.length()==1) {
			hour=0+hour;
		}
		
		h=hour+":"+minutes+":"+seconds+" "+amPM;
		
		return h;
	}
	
	public boolean getIsManual() {
		
		return isManual;
	}

	public LocalDateTime getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(LocalDateTime currentDate) {
		this.currentDate = currentDate;
	}
	
	
	
}
