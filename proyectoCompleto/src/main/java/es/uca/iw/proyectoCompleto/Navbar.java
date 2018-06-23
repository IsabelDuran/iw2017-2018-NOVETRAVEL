package es.uca.iw.proyectoCompleto;

import com.vaadin.server.Page;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.themes.ValoTheme;

import es.uca.iw.proyectoCompleto.security.RegisterScreen;
import es.uca.iw.proyectoCompleto.security.SecurityUtils;

@UIScope
public class Navbar extends HorizontalLayout {
	/**
	 * @author IsabelDuran
	 */
	private static final long serialVersionUID = 1L;

	public void heCambiado() {
		removeAllComponents();
		setWidth("100%");
		setHeight("70px");
		setStyleName("pink-header");
		addStyleName("pink-header");

		HorizontalLayout labelWrapper = new HorizontalLayout();
		labelWrapper.addLayoutClickListener(event -> getUI().getNavigator().navigateTo(DefaultView.VIEW_NAME));
		Label title = new Label("NOVETRAVEL");
		title.setStyleName("white-title");
		labelWrapper.addComponent(title);
		
		addComponent(labelWrapper);
		this.setComponentAlignment(labelWrapper, Alignment.MIDDLE_LEFT);
		labelWrapper.setComponentAlignment(title, Alignment.MIDDLE_CENTER);
		
		if (SecurityUtils.isLoggedIn()) {
			Button logoutButton = new Button("Logout", e -> {
				getUI().getNavigator().navigateTo(DefaultView.VIEW_NAME);
				
				getSession().close();
				Page.getCurrent().reload();
			
				});
			logoutButton.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
			addComponent(logoutButton);
			this.setComponentAlignment(logoutButton, Alignment.MIDDLE_RIGHT);

			Button profileButton = new Button("Tu perfil",
					e -> getUI().getNavigator().navigateTo(MainScreen.VIEW_NAME));
			profileButton.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
			addComponent(profileButton);
			this.setComponentAlignment(profileButton, Alignment.MIDDLE_RIGHT);
		} else {
			Button loginButton = new Button("Login", e -> getUI().getNavigator().navigateTo(LoginView.VIEW_NAME));
			loginButton.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
			addComponent(loginButton);
			this.setComponentAlignment(loginButton, Alignment.MIDDLE_RIGHT);

			Button registerButton = new Button("Registrarme");
			registerButton.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
			addComponent(registerButton);
			registerButton.addClickListener(e -> getUI().getNavigator().navigateTo(RegisterScreen.VIEW_NAME));
			this.setComponentAlignment(registerButton, Alignment.MIDDLE_RIGHT);
		}
	}

}
