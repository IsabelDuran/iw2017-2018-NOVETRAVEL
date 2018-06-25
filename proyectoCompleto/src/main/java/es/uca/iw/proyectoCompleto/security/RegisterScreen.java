package es.uca.iw.proyectoCompleto.security;

import java.util.HashSet;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.ValidationResult;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import es.uca.iw.proyectoCompleto.LoginView;
import es.uca.iw.proyectoCompleto.users.Role;
import es.uca.iw.proyectoCompleto.users.User;
import es.uca.iw.proyectoCompleto.users.UserService;

@SpringView(name = RegisterScreen.VIEW_NAME)
public class RegisterScreen extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;

	private Binder<User> binder = new Binder<>();

	public static final String VIEW_NAME = "registerScreen";
	@Autowired
	private UserService userService;
	
	@Autowired
	private MailService mailService;
	
	@PostConstruct
	public void init() {
		Button save = new Button("Guardar");
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		TextField firstName = new TextField("Nombre:");
		TextField lastName = new TextField("Apellidos:");
		TextField username = new TextField("Nombre de usuario:");
		PasswordField password = new PasswordField("Contraseña");
		TextField zipcode = new TextField("Codigo postal:");
		TextField direccion = new TextField("Direccion");
		TextField email = new TextField("Email");

		Label title_ = new Label("Registrate en Novetravel: ");
		title_.setStyleName("title-text");
		addComponent(title_);

		FormLayout form = new FormLayout();
		form.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		firstName.setIcon(VaadinIcons.USER);
		lastName.setIcon(VaadinIcons.USER);
		username.setIcon(VaadinIcons.USER);
		password.setIcon(VaadinIcons.PASSWORD);
		direccion.setIcon(VaadinIcons.ROAD);
		zipcode.setIcon(VaadinIcons.ENVELOPE);
		email.setIcon(VaadinIcons.ENVELOPE);
		form.addComponents(firstName, lastName, username, password, direccion, zipcode, email);
		addComponents(form, save);
		setComponentAlignment(form, Alignment.MIDDLE_CENTER);

		binder.forField(firstName).asRequired("Debes indicar el nombre").bind(User::getFirstName, User::setFirstName);
		binder.forField(lastName).asRequired("Debes indicar tu(s) apellido(s)").bind(User::getLastName,
				User::setLastName);
		binder.forField(username).asRequired("Debes indicar el nombre de usuario").bind(User::getUsername,
				User::setUsername);
		binder.forField(password).asRequired("Debes indicar tu contraseña").bind(User::getPassword, User::setPassword);
		binder.forField(zipcode).asRequired("Debes indicar tu código postal")
				.withConverter(new StringToIntegerConverter("Introducir un numero"))
				.bind(User::getZipcode, User::setZipcode);
		binder.forField(direccion).asRequired("Debes indicar tu dirección").bind(User::getAddress, User::setAddress);
		binder.forField(email).withValidator(new EmailValidator("Indique un email correcto."))
				.asRequired("Debes indicar el email").bind(User::getEmail, User::setEmail);

		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		save.addClickListener(ev -> {
			User user = new User();
			try {
				HashSet<Role> roles = new HashSet<>();
				roles.add(userService.findRole("ROLE_USER"));
				user.setRoles(roles);
				binder.writeBean(user);
				userService.save(user);
				Notification.show("¡Registro exitoso!");
				
				String mensaje = "Estimado/a " + user.getFirstName() + " " + user.getLastName()
				+ ",\n\n " + "se ha registrado correctamente como " + user.getUsername()
				+ " le deseamos que disfrute del sistema.\n\n"
				+ "\n\n" + "Gracias por confiar en nuestros servicios, \n\n Atte: El equipo de Novetravel. ";

				mailService.enviarCorreo("¡Gracias por registrate en Novetravel!", mensaje, user.getEmail());

				
				getUI().getNavigator().navigateTo(LoginView.VIEW_NAME);
			} catch (ValidationException e) {
				ValidationResult validationResult = e.getValidationErrors().iterator().next();
				Notification.show(validationResult.getErrorMessage());
			} 

		});
	}

}
