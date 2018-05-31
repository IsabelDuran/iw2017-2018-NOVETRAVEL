package es.uca.iw.proyectoCompleto;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.server.VaadinService;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import es.uca.iw.proyectoCompleto.security.LoginScreen;
import es.uca.iw.proyectoCompleto.security.SecurityUtils;


@Theme("vaadinlayouts")
@SpringView(name = LoginView.VIEW_NAME)
public class LoginView extends VerticalLayout implements View {
			

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired	
	AuthenticationManager authenticationManager;
	
	public static final String VIEW_NAME = "login";

	@PostConstruct
	public void init() {
		if (!SecurityUtils.isLoggedIn()) 
			showLoginScreen();
	}
	
	private void showLoginScreen() {
		addComponent(new LoginScreen(this::login));
	}
	
	private void showMainScreen() {
		getUI().getNavigator().navigateTo(MainScreen.VIEW_NAME);
	}
	

	
	private boolean login(String username, String password) {
		try {
			Authentication token = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			// Reinitialize the session to protect against session fixation
			// attacks. This does not work with websocket communication.
			VaadinService.reinitializeSession(VaadinService.getCurrentRequest());
			SecurityContextHolder.getContext().setAuthentication(token);
			
			// Show the main UI
			showMainScreen();
			return true;
		} catch (AuthenticationException ex) {
			return false;
		}
	}

	
}