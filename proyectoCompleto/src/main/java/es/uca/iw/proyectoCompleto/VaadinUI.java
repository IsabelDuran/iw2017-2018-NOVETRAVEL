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


@SpringUI
@Theme("vaadinlayouts")
public class VaadinUI extends UI {
			
	@Autowired
	SpringViewProvider viewProvider;

	@Autowired	
	AuthenticationManager authenticationManager;

	@Autowired
    MainScreen mainScreen;
	
	@Autowired
	ApartmentService servicio;
	
	@Autowired
	ImageApartmentService im;
	
	@Autowired
	UserService se;
	
	@Autowired 
	BookingEditor be;
	
	@Override
	protected void init(VaadinRequest request) {
		
	   	this.getUI().getNavigator().setErrorView(ErrorView.class);
		viewProvider.setAccessDeniedViewClass(AccessDeniedView.class);
			
		
		if (SecurityUtils.isLoggedIn()) {
			showMainScreen();
		} else {
			showFrontPage();
		}

	}
	
	private void showFrontPage() {
		setContent(new FrontPage(servicio,se,im,be));
		
	}

//	private void showLoginScreen() {
//		setContent(new LoginScreen(this::login));
//	}

	private void showMainScreen() {
		setContent(mainScreen);
	}
	
//	
//	private boolean login(String username, String password) {
//		try {
//			Authentication token = authenticationManager
//					.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//			// Reinitialize the session to protect against session fixation
//			// attacks. This does not work with websocket communication.
//			VaadinService.reinitializeSession(VaadinService.getCurrentRequest());
//			SecurityContextHolder.getContext().setAuthentication(token);
//			
//			// Show the main UI
//			showMainScreen();
//			return true;
//		} catch (AuthenticationException ex) {
//			return false;
//		}
//	}

	
}