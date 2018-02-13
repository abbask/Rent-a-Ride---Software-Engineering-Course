package edu.uga.cs.rentaride.entity.impl;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.HourlyPrice;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.persistence.impl.Persistent;

public class HourlyPriceImpl extends Persistent implements HourlyPrice   {

	private int minHours;
	private int maxHours;
	private int price;
	private VehicleType vehicleType;
	
	public HourlyPriceImpl() {
	}

	public HourlyPriceImpl(int minHours, int maxHours, int price, VehicleType vehicleType) {
		this.minHours = minHours;
		this.maxHours = maxHours;
		this.price = price;
		this.vehicleType = vehicleType;
	}


	public int getMinHours() {
		return minHours;
	}
	
	public void setMinHours(int minHours) throws RARException {
		this.minHours = minHours;
	}
	
	public int getMaxHours() {
		return maxHours;
	}
	
	public void setMaxHours(int maxHours) throws RARException {
		this.maxHours = maxHours;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) throws RARException {
		this.price = price;
	}
	
	public VehicleType getVehicleType() {
		return vehicleType;
	}


	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String toString() {
		return "Minimum Hours: " + getMinHours() + " Maximum Hours: " + getMaxHours() + " Price: " + getPrice() + " for VhicleType" + getVehicleType().toString();
	}

	public boolean equals(Object otherHourlyPrice) {
		if (otherHourlyPrice == null) {
			return false;
		}
		if (otherHourlyPrice instanceof HourlyPrice) // 
		{
			if (getMinHours()==((HourlyPrice) otherHourlyPrice).getMinHours() 
					&& getMaxHours()==((HourlyPrice) otherHourlyPrice).getMaxHours()
					&& getPrice()==((HourlyPrice) otherHourlyPrice).getPrice()){
				return true;
			}
			
		}
		return false;
	}


}
