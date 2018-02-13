package edu.uga.cs.rentaride.entity.impl;

import java.util.Date;

import edu.uga.cs.rentaride.entity.Comment;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.HourlyPrice;
import edu.uga.cs.rentaride.entity.Rental;
import edu.uga.cs.rentaride.persistence.impl.Persistent;

public class CommentImpl extends Persistent implements Comment {
	
	private String comment;
	private Date date;
	private Customer customer;
	private Rental rental;
	
	public CommentImpl() {
	
	}
	
	public CommentImpl(String comment, Date date, Customer customer, Rental rental) {
		
		this.comment = comment;
		this.date = date;
		this.customer = customer;
		this.rental = rental;				
		
	}

	public String getComment() {
		return comment;
	}





	public void setComment(String comment) {
		this.comment = comment;
	}





	public Date getDate() {
		return date;
	}



	public void setDate(Date date) {
		this.date = date;
	}



	public Customer getCustomer() {
		return customer;
	}



	public void setCustomer(Customer customer) {
		this.customer = customer;
	}



	public Rental getRental() {
		return rental;
	}



	public void setRental(Rental rental) {
		this.rental = rental;
	}



	public String toString() {
		return "Comment: " + getComment() + " Date: " + getDate() + " By User: " + getCustomer().getFirstName() + " " + getCustomer().toString() + " for Rental:" + getRental().toString();
	}

	public boolean equals(Object otherComment) {
		if (otherComment == null) {
			return false;
		}
		if (otherComment instanceof HourlyPrice) // 
		{
			if (getComment()==((Comment) otherComment).getComment()
					&& getDate()==((Comment) otherComment).getDate())
					{
				return true;
			}
			
		}
		return false;
	}
	
}

