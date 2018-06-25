package es.uca.iw.proyectoCompleto.security;

import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class LoginScreen extends VerticalLayout {
	
	TextField firstName = new TextField("First name");
	TextField lastName = new TextField("Last name");
	TextField username = new TextField("Username");
	TextField password = new TextField("Password");
	TextField direccion = new TextField("Direccion");
	TextField zipcodee = new TextField("Zip code");
	
	Button save = new Button("Save");
	Button cancel = new Button("Cancel");

	CssLayout actions = new CssLayout(save, cancel);

	
	public LoginScreen(LoginCallback callback) {
        setMargin(false);
        setSpacing(true);
        

        this.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        Label title_ = new Label("Iniciar sesión: ");
        title_.setStyleName("title-text");
        addComponent(title_);
        
        this.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        TextField username = new TextField("Username");
        username.setIcon(VaadinIcons.USER);
        addComponent(username);
 
        PasswordField password = new PasswordField("Password");
        password.setIcon(VaadinIcons.PASSWORD);
        addComponent(password);
        this.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        
        Button login = new Button("Login", evt -> {
            String pword = password.getValue();
            password.setValue("");
            if (!callback.login(username.getValue(), pword)) {
                Notification.show("Login failed");
                username.focus();
            }
        });
        login.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        //register.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        addComponent(login);
       
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
