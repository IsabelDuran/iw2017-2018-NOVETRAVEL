package es.uca.iw.proyectoCompleto.host_ratings;


public class Rating{

	protected int value;
	protected String descripcion;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Rating(int value,String descripcion) {
		this.value = value;
	}
	
	
}