/**
 * 
 */
package es.uca.iw.proyectoCompleto;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import es.uca.iw.proyectoCompleto.apartments.ApartmentListView;
import es.uca.iw.proyectoCompleto.apartments.ApartmentManagementView;
import es.uca.iw.proyectoCompleto.bookings.BookingManagementView;
import es.uca.iw.proyectoCompleto.reports.ReportManagementView;
import es.uca.iw.proyectoCompleto.users.UserManagementView;
import es.uca.iw.proyectoCompleto.users.UserProfileView;
import es.uca.iw.proyectoCompleto.users.UserView;


/**
 * @author ruizrube
 *
 */
@SpringView(name = MainScreen.VIEW_NAME)
@UIScope
public class MainScreen extends VerticalLayout implements View {
	
	private static final long serialVersionUID = 1L;
	
	private Panel springViewDisplay;

	public static final String VIEW_NAME = "mainScreen";
	
	public MainScreen()
	{
		setMargin(false);
        setSpacing(true);
	}

	@PostConstruct
	void init() {
		final VerticalLayout root = new VerticalLayout();
		root.setSizeFull();
		
		// Creamos la barra de navegación
		final CssLayout navigationBar = new CssLayout();
		navigationBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		navigationBar.addComponent(createNavigationButton("Usuarios Registrados", UserView.VIEW_NAME));
		navigationBar.addComponent(createNavigationButton("Gestión de Usuarios", UserManagementView.VIEW_NAME));
		navigationBar.addComponent(createNavigationButton("Gestión Apartamentos", ApartmentManagementView.VIEW_NAME));
		navigationBar.addComponent(createNavigationButton("Apartamentos", ApartmentListView.VIEW_NAME));
		navigationBar.addComponent(createNavigationButton("Quejas", ReportManagementView.VIEW_NAME));
		navigationBar.addComponent(createNavigationButton("Reservas", BookingManagementView.VIEW_NAME));
		navigationBar.addComponent(createNavigationButton("Reservas", UserProfileView.VIEW_NAME));
		root.addComponent(navigationBar);

		// Creamos el panel
		springViewDisplay = new Panel();
		springViewDisplay.setSizeFull();
		root.addComponent(springViewDisplay);
		root.setExpandRatio(springViewDisplay, 1.0f);

		addComponent(root);
		
	}

	private Button createNavigationButton(String caption, final String viewName) {
		Button button = new Button(caption);
		button.addStyleName(ValoTheme.BUTTON_SMALL);
		button.addClickListener(event -> getUI().getNavigator().navigateTo(viewName));
		return button;
	}

}
