package com.beans;

import java.util.List;

public class BugDetails {

	private List<Bug> bugDetails;
	private AddBugInfo addBugInfo;
	
	public List<Bug> getBugDetails() {
		return bugDetails;
	}
	public void setBugDetails(List<Bug> bugDetails) {
		this.bugDetails = bugDetails;
	}
	public AddBugInfo getAddBugInfo() {
		return addBugInfo;
	}
	public void setAddBugInfo(AddBugInfo addBugInfo) {
		this.addBugInfo = addBugInfo;
	}

}
