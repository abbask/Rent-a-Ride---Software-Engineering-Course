package edu.uga.cs.rentaride.entity.impl;


import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.persistence.impl.Persistent;

public class RentalLocaitonImpl extends Persistent implements RentalLocation{
	String name;
	String address;
	int capacity;	
	
	public RentalLocaitonImpl(){
		
	}
	
	public RentalLocaitonImpl(String name, String address, int capacity) {
		this.name = name;
		this.address = address;
		this.capacity = capacity;
	}

	
	public String getName() {
																		
		return name;
	}

	
	public void setName(String name) {
																		
		this.name=name;
	}

	
	public String getAddress() {
																		
		return address;
	}

	
	public void setAddress(String address) {
																		
		this.address=address;
	}

	
	public int getCapacity() {
																		
		return capacity;
	}

	
	public void setCapacity(int capacity) {

		this.capacity=capacity;
	}

    public String toString()
    {
        return "RentalLocation [" + getId() + "] "+" name: " + getName() 
        + " address: "+getAddress() + " capacity: " + getCapacity();
    }
    
    public boolean equals( Object otherRentalLocation)
    {
        if( otherRentalLocation == null )
            return false;
        if( otherRentalLocation instanceof RentalLocation ) // name is a unique attribute
            if ( getName() == ((RentalLocation)otherRentalLocation).getName() 
            		&& getAddress() == ((RentalLocation)otherRentalLocation).getAddress() 
            		)
            	return true;
        
        return false;        
    }	
	
}
