package edu.uga.cs.rentaride.entity.impl;

import edu.uga.cs.rentaride.entity.RentARideConfig;
import edu.uga.cs.rentaride.persistence.impl.Persistent;

public class RentARideConfigImpl extends Persistent implements RentARideConfig{

	private int membershipPrice;
	private int overtimePenalty;
	
	public RentARideConfigImpl() {
	
	}
	
	public RentARideConfigImpl(int membershipPrice,int overtimePenalty) {
		this.membershipPrice = membershipPrice;
		this.overtimePenalty = overtimePenalty;		
	}

	public int getMembershipPrice() {
		return membershipPrice;
	}

	public void setMembershipPrice(int membershipPrice) {
		this.membershipPrice = membershipPrice;
	}

	public int getOvertimePenalty() {
		return overtimePenalty;
	}

	public void setOvertimePenalty(int overtimePenalty) {
		this.overtimePenalty = overtimePenalty;
	}
	
	public String toString() {
		return "Membership Price: " + getMembershipPrice() + " Overtime Penalty: " + getOvertimePenalty();
	}

	
}
