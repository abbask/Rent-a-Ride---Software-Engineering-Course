package edu.uga.cs.rentaride.logic.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.HourlyPrice;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.entity.VehicleStatus;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class CreateReservation_CTRL {
	
	private ObjectLayer objectLayer = null;

	public CreateReservation_CTRL( ObjectLayer objectModel )
	{
		this.objectLayer = objectModel;
	}
	
	public List<Vehicle> restoreVehicleVehicleTypeRentalLocation(long rentalLocationId, long vehicleTypeId  ) throws RARException{
       
      
        VehicleType              vehicleType = null;
        VehicleType              modelVehicleType = null;
        Iterator<VehicleType>    vehicleTypeIter = null;
        
        RentalLocation           rentalLocation = null;
        RentalLocation           modelRentalLocation = null;
        Iterator<RentalLocation> rentalLocationIter = null;
        
        List<Vehicle> 				lstVehicle = null;
        List<Vehicle>				lstVehicle2 = null;
        List<Vehicle>				returnLstVehicle = null;
        
        Vehicle					modelVehicle = null;
        Iterator<Vehicle>		vehicleIter = null;             
        
        //Retrieve RentalLocation
        modelRentalLocation = objectLayer.createRentalLocation();
        modelRentalLocation.setId(rentalLocationId);
        rentalLocationIter = objectLayer.findRentalLocation(modelRentalLocation);
        while (rentalLocationIter.hasNext()){
        	rentalLocation = rentalLocationIter.next();
        }
        
      //Retrieve vehicleType
        modelVehicleType = objectLayer.createVehicleType();
        modelVehicleType.setId(vehicleTypeId);
        vehicleTypeIter = objectLayer.findVehicleType(modelVehicleType);
        while (vehicleTypeIter.hasNext()){
        	vehicleType = vehicleTypeIter.next();
        }
        
        
        lstVehicle = new LinkedList<Vehicle>();
        
        vehicleIter = objectLayer.restoreVehicleRentalLocation(rentalLocation);
        while (vehicleIter.hasNext()){
        	Vehicle vehicle = vehicleIter.next();
        	lstVehicle.add(vehicle);
        }
        
        
        lstVehicle2 = new LinkedList<Vehicle>();
        
        vehicleIter = objectLayer.restoreVehicleVehicleType(vehicleType);  
        while (vehicleIter.hasNext()){
        	Vehicle vehicle2 = vehicleIter.next();
        	lstVehicle2.add(vehicle2);
        }
        
        returnLstVehicle = new LinkedList<Vehicle>();
        
        System.out.println(lstVehicle.size());
        System.out.println(lstVehicle2.size());
        
        for (Vehicle v1: lstVehicle){
        	for(Vehicle v2: lstVehicle2){
        		System.out.println(v1.getId());
        		System.out.println(v2.getId());
        		if (v1.getId() == v2.getId() && v1.getStatus() == VehicleStatus.INLOCATION){
        			returnLstVehicle.add(v1);
        		}
        		
        	}        	       
        }
        
        if (returnLstVehicle.size() == 0)
        	 throw new RARException( "There is no vehicle Type available in this location!");
        
        return returnLstVehicle;

        
	}
	
	public List<HourlyPrice> restoreVehicleTypeHourlyPrice( long vehicleTypeId ) throws RARException{
		
		VehicleType              vehicleType = null;
        VehicleType              modelVehicleType = null;
        Iterator<VehicleType>    vehicleTypeIter = null;
               
        
        Iterator<HourlyPrice>    hourlyPriceIter = null;
        List<HourlyPrice> 		 lstHourlyPrice = null;
        
        //Retrieve RentalLocation
        modelVehicleType = objectLayer.createVehicleType();
        modelVehicleType.setId(vehicleTypeId);
        vehicleTypeIter = objectLayer.findVehicleType(modelVehicleType);
        while (vehicleTypeIter.hasNext()){
        	vehicleType = vehicleTypeIter.next();
        }              
        
        lstHourlyPrice = new LinkedList<HourlyPrice>();
        
        hourlyPriceIter = objectLayer.restoreVehicleTypeHourlyPrice(modelVehicleType);
        while (hourlyPriceIter.hasNext()){
        	HourlyPrice h = hourlyPriceIter.next();
        	lstHourlyPrice.add(h);
        }        
                
        if (lstHourlyPrice.size() == 0)
        	 throw new RARException( "There is no vehicle Type available in this location!");
        
        return lstHourlyPrice;		
		
	}
	
	
	
	public long createReservation( long vehicleTypeId, long rentalLocationId, long customerId,
			Date pickupTime, int rentalDuration ) throws RARException{
		
		VehicleType				 vehicleType = null;
		VehicleType              modelVehicleType = null;
        Iterator<VehicleType>    vehicleTypeIter = null;
        
        RentalLocation		     rentalLocation = null;
        RentalLocation           modelRentalLocation = null;
        Iterator<RentalLocation>    rentalLocationIter = null;
        
        Customer				 customer = null;
        Customer              modelCustomer = null;
        Iterator<Customer>    customerIter = null;
		
		Reservation				 reservation = null;
		Reservation              modelReservation = null;
        Iterator<Reservation>    reservationIter = null;
               

        modelVehicleType = objectLayer.createVehicleType();
        modelVehicleType.setId(vehicleTypeId);
        	
        vehicleTypeIter = objectLayer.findVehicleType(modelVehicleType);
        if( vehicleTypeIter.hasNext() )
            vehicleType = vehicleTypeIter.next();
        

        modelRentalLocation = objectLayer.createRentalLocation();
        modelRentalLocation.setId(rentalLocationId);
        	
        rentalLocationIter = objectLayer.findRentalLocation(modelRentalLocation);
        if( rentalLocationIter.hasNext() )
            rentalLocation = rentalLocationIter.next();
        
        modelCustomer = objectLayer.createCustomer();
        modelCustomer.setId(customerId);
        	
        customerIter = objectLayer.findCustomer(modelCustomer);
        if( customerIter.hasNext() )
            customer = customerIter.next();        

        // create the Reservation
        
        modelReservation = objectLayer.createReservation();
        
        modelReservation.setRentalDuration(rentalDuration);
        modelReservation.setPickupTime(pickupTime);
        modelReservation.setVehicleType(modelVehicleType);
        modelReservation.setCustomer(modelCustomer);
        modelReservation.setRentalLocation(modelRentalLocation);
        
        objectLayer.storeReservation(modelReservation);
       
        return modelReservation.getId();
	}
	

}
