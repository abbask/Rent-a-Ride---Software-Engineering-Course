package edu.uga.cs.rentaride.entity;

import java.util.Date;

import edu.uga.cs.rentaride.RARException;


/** This class represents information about a registered customer of Rent-A-Ride.
 *
 */
public interface Customer 
    extends User
{
    /** Return the expiration Date of this Customer's membership in Rent-A-Ride.
     * @return the membership expiration Date for this customer 
     */
    public Date   getMembershipExpiration();
    
    /** Set the expiration Date of this Customer's membership in Rent-A-Ride.
     * @param membershipExpiration the new expiration Date for this customer
     * @throws RARException in case the membership date is in the past
     */
    public void   setMembershipExpiration( Date membershipExpiration ) throws RARException;
    
    /** Return the license state for this customer.
     * @return the String representing the state of the customer's license
     */
    public String getLicenseState();
    
    /** Set the new license state for this customer.
     * @param state the new state of this customer's license
     */
    public void   setLicenseState( String state );
    
    /** Return the license number of this customer.
     * @return the license number of this customer
     */
    public String getLicenseNumber();
    
    /** Set the new license number of this customer.
     * @param licenseNumber the new license number of this customer
     */
    public void   setLicenseNumber( String licenseNumber );
    
    /** Return the credit card number of this customer.
     * @return the credit card number of this customer
     */
    public String getCreditCardNumber();
    
    /** Set the new credit card number of this customer.
     * @param cardNumber the new credit card number of this customer
     */
    public void   setCreditCardNumber( String cardNumber );
    
    /** Return the expiration date of this customer's credit card.
     * @return the expiration date of this customer's credit card
     */
    public Date   getCreditCardExpiration();
    
    /** Set the new expiration date of this customer's credit card.
     * @param cardExpiration the new expiration date of this customer's credit card
     */
    public void   setCreditCardExpiration( Date cardExpiration);
}
