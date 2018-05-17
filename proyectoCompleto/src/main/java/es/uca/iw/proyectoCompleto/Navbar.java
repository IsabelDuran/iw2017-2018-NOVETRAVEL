package es.uca.iw.proyectoCompleto;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.themes.ValoTheme;

import es.uca.iw.proyectoCompleto.security.SecurityUtils;

public class Navbar extends HorizontalLayout
{
	/**
	 * @author IsabelDuran
	 */
	private static final long serialVersionUID = 1L;

	public Navbar()
	{
//		CssLayout layout = new CssLayout() {
//			  
//			private static final long serialVersionUID = 1L;
//			@Override
//		    protected String getCss(Component c) {
//		        if (c instanceof Label) {
//		            return "background: #F50A7F";
//		        }
//		       
//		        return null;
//		    }
//		};
        setWidth("100%");
        setHeight("70px");
        setStyleName("pink-header");
        addStyleName("pink-header");
  
		
//        layout.setHeight("70px");
//		layout.setWidth("100%"); 
//	
//		Label box = new Label("NOVETRAVEL");
//		box.setWidth("100%");
//		box.setHeight(70, Unit.PIXELS);
//		layout.addComponent(box);
//		addComponent(layout);
		
        if (!SecurityUtils.isLoggedIn())
        {
			Button loginButton = new Button("Login", event -> login());
			loginButton.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
			addComponent(loginButton);
			
			Button registerButton = new Button("Registrarme", event -> register());
			registerButton.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
			addComponent(registerButton);
        }
        else
        {
        	Button logoutButton = new Button("Logout", event -> logout());
    		logoutButton.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
    		addComponent(logoutButton);
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
