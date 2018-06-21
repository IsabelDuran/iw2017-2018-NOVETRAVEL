package es.uca.iw.proyectoCompleto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.UI;

import es.uca.iw.proyectoCompleto.security.AccessDeniedView;
import es.uca.iw.proyectoCompleto.security.ErrorView;


@SpringUI(path="/")
@Theme("vaadinlayouts")
public class VaadinUI extends UI {
			
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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