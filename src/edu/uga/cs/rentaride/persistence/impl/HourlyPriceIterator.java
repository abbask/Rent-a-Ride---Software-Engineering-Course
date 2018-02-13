package edu.uga.cs.rentaride.persistence.impl;

import java.sql.ResultSet;
import java.util.Iterator;
import java.util.NoSuchElementException;


import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.HourlyPrice;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class HourlyPriceIterator implements Iterator<HourlyPrice> {
	
	private ResultSet    rs = null;
    private ObjectLayer  objectLayer = null;
    private boolean      more = false;
    
    public HourlyPriceIterator(ResultSet rs, ObjectLayer objectModel) throws RARException {
    	this.rs = rs;
        this.objectLayer = objectModel;
        try {
            more = rs.next();
        }
        catch( Exception e ) {  // just in case...
            throw new RARException( "HourlyPriceIterator: Cannot create HourlyPrice iterator; root cause: " + e );
        }
	}

    public boolean hasNext() 
    { 
        return more; 
    }
    
    public HourlyPrice next() {
    	
    	HourlyPrice hourlyPrice=null;
    	long id;
    	int minHours;
    	int maxHours;
    	int price;
    	VehicleType vehicleType = null;
    	long vehicleTypeId;
    	String vehicleTypeName;

        if( more ) {

            try {
            	//h.Id, h.minHrs, h.MaxHrs, h.Price, h.VehicleTypeId, v.Name 
                id = rs.getLong( 1 );
                minHours = rs.getInt( 2 );
                maxHours = rs.getInt( 3 );
                price = rs.getInt( 4 );
                
                vehicleTypeId = rs.getLong( 5 );
                vehicleTypeName = rs.getString(6);

                more = rs.next();
            }
            catch( Exception e ) {      // just in case...
                throw new NoSuchElementException( "HourlyPriceIterator: No next HourlyPrice object; root cause: " + e );
            }
            vehicleType = objectLayer.createVehicleType(vehicleTypeName);
            vehicleType.setId(vehicleTypeId);
            
            try {
            	hourlyPrice = objectLayer.createHourlyPrice(minHours, maxHours, price, vehicleType);            	              
            	hourlyPrice.setId( id );
                //club.setFounderId( founderId );
            }
            catch( RARException ce ) {
                // safe to ignore: we explicitly set the persistent id of the founder Person object above!
            }
            
            return hourlyPrice;
        }
        else {
            throw new NoSuchElementException( "HourlyPriceIterator: No next HourlyPrice object" );
        }
    	
    }
    
    
    public void remove()
    {
        throw new UnsupportedOperationException();
    }
}
