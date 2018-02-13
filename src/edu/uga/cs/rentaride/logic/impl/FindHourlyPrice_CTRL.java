package edu.uga.cs.rentaride.logic.impl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.arjuna.ats.internal.jdbc.drivers.modifiers.list;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.HourlyPrice;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class FindHourlyPrice_CTRL {

	private ObjectLayer objectLayer = null;

	public FindHourlyPrice_CTRL( ObjectLayer objectModel )
	{
		this.objectLayer = objectModel;
	}


	public List<HourlyPrice> findHourlyPrice(long hourlyPriceId, int minHours, int maxHours, int price, long vehicleTypeId) throws RARException{

		//		HourlyPrice				 hourlyPrice = null;
		HourlyPrice              modelHourlyPrice = null;
		Iterator<HourlyPrice>    hourlyPriceIter = null;
		List<HourlyPrice> 		 lstHourlyPrice = null;
		
		VehicleType				 vehicleType = null;
		VehicleType              modelVehicleType = null;
		Iterator<VehicleType>    vehicleTypeIter = null;

		// check if a hourly price with this information already exists
		modelHourlyPrice = objectLayer.createHourlyPrice();

		lstHourlyPrice = new LinkedList<HourlyPrice>();
		if (hourlyPriceId != 0){
			modelHourlyPrice.setId(hourlyPriceId);
			
			hourlyPriceIter = objectLayer.findHourlyPrice(modelHourlyPrice);
			while( hourlyPriceIter.hasNext() ){
				HourlyPrice hourlyPrice = hourlyPriceIter.next();
				lstHourlyPrice.add(hourlyPrice);        	
			} 
		}
		else{
			modelVehicleType = objectLayer.createVehicleType();
			if (vehicleTypeId != 0){
				
				modelVehicleType.setId(vehicleTypeId);
				vehicleTypeIter = objectLayer.findVehicleType(modelVehicleType);
				
				if (vehicleTypeIter.hasNext())
					vehicleType = vehicleTypeIter.next();
			}
			
			modelHourlyPrice.setMaxHours(maxHours);
			modelHourlyPrice.setMinHours(minHours);
			modelHourlyPrice.setPrice(price);
			modelHourlyPrice.setVehicleType(vehicleType);

			hourlyPriceIter = objectLayer.findHourlyPrice(modelHourlyPrice);
			while( hourlyPriceIter.hasNext() ){
				HourlyPrice hourlyPrice = hourlyPriceIter.next();
				lstHourlyPrice.add(hourlyPrice);        	
			}        

		}

		return lstHourlyPrice;
	}

}
