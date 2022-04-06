package innotech.com.sv.modelosreportes;

import javax.persistence.ManyToOne;

import innotech.com.sv.modelos.Promocion;

public class CargosAdicionalesReporte {
	private long id;
	private String nombre;
	private int cantidad;

	private double preciounitario;

	private float total;

	
	public CargosAdicionalesReporte() {
		super();
	}

	
	
	public CargosAdicionalesReporte(long id, String nombre, int cantidad, double preciounitario, float total) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.preciounitario = preciounitario;
		this.total = total;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public int getCantidad() {
		return cantidad;
	}


	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}


	public double getPreciounitario() {
		return preciounitario;
	}


	public void setPreciounitario(double preciounitario) {
		this.preciounitario = preciounitario;
	}


	public float getTotal() {
		return total;
	}


	public void setTotal(float total) {
		this.total = total;
	}

	
	
}
