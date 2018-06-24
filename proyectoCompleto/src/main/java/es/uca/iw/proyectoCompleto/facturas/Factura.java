package es.uca.iw.proyectoCompleto.facturas;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.itextpdf.awt.geom.Rectangle;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;

import es.uca.iw.proyectoCompleto.bookings.Booking;

@Entity
public class Factura {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private LocalDate fechaFactura;
	
	private String detalles;
	
	//@OneToOne(fetch = FetchType.EAGER, mappedBy ="factura", cascade=CascadeType.ALL)
	@OneToOne(fetch = FetchType.EAGER, mappedBy ="factura", cascade=CascadeType.ALL)
	private Booking booking;
	
	public Factura() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getFechaFactura() {
		return fechaFactura;
	}

	public void setFechaFactura(LocalDate fechaFactura) {
		this.fechaFactura = fechaFactura;
	}

	public String getDetalles() {
		return detalles;
	}

	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	} 
	
	public void generarPdf()
	{
		 Document document = new Document();
		
		 try {
			 	String path = new File(".").getCanonicalPath();
	        	String FILE_NAME = path + "/factura.pdf";
	        	
	            PdfWriter.getInstance(document, new FileOutputStream(new File(FILE_NAME)));
	 
	            document.open();
	            
	            Chunk glue = new Chunk(new VerticalPositionMark());
	            
	            Font fuente = new Font();
	            fuente.setFamily(FontFamily.TIMES_ROMAN.name());
	            fuente.setStyle(Font.BOLD);
	            fuente.setSize(17);
	            
	            Font fuente2 = new Font();
	            fuente2.setFamily(FontFamily.TIMES_ROMAN.name());
	            //fuente2.setStyle(Font.BOLD);
	            fuente2.setSize(13);
	            
	            Paragraph cabecera = new Paragraph();
	            cabecera.setFont(fuente);
	            cabecera.add("NOVETRAVEL                                                               FACTURA");
	            cabecera.setAlignment(Element.ALIGN_LEFT);
	            
	            Paragraph cabecera1 = new Paragraph();
	            cabecera1.setFont(fuente2);
	            cabecera1.add("    Calle Alcalá 10                                                                                          No.:"+ id +     "\n28032 Madrid                                                                                    Fecha: "+ fechaFactura.toString());
	         
	            cabecera1.setAlignment(Element.ALIGN_JUSTIFIED);
	           
	            document.add(cabecera);
	            document.add(cabecera1);
	            
	            LineSeparator l =new LineSeparator(0.5f, 100, null, 0, -5);
	            l.setLineWidth(2);
	            document.add(l);
	            
	 
	            Paragraph paragraphLorem = new Paragraph();
	            paragraphLorem.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit."
	            		+ "Maecenas finibus fringilla turpis, vitae fringilla justo."
	            		+ "Sed imperdiet purus quis tellus molestie, et finibus risus placerat."
	            		+ "Donec convallis eget felis vitae interdum. Praesent varius risus et dictum hendrerit."
	            		+ "Aenean eu semper nunc. Aenean posuere viverra orci in hendrerit. Aenean dui purus, eleifend nec tellus vitae,"
	            		+ " pretium dignissim ex. Aliquam erat volutpat. ");
	            
	            List<Element> paragraphList = new ArrayList<>();
	            
	            paragraphList = paragraphLorem.breakUp();
	 
	            Font f = new Font();
	            f.setFamily(FontFamily.COURIER.name());
	            f.setStyle(Font.BOLDITALIC);
	            f.setSize(8);
	            
	            Paragraph p3 = new Paragraph();
	            p3.setFont(f);
	            p3.addAll(paragraphList);
	            p3.add("TEST LOREM IPSUM DOLOR SIT AMET CONSECTETUR ADIPISCING ELIT!");
	 
	            document.add(paragraphLorem);
	            document.add(p3);
	            document.close();
			 
			 
		 } catch(Exception e){ }
	}
	
}
