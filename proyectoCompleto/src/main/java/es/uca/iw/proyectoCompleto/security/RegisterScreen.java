package es.uca.iw.proyectoCompleto.security;

import javax.annotation.PostConstruct;

import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import es.uca.iw.proyectoCompleto.Navbar;
import es.uca.iw.proyectoCompleto.users.User;

public class RegisterScreen extends VerticalLayout
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RegisterScreen()
	{
        Navbar navbar_ = new Navbar(0);
        addComponent(navbar_);
		
		TextField name = new TextField("Nombre:");
		TextField lastname = new TextField("Apellidos:");
		TextField username = new TextField("Nombre de usuario:");
		TextField password = new TextField("Contraseña: ");
		
		addComponent(name);
		addComponent(lastname);
		addComponent(username);
		addComponent(password);
		
		setMargin(false);
		setSpacing(true);
		
	}
	
	@PostConstruct
	void init()
	{
		
	}
	
	private void addUser()
	{
		User new_user = new User("firstName", 
				"lastName", 
				"username", 
				"address", 
				1111); 
	}
}
