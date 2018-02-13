package edu.uga.cs.rentaride.logic.impl;

import java.util.Iterator;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.HourlyPrice;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class DeleteHourlyPrice_CTRL {
	
	private ObjectLayer objectLayer = null;

	public DeleteHourlyPrice_CTRL( ObjectLayer objectModel )
	{
		this.objectLayer = objectModel;
	}
	
	public void deleteHourlyPrice(Long id) throws RARException{
		HourlyPrice              modelHourlyPrice = null;
        Iterator<HourlyPrice>    hourlyPriceIter = null;
        
        HourlyPrice hourlyPrice = null;
        
        VehicleType vehicleType = null;                     

        // check if a hourly price with this information already exists
        modelHourlyPrice = objectLayer.createHourlyPrice();
        modelHourlyPrice.setId(id);
        
        hourlyPriceIter = objectLayer.findHourlyPrice(modelHourlyPrice);
        if( ! hourlyPriceIter.hasNext() )
            throw new RARException( "No such a Hourly Price exists id=" + id );
        else
        	hourlyPrice = hourlyPriceIter.next();
               
        
//        vehicleType = objectLayer.restoreVehicleTypeHourlyPrice(hourlyPrice);
//        if (vehicleType != null)
//        	throw new RARException( "There is a vehicle assigned to this hourly price. id=" + id );
        
        objectLayer.deleteHourlyPrice(hourlyPrice);                               

	}

}
