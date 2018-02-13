package edu.uga.cs.rentaride.entity;


import edu.uga.cs.rentaride.persistence.Persistable;


/** This class represents possible vehicle types of all vehicles in the Rent-A-Ride system.
 * The types should be similar to sedan, pickup, luxury sedan, etc.
 *
 */
public interface VehicleType
    extends Persistable
{
    /** Return the description of this vehicle type.
     * @return description of this vehicle type
     */
    public String getType();
    
    /** Set the description of this vehicle type.
     * @param type the new description of this vehicle type
     */
    public void setType( String type );
}
