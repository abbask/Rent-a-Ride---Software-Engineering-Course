package edu.uga.cs.rentaride.persistence.impl;

import java.sql.ResultSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.object.ObjectLayer;



public class RentalLocationIterator 
implements Iterator<RentalLocation>
{
   private ResultSet    rs = null;
   private ObjectLayer  objectLayer = null;
   private boolean      more = false;

   // these two will be used to create a new object
   //
   public RentalLocationIterator( ResultSet rs, ObjectLayer objectModel )
           throws RARException
   { 
       this.rs = rs;
       this.objectLayer = objectModel;
       try {
           more = rs.next();
       }
       catch( Exception e ) {  // just in case...
           throw new RARException( "RentalLocationterator: Cannot create RentalLocation iterator; root cause: " + e );
       }
   }

   public boolean hasNext() 
   { 
       return more; 
   }

   public RentalLocation next() 
   {
       long   id;
       String name;
   	   String address;
   	   int capacity;
   	   RentalLocation rentallocation;

       if( more ) {

           try {
               id = rs.getLong( 1 );
               name = rs.getString( 2 );
               address = rs.getString( 3 );
               capacity = rs.getInt( 4 );
               more = rs.next();
           }
           catch( Exception e ) {      // just in case...
               throw new NoSuchElementException( "RentalLocationterator: No next Club object; root cause: " + e );
           }

               rentallocation = objectLayer.createRentalLocation( name, address, capacity);
               rentallocation.setId( id );
         
           
           return rentallocation;
       }
       else {
           throw new NoSuchElementException( "RentalLocation Iterator: No next Rental object" );
       }
   }

   public void remove()
   {
       throw new UnsupportedOperationException();
   }

};

