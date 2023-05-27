package it.uniroma3.siw.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;


@Repository
public interface CredentialsRepository extends CrudRepository<Credentials, Long> {

	public Optional<Credentials> findByUsername(String username);


	@Query("SELECT u FROM User u JOIN u.credentials c WHERE c.username = :username")
	User findUserByUserName(@Param("username") String username);


}