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

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "facturacionenc", uniqueConstraints= {@UniqueConstraint(columnNames= {"empresa_id","ocupacion_id"})})
public class FacturacionEnc implements Serializable {

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
	
	@ManyToOne
    @NotNull
	private Ocupacion ocupacion;
	
//	@Temporal(TemporalType.DATE)
//	@DateTimeFormat(pattern = "yyyy-MM-dd")
//	private Date fechaInicioCobro;
//	
//	@Temporal(TemporalType.DATE)
//	@DateTimeFormat(pattern = "yyyy-MM-dd")
//	private Date fechaFinCobro;
//	
	private String comentarios;
	
	private float montoOcupacion;
	
	private float montoCargosAdicionales;
	
	private double montoPreReserva;
		
	private float montoImpuestos;
	
	private float totalFacturacion;
	
	private EstadoFacturasEnum estadoFactura;

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

	

	public Ocupacion getOcupacion() {
		return ocupacion;
	}

	public void setOcupacion(Ocupacion ocupacion) {
		this.ocupacion = ocupacion;
	}

//	public Date getFechaInicioCobro() {
//		return fechaInicioCobro;
//	}
//
//	public void setFechaInicioCobro(Date fechaInicioCobro) {
//		this.fechaInicioCobro = fechaInicioCobro;
//	}
//
//	public Date getFechaFinCobro() {
//		return fechaFinCobro;
//	}
//
//	public void setFechaFinCobro(Date fechaFinCobro) {
//		this.fechaFinCobro = fechaFinCobro;
//	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public float getTotalFacturacion() {
		return totalFacturacion;
	}

	public void setTotalFacturacion(float totalFacturacion) {
		this.totalFacturacion = totalFacturacion;
	}

	public EstadoFacturasEnum getEstadoFactura() {
		return estadoFactura;
	}

	public void setEstadoFactura(EstadoFacturasEnum estadoFactura) {
		this.estadoFactura = estadoFactura;
	}

	public float getMontoOcupacion() {
		return montoOcupacion;
	}

	public void setMontoOcupacion(float double1) {
		this.montoOcupacion = double1;
	}

	public float getMontoCargosAdicionales() {
		return montoCargosAdicionales;
	}

	public void setMontoCargosAdicionales(float montoCargosAdicionales2) {
		this.montoCargosAdicionales = montoCargosAdicionales2;
	}

	public float getMontoImpuestos() {
		return montoImpuestos;
	}

	public void setMontoImpuestos(float montoImpuestos) {
		this.montoImpuestos = montoImpuestos;
	}

	public double getMontoPreReserva() {
		return montoPreReserva;
	}

	public void setMontoPreReserva(double montoPreReserva) {
		this.montoPreReserva = montoPreReserva;
	}

	
	
	
	
}
