package innotech.com.sv.modelos;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import javax.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "servicios")
public class Servicio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecha_ins;

	@PrePersist
	public void preinsert() {
		this.fecha_ins = new Date();
	}
	
	@ManyToOne
    @NotNull
	private Empresa empresa;
	
	@NotBlank(message = "Nombre del servicio no puede estar vacio")
	private String nombre;
	
	@NotBlank(message = "Descripción del servicio no puede estar vacio")
	private String descripcion;
	
	//@NotBlank(message = "Precio Unitario del servicio no puede estar vacio")
	private double precioUnitario;
	
	@ManyToOne
	//@NotBlank(message = "Clase de servicio no puede estar vacio")
	private ClaseServicio claseServicio;
	//private Promocion promocion = null;
	
	//@NotBlank(message = "Estado del servicio no puede estar vacio")
	private EstadosEnum estado = EstadosEnum.Activo;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public EstadosEnum getEstado() {
		return estado;
	}

	public void setEstado(EstadosEnum estado) {
		this.estado = estado;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getFecha_ins() {
		return fecha_ins;
	}

	public void setFecha_ins(Date fecha_ins) {
		this.fecha_ins = fecha_ins;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public ClaseServicio getClaseServicio() {
		return claseServicio;
	}

	public void setClaseServicio(ClaseServicio claseServicio) {
		this.claseServicio = claseServicio;
	}

	/*public Promocion getPromocion() {
		return promocion;
	}

	public void setPromocion(Promocion promocion) {
		this.promocion = promocion;
	}
	*/
	
	
}
