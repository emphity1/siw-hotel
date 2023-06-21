package it.uniroma3.siw.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import it.uniroma3.siw.model.Room;
import it.uniroma3.siw.repository.RoomRepository;





@Controller
public class RoomController {


    @Autowired
    private RoomRepository roomRepository;


/* =============================================================== */
/* =================    POSTMAPPING     ========================= */
/* ============================================================= */


    @PostMapping("/admin/addRoom")
    public String addRoom(
                        @RequestParam("name") String name, 
                        @RequestParam("description") String description, 
                        @RequestParam("capacity") Integer capacity, 
                        @RequestParam("price") Float price,
                        @RequestParam("photo") MultipartFile photo,
                        HttpServletRequest request) {
    
        String referer = request.getHeader("Referer");//uso HttpServletREquest per aggiornare la pagina
        Room room = new Room();
        room.setName(name);
        room.setDesc(description);
        room.setCapacity(capacity);
        room.setPrice(price);
        room.setAvailable(true);
        room.setCreationDate(LocalDate.now());
        try{
            byte[] bytes = photo.getBytes();
            room.setPhoto(bytes);
        }
        catch(Exception e){
            e.printStackTrace();
        }



        this.roomRepository.save(room);
        
        return "redirect:" + referer;
    
    }


    @PostMapping("/admin/deleteRoom/{id}")
    public String deleteRoom(@PathVariable("id") Long id,HttpServletRequest request) {
    // cerca la stanza per id
    Optional<Room> optionalRoom = roomRepository.findById(id);

    String referer = request.getHeader("Referer");

    // check se la stanza esiste
    if (optionalRoom.isPresent()) {
        // elimina la stanza
        Room room = optionalRoom.get();
        roomRepository.delete(room);
    } else {
        return "redirect:" + referer;
    }

    return "redirect:" + referer;
    }

    //aggiornamento della stanza da parte dell'admin
    @PostMapping("/admin/upateRoomRequest/{id}")
    public String updateRoomRequest(@PathVariable("id") Long id,
                                    @RequestParam("name") String name,
                                    @RequestParam("description") String description,
                                    @RequestParam("capacity") Integer capacity,
                                    @RequestParam("price") Float price,
                                    @RequestParam("photo") MultipartFile photo,
                                    HttpServletRequest request) {
        String referer = request.getHeader("Referer");//uso HttpServletREquest per aggiornare la pagina
        Optional<Room> optionalRoom = roomRepository.findById(id);
        Room room = optionalRoom.get();

        if (optionalRoom.isPresent()) {
            room.setName(name);
            room.setDesc(description);
            room.setCapacity(capacity);
            room.setPrice(price);
            try{
                byte[] bytes = photo.getBytes();
                room.setPhoto(bytes);
            }catch(Exception e){
                e.printStackTrace();
            }
        roomRepository.save(room);
        } else {
            return "redirect:" + referer;
        }

        return "redirect:/admin/updateRooms" ;

    }



/* =============================================================== */
/* =================    GETMAPPING     ========================= */
/* ============================================================= */



@GetMapping("/admin/roomList")
public String getMovieAdmin(Model model) {
    List<Room> rooms = this.roomRepository.findAll();

    model.addAttribute("room", rooms);
    return "/admin/RoomListRoomAdd";
}



@GetMapping("/admin/adminDeleteRoom")
public String deleteRoom(Model model) {
    List<Room> rooms = this.roomRepository.findAll();

    // Creazione della mappa per le foto delle stanze
    Map<Long, String> roomPhotos = new HashMap<>();

    // Cerco id di ogni stanza, trovo la foto e la converto in stringa
    for (Room room : rooms) {
        byte[] photoBytes = room.getPhoto();
        String photo = Base64.getEncoder().encodeToString(photoBytes);
        roomPhotos.put(room.getId(), photo); // Aggiungo la foto alla mappa associandola all'id della stanza
    }

    model.addAttribute("roomPhotos", roomPhotos); // Aggiungo la mappa al modello
    model.addAttribute("room", rooms);

    return "admin/DeleteRoom.html" ;
}



@GetMapping("/admin/updateRooms")
public String updateRoom(Model model) {
    List<Room> rooms = roomRepository.findAll();

    // Creazione della mappa per le foto delle stanze
    Map<Long, String> roomPhotos = new HashMap<>();

    // Cerco id di ogni stanza, trovo la foto e la converto in stringa
    for (Room room : rooms) {
        byte[] photoBytes = room.getPhoto();
        String photo = Base64.getEncoder().encodeToString(photoBytes);
        roomPhotos.put(room.getId(), photo); // Aggiungo la foto alla mappa associandola all'id della stanza
    }

    model.addAttribute("roomPhotos", roomPhotos); // Aggiungo la mappa al modello
    model.addAttribute("rooms", rooms); // Aggiungo la lista di stanze al modello

    return "admin/UpdateRoom.html";
}





@GetMapping("/admin/updateRoom/{id}")
public String updateRoomById(@PathVariable("id") Long id, Model model) {
    Optional<Room> optionalRoom = roomRepository.findById(id);
    byte[] photoBytes = optionalRoom.get().getPhoto();

    if (optionalRoom.isPresent() && photoBytes != null) {
        Room room = optionalRoom.get();
        String photo = Base64.getEncoder().encodeToString(photoBytes);
        model.addAttribute("photo", photo);
        model.addAttribute("room", room);
        return "admin/UpdateRoomForm.html";
    } else {
        return "redirect:/admin/updateRooms";
    }


}


}