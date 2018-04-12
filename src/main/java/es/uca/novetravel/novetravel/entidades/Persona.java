package es.uca.novetravel.novetravel.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Persona {

	@Id
	@GeneratedValue
	private Long id;

	private String nombre;
	private String apellidos;
	private String user;
	private String password;
	private String tarjetadecredito;

	protected Persona() {
	}

	public Persona(Long id, String nombre, String apellidos, String user, String password, String tarjetadecredito) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.user = user;
		this.password = password;
		this.tarjetadecredito = tarjetadecredito;
	}




	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}




	public String getNombre() {
		return nombre;
	}




	public void setNombre(String nombre) {
		this.nombre = nombre;
	}




	public String getApellidos() {
		return apellidos;
	}




	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}




	public String getUser() {
		return user;
	}




	public void setUser(String user) {
		this.user = user;
	}




	public String getPassword() {
		return password;
	}




	public void setPassword(String password) {
		this.password = password;
	}




	public String getTarjetadecredito() {
		return tarjetadecredito;
	}




	public void setTarjetadecredito(String tarjetadecredito) {
		this.tarjetadecredito = tarjetadecredito;
	}

	@Override
	public String toString() {
		return "Persona [id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", user=" + user
				+ ", password=" + password + ", tarjetadecredito=" + tarjetadecredito + "]";
	}
	
}