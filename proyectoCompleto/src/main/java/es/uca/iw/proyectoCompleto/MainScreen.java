/**
 * 
 */
package es.uca.iw.proyectoCompleto;

import javax.annotation.PostConstruct;

import com.vaadin.navigator.View;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import es.uca.iw.proyectoCompleto.apartments.ApartmentManagementView;
import es.uca.iw.proyectoCompleto.bookings.BookingManagementView;
import es.uca.iw.proyectoCompleto.disputes.DisputeManagementView;
import es.uca.iw.proyectoCompleto.security.SecurityUtils;
import es.uca.iw.proyectoCompleto.users.UserEditor;
import es.uca.iw.proyectoCompleto.users.UserManagementView;

@SpringView(name = MainScreen.VIEW_NAME)
@UIScope
public class MainScreen extends VerticalLayout implements View {
	
	private static final long serialVersionUID = 1L;
	
	public static final String VIEW_NAME = "mainScreen";
	
	@PostConstruct
	void init() {
		final VerticalLayout root = new VerticalLayout();
		root.setSizeFull();
		
		createUserMainMenu();
		
		createAdminMainMenu();
		
		if(SecurityUtils.hasRole("ROLE_MANAGER")){
			createManagerMainMenu();
		}
		
	
	}

	private void createAdminMainMenu() {
		Label userManagementLabel = new Label("Gestión de usuarios: ");
		userManagementLabel.setStyleName("title-text");
		userManagementLabel.setVisible(SecurityUtils.hasRole("ROLE_ADMIN"));
		
		Button userManagementButton = new Button("Gestion de usuarios", e -> getUI().getNavigator().navigateTo(UserManagementView.VIEW_NAME));
		userManagementButton.setVisible(SecurityUtils.hasRole("ROLE_ADMIN"));
		addComponents(userManagementLabel, userManagementButton);
	}

	private void createUserMainMenu() {
		Label apartmentLabel = new Label("Tus alojamientos: ");
		apartmentLabel.setStyleName("title-text");
		apartmentLabel.setVisible(SecurityUtils.hasRole("ROLE_USER"));
		
		Button apartmentNavigationButton = new Button("¡Gestionar mis apartamentos!",  e -> getUI().getNavigator().navigateTo(ApartmentManagementView.VIEW_NAME));
		apartmentNavigationButton.setStyleName("box-padding");
		apartmentNavigationButton.setVisible(SecurityUtils.hasRole("ROLE_USER"));
		
		Label myBookingsLabel = new Label("Tus Reservas: ");
		myBookingsLabel.setStyleName("title-text");
		myBookingsLabel.setVisible(SecurityUtils.hasRole("ROLE_USER"));
		
		Button myBookingsNavigationButton = new Button("¡Gestionar mis reservas!",  e -> getUI().getNavigator().navigateTo(BookingManagementView.VIEW_NAME));
		myBookingsNavigationButton.setStyleName("box-padding");
		myBookingsNavigationButton.setVisible(SecurityUtils.hasRole("ROLE_USER"));
		
		Label profileLabel = new Label("Tu Perfil: ");
		profileLabel.setStyleName("title-text");
		profileLabel.setVisible(SecurityUtils.hasRole("ROLE_USER"));
		
		Button profileNavigationButton = new Button("¡Editar mi perfil!",  e -> {
			VaadinSession.getCurrent().setAttribute("usuarioEditado", SecurityUtils.getCurrentUserId());
			getUI().getNavigator().navigateTo(UserEditor.VIEW_NAME);
		});
		profileNavigationButton.setStyleName("box-padding");
		profileNavigationButton.setVisible(SecurityUtils.hasRole("ROLE_USER"));
		
		addComponents(apartmentLabel, apartmentNavigationButton, myBookingsLabel, myBookingsNavigationButton, profileLabel, profileNavigationButton);
	}
	
	
	private void createManagerMainMenu() {
		Label apartmentLabel = new Label("Gestión de quejas: ");
		apartmentLabel.setStyleName("title-text");
		apartmentLabel.setVisible(SecurityUtils.hasRole("ROLE_MANAGER"));
		
		Button apartmentNavigationButton = new Button("¡Gestionar quejas!",  e -> getUI().getNavigator().navigateTo(DisputeManagementView.VIEW_NAME));
		apartmentNavigationButton.setStyleName("box-padding");
		apartmentNavigationButton.setVisible(SecurityUtils.hasRole("ROLE_MANAGER"));
		
		addComponents(apartmentLabel,apartmentNavigationButton);
	}

}
