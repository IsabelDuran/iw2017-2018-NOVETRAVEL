package es.uca.iw.proyectoCompleto;

import com.vaadin.data.Binder;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import es.uca.iw.proyectoCompleto.apartments.ApartmentListView;
import es.uca.iw.proyectoCompleto.apartments.ApartmentService;
import es.uca.iw.proyectoCompleto.security.SecurityUtils;
import es.uca.iw.proyectoCompleto.users.UserService;
import es.uca.iw.proyectoCompleto.bookings.BookingEditor;
import es.uca.iw.proyectoCompleto.location.Location;

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
	
	public FrontPage(ApartmentService s,UserService se, BookingEditor be)
	{
		setMargin(false);
        setSpacing(true);
       
        Navbar navbar_;
        if (SecurityUtils.isLoggedIn()) {
          	   navbar_ = new Navbar(1);
               addComponent(navbar_);
   		} else {
   			 navbar_ = new Navbar(0);
   		     addComponent(navbar_);
   		}
       
        
        this.setDefaultComponentAlignment(Alignment.TOP_LEFT);
        Label searchtitle_ = new Label("Buscar: ");
        searchtitle_.setStyleName("title-text");
        addComponent(searchtitle_);
        
        TextField searchbar = new TextField();
        searchbar.setWidth("90%");
        searchbar.setStyleName("box-padding");
        Binder<Location> binder = new Binder<>(Location.class);
        
        
        Button searchbutton = new Button("¡Busca mi apartamento!");
        searchbutton.setStyleName("box-padding");
        addComponents(searchbar,searchbutton);
        		
        
        Label title_ = new Label("Pisos destacados: ");
        title_.setStyleName("title-text");
        addComponent(title_);
        
        // Creamos el panel
		springViewDisplay = new Panel();
		springViewDisplay.setSizeFull();
		addComponent(springViewDisplay);
		setExpandRatio(springViewDisplay, 1.0f);
		

		ApartmentListView v=new ApartmentListView(s,be);
		
	    searchbutton.addClickListener(e -> v.listApartments(s.loadApartmentByLocation(searchbar.getValue())));
		
		navbar_.setDisplay(springViewDisplay);
		navbar_.setUserService(se);
        addComponent(springViewDisplay);
                
        springViewDisplay.setContent(v);
        v.listApartments(s.findAll());
             
	}
	
	@Override
	public void showView(View view) {
		springViewDisplay.setContent((Component) view);
		
	}

}