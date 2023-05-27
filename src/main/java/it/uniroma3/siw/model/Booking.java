package it.uniroma3.siw.model;

import java.time.LocalDate;

import javax.persistence.*;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "room_id")
    private Room room;

    private LocalDate BookingDate;

    private String bookedByUser;

    private String bookedByUsername;


    




    /* ========= GETTERS AND SETTERS =========== */

    public String getBookedByUsername() {
        return this.bookedByUsername;
    }

    public void setBookedByUsername(String bookedByUsername) {
        this.bookedByUsername = bookedByUsername;
    }


    public String getBookedByUser() {
        return this.bookedByUser;
    }

    public void setBookedByUser(String bookedByUser) {
        this.bookedByUser = bookedByUser;
    }



    public LocalDate getBookingDate() {
        return this.BookingDate;
    }

    public void setBookingDate(LocalDate BookingDate) {
        this.BookingDate = BookingDate;
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return this.room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

}
