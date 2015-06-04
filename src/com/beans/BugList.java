package com.beans;
import java.util.ArrayList;
import java.util.List;

public class BugList {

	public BugList() {
		// TODO Auto-generated constructor stub
	}
	
	List<Bug> bugList = new ArrayList<Bug>();
	
	public void mockInput(){
		
		
		Bug b1 = new Bug("1234","Naveen","Project Newton","Production Issue","Medium","John Baartz","Testing","comments");
		this.bugList.add(b1);
		
		b1 = new Bug("1345","Test 2","Project Motion","Incomplete Requirements","Critical","John Doe","New","Need ASAP.");
		this.bugList.add(b1);
		
		b1 = new Bug("1451","Test 3","Project Alpha","Production Issue","Medium","Michael Boltz","Testing","Need immediate action.");
		this.bugList.add(b1);
		
		b1 = new Bug("1898","Test 4","Project Alpha","Internal Issues","Low","Wendy Kim","Testing","WIll be pushed to DEV.");
		this.bugList.add(b1);
		
		b1 = new Bug("1678","Test 5","Project Motion","Design Issue","High","John Doe","New","This is a Dev Blocker.");
		this.bugList.add(b1);
		
		b1 = new Bug();
		this.bugList.add(b1);
		
	}

}
