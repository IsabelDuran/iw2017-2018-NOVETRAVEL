package es.uca.iw.proyectoCompleto.users;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class User implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8883789651072229337L;

	@Id
	@GeneratedValue
	private Long id;

	private String firstName;

	private String lastName;
	
	private String username;

	private String password;
	
	private String direccion;
	
	private int zipcode;
	
	protected User() {
	}

	public User(String firstName, String lastName, String username, String direccion, int zipcode) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.direccion = direccion;
		this.zipcode = zipcode;
	}

	public User(String firstName, String lastName, String address, int zipcode) {
		this(firstName,lastName,firstName, address, zipcode);
	}
	
	public User(String firstName, String lastName) {
		this(firstName,lastName,firstName, "sad",3321);
	}

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setUsername(String username) {
		this.username= username;
	}
	
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getDireccion() {
		return direccion;
	}
	
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	
	public int getZipcode() {
		return zipcode;
	}

	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}

	@Override
	public String toString() {
		return String.format("User[id=%d, firstName='%s', lastName='%s', username='%s', password='%s', direccion='%s']", id,
				firstName, lastName,username,password, direccion);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> list=new ArrayList<GrantedAuthority>();
		list.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
		return list;
		
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}