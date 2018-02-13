package edu.uga.cs.rentaride.entity.impl;

import java.util.Date;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.entity.VehicleCondition;
import edu.uga.cs.rentaride.entity.VehicleStatus;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.persistence.impl.Persistent;

public class VehicleImpl extends Persistent implements Vehicle {

	
	private String make;
	private String model;
	private int year;
	private String registrationTag;
	private int mileage;
	private Date lastServiced;
	private VehicleStatus vehicleStatus;
	private VehicleCondition vehicleCondition;
	private RentalLocation rentalLocation;
	private VehicleType vehicleType;

	public VehicleImpl() {

	}
	
	public VehicleImpl(String make,String model,int year,String registrationTag,int mileage,
			Date lastServiced,VehicleStatus vehicleStatus,VehicleCondition vehicleCondition,
			VehicleType vehicleType,RentalLocation rentalLocation)
	{
		 this.make=make;
		 this.model=model;
		 this.year=year;
		 this.registrationTag=registrationTag;
		 this.mileage=mileage;
		 this.lastServiced=lastServiced;
		 this.vehicleStatus=vehicleStatus;
		 this.vehicleCondition=vehicleCondition;
		 this.vehicleType=vehicleType;
		 this.rentalLocation=rentalLocation;
		 
		
	}
	
	public String getMake() {
		return this.make;
	}

	
	public void setMake(String make) {

		this.make=make;
		
	}

	
	public String getModel() {

		return this.model;
	}

	
	public void setModel(String model) {

		this.model=model;
		
	}

	
	public int getYear() {

		
		return this.year;
	}

	
	public void setYear(int year) throws RARException {

		this.year=year;
		
	}

	
	public String getRegistrationTag() {

		
		return registrationTag;
	}

	
	public void setRegistrationTag(String registrationTag) {

		this.registrationTag=registrationTag;
		
	}

	
	public int getMileage() {

		return this.mileage;
	}

	
	public void setMileage(int mileage) {

		this.mileage=mileage;
		
		
	}

	
	public Date getLastServiced() {

		
		return this.lastServiced;
	}

	
	public void setLastServiced(Date lastServiced) {

		this.lastServiced=lastServiced;
		
	}

	
	public VehicleStatus getStatus() {

		return this.vehicleStatus;
	}

	
	public void setStatus(VehicleStatus vehicleStatus) {

		this.vehicleStatus=vehicleStatus;
		
	}

	
	public VehicleCondition getCondition() {

		return this.vehicleCondition;
	}

	
	public void setCondition(VehicleCondition vehicleCondition) {

		this.vehicleCondition=vehicleCondition;
		
	}

	
	public VehicleType getVehicleType() {

		return this.vehicleType;
	}

	
	public void setVehicleType(VehicleType vehicleType) {

		this.vehicleType=vehicleType;
		
	}

	
	public RentalLocation getRentalLocation() {

		return this.rentalLocation;
	}

	
	public void setRentalLocation(RentalLocation rentalLocation) {
		this.rentalLocation=rentalLocation;		
	}
	
	

	public String toString() {
		return "Make: " + getMake() + " Model: " + getModel() + " Year: " + getYear() + " RegisterationTag: " + getRegistrationTag()+ " Status: " +getStatus().toString()+" Condition: "+ getCondition().toString() + " RentalLocation: "+ getRentalLocation().toString();
	}

	public boolean equals(Object vehicle) {
		if (vehicle == null) {
			return false;
		}
		if (vehicle instanceof Vehicle) // 
		{
			if (getMake()==((Vehicle) vehicle).getMake() 
					&& getModel()==((Vehicle) vehicle).getModel()
					&& getRegistrationTag()==((Vehicle) vehicle).getRegistrationTag()
			    	&& getMileage()==((Vehicle) vehicle).getMileage()		
	    			&& getLastServiced()==((Vehicle) vehicle).getLastServiced()					
	    			&& getCondition()==((Vehicle) vehicle).getCondition()				
	    			&& getStatus()==((Vehicle) vehicle).getStatus()
					&& getYear()==((Vehicle) vehicle).getYear()){
				return true;
			}
			
		}
		return false;
	}



	


	

}
