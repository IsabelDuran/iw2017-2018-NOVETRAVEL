package es.uca.iw.proyectoCompleto.users;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

import es.uca.iw.proyectoCompleto.MainScreen;
import es.uca.iw.proyectoCompleto.security.Correo;

@SpringView(name = UserProfileView.VIEW_NAME)
public class UserProfileView extends VerticalLayout implements View{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String VIEW_NAME = "userProfileView";
	
	private UserEditor editor;

	@Autowired
	public UserProfileView(UserService service, UserEditor editor) {
		this.editor = editor;
	}
	
	@PostConstruct
	void init() {
		addComponent(editor);
		User user_ = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		editor.editUser(user_);
		
		
		editor.setChangeHandler(() -> {
			Correo correo_ = new Correo();
		    correo_.enviarCorreo("Cambio contraseña", "Su contraseña ha sido cambiada", user_.getEmail());
		    Notification.show("Se ha enviado un correo con los cambios");
			getUI().getNavigator().navigateTo(MainScreen.VIEW_NAME);
		});
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
