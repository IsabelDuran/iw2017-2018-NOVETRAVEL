package es.uca.iw.proyectoCompleto.security;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import es.uca.iw.proyectoCompleto.Navbar;
import es.uca.iw.proyectoCompleto.apartments.ApartmentManagementView;
import es.uca.iw.proyectoCompleto.apartments.ApartmentView;
import es.uca.iw.proyectoCompleto.reports.Report;
import es.uca.iw.proyectoCompleto.reports.ReportEditor.ChangeHandler;
import es.uca.iw.proyectoCompleto.users.User;
import es.uca.iw.proyectoCompleto.users.UserEditor;
import es.uca.iw.proyectoCompleto.users.UserService;

@SpringComponent
public class RegisterScreen extends VerticalLayout implements ViewDisplay
{
	/**
	 * 
	 */
	
	private User user;
	private Binder<User> binder = new Binder<>(User.class);
    
    TextField firstName = new TextField("Nombre:");
	TextField lastName = new TextField("Apellidos:");
	TextField username = new TextField("Nombre de usuario:");
	PasswordField password = new PasswordField("Contraseña");
	TextField zipcodee = new TextField("Codigo postal:");
	TextField direccion = new TextField("Direccion");
	TextField email = new TextField("Email");
	
	private static long serialVersionUID = 1L;
	
	Button save = new Button("Guardar");
	
	@Override
    public void attach() {
        super.attach();
        this.getUI().getNavigator().navigateTo("");
    }
	
	@Autowired
	public RegisterScreen(UserService se)
	{
		setMargin(false);
		setSpacing(true); 
		
        Navbar navbar_ = new Navbar(0);
        addComponent(navbar_);
		
        this.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        Label title_ = new Label("Registrate en Novetravel: ");
        title_.setStyleName("title-text");
        addComponent(title_);
       
		addComponents(firstName, lastName, username, password, direccion, zipcodee, email);
		addComponent(save);
		
		binder.forField(firstName).bind(User::getFirstName, User::setFirstName);
		binder.forField(lastName).bind(User::getLastName,User::setLastName);
		binder.forField(username).bind(User::getUsername,User::setUsername);
		binder.forField(password).bind(User::getPassword, User::setPassword);
		binder.forField(zipcodee).withConverter(new StringToIntegerConverter("Introducir un numero")).bind(User::getZipcode, User::setZipcode);
		binder.forField(direccion).bind(User::getAddress, User::setAddress);
		binder.forField(email).bind(User::getEmail, User::setEmail);
		
		binder.setBean(user);
		Label aux = new Label(firstName.getCaption());
		addComponent(aux);
		
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		try{
			save.addClickListener(ev-> se.save(user));
			System.out.println(user.getFirstName());
		} catch(Exception e)
		{
			System.out.println("Usuario llega vacío");
		}
	
	
	}	

	public interface ChangeHandler {

		void onChange();
	}
	
	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		save.addClickListener(e -> h.onChange());
	}

	@Override
	public void showView(View view) {
		// TODO Auto-generated method stub
		
	}
}
