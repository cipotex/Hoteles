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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import javax.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="detallecontable")
public class DetalleContable implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date fecha_ins;
	
	@PrePersist
	public void preinsert() {
		this.fecha_ins = new Date();
		this.estado= EstadoContableEnum.Pendiente;
	}
	
	@NotNull
	private EstadoContableEnum estado;
	
	
	@ManyToOne
    @NotNull
	private Empresa empresa;
	
	
	@ManyToOne
	private Ocupacion ocupacion;
	
	@NotNull
	private  String cuenta;
	
	@NotNull
	private double montocargo;
	
	@NotNull
	private double montoabono;
	
	private String descripcion;
	
	

	public String getDescripcion() {
		return descripcion;
	}



	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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



	public EstadoContableEnum getEstado() {
		return estado;
	}



	public void setEstado(EstadoContableEnum estado) {
		this.estado = estado;
	}



	public Empresa getEmpresa() {
		return empresa;
	}



	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}



	public Ocupacion getOcupacion() {
		return ocupacion;
	}



	public void setOcupacion(Ocupacion ocupacion) {
		this.ocupacion = ocupacion;
	}



	public String getCuenta() {
		return cuenta;
	}



	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}



	

	public double getMontocargo() {
		return montocargo;
	}



	public void setMontocargo(double montocargo) {
		this.montocargo = montocargo;
	}



	public double getMontoabono() {
		return montoabono;
	}



	public void setMontoabono(double montoabono) {
		this.montoabono = montoabono;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
