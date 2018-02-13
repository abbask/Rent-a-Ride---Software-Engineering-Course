package edu.uga.cs.rentaride.entity.impl;

import java.util.Date;


import edu.uga.cs.rentaride.entity.User;
import edu.uga.cs.rentaride.entity.UserStatus;
import edu.uga.cs.rentaride.persistence.impl.Persistent;

public class UserImpl extends Persistent implements User{
	private String firstName;
	private String lastName;
	private String userName;
	private String emailAddress;
	private String password;
	private Date createdDate;
	private String residenceAddress;
	private UserStatus userStatus;
	
	public UserImpl() {

	}
	
	public UserImpl(String firstName, String lastName, String userName, String emailAddress, 
			String password, Date createdDate, String residenceAddress, UserStatus userStatus) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.emailAddress = emailAddress;
		this.password = password;
		this.createdDate = createdDate;
		this.residenceAddress  = residenceAddress;
		this.userStatus = userStatus;		
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreateDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getResidenceAddress() {
		return residenceAddress;
	}

	public void setResidenceAddress(String residenceAddress) {
		this.residenceAddress = residenceAddress;
	}

	public UserStatus getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}
	
	//String firstName, String lastName, String userName, String emailAddress, 
	//String password, Date createdDate, String residenceAddress, UserStatus userStatus
	public String toString() {
		return "First Name: " + getFirstName() + " Last Name: " + getLastName() + " UserName: " + 
	getUserName() + " Email Address: " + getEmailAddress() + " Password: " + getPassword() +
	" Created Date: " + getCreatedDate() + " Residential Address:" + getResidenceAddress() + 
	" User Status:" + getUserStatus();
	}

	public boolean equals(Object otherUser) {
		if (otherUser == null) {
			return false;
		}
		if (otherUser instanceof User) // 
		{
			if (getUserName() == ((User)otherUser).getUserName()){
				return true;
			}
				
		}
		return false;
	}
	
	
}
