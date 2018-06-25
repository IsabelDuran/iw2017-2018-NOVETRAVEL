package es.uca.iw.proyectoCompleto.facturas;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;

@Service
public class FacturaService {
	
	@Autowired
	FacturaRepository repository;
	
	public Factura loadFacturaByFacturaId(Long facturaId)
	{
		return repository.findById(facturaId);

	}
	
	public void generarPdf (Long facturaId)
	{
		Factura f = repository.findById(facturaId);
		
		 Document document = new Document();
			
		 try {
			 	String path = new File(".").getCanonicalPath();
	        	String FILE_NAME = path + "/factura.pdf";
	        	String imagenLogo = path + "/logonovetravel.jpg";
	        	
	            PdfWriter.getInstance(document, new FileOutputStream(new File(FILE_NAME)));
	 
	            document.open();
	            
	            Image logoTitulo = Image.getInstance(imagenLogo);
	            
	            logoTitulo.setAlignment(Image.ALIGN_LEFT);
	            Chunk glue = new Chunk(new VerticalPositionMark());
	            
	            Font fuente = new Font();
	            fuente.setFamily(FontFamily.TIMES_ROMAN.name());
	            fuente.setSize(14);
	            
	            Font fuente2 = new Font();
	            fuente2.setFamily(FontFamily.TIMES_ROMAN.name());
	            fuente2.setStyle(Font.BOLD);
	            fuente2.setSize(14);
	            
	            Font fuente3 = new Font();
	            fuente3.setFamily(FontFamily.TIMES_ROMAN.name());
	            fuente3.setSize(12);
	            
	            Paragraph datos1 = new Paragraph();
	            Paragraph datos2 = new Paragraph();
	            Paragraph datos3 = new Paragraph();
	            Paragraph cliente = new Paragraph();
	            Paragraph detalleCliente = new Paragraph();
	            Paragraph descripcion = new Paragraph();
	            Paragraph detalleDescripcion = new Paragraph();
	            Paragraph dinero = new Paragraph();
	            
	            datos1.setFont(fuente);
	            datos1.add("   Calle Alcalá 10");
	            datos1.add(glue);
	            datos1.add("Número de Factura: "+ facturaId);
	            
	            datos2.setFont(fuente);
	            datos2.add("28001 Madrid, España");
	            datos2.add(glue);
	            datos2.add("Fecha "+ f.getFechaFactura().toString());
	            
	            datos3.setFont(fuente);
	            datos3.add("novetravel@gmail.com");
	            
	            cliente.setFont(fuente2);
	            cliente.add("\nDATOS DEL CLIENTE\n");  
	            
	            detalleCliente.setFont(fuente3);
	            detalleCliente.add("Nombre: "+ f.getBooking().getUser().getFirstName() + "\n"
	            					+ "Apellidos: "+ f.getBooking().getUser().getFirstName());
	            
	            descripcion.setFont(fuente2);
	            descripcion.add("\nDESCRIPCIÓN\n");
	            
	            detalleDescripcion.setFont(fuente3);
	            detalleDescripcion.add("Servicio: Reserva\nFecha de entrada: "+ f.getBooking().getEntryDate().toString()
	            						+ "\nFecha de salida: "+ f.getBooking().getDepartureDate().toString()
	            						+ "\nDetalles: \n" + f.getDetalles());
	            
	            dinero.setFont(fuente3);
	            dinero.setAlignment(Element.ALIGN_RIGHT); 
	            dinero.add("Subtotal sin IVA: "+ f.getPrecioSinIva() 
	            			+ "\nIVA " + f.getIva() + "% de " + f.getPrecioSinIva() + ": " + (double)Math.round((f.getIva()*f.getPrecioSinIva()/121) *100d/ 100d)
	            			+ "\nTotal EUR: "+ f.getBooking().getTotalPrice());
	            
	            LineSeparator l =new LineSeparator(0.5f, 100, null, 0, -5);
	            l.setLineWidth(2);
	            
	            document.add(logoTitulo);
	            document.add(datos1);
	            document.add(datos2);
	            document.add(datos3);
	            document.add(l);
	            document.add(cliente);
	            document.add(detalleCliente);
	            document.add(descripcion);
	            document.add(detalleDescripcion);
	            document.add(l);
	            document.add(dinero);

	            document.close();
			 	 
		 } catch(Exception e){ }
	

	}
	
	public Factura save(Factura factura) {
		return repository.save(factura);
	}

	public Factura findOne(Long arg0) {
		return repository.findOne(arg0);
	}

	public void delete(Factura arg0) {
		repository.delete(arg0);
	}

	public List<Factura> findAll() {
		return repository.findAll();
	}
}
