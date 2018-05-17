package es.uca.iw.proyectoCompleto;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.themes.ValoTheme;

import es.uca.iw.proyectoCompleto.security.AccessDeniedView;
import es.uca.iw.proyectoCompleto.security.ErrorView;
import es.uca.iw.proyectoCompleto.security.LoginScreen;
import es.uca.iw.proyectoCompleto.security.SecurityUtils;


public class Navbar extends HorizontalLayout
{
	/**
	 * @author IsabelDuran
	 */
	private static final long serialVersionUID = 1L;

	public Navbar()
	{
		setWidth("100%");
        setHeight("70px");
        setStyleName("pink-header");
        addStyleName("pink-header");
		
       if (SecurityUtils.isLoggedIn())
        {
        	Button logoutButton = new Button("Logout", event -> logout());
    		logoutButton.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
    		addComponent(logoutButton);
        }
        else
        {
        	Button loginButton = new Button("Login", event -> login());
			loginButton.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
			addComponent(loginButton);
			
			Button registerButton = new Button("Registrarme", event -> register());
			registerButton.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
			addComponent(registerButton);
        }

	}
	
	private void logout() {
		getUI().getPage().reload();
		getSession().close();
	}
	
	private void login()
	{
		
	}
	
	private void register()
	{
		
	}
}
