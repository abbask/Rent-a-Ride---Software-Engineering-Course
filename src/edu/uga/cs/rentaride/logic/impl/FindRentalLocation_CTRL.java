package edu.uga.cs.rentaride.logic.impl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.HourlyPrice;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class FindRentalLocation_CTRL {
	
	private ObjectLayer objectLayer = null;

	public FindRentalLocation_CTRL( ObjectLayer objectModel )
	{
		this.objectLayer = objectModel;
	}
	
public List<RentalLocation> findRentalLocation( String name, String address,int capacity ) throws RARException{
		
		RentalLocation              modelRentalLocation = null;
        Iterator<RentalLocation>    rentalLocationIter = null;
        List<RentalLocation> 		 lstRentalLocation = null;

        modelRentalLocation = objectLayer.createRentalLocation(name, address, capacity);
        
        lstRentalLocation = new LinkedList<RentalLocation>();
        
        modelRentalLocation.setName(name);
        modelRentalLocation.setAddress(address);
        modelRentalLocation.setCapacity(capacity);
        
        
        rentalLocationIter = objectLayer.findRentalLocation(modelRentalLocation);
        while( rentalLocationIter.hasNext() ){
        	RentalLocation rentalLocation = rentalLocationIter.next();
        	lstRentalLocation.add(rentalLocation);       	
        }        

        return lstRentalLocation;
	}


}
