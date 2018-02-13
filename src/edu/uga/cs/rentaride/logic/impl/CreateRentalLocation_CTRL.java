package edu.uga.cs.rentaride.logic.impl;

import java.util.Date;
import java.util.Iterator;

import java.util.Iterator;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.HourlyPrice;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.object.ObjectLayer;


public class CreateRentalLocation_CTRL {
    
    private ObjectLayer objectLayer = null;
    
    public CreateRentalLocation_CTRL( ObjectLayer objectModel )
    {
        this.objectLayer = objectModel;
    }
    
    public RentalLocation createRentalLocation( String RentalLocation_name, String RentalLocation_addr, int capacity )
            throws RARException
    { 
        RentalLocation 		    rentallocation  = null;
        RentalLocation                modelClub = null;
        Iterator<RentalLocation>      clubIter = null;


        // check if a club with this name already exists (name is unique)
        modelClub = objectLayer.createRentalLocation();
        modelClub.setName( RentalLocation_name );
        clubIter = objectLayer.findRentalLocation( modelClub );
        if( clubIter.hasNext() )
            throw new RARException( "A club with this name already exists: " + RentalLocation_name );


        rentallocation = objectLayer.createRentalLocation( RentalLocation_name, RentalLocation_addr, capacity );
        objectLayer.storeRentalLocation( rentallocation );

        return rentallocation;
    }
}

