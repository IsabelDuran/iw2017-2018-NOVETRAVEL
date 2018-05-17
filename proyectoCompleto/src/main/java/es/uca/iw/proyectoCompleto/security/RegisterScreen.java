package es.uca.iw.proyectoCompleto.security;

import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import es.uca.iw.proyectoCompleto.Navbar;
import es.uca.iw.proyectoCompleto.users.User;

public class RegisterScreen extends VerticalLayout
{
	public RegisterScreen()
	{
        Navbar navbar_ = new Navbar();
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
	
	private void addUser()
	{
		User new_user = new User("firstName", 
				"lastName", 
				"username", 
				"address", 
				1111); 
	}
}
