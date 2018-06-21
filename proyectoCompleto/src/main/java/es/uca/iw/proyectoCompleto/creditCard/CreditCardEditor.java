package es.uca.iw.proyectoCompleto.creditCard;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.event.ShortcutAction;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import es.uca.iw.proyectoCompleto.security.SecurityUtils;

@SpringComponent
@UIScope
public class CreditCardEditor extends VerticalLayout{
	

	private static final long serialVersionUID = 1L;
	private CreditCardService service;
	private CreditCard creditCard_;
	

	private Binder<CreditCard> binder = new Binder<>(CreditCard.class);
	
	TextField name_ =  new TextField("Nombre del titular: ");
	TextField lastname_ =  new TextField("Apellidos del titular: ");
	TextField number_ =  new TextField("Número de la tarjeta: ");
	TextField cvv_ =  new TextField("CVV de la tarjeta: ");
	DateField expirationDate_ = new DateField();
	
	Button save = new Button("Save");
	Button cancel = new Button("Cancel");
	Button delete = new Button("Delete");
			
	CssLayout actions = new CssLayout(save, cancel, delete);
	
	@Autowired
	public CreditCardEditor(CreditCardService service)
	{
this.service = service;
		
		addComponents(name_, lastname_, number_, cvv_, expirationDate_, actions);
		
		binder.forField(name_).bind(CreditCard::getName, CreditCard::setName);
		binder.forField(lastname_).bind(CreditCard::getLastname, CreditCard::setLastname);
		binder.forField(number_).bind(CreditCard::getCard_number, CreditCard::setCard_number);
		binder.forField(cvv_).withConverter(new StringToIntegerConverter("")).bind(CreditCard::getCVC_CVV, CreditCard::setCVC_CVV);
		binder.forField(expirationDate_).bind(CreditCard::getExpiration_date, CreditCard::setExpiration_date);
		
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		save.addClickListener(e -> service.save(creditCard_));
		delete.addClickListener(e -> service.delete(creditCard_));
		cancel.addClickListener(e -> editCreditCard(creditCard_));
		setVisible(false);
		
		delete.setEnabled(SecurityUtils.hasRole("USUARIO_REGISTRADO"));
	}

	public interface ChangeHandler {

		void onChange();
	}
	
	private final void editCreditCard(CreditCard creditcard) {
		
		if(creditcard == null)
		{
			setVisible(false);
			return;
		}
		final boolean persisted =  creditcard.getId() != null;
		if(persisted)
		{
			creditCard_ = service.findOne(creditCard_.getId());
		}
		else
			creditCard_ = creditcard;
		cancel.setVisible(persisted);
		binder.setBean(creditCard_);

		setVisible(true);
		save.focus();
		number_.selectAll();
	}

	
	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
	}

}
