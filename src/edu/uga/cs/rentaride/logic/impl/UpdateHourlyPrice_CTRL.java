package edu.uga.cs.rentaride.logic.impl;

import java.util.Iterator;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.HourlyPrice;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class UpdateHourlyPrice_CTRL {
	
	private ObjectLayer objectLayer = null;

	public UpdateHourlyPrice_CTRL( ObjectLayer objectModel )
	{
		this.objectLayer = objectModel;
	}
	
	public void updateHourlyPrice(long id, int minHours, int maxHours, int price, long vehicelTypeId  ) throws RARException{
		
		HourlyPrice				 hourlyPrice = null;
		HourlyPrice              modelHourlyPrice = null;
		Iterator<HourlyPrice>   hourlyPriceIter = null;
		
		VehicleType              vehicleType = null;
        VehicleType              modelVehicleType = null;
        Iterator<VehicleType>    vehicleTypeIter = null;
        
        // retrieve the vehicle Type
        modelVehicleType = objectLayer.createVehicleType();
        modelVehicleType.setId(vehicelTypeId);
        vehicleTypeIter = objectLayer.findVehicleType(modelVehicleType);
        while (vehicleTypeIter.hasNext()){
        	vehicleType = vehicleTypeIter.next();
        }

        // check if the vehicle Tpye actually exists
        if( vehicleType == null )
            throw new RARException( "A vehicle type with this id does not exist: " + vehicelTypeId );

        // create the hourly Price
        
        modelHourlyPrice = objectLayer.createHourlyPrice();
        modelHourlyPrice.setId(id);
        hourlyPriceIter = objectLayer.findHourlyPrice(modelHourlyPrice);
        
        if (hourlyPriceIter.hasNext()){
        	hourlyPrice = hourlyPriceIter.next();
        }
        
        hourlyPrice.setVehicleType(vehicleType);
        hourlyPrice.setMinHours(minHours);
        hourlyPrice.setMaxHours(maxHours);
        hourlyPrice.setPrice(price);
        
        objectLayer.storeHourlyPrice(hourlyPrice);
                  
		
	}

}
