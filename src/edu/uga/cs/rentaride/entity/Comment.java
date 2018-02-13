package edu.uga.cs.rentaride.entity;

import java.util.Date;

import edu.uga.cs.rentaride.persistence.Persistable;


/** This class represents a comment made by a specific Customer regarding a specific Rental experience.
 *
 */
public interface Comment 
    extends Persistable
{
    /** Return the comment string.
     * @return a String this is this comment.
     */
    public String getComment();
    
    /** Set the new value of this comment.
     * @param comment a String which is the new comment.
     */
    public void   setComment( String comment );
    
    /** Return the date of this comment.
     * @return the date of this comment
     */
    public Date getDate();
    
    /** Set the date of this comment.
     * @param date the new date of this comment
     */
    public void setDate( Date date );
    
    /** Return the Rental object this comment is linked to.
     * @return the Rental object for this comment.
     */
    public Rental getRental();
    
    /** Set the new Rental object linked to this comment.
     * @param rental the new Rental object this comment should be linked to.
     */
    public void   setRental( Rental rental );
    
    /** Returns the customer who commented on a rental.
     * @return the Customer who made this comment
     */
    public Customer getCustomer();
    
    /** Set the new Customer who made this comment.
     * @param customer ths new Customer who made this comment.
     */
    public void   setCustomer( Customer customer );
}
