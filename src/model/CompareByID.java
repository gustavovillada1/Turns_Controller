package model;

import java.util.Comparator;

public class CompareByID implements Comparator<User>{

	@Override
	public int compare(User u1, User u2) {
		if(Integer.compare(u1.getId(), u2.getId())<0)
			return -1; 
		else {
			if(Integer.compare(u1.getId(), u2.getId())>0)
				return 1;
			else
				return 0; 
		}
	}


}
