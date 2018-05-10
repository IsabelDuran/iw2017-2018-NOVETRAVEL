package es.uca.iw.proyectoCompleto;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;

@SpringViewDisplay
public class HeaderView extends HorizontalLayout implements ViewDisplay
{
	private static final long serialVersionUID = 1L;
	private Panel springViewDisplay;
	
	@Override
	public void showView(View view) {
		// TODO Auto-generated method stub
		
	}
}
