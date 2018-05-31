package es.uca.iw.proyectoCompleto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.UI;

import es.uca.iw.proyectoCompleto.apartments.ApartmentService;
import es.uca.iw.proyectoCompleto.bookings.BookingEditor;
import es.uca.iw.proyectoCompleto.imageApartment.ImageApartmentService;
import es.uca.iw.proyectoCompleto.security.AccessDeniedView;
import es.uca.iw.proyectoCompleto.security.ErrorView;
import es.uca.iw.proyectoCompleto.security.LoginScreen;
import es.uca.iw.proyectoCompleto.security.SecurityUtils;
import es.uca.iw.proyectoCompleto.users.UserService;


@SpringUI(path="/")
@Theme("vaadinlayouts")
public class VaadinUI extends UI {
			
	@Autowired
	 SpringViewProvider viewProvider;

	@Autowired	
	 AuthenticationManager authenticationManager;
	
	@Autowired
	private FrontPage frontPage;
	
	@Override
	protected void init(VaadinRequest request) {
		
	   	this.getUI().getNavigator().setErrorView(ErrorView.class);
		viewProvider.setAccessDeniedViewClass(AccessDeniedView.class);
		showFrontPage();


	}
	
	private void showFrontPage() {
		setContent(frontPage);
		
	}
	
}