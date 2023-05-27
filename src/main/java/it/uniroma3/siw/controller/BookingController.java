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

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Room;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.CredentialsRepository;
import it.uniroma3.siw.repository.RoomRepository;
import it.uniroma3.siw.repository.UserRepository;




@Controller
public class BookingController {


    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CredentialsRepository credentialsRepository;


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


/*  ============ DA FIXARE ======0 */
@PatchMapping("/bookRoom/{id}")
public String bookRoom(@PathVariable("id") Long id, HttpServletRequest request, Principal principal){

    String referer = request.getHeader("Referer");

    Optional<Room> optionalRoom = roomRepository.findById(id);

    if(optionalRoom.isPresent() && optionalRoom.get().isAvailable()){
        Room room = optionalRoom.get();
        room.setAvailable(false);

        String userName = principal.getName();

        //mi pesco il nome e cognome dell'utente loggato
        String name = userRepository.findNameByUsername(userName);
        String surname = userRepository.findSurnameByUsername(userName);

        //aggiungo il nome e cognome dell'utente alla stanza
        room.setBookedByUser(name + " " + surname);

        roomRepository.save(room);
        return "redirect:" + referer;
    }

    return "redirect:" + referer;
}








/* =============================================================== */
/* =================     GETMAPPING      ======================== */
/* ============================================================= */


@GetMapping("/UserBookings")
public String getUserBookings(Model model, Principal principal) {

    User user = userRepository.findByUsername(principal.getName());


    return "/userBookings";
}


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
    List<Room> rooms = roomRepository.findAllByExactName("Camera Singola");
    model.addAttribute("room", rooms);
    return "room1Booking";
}
@GetMapping(value = "/room2Booking")
public String getRoom2(Model model) {
    List<Room> rooms = roomRepository.findAllByExactName("Camera Matrimoniale");
    model.addAttribute("room", rooms);
    return "room2Booking";
}

@GetMapping(value = "/room3Booking")
public String getRoom3(Model model) {
    List<Room> rooms = roomRepository.findAllByExactName("Camera Suite");
    model.addAttribute("room", rooms);
    return "room3Booking";
}



/* 
@GetMapping(value = "/room2Booking")
public String getRoom2() {
    return "room2Booking";

}

@GetMapping(value = "/room3Booking")
public String getRoom3() {
    return "room3Booking";

}
*/

@GetMapping(value = "/userBookings")
    public String getUserBookings() {
    return "userBookings";
}

  

}