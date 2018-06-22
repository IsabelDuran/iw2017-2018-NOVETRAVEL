package es.uca.iw.proyectoCompleto.users;


import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.provisioning.UserDetailsManager;

import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import es.uca.iw.proyectoCompleto.MainScreen;
import es.uca.iw.proyectoCompleto.security.DeleteConfirmationView;

@SpringView(name = UserEditor.VIEW_NAME)
public class UserEditor extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String VIEW_NAME = "userEditor";
	
	@Autowired
	private UserService userService;

	@Autowired
	private UserDetailsManager userDetailsManager;
	
	private User user;

	private Binder<User> binder = new Binder<>();

	TextField firstName = new TextField("Nombre");
	TextField lastName = new TextField("Apellidos");
	TextField username = new TextField("Nombre de usuario");
	PasswordField password = new PasswordField("Contraseña");
	TextField direccion = new TextField("Direccion");
	TextField zipcode = new TextField("Código Postal");
	TextField email = new TextField("Correo Electronico");

	
	@PostConstruct
	public void init() 
	{
		/* Action buttons */
		Button save = new Button("Guardar cambios");
		Button cancel = new Button("Cancelar");
		Button delete = new Button("Eliminar mi cuenta");

		/* Layout for buttons */
		CssLayout actions = new CssLayout(save, cancel, delete);

		addComponents(firstName, lastName, username, password, direccion, zipcode, email, actions);

		
		binder.forField(firstName).asRequired("Introduce tu nombre porfi :(").bind(User::getFirstName, User::setFirstName);
		binder.forField(lastName).asRequired("Introduce tu apellido").bind(User::getLastName, User::setLastName);
		binder.forField(username).asRequired("Introduce tu usuario").bind(User::getUsername, User::setUsername);
		binder.forField(direccion).asRequired("Introduce tu dirección").bind(User::getAddress, User::setAddress);
		binder.forField(zipcode).asRequired("Introduce tu código postal").withConverter(new StringToIntegerConverter("Debe ser un número")).bind(User::getZipcode, User::setZipcode);
		binder.forField(email).asRequired("Introduce un correo electrónico").withValidator(new EmailValidator("Indique un email correcto.")).bind(User::getEmail, User::setEmail);
		
		
		// Configure and style components
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		// wire action buttons to save, delete and reset
		save.addClickListener(e -> {
			if(!password.isEmpty())
					user.setPassword(password.getValue());
			
			userDetailsManager.updateUser(user);
		});
		delete.addClickListener(e -> getUI().getNavigator().navigateTo(DeleteConfirmationView.VIEW_NAME));
		cancel.addClickListener(e -> getUI().getNavigator().navigateTo(MainScreen.VIEW_NAME));
	}

	public final void enter(ViewChangeEvent event) 
	{
		Long editedUser = (Long) VaadinSession.getCurrent().getAttribute("usuarioEditado");
		if (editedUser == null) 
		{
			this.user = new User();
		}
		else
		{
			this.user = userService.findOne(editedUser);
			binder.setBean(user);
		}
	}

}