package es.uca.iw.proyectoCompleto;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import es.uca.iw.proyectoCompleto.apartments.ApartmentListView;
import es.uca.iw.proyectoCompleto.security.SecurityUtils;

@SpringViewDisplay
public class FrontPage extends VerticalLayout implements ViewDisplay{
	
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
	
	public FrontPage()
	{
		setMargin(false);
        setSpacing(true);
       
        if (SecurityUtils.isLoggedIn()) {
        	 Navbar navbar_ = new Navbar(1);
             addComponent(navbar_);
		} else {
			 Navbar navbar_ = new Navbar(0);
		        addComponent(navbar_);
		}
       
        
        this.setDefaultComponentAlignment(Alignment.TOP_LEFT);
        Label title_ = new Label("Pisos destacados: ");
        title_.setStyleName("title-text");
        addComponent(title_);
           

	}
	
	@Override
	public void showView(View view) {
		springViewDisplay.setContent((Component) view);
		
	}

}
