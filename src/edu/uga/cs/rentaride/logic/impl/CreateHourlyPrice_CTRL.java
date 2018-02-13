package edu.uga.cs.rentaride.logic.impl;

import java.util.Iterator;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.HourlyPrice;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class CreateHourlyPrice_CTRL {

	private ObjectLayer objectLayer = null;

	public CreateHourlyPrice_CTRL( ObjectLayer objectModel )
	{
		this.objectLayer = objectModel;
	}
	
	public long createHourlyPrice( int minHours, int maxHours, int price, long vehicelTypeId  ) throws RARException{
        HourlyPrice				 hourlyPrice = null;
		HourlyPrice              modelHourlyPrice = null;
        Iterator<HourlyPrice>    hourlyPriceIter = null;
        VehicleType              vehicleType = null;
        VehicleType              modelVehicleType = null;
        Iterator<VehicleType>    vehicleTypeIter = null;

        // check if a hourly price with this information already exists
        modelHourlyPrice = objectLayer.createHourlyPrice();
        modelHourlyPrice.setMinHours(minHours);
        modelHourlyPrice.setMaxHours(maxHours);
        modelHourlyPrice.setPrice(price);
        
        hourlyPriceIter = objectLayer.findHourlyPrice(modelHourlyPrice);
        if( hourlyPriceIter.hasNext() )
            throw new RARException( "A Hourly Price with this definition already exists: ");

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
        
        hourlyPrice = objectLayer.createHourlyPrice(minHours, maxHours, price, vehicleType);
        objectLayer.storeHourlyPrice(hourlyPrice);
        
     

        return hourlyPrice.getId();
	}
	
}
