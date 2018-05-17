package es.uca.iw.proyectoCompleto.security;

import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;

import es.uca.iw.proyectoCompleto.MainScreen;
import es.uca.iw.proyectoCompleto.reports.Report;
import es.uca.iw.proyectoCompleto.users.*;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class LoginScreen extends VerticalLayout {
	
	private Grid<User> grid;
	
	private User user;
	
	TextField firstName = new TextField("First name");
	TextField lastName = new TextField("Last name");
	TextField username = new TextField("Username");
	TextField password = new TextField("Password");
	TextField direccion = new TextField("Direccion");
	TextField zipcodee = new TextField("Zip code");
	
	private Binder<User> binder = new Binder<>(User.class);
	
	Button save = new Button("Save", FontAwesome.SAVE);
	Button cancel = new Button("Cancel");

	CssLayout actions = new CssLayout(save, cancel);

	
	private UserService service;

  
	public LoginScreen(LoginCallback callback) {
        setMargin(true);
        setSpacing(true);

        TextField username = new TextField("Username");
        addComponent(username);

        PasswordField password = new PasswordField("Password");
        addComponent(password);

        Button login = new Button("Login", evt -> {
            String pword = password.getValue();
            password.setValue("");
            if (!callback.login(username.getValue(), pword)) {
                Notification.show("Login failed");
                username.focus();
            }
        });
        
        Button register = new Button("Register");
        login.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        //register.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        addComponent(login);
        addComponent(register);
        UserService us = new UserService();
        UserEditor ue = new UserEditor(us);
        HorizontalLayout actions = new HorizontalLayout();
        actions.addComponent(register);
        register.addClickListener(e -> ue.editUser(new User("","","","", 0)));
    }

    @FunctionalInterface
    public interface LoginCallback {

        boolean login(String username, String password);
    }
    
	
    /**
  	 * 
  	 */
  	private static final long serialVersionUID = 5304492736395275231L;

	
}
