package es.uca.iw.proyectoCompleto.users;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import es.uca.iw.proyectoCompleto.MainScreen;
import es.uca.iw.proyectoCompleto.security.Correo;

@SpringView(name = UserProfileView.VIEW_NAME)
public class UserProfileView extends VerticalLayout implements View{
	public static final String VIEW_NAME = "userProfileView";
	
	private Grid<User> grid;
	
	
	private final UserService service;
	private Button addNewBtn;
	
	private UserEditor editor;

	@Autowired
	public UserProfileView(UserService service, UserEditor editor) {
		this.service = service;
		this.grid = new Grid<>(User.class);
		this.editor = editor;
		this.addNewBtn = new Button("New user", FontAwesome.PLUS); 
	}
	
	@PostConstruct
	void init() {
		addComponent(editor);
		User user_ = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		editor.editUser(user_);
		
		
		editor.setChangeHandler(() -> {
			Correo correo_ = new Correo();
		    correo_.enviarCorreo("Cambio contraseña", "Su contraseña ha sido cambiada");
			
			getUI().getNavigator().navigateTo(MainScreen.VIEW_NAME);
		});
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
