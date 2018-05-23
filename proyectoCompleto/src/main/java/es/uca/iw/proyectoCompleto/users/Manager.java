package es.uca.iw.proyectoCompleto.users;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Entity
public class Manager extends User{
	
	public Manager() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Manager(String firstName, String lastName, String address, int zipcode) {
		super(firstName, lastName, address, zipcode);
		// TODO Auto-generated constructor stub
	}

	public Manager(String firstName, String lastName, String username, String address, int zipcode) {
		super(firstName, lastName, username, address, zipcode);
		// TODO Auto-generated constructor stub
	}

	public Manager(String firstName, String lastName) {
		super(firstName, lastName);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> list=new ArrayList<GrantedAuthority>();
		list.add(new SimpleGrantedAuthority("ROLE_MANAGEMENT"));
		return list;
		
	}

}
