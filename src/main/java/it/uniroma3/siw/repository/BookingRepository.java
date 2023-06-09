package it.uniroma3.siw.repository;



import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.model.Booking;

@Repository
public interface BookingRepository  extends CrudRepository<Booking, Long>  {
    
    //se user ha effettuato una prenotazione
    @Query("SELECT COUNT(b) > 0 FROM Booking b WHERE b.bookedByUsername = :user")
    boolean hasBooking(@Param("user") String user);



}
