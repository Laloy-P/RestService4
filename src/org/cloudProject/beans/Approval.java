package org.cloudProject.beans;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "approval")
public class Approval {
	
	private int id;
	private String response;
	private String description;
	private String name;
	
	public Approval() {
		
	}
	public Approval(int id, String response, String description, String name) {
		this.id = id;
		this.response = response;
		this.description = description;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public void setResponse(String response) {
		this.response = response;
	}
	public String getResponse() {
		return response;
	}
	
	
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
	
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
		
	@Override
	public String toString() {
		StringBuffer approval = new StringBuffer();
		approval.append("Approval n° ");
		approval.append(id);
		approval.append(" is ");
		if(getResponse().equals("accepted")) {
			approval.append("accepted");
		}else if (getResponse().equals("refused")) {
			approval.append("refused");
		}else {
			approval.append(" is on standby");
		}
		approval.append("\n");
		
		return approval.toString();
	}
	
	

}
