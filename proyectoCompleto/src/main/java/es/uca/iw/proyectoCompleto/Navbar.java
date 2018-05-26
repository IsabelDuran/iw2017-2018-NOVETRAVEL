package es.uca.iw.proyectoCompleto;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import es.uca.iw.proyectoCompleto.imageApartment.ImageApartmentView;
import es.uca.iw.proyectoCompleto.security.AccessDeniedView;
import es.uca.iw.proyectoCompleto.security.ErrorView;
import es.uca.iw.proyectoCompleto.security.LoginScreen;

import es.uca.iw.proyectoCompleto.security.RegisterScreenView;
import es.uca.iw.proyectoCompleto.security.SecurityUtils;
import es.uca.iw.proyectoCompleto.users.UserService;


public class Navbar extends HorizontalLayout
{
	/**
	 * @author IsabelDuran
	 */
	private static final long serialVersionUID = 1L;
	
	private Panel springViewDisplay;
	
	UserService se;
	
	public Navbar(int loggedin)
	{
		setWidth("100%");
        setHeight("70px");
        setStyleName("pink-header");
        addStyleName("pink-header");
	
       
        Button frontPage = new Button("NOVETRAVEL", event -> frontPage());
        frontPage.addStyleNames(ValoTheme.BUTTON_DANGER);
		addComponent(frontPage);
		 this.setComponentAlignment(frontPage, Alignment.MIDDLE_RIGHT);
		
       if (loggedin == 1)
        {
        	Button logoutButton = new Button("Logout", event -> logout());
    		logoutButton.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
    		addComponent(logoutButton);
    		this.setComponentAlignment(logoutButton, Alignment.MIDDLE_RIGHT);
        }
        else
        {
        	Button loginButton = new Button("Login", event -> login());
			loginButton.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
			addComponent(loginButton);
			this.setComponentAlignment(loginButton, Alignment.MIDDLE_RIGHT);
			
			Button registerButton = new Button("Registrarme");
			registerButton.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
			addComponent(registerButton);
			registerButton.addClickListener(e->register());
			this.setComponentAlignment(registerButton, Alignment.MIDDLE_RIGHT);
        }

	}
	
	public void setDisplay(Panel p) {
		springViewDisplay=p;
	}
	
	public void setUserService(UserService s) {
		se=s;
	}
	

	
	private void frontPage() {
		getUI().getPage().replaceState("/");
		getUI().getPage().reload();
	}

	private void logout() {
		getUI().getPage().reload();
		getSession().close();
	}
	
	private void login()
	{
		getUI().getPage().replaceState("/LoginUI");
		getUI().getPage().reload();
	}
	
	private void register()
	{
		RegisterScreenView r=new RegisterScreenView(se);

		r.init();
		springViewDisplay.setContent(r);
		
	}
	
}
