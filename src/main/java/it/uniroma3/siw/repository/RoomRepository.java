package it.uniroma3.siw.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Room;

public interface RoomRepository extends CrudRepository<Room, Long> {

    Object findByName(String name);

    List<Room> findByAvailable(boolean available);

    Optional<Room> findById(Long id);

    List<Room> findAll();
    
    
}
