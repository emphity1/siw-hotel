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
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import it.uniroma3.siw.model.Booking;
import it.uniroma3.siw.model.Room;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.BookingRepository;
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

    @Autowired
    private BookingRepository bookingRepository;





/* =============================================================== */
/* =================    POSTMAPPING     ========================= */
/* ============================================================= */

@PatchMapping("/bookRoom/{id}")
public String bookRoom(@PathVariable("id") Long id, HttpServletRequest request, Principal principal){


    //nome dell'utente loggato
    String userName = principal.getName();

    String referer = request.getHeader("Referer");

    Optional<Room> optionalRoom = roomRepository.findById(id);

    //controllo se l'utente ha gia prenotato una stanza
    boolean hasBooking = bookingRepository.hasBooking(userName);

    if(optionalRoom.isPresent() && optionalRoom.get().isAvailable() && !hasBooking  ){
        Room room = optionalRoom.get();
        room.setAvailable(false);
        room.setBookedByUsername(userName);


        //mi pesco il nome e cognome dell'utente loggato
        String name = userRepository.findNameByUsername(userName);
        String surname = userRepository.findSurnameByUsername(userName);

        //aggiungo il nome e cognome dell'utente alla stanza

        //creo un nuovo booking
        //pesco il nome e cognome dell'utente loggato di TIPO USER
        //perche setUser accetta solo tipo USER
        User userNameAndSurename = credentialsRepository.findUserByUserName(userName);
        Booking booking = new Booking();
        booking.setUser(userNameAndSurename);
        booking.setBookedByUser(surname + " " + name);
        booking.setBookedByUsername(userName);
        booking.setRoom(room);
        booking.setBookingDate(LocalDate.now());
        bookingRepository.save(booking);

        roomRepository.save(room);
        return "redirect:/UserBookings" ;
    }

    return "redirect:" + referer;
}

@Transactional
@PostMapping("/admin/deleteBooking/{id}")
public String deleteBooking(@PathVariable("id") Long id, HttpServletRequest request) {
    Optional<Booking> optionalBooking = bookingRepository.findById(id);


    String referer = request.getHeader("Referer");

    if (optionalBooking.isPresent()) {
        //pesco il booking e lo cancello
        Booking booking = optionalBooking.get();
        bookingRepository.deleteById(id);


        //pesco la stanza e aggiorno il suo stato
        Room room = booking.getRoom();

        room.setAvailable(true);
        room.setBookedByUsername(null);
        roomRepository.save(room);
        return "redirect:" + referer;

    }
    return "redirect:" + referer;
}



/* =============================================================== */
/* =================     GETMAPPING      ======================== */
/* ============================================================= */


@GetMapping("/admin/manageBookings")
public String getManageBookings(Model model) {
    List<Booking> bookings = (List<Booking>) bookingRepository.findAll();
    model.addAttribute("bookings", bookings);
    return "/admin/DeleteBooking";
}


@GetMapping("/UserBookings")
public String getUserBookings(Model model, Principal principal) {
    String username = principal.getName();
    System.out.println("Username: " + username);

    List<Room> userBookings = roomRepository.findRoomsBybookedByUsername(username);


    model.addAttribute("userBookings", userBookings);

    return "/userBookings";
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


  

}