package es.uca.iw.proyectoCompleto;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import es.uca.iw.proyectoCompleto.apartments.ApartmentListView;
import es.uca.iw.proyectoCompleto.imageApartment.ImageApartmentView;
import es.uca.iw.proyectoCompleto.security.AccessDeniedView;
import es.uca.iw.proyectoCompleto.security.LoginScreen;
import es.uca.iw.proyectoCompleto.security.RegisterScreen;
import es.uca.iw.proyectoCompleto.security.SecurityUtils;
import es.uca.iw.proyectoCompleto.users.UserService;

@UIScope
public class Navbar extends HorizontalLayout
{
	/**
	 * @author IsabelDuran
	 */
	private static final long serialVersionUID = 1L;
	

//	@Autowired
//	private RegisterScreen registerScreen;
	
	public Navbar()
	{
		


	}
	
	

	public void heCambiado()
	{
		removeAllComponents();
		setWidth("100%");
        setHeight("70px");
        setStyleName("pink-header");
        addStyleName("pink-header");
        
       
        Label title = new Label("NOVETRAVEL");
        title.setStyleName("white-title");
        addComponent(title);
        this.setComponentAlignment(title, Alignment.MIDDLE_LEFT);
       
        Button frontPage = new Button("NOVETRAVEL", event -> getUI().getNavigator().navigateTo(DefaultView.VIEW_NAME));
        frontPage.addStyleNames(ValoTheme.BUTTON_DANGER);
		addComponent(frontPage);
		 this.setComponentAlignment(frontPage, Alignment.MIDDLE_RIGHT);
		 if (SecurityUtils.isLoggedIn())
	        {
	        	Button logoutButton = new Button("Logout", e -> getSession().close());
	    		logoutButton.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
	    		addComponent(logoutButton);
	    		this.setComponentAlignment(logoutButton, Alignment.MIDDLE_RIGHT);
	        }
	        else
	        {
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
