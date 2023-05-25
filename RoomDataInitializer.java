package it.uniroma3.siw.controller;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import it.uniroma3.siw.model.Room;
import it.uniroma3.siw.repository.RoomRepository;

@Component
public class RoomDataInitializer {

    private final RoomRepository roomRepository;

    public RoomDataInitializer(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @PostConstruct
    public void initData() {
        // Creazione e inizializzazione delle stanze
        Room room1 = new Room();
        room1.setAvailable(true);
        room1.setName("Camera Singola");
        room1.setDesc("Camera confortevole con un letto singolo");
        room1.setCapacity(1);
        room1.setPrice(100.0f);
        room1.setImg("images/room1.jpg");
        room1.setAvailable(true);

    

        // Salva le stanze nel repository
        roomRepository.save(room1);
    }
}
