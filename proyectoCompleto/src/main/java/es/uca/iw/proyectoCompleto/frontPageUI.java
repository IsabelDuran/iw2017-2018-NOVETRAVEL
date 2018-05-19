/*package es.uca.iw.proyectoCompleto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.UI;

import es.uca.iw.proyectoCompleto.apartments.ApartmentListView;
import es.uca.iw.proyectoCompleto.security.AccessDeniedView;
import es.uca.iw.proyectoCompleto.security.ErrorView;
import es.uca.iw.proyectoCompleto.security.SecurityUtils;

@SpringUI
@Theme("vaadinlayouts")
public class frontPageUI extends UI
{
	
	@Autowired
	SpringViewProvider viewProvider;

	@Autowired	
	AuthenticationManager authenticationManager;

	@Autowired
    ApartmentListView randomApartments;
	
	

	@Override
	protected void init(VaadinRequest request) {
	
	   	this.getUI().getNavigator().setErrorView(ErrorView.class);
		viewProvider.setAccessDeniedViewClass(AccessDeniedView.class);

		
		if (SecurityUtils.isLoggedIn()) {
			Navbar navbar_ = new Navbar(1);
			setContent(navbar_);
			showRandomApartments();
		} else {
			Navbar navbar_ = new Navbar(0);
			setContent(navbar_);
			showRandomApartments();
		}

	}
	
	private void showRandomApartments()
	{
		setContent(randomApartments);
	}
}*/
