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

import es.uca.iw.proyectoCompleto.apartments.ApartmentListView;
import es.uca.iw.proyectoCompleto.apartments.ApartmentManagementView;
import es.uca.iw.proyectoCompleto.bookings.BookingManagementView;
import es.uca.iw.proyectoCompleto.imageApartment.ImageApartmentView;
import es.uca.iw.proyectoCompleto.reports.ReportManagementView;
import es.uca.iw.proyectoCompleto.users.UserManagementView;
import es.uca.iw.proyectoCompleto.users.UserView;


/**
 * @author ruizrube
 *
 */
@SpringViewDisplay
public class MainScreen extends VerticalLayout implements ViewDisplay {

	private static Object ultimoPinchado;
	private static final long serialVersionUID = 1L;
	private Panel springViewDisplay;
	Navbar navbar_;
	
	public MainScreen()
	{
		setMargin(false);
        setSpacing(true);
	}
	
	@Override
    public void attach() {
        super.attach();
        this.getUI().getNavigator().navigateTo("");
    }
	
	@PostConstruct
	void init() {
		
		navbar_ = new Navbar(1);
		addComponent(navbar_);

		final VerticalLayout root = new VerticalLayout();
		root.setSizeFull();
		
		// Creamos l barra de navegación
		final CssLayout navigationBar = new CssLayout();
		navigationBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		navigationBar.addComponent(createNavigationButton("Bienvenido", WelcomeView.VIEW_NAME));
		navigationBar.addComponent(createNavigationButton("Usuarios", UserView.VIEW_NAME));
		navigationBar.addComponent(createNavigationButton("Gestión de Usuarios", UserManagementView.VIEW_NAME));
		navigationBar.addComponent(createNavigationButton("Gestión Apartamentos", ApartmentManagementView.VIEW_NAME));
		navigationBar.addComponent(createNavigationButton("Apartamentos", ApartmentListView.VIEW_NAME));
		navigationBar.addComponent(createNavigationButton("Quejas", ReportManagementView.VIEW_NAME));
		//navigationBar.addComponent(createNavigationButton("Disputas", DisputeManagementView.VIEW_NAME));
		navigationBar.addComponent(createNavigationButton("Mis Reservas", BookingManagementView.VIEW_NAME));
		navigationBar.addComponent(createNavigationButton("Imagenes",ImageApartmentView.VIEW_NAME));
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
	
	public static Object getUltimoPinchado() {
		return ultimoPinchado;
	}

	public static void setUltimoPinchado(Object ultimoPinchado) {
		MainScreen.ultimoPinchado = ultimoPinchado;
	}
}
