package es.uca.iw.proyectoCompleto.facturas;

import java.io.ByteArrayOutputStream;
import java.io.File;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;


@Service
public class FacturaService {

	@Autowired
	FacturaRepository repository;
		
	public Factura loadFacturaByFacturaId(Long facturaId) {
		return repository.findById(facturaId);

	}

	public ByteArrayOutputStream generarPdf(Factura factura) {
		
		Document document = new Document();
		ByteArrayOutputStream outputDocument = new ByteArrayOutputStream();
		try {
			String path = new File(".").getCanonicalPath();
			String imagenLogo = path + "/logonovetravel.jpg";

			PdfWriter.getInstance(document, outputDocument);

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
			datos1.add("   Calle Aprobado");
			datos1.add(glue);
			datos1.add("Número de Factura: " + factura.getId());

			datos2.setFont(fuente);
			datos2.add("11510, Puerto Real, Escuela Superior de Ingeniería");
			datos2.add(glue);
			datos2.add("Fecha " + factura.getFechaFactura());

			datos3.setFont(fuente);
			datos3.add("novetravel@gmail.com");

			cliente.setFont(fuente2);
			cliente.add("\nDATOS DEL CLIENTE\n");

			detalleCliente.setFont(fuente3);
			detalleCliente.add("Nombre: " + factura.getBooking().getUser().getFirstName() + "\n" + "Apellidos: "
					+ factura.getBooking().getUser().getFirstName());

			descripcion.setFont(fuente2);
			descripcion.add("\nDESCRIPCIÓN\n");

			detalleDescripcion.setFont(fuente3);
			detalleDescripcion.add("Servicio: Reserva\nFecha de entrada: " + factura.getBooking().getEntryDate().toString()
					+ "\nFecha de salida: " + factura.getBooking().getDepartureDate().toString() + "\nDetalles: \n"
					+ factura.getDetalles());

			dinero.setFont(fuente3);
			dinero.setAlignment(Element.ALIGN_RIGHT);
			dinero.add(
					"Subtotal sin IVA: " + factura.getPrecioSinIva() + "\nIVA " + factura.getIva() + "% de " + factura.getPrecioSinIva()
							+ ": " + (factura.getIva()/100) * factura.getPrecioSinIva()
							+ "\nTotal EUR: " + factura.getPrecioSinIva()*1.21);

			LineSeparator l = new LineSeparator(0.5f, 100, null, 0, -5);
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

		} catch (Exception e) {
		}
		return outputDocument;

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
