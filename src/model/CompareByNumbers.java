package model;

import java.util.Comparator;

public class CompareByNumbers implements Comparator<Turn>{

	@Override
	public int compare(Turn u1, Turn u2) {
		if(Integer.compare(getNumbers(u1.getTurn()), getNumbers(u2.getTurn()))<0)
			return -1; 
		else {
			if(Integer.compare(getNumbers(u1.getTurn()), getNumbers(u2.getTurn()))>0)
				return 1;
			else
				return 0; 
		}
	}

	public int getNumbers(String t) {
		int r=Integer.parseInt(t.charAt(1)+""+t.charAt(2));	
		return r;
	}

}
