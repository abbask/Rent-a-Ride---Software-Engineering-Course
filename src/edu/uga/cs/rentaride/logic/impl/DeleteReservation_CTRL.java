package edu.uga.cs.rentaride.logic.impl;

import java.util.Iterator;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.HourlyPrice;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class DeleteReservation_CTRL {
	
	private ObjectLayer objectLayer = null;

	public DeleteReservation_CTRL( ObjectLayer objectModel )
	{
		this.objectLayer = objectModel;
	}

	public void deleteReservation(Long reservationId) throws RARException{
		Reservation              modelReservation = null;
        Iterator<Reservation>    reservationIter = null;

        // check if a hourly price with this information already exists
        modelReservation = objectLayer.createReservation();
        modelReservation.setId(reservationId);
        
        reservationIter = objectLayer.findReservation(modelReservation);
//        hourlyPriceIter = objectLayer.findHourlyPrice(modelHourlyPrice);
        if( ! reservationIter.hasNext() )
            throw new RARException( "No such a Reservation exists id=" + reservationId );
        
        objectLayer.deleteReservation(modelReservation);                               

	}
}
