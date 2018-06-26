package es.uca.iw.proyectoCompleto.security;

import java.io.ByteArrayOutputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.stereotype.Service;

@Service
public class MailService {

	public void enviarCorreo(String asunto, String cuerpo, String email) 
	{
		Properties props;
		Session session;
		
		props = System.getProperties();
		props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
		props.put("mail.smtp.user", "novetravel@gmail.com");
		props.put("mail.smtp.clave", "fannyperipatriisa");    //La clave de la cuenta
		props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
		props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google
		
		session = Session.getDefaultInstance(props);
		
		MimeMessage message = new MimeMessage(session);
		try {
			Address to = new InternetAddress(email);
	        message.setFrom(new InternetAddress("novetravel@gmail.com"));
	        message.addRecipient(Message.RecipientType.TO, to);   //Se podrían añadir varios de la misma manera
	        message.setSubject(asunto);
	        message.setText(cuerpo);
	        Transport transport = session.getTransport("smtp");
	        transport.connect("smtp.gmail.com", "novetravel@gmail.com", "fannyperipatriisa");
	        transport.sendMessage(message, message.getAllRecipients());
	        transport.close();
		}catch(MessagingException me) {
	        me.printStackTrace();   //Si se produce un error
	    }
	}
	
	public void enviarCorreoAttachment(String asunto, String cuerpo, String email, ByteArrayOutputStream adjunto, String filename) 
	{
		Properties props;
		Session session;
		
		props = System.getProperties();
		props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
		props.put("mail.smtp.user", "novetravel@gmail.com");
		props.put("mail.smtp.clave", "fannyperipatriisa");    //La clave de la cuenta
		props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
		props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google
		
		session = Session.getDefaultInstance(props);
		try {
			Message message = new MimeMessage(session);
			Address to = new InternetAddress(email);
	        message.setFrom(new InternetAddress("novetravel@gmail.com"));
	        message.addRecipient(Message.RecipientType.TO, to);   //Se podrían añadir varios de la misma manera
	        message.setSubject(asunto);
	        
	        BodyPart messageBodyPart = new MimeBodyPart();
	        messageBodyPart.setText(cuerpo);
	        
	        Multipart multipart = new MimeMultipart();
	        multipart.addBodyPart(messageBodyPart); 
	        
	        messageBodyPart = new MimeBodyPart();
	        try {
	 
	        DataSource source = new ByteArrayDataSource(adjunto.toByteArray(), "application/pdf");
	        messageBodyPart.setDataHandler(new DataHandler(source));
	        messageBodyPart.setFileName(filename);
	        multipart.addBodyPart(messageBodyPart);
	        message.setContent(multipart);
	        
	        Transport transport = session.getTransport("smtp");
	        transport.connect("smtp.gmail.com", "novetravel@gmail.com", "fannyperipatriisa");
	        transport.sendMessage(message, message.getAllRecipients());
	        transport.close();
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
	        
		} catch(MessagingException e) {
			e.printStackTrace();
		}
		
		
	}

}
