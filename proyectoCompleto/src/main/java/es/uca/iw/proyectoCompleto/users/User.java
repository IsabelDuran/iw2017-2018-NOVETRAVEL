package es.uca.iw.proyectoCompleto.users;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import es.uca.iw.proyectoCompleto.apartments.Apartment;
import es.uca.iw.proyectoCompleto.bookings.Booking;
import es.uca.iw.proyectoCompleto.disputes.Dispute;

@Entity
@Table(indexes = { @Index(name = "lastNameIndex", columnList = "lastName")})
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
	
	@Column(unique=true)
	private String username;

	private String password;
	
	@Column(unique=true)
	private String email;
	
	private String address;
	
	private int zipcode;
	

	@OneToMany(mappedBy = "user")
	private Set<Booking> booking;
	
	@OneToMany(mappedBy="user",fetch=FetchType.EAGER)
	private Set<Apartment> apartments;
	
	@OneToMany(mappedBy = "user")
	private List<Dispute> disputes;
	
	@ManyToMany(fetch=FetchType.EAGER)
	private Set<Role> roles;

	public User() {
	}

	public User(String firstName, String lastName, String username, String address, int zipcode, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.address = address;
		this.zipcode = zipcode;
		this.email = email;
	}
	
	public User(String firstName, String lastName, String username, String address, int zipcode, String email, Apartment apartment) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.address = address;
		this.zipcode = zipcode;
		this.email = email;
		this.apartments.add(apartment);
	}

	public User(String firstName, String lastName, String address, int zipcode, String email) {
		this(firstName,lastName,firstName, address, zipcode, email);
	}
	
	public User(String firstName, String lastName) {
		this(firstName,lastName,firstName, "sad",3321, lastName);
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

	public Set<Apartment> getApartments() {
		return apartments;
	} 
	
	public void setApartments(Set<Apartment> apartments)
	{
		this.apartments = apartments;
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
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	public int getZipcode() {
		return zipcode;
	}

	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}
	
	public Set<Booking> getBooking() {
		return booking;
	}

	public void setBooking(Set<Booking> booking) {
		this.booking = booking;
	}
	
	public void addBooking(Booking book)
	{
		if(book != null)
			this.booking.add(book);
		
        if (book.getUser() != this) {
            book.setUser(this);
        }
	}
	
	public List<Dispute> getDisputes() {
		return disputes;
	}

	public void setDisputes(List<Dispute> disputes) {
		this.disputes = disputes;
	}
		
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> list=new ArrayList<GrantedAuthority>();
		for(Role r : roles)
			list.add(new SimpleGrantedAuthority(r.getName()));
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}