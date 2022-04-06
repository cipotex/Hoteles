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
import javax.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.sun.istack.NotNull;


@Entity
@Table(name="empresas")
public class Empresa implements Serializable{
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotBlank(message = "debe indicar el nombre de la empresa.")
	private String nombre;
	
	@NotBlank(message = "debe indicar la dirección de la empresa.")
	private String direccion;
	
	@NotBlank(message = "debe indicar el nit de la empresa.")
	private String nit;
	
	@NotBlank(message = "debe indicar el numero de registro de la empresa.")
	private String nrc;
	
	@NotBlank(message = "debe indicar el nombre del representante de empresa.")
	private String nomb_representante;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date fecha_ins;
	
	@ManyToOne
	@NotNull
	private Moneda moneda;
	
	@PrePersist
	public void preinsert() {
		this.fecha_ins = new Date();
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

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public String getNrc() {
		return nrc;
	}

	public void setNrc(String nrc) {
		this.nrc = nrc;
	}

	public String getNomb_representante() {
		return nomb_representante;
	}

	public void setNomb_representante(String nomb_representante) {
		this.nomb_representante = nomb_representante;
	}

	public Date getFecha_ins() {
		return fecha_ins;
	}

	public void setFecha_ins(Date fecha_ins) {
		this.fecha_ins = fecha_ins;
	}


	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}


	private static final long serialVersionUID = 1L;

	
}
