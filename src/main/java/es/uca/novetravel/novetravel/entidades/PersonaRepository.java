package es.uca.novetravel.novetravel.entidades;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonaRepository extends JpaRepository<Persona, Long> {

	List<Persona> findByLastNameStartsWithIgnoreCase(String lastName);
}