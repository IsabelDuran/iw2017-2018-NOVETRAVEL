package es.uca.iw.proyectoCompleto;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import es.uca.iw.proyectoCompleto.security.SecurityUtils;

@SpringViewDisplay
public class FrontPage extends VerticalLayout implements ViewDisplay{
	
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
		// TODO Auto-generated method stub
		
	}

}
