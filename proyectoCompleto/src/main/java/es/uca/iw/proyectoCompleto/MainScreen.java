/**
 * 
 */
package es.uca.iw.proyectoCompleto;

import javax.annotation.PostConstruct;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import es.uca.iw.proyectoCompleto.apartments.ApartmentManagementView;
import es.uca.iw.proyectoCompleto.bookings.BookingManagementView;
import es.uca.iw.proyectoCompleto.reports.ReportManagementView;
import es.uca.iw.proyectoCompleto.users.UserManagementView;
import es.uca.iw.proyectoCompleto.users.UserView;


/**
 * @author ruizrube
 *
 */
@SpringViewDisplay
public class MainScreen extends VerticalLayout implements ViewDisplay {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Panel springViewDisplay;
	
	@Override
    public void attach() {
        super.attach();
        this.getUI().getNavigator().navigateTo("");
    }
	
	@PostConstruct
	void init() {

		final VerticalLayout root = new VerticalLayout();
		root.setSizeFull();
		
		Button logoutButton = new Button("Logout", event -> logout());
		logoutButton.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		root.addComponent(logoutButton);

		// Creamos la barra de navegación
		final CssLayout navigationBar = new CssLayout();
		navigationBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		navigationBar.addComponent(createNavigationButton("Bienvenido", WelcomeView.VIEW_NAME));
		navigationBar.addComponent(createNavigationButton("Usuarios", UserView.VIEW_NAME));
		navigationBar.addComponent(createNavigationButton("Gestión de Usuarios", UserManagementView.VIEW_NAME));
		navigationBar.addComponent(createNavigationButton("Apartamentos", ApartmentManagementView.VIEW_NAME));
		navigationBar.addComponent(createNavigationButton("Quejas", ReportManagementView.VIEW_NAME));
		navigationBar.addComponent(createNavigationButton("Reservas", BookingManagementView.VIEW_NAME));
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
		// If you didn't choose Java 8 when creating the project, convert this
		// to an anonymous listener class
		button.addClickListener(event -> getUI().getNavigator().navigateTo(viewName));
		return button;
	}
	
	
	@Override
	public void showView(View view) {
		springViewDisplay.setContent((Component) view);
	}

	
	private void logout() {
		getUI().getPage().reload();
		getSession().close();
	}
}
