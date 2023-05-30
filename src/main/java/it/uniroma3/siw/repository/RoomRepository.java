package it.uniroma3.siw.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.model.Room;


@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {

    Object findByName(String name);

    List<Room> findByAvailable(boolean available);

    Optional<Room> findById(Long id);
    
    //find room by name
    @Query("SELECT r FROM Room r WHERE r.name=:name")
    List<Room> findAllByExactName(@Param("name") String name);



   List<Room> findRoomsBybookedByUsername(String username);



    List<Room> findAll();
    
    
}
