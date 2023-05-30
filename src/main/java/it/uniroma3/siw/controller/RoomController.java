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
                                    @RequestParam("img") String img,
                                    HttpServletRequest request) {
        String referer = request.getHeader("Referer");//uso HttpServletREquest per aggiornare la pagina
        Optional<Room> optionalRoom = roomRepository.findById(id);
        Room room = optionalRoom.get();

        if (optionalRoom.isPresent()) {
            room.setName(name);
            room.setDesc(description);
            room.setCapacity(capacity);
            room.setPrice(price);
            room.setImg(img);
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

    model.addAttribute("room", rooms);

    return "/admin/DeleteRoom" ;
}

@GetMapping("/admin/updateRooms")
public String updateRoom(Model model) {
    List<Room> rooms = roomRepository.findAll();

    model.addAttribute("room", rooms);

    return "/admin/UpdateRoom" ;

}

@GetMapping("/admin/updateRoom/{id}")
public String updateRoomById(@PathVariable("id") Long id, Model model) {
    Optional<Room> optionalRoom = roomRepository.findById(id);

    if (optionalRoom.isPresent()) {
        Room room = optionalRoom.get();
        model.addAttribute("room", room);
        return "/admin/UpdateRoomForm";
    } else {
        return "redirect:/admin/updateRooms";
    }


}


}