package es.uca.iw.proyectoCompleto;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import es.uca.iw.proyectoCompleto.apartments.ApartmentListView;
import es.uca.iw.proyectoCompleto.apartments.ApartmentService;

@SpringViewDisplay
public class FrontPage extends VerticalLayout implements ViewDisplay {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Panel springViewDisplay;

	Navbar navbar;
	
	@Override
	public void attach() {
		super.attach();
		this.getUI().getNavigator().navigateTo("");

	}

	@PostConstruct
	public void init() {
		
		navbar = new Navbar();
		setMargin(false);
		setSpacing(true);

		addComponent(navbar);

		// Creamos el panel
		springViewDisplay = new Panel();
		springViewDisplay.setSizeFull();
		addComponent(springViewDisplay);
		setExpandRatio(springViewDisplay, 1.0f);

	}
	
	
	@Override
	public void showView(View view) {

		navbar.heCambiado();
		springViewDisplay.setContent((Component) view);
	}

}