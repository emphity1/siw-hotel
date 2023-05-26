package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;


import it.uniroma3.siw.model.Room;
import it.uniroma3.siw.repository.RoomRepository;




@Controller
public class BookingController {


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
/* =================     GETMAPPING      ======================== */
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



@GetMapping(value = "/index")
public String index() {
    return "index";
}


@GetMapping(value = "/room1Booking")
public String getRoom1(Model model) {
    List<Room> rooms = this.roomRepository.findAllByExactName("Camera Singola");
    model.addAttribute("room", rooms);

    return "room1Booking";

}
@GetMapping(value = "/room2Booking")
public String getRoom2() {
    return "room2Booking";

}

@GetMapping(value = "/room3Booking")
public String getRoom3() {
    return "room3Booking";

}

@GetMapping(value = "/userBookings")
public String getUserBookings() {
    return "userBookings";
}

  

}