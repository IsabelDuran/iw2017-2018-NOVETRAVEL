package es.uca.iw.proyectoCompleto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

import es.uca.iw.proyectoCompleto.apartments.ApartmentListView;
import es.uca.iw.proyectoCompleto.security.AccessDeniedView;
import es.uca.iw.proyectoCompleto.security.ErrorView;
import es.uca.iw.proyectoCompleto.security.SecurityUtils;

@SpringUI(path = "/frontPage")
@Theme("vaadinlayouts")
public class frontPageUI extends UI
{
	
	@Autowired
	SpringViewProvider viewProvider;
	
	ApartmentListView apartmentListView;

	@Override
	protected void init(VaadinRequest request) {
		
		if (SecurityUtils.isLoggedIn()) {
			Navbar navbar_ = new Navbar(1);
			setContent(navbar_);
			//setContent(apartmentListView);
		
		} else {
			Navbar navbar_ = new Navbar(0);
			setContent(navbar_);
			//setContent(apartmentListView);
		
		}
		

	}
}
