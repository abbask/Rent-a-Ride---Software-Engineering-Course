package edu.uga.cs.rentaride.logic.impl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class FindVehicleType_CTRL {
	
	private ObjectLayer objectLayer = null;

	public FindVehicleType_CTRL( ObjectLayer objectModel )
	{
		this.objectLayer = objectModel;
	}
	
	public List<VehicleType> findVehicleType(long vehicleTypeId, String type)throws RARException{
		List<VehicleType> lstVehicleType = null;
		Iterator<VehicleType> 	vehicleTypeIter = null;
		VehicleType vehicleType = null;
		VehicleType modelVehicleType = null;
		
		modelVehicleType = objectLayer.createVehicleType();
		if (vehicleTypeId != 0){
			
			modelVehicleType.setId(vehicleTypeId);					
		}
		else{
			modelVehicleType.setType(type);
		}
		
		vehicleTypeIter = objectLayer.findVehicleType(modelVehicleType);
		
		lstVehicleType = new LinkedList<VehicleType>();
		while (vehicleTypeIter.hasNext()){
			vehicleType = vehicleTypeIter.next();
			lstVehicleType.add(vehicleType);
		}
		
		return lstVehicleType;
	}

}




	
	

