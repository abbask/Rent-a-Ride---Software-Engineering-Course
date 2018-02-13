package edu.uga.cs.rentaride.entity.impl;



import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.persistence.impl.Persistent;

public class VehicleTypeImpl extends Persistent implements VehicleType{

	private String type;
	
	public VehicleTypeImpl() {
		// TODO Auto-generated constructor stub
	}
	
	public  VehicleTypeImpl(String  Type )
	{
		this.type=Type;
	}



	public String getType() {
		return this.type;
	}


	public void setType(String type) {
		this.type=type;
		
	}
	

	public String toString() {
		return "Type: " + getType() + "" ;
	}

	public boolean equals(Object vehicleType) {
		if (vehicleType == null) {
			return false;
		}
		if (vehicleType instanceof VehicleType) // 
		{
			if (getType()==((VehicleType) vehicleType).getType())
			
            {
				return true;
			}
			
		}
		return false;
	}


	

}
