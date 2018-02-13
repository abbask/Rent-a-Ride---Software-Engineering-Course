package edu.uga.cs.rentaride.logic.impl;

import java.util.Iterator;

import org.hibernate.loader.collection.OneToManyJoinWalker;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.HourlyPrice;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class DeleteVehicleType_CTRL {
	
	private ObjectLayer objectLayer = null;

	public DeleteVehicleType_CTRL( ObjectLayer objectModel )
	{
		this.objectLayer = objectModel;
	}
	
	public void deleteVehicleType(Long id) throws RARException{
		VehicleType              modelVehicleType = null;
        Iterator<VehicleType>    vehicleTypeIter = null;
        
        HourlyPrice                      modelHourlyPrice = null;
        Iterator<HourlyPrice>            hourlyPriceIter = null;
        
        Vehicle                      	 modelVehicle = null;
        Iterator<Vehicle>            	 vehicleIter = null;

        Reservation                      	 modelReservation = null;
        Iterator<Reservation>            	 reservationIter = null;
        
        modelVehicleType = objectLayer.createVehicleType();
        modelVehicleType.setId(id);
        
        
        //hourly Price
        modelHourlyPrice = objectLayer.createHourlyPrice();
        modelHourlyPrice.setVehicleType(modelVehicleType);
        hourlyPriceIter = objectLayer.findHourlyPrice(modelHourlyPrice);
        if (hourlyPriceIter.hasNext())
        	throw new RARException( "Cannot Delete Vehicle Type becuase, Vehicle Type has been used for hourly price. " + id );
        
        //Vehicle
        modelVehicle = objectLayer.createVehicle();
        modelVehicle.setVehicleType(modelVehicleType);
        vehicleIter = objectLayer.findVehicle(modelVehicle);
        if (vehicleIter.hasNext())
        	throw new RARException( "Cannot Delete Vehicle Type becuase,Vehicle Type has been used for one or more Vehicles. " + id );
        
        
        //Reservation
        modelReservation = objectLayer.createReservation();
        modelReservation.setVehicleType(modelVehicleType);        
        reservationIter = objectLayer.findReservation(modelReservation);
        if (reservationIter.hasNext())
        	throw new RARException( "Cannot Delete Vehicle Type becuase,Vehicle Type has been used for one or more Reservations. " + id );
        
        
       
        
        vehicleTypeIter = objectLayer.findVehicleType(modelVehicleType);
        if( ! vehicleTypeIter.hasNext() )
            throw new RARException( "No such a VehicleType exists id=" + id );
        
        objectLayer.deleteVehicleType(modelVehicleType);                               

	}

}
