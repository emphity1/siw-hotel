package it.uniroma3.siw.model;

import javax.persistence.*;

import java.time.LocalDate;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;


    @Column(name = "description")//parola riservata?
    private String desc;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    private Float price;


    private byte[] photo;


    private boolean available;

    @Column(nullable = false)
    private LocalDate creationDate;

    @Column(nullable = false)
    private String bookedByUsername;


    @OneToOne(mappedBy = "room")
    private Booking booking;



  


    /* ========= GETTERS AND SETTERS =========== */



    public byte[] getPhoto() {
        return this.photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getBookedByUsername() {
        return this.bookedByUsername;
    }

    public void setBookedByUsername(String bookedByUsername) {
        this.bookedByUsername = bookedByUsername;
    }

    
    public LocalDate getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }


    public Booking getBooking() {
        return this.booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getCapacity() {
        return this.capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Float getPrice() {
        return this.price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }



    public boolean isAvailable() {
        return this.available;
    }

    public boolean getAvailable() {
        return this.available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

}