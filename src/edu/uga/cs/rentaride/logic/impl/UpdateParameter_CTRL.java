package edu.uga.cs.rentaride.logic.impl;

import java.util.Iterator;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.HourlyPrice;
import edu.uga.cs.rentaride.entity.RentARideConfig;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class UpdateParameter_CTRL {
	
	private ObjectLayer objectLayer = null;

	public UpdateParameter_CTRL( ObjectLayer objectModel )
	{
		this.objectLayer = objectModel;
	}

	public void updateParameter(int membershipPrice, int overtimePenalty) throws RARException{
		
		RentARideConfig parameters = null;
		
		parameters = objectLayer.findRentARideCfg();
		
		parameters.setMembershipPrice(membershipPrice);
		parameters.setOvertimePenalty(overtimePenalty);
				
		objectLayer.storeRentARideCfg(parameters);
		
	}

}
