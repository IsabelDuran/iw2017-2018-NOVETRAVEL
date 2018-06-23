package es.uca.iw.proyectoCompleto.security;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import es.uca.iw.proyectoCompleto.DefaultView;
import es.uca.iw.proyectoCompleto.MainScreen;
import es.uca.iw.proyectoCompleto.users.User;
import es.uca.iw.proyectoCompleto.users.UserService;

@SpringView(name = DeleteConfirmationView.VIEW_NAME)
public class DeleteConfirmationView extends VerticalLayout implements View
{

	private static final long serialVersionUID = 1L;
	
	public static final String VIEW_NAME = "deleteConfirmationView";
	
	@Autowired
	private UserService userService;
	private User user;
	
	@PostConstruct
	public void init()
	{
		Label notificationLabel = new Label("¿Está seguro que desea eliminar su cuenta?");
		Button confirmationAfirmative = new Button("Sí", e -> {
			user = userService.findOne(SecurityUtils.getCurrentUserId());
			 userService.delete(user);
			 getUI().getNavigator().navigateTo(DefaultView.VIEW_NAME);
			 
		});
		Button confirmationNegative = new Button("Cancelar y volver", e -> 
		{
			VaadinSession.getCurrent().setAttribute("usuarioEditado", SecurityUtils.getCurrentUserId());
			getUI().getNavigator().navigateTo(MainScreen.VIEW_NAME);
			getSession().close();
		});
		
		addComponents(notificationLabel, confirmationAfirmative, confirmationNegative);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
}
