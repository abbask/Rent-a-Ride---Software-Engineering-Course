package edu.uga.cs.rentaride.entity;

import edu.uga.cs.rentaride.persistence.Persistable;

/** This class represents the configuration parameters of the Rent-A-Ride system.
 * Note that the prices are always expressed in Cents, not Dollars.
 * This is a singleton class.
 *
 */
public interface RentARideConfig 
    extends Persistable
{
    /** Return the current price of the Rent-A-Ride membership.
     * @return the Rent-A-Ride current price (in cents) of the Rent-A-Ride membership
     */
    public int getMembershipPrice();
    
    /** Set the price of the Rent-A-Ride membership.
     * @param membershipPrice the new price (in cents) of the Rent-A-Ride membership
     */
    public void setMembershipPrice( int membershipPrice );
    
    /** Return the current overtime penalty of the Rent-A-Ride membership.
     * @return the Rent-A-Ride current overtime penalty (in cents)
     */
    public int getOvertimePenalty();
    
    /** Set the overtime penalty of the Rent-A-Ride membership.
     * @param overtimePenalty the new overtime penalty (in cents)
     */
    public void setOvertimePenalty( int overtimePenalty );
}
