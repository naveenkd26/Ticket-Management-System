package com.beans;

public class Bug {

	public Bug() {
		// TODO Auto-generated constructor stub
	}
	
	public String bugId;
	public String bugName;
	public String projectName;
	public String category;
	public String priority;
	public String teamMember;
	public String status;
	public String comments;
	
	public String getBugId() {
		return bugId;
	}

	public void setBugId(String bugId) {
		this.bugId = bugId;
	}

	public String getBugName() {
		return bugName;
	}

	public void setBugName(String bugName) {
		this.bugName = bugName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getTeamMember() {
		return teamMember;
	}

	public void setTeamMember(String teamMember) {
		this.teamMember = teamMember;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Bug(String bugId, String bugName, String projectName,
			String category, String priority, String teamMember, String status,
			String comments) {
		super();
		this.bugId = bugId;
		this.bugName = bugName;
		this.projectName = projectName;
		this.category = category;
		this.priority = priority;
		this.teamMember = teamMember;
		this.status = status;
		this.comments = comments;
	}
	
}
