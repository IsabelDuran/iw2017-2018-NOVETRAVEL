package es.uca.iw.proyectoCompleto.security;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.security.core.context.SecurityContextHolder;

import es.uca.iw.proyectoCompleto.users.User;



public class Correo {
	
	String origen;
	String destino;
	Properties props;
	Session session;
	
	public Correo()
	{
		props = System.getProperties();
	}
	
	public void enviarCorreo(String asunto, String cuerpo) 
	{
		User user_ = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
		 props.put("mail.smtp.user", "pruebassdperi@gmail.com");
		 props.put("mail.smtp.clave", "quevivaSD");    //La clave de la cuenta
		 props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
		 props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
		 props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		 props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google
		
		session = Session.getDefaultInstance(props);
		 
		MimeMessage message = new MimeMessage(session);
		try {
			Address to = new InternetAddress(user_.getEmail());
	        message.setFrom(new InternetAddress("pruebasdperi@gmail.com"));
	        message.addRecipient(Message.RecipientType.TO, to);   //Se podrían añadir varios de la misma manera
	        message.setSubject(asunto);
	        message.setText(cuerpo);
	        Transport transport = session.getTransport("smtp");
	        transport.connect("smtp.gmail.com", "pruebassdperi@gmail.com", "quevivaSD");
	        transport.sendMessage(message, message.getAllRecipients());
	        transport.close();
		}catch(MessagingException me) {
	        me.printStackTrace();   //Si se produce un error
	    }
	}
}
