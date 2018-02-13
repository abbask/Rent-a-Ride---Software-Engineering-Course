package edu.uga.cs.rentaride.logic.impl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.HourlyPrice;
import edu.uga.cs.rentaride.entity.RentARideConfig;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class FindParameters_CTRL {
	
	private ObjectLayer objectLayer = null;

	public FindParameters_CTRL( ObjectLayer objectModel )
	{
		this.objectLayer = objectModel;
	}
	
	public RentARideConfig findParameter() throws RARException{

		RentARideConfig parameters = null;
		parameters = objectLayer.findRentARideCfg();
		return parameters;		
	}


}
