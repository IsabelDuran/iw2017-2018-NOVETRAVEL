package es.uca.iw.proyectoCompleto.users;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import es.uca.iw.proyectoCompleto.MainScreen;

@SpringComponent
@UIScope
public class UserEditor extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final UserService service;

	private User user;

	private Binder<User> binder = new Binder<>(User.class);

	
	/* Fields to edit properties in User entity */
	TextField firstName = new TextField("First name");
	TextField lastName = new TextField("Last name");
	TextField username = new TextField("Username");
	PasswordField password = new PasswordField("Password");
	TextField direccion = new TextField("Direccion");
	TextField zipcodee = new TextField("Zip code");

	/* Action buttons */
	Button save = new Button("Guardar cambios");
	Button cancel = new Button("Cancelar");
	Button delete = new Button("Eliminar mi cuenta");

	/* Layout for buttons */
	CssLayout actions = new CssLayout(save, cancel, delete);


	@Autowired
	public UserEditor(UserService service) {
		this.service = service;

		addComponents(firstName, lastName, username, password, direccion, zipcodee, actions);

		// bind using naming convention
		binder.bindInstanceFields(this);

		
		// Configure and style components
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		// wire action buttons to save, delete and reset
		save.addClickListener(e -> service.save(user));
		delete.addClickListener(e -> service.delete(user));
		cancel.addClickListener(e -> getUI().getNavigator().navigateTo(MainScreen.VIEW_NAME));
		setVisible(false);
	}

	public interface ChangeHandler {

		void onChange();
	}

	public final void editUser(User c) {
		if (c == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = c.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			user = service.findOne(c.getId());
		}
		else {
			user = c;
		}
		cancel.setVisible(persisted);
		binder.setBean(user);
		setVisible(true);
		save.focus();
		firstName.selectAll();
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
	}

}