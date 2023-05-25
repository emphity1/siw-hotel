package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Room;
import it.uniroma3.siw.repository.RoomRepository;
import it.uniroma3.siw.service.CredentialsService;




@Controller
public class BookingController {

    @Autowired
	private CredentialsService credentialsService;

    @Autowired
    private RoomRepository roomRepository;


/* =============================================================== */
/* =================    POSTMAPPING     ========================= */
/* ============================================================= */


@PostMapping("/admin/addRoom")
public String addRoom(Model model, 
                    @RequestParam String name, 
                    @RequestParam String desc, 
                    @RequestParam Integer capacity, 
                    @RequestParam Float price, 
                    @RequestParam String img,
                    HttpServletRequest request) {
    Room room = new Room();
    room.setName(name);
    room.setDesc(desc);
    room.setCapacity(capacity);
    room.setPrice(price);
    room.setImg(img);
    room.setAvailable(true);
    this.roomRepository.save(room);

    String referer = request.getHeader("Referer");//uso HttpServletREquest per aggiornare la pagina
    return "redirect:"+ referer;

}











/* =============================================================== */
/* =================     GETMAPPING      ======================== */
/* ============================================================= */


@GetMapping("/admin/roomList")
public String getMovieAdmin(Model model, @RequestParam(required = false) String sort) {
    List<Room> rooms = this.roomRepository.findAll();

    if (sort != null) {
        if (sort.equals("asc")) {
            rooms.sort(Comparator.comparing(Room::getName));
        } else if (sort.equals("desc")) {
            rooms.sort(Comparator.comparing(Room::getName).reversed());
        }
    }

    model.addAttribute("room", rooms);
    return "/admin/RoomListRoomAdd.html";
}





    @GetMapping(value = "/index")
    public String index() {
        return "index.html";
    }


    @GetMapping(value = "/room1Booking")
    public String getRoom1() {
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