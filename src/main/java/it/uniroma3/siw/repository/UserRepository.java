package it.uniroma3.siw.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

    //joinando due tabelle mi permette di trovare l'utente dato lo username
    @Query("SELECT u.name FROM User u JOIN u.credentials c WHERE c.username = :username")
    String findNameByUsername(@Param("username") String username);

    @Query("SELECT u.surname FROM User u JOIN u.credentials c WHERE c.username = :username")
    String findSurnameByUsername(@Param("username") String username);

}