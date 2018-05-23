package es.uca.iw.proyectoCompleto;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;

import es.uca.iw.proyectoCompleto.security.AccessDeniedView;
import es.uca.iw.proyectoCompleto.security.ErrorView;
import es.uca.iw.proyectoCompleto.security.LoginScreen;
import es.uca.iw.proyectoCompleto.security.RegisterScreen;
import es.uca.iw.proyectoCompleto.security.SecurityUtils;
import es.uca.iw.proyectoCompleto.users.UserService;

@SpringUI(path = "/RegisterUI")
@Theme("vaadinlayouts")
public class RegisterUI extends UI{
	@Override
	protected void init(VaadinRequest request) {
		
		setContent(new RegisterScreen());
	   	
	}

}
