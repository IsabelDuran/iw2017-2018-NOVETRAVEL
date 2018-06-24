package es.uca.iw.proyectoCompleto.security;

import org.springframework.data.jpa.repository.JpaRepository;

import es.uca.iw.proyectoCompleto.users.Role;

public interface RoleRepository extends JpaRepository<Role, Long>
{
	public Role findByName(String name);
}
