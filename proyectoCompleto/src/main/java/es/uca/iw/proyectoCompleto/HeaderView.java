package es.uca.iw.proyectoCompleto;

import javax.annotation.PostConstruct;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SpringViewDisplay
public class HeaderView extends VerticalLayout implements ViewDisplay
{
	private static final long serialVersionUID = 1L;
	private Panel springViewDisplay;

	@Override
    public void attach() {
        super.attach();
        this.getUI().getNavigator().navigateTo("");
    }
	
	@PostConstruct
	void init() {
		
		final VerticalLayout header = new VerticalLayout();
		header.setSizeFull();
		
		Button logoutButton = new Button("Logout", event -> logout());
		logoutButton.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		header.addComponent(logoutButton);
	}
	
	private void logout() {
		getUI().getPage().reload();
		getSession().close();
	}
	
	@Override
	public void showView(View view) {
		springViewDisplay.setContent((Component) view);
		
	}
}
