package it.uniroma3.siw.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import it.uniroma3.siw.model.Booking;
import it.uniroma3.siw.model.Room;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.BookingRepository;
import it.uniroma3.siw.repository.CredentialsRepository;
import it.uniroma3.siw.repository.RoomRepository;
import it.uniroma3.siw.repository.UserRepository;





@Controller
public class RoomController {


    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CredentialsRepository credentialsRepository;

    @Autowired
    private BookingRepository bookingRepository;


/* =============================================================== */
/* =================    POSTMAPPING     ========================= */
/* ============================================================= */


    @PostMapping("/admin/addRoom")
    public String addRoom(
                        @RequestParam("name") String name, 
                        @RequestParam("description") String description, 
                        @RequestParam("capacity") Integer capacity, 
                        @RequestParam("price") Float price, 
                        @RequestParam("img") String img,
                        HttpServletRequest request) {
    
        String referer = request.getHeader("Referer");//uso HttpServletREquest per aggiornare la pagina
        Room room = new Room();
    
        room.setName(name);
        room.setDesc(description);
        room.setCapacity(capacity);
        room.setPrice(price);
        room.setImg(img);
        room.setAvailable(true);
        room.setCreationDate(LocalDate.now());
        this.roomRepository.save(room);
        
        return "redirect:" + referer;
       // return "/admin/roomList";
    
    }


    @PostMapping("/admin/deleteRoom/{id}")
public String deleteRoom(@PathVariable("id") Long id,HttpServletRequest request) {
    // 1. Find the room by ID
    Optional<Room> optionalRoom = roomRepository.findById(id);

    String referer = request.getHeader("Referer");

    // 2. Check if the room exists
    if (optionalRoom.isPresent()) {
        // 3. Delete the room from the repository
        Room room = optionalRoom.get();
        roomRepository.delete(room);
    } else {
        return "redirect:" + referer;
    }

    return "redirect:" + referer;
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

    model.addAttribute("room", rooms);

    return "/admin/DeleteRoom" ;
}




    
}
