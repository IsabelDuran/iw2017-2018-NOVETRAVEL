package es.uca.iw.proyectoCompleto.users;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	public List<User> findByLastNameStartsWithIgnoreCase(String lastName);
	
	public User findByUsername(String username);
	
	@Query("SELECT u FROM User u LEFT JOIN FETCH u.booking WHERE u.username = :username")
	public User findByUsernameWithBookins(@Param("username") String username);

	
	public Page<User> findByLastNameContaining(String lastName, Pageable page);
}

