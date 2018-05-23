package es.uca.iw.proyectoCompleto.users;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Entity
public class Administrator extends User{

	public Administrator() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Administrator(String firstName, String lastName, String address, int zipcode, String email) {
		super(firstName, lastName, address, zipcode, email);
		// TODO Auto-generated constructor stub
	}

	public Administrator(String firstName, String lastName, String username, String address, int zipcode, String email) {
		super(firstName, lastName, username, address, zipcode, email);
		// TODO Auto-generated constructor stub
	}

	public Administrator(String firstName, String lastName) {
		super(firstName, lastName);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> list=new ArrayList<GrantedAuthority>();
		list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		return list;
		
	}
	
}
