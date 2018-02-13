package edu.uga.cs.rentaride.persistence.impl;

import java.sql.ResultSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class VehicleTypeIterator implements Iterator<VehicleType> {
	
	private ResultSet    rs = null;
    private ObjectLayer  objectLayer = null;
    private boolean      more = false;
    
    public VehicleTypeIterator(ResultSet rs, ObjectLayer objectModel) throws RARException {
    	this.rs = rs;
        this.objectLayer = objectModel;
        try {
            more = rs.next();
        }
        catch( Exception e ) {  // just in case...
            throw new RARException( "VehicleTypeIterator: Cannot create VehicleType iterator; root cause: " + e );
        }
	}
    
    public boolean hasNext() 
    { 
        return more; 
    }
    
    public VehicleType next() {
    	
    	VehicleType vehicleType=null;
        String Type;
        Long Id;

        if( more ) {

            try {

                Id = rs.getLong( 1 );
                Type = rs.getString( 2 );
                
                more = rs.next();
            }
            catch( Exception e ) {      // just in case...
                throw new NoSuchElementException( "VehicleTypeIterator: No next VehicleType object; root cause: " + e );
            }
           
            
            vehicleType = objectLayer.createVehicleType(Type);        	              
			vehicleType.setId( Id );
			//club.setFounderId( founderId );
            
            return vehicleType;
        }
        else {
            throw new NoSuchElementException( "VehicleTypeIterator: No next VehicleType object" );
        }
    	
    }
    
    
    public void remove()
    {
        throw new UnsupportedOperationException();
    }
}
