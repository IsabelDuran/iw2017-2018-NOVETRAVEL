package es.uca.iw.proyectoCompleto.security;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
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
import es.uca.iw.proyectoCompleto.users.UserManagementView;
import es.uca.iw.proyectoCompleto.users.UserService;

@SpringView(name = RegisterScreenView.VIEW_NAME)
public class RegisterScreenView extends VerticalLayout implements View
{
	public static final String VIEW_NAME = "registerScreenView";
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
	

	UserService se;
	
	private static long serialVersionUID = 1L;
	
	Button save = new Button("Guardar");
	
	@Override
    public void attach() {
        super.attach();
        this.getUI().getNavigator().navigateTo("");
    }
	
	@Autowired
	public RegisterScreenView(UserService s)
	{
		
		se=s;
		
		
	
	}	
	

	public void init() {
		
		
		setMargin(false);
		setSpacing(true); 
		
		//System.out.println("Hay n usuarisos "+se.findAll().size());
		
        this.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        Label title_ = new Label("Registrate en Novetravel: ");
        title_.setStyleName("title-text");
        addComponent(title_);
       
		addComponents(firstName, lastName, username, password, direccion, zipcodee, email);
		addComponent(save);
		
		// bind using naming convention
		binder.bindInstanceFields(this);
		
		
		
		
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


}
