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

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "ocupaciones")
public class Ocupacion implements Serializable {

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
	private Reserva reserva;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date fechaInicioOcupacion;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")	
	private Date fechaFinOcupacion;
	
	private EstadosEnum estado;

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
	public Reserva getReserva() {
		return reserva;
	}
	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}
	public Date getFechaInicioOcupacion() {
		return fechaInicioOcupacion;
	}
	public void setFechaInicioOcupacion(Date fechaInicioOcupacion) {
		this.fechaInicioOcupacion = fechaInicioOcupacion;
	}
	public Date getFechaFinOcupacion() {
		return fechaFinOcupacion;
	}
	public void setFechaFinOcupacion(Date fechaFinOcupacion) {
		this.fechaFinOcupacion = fechaFinOcupacion;
	}
	public EstadosEnum getEstado() {
		return estado;
	}
	public void setEstado(EstadosEnum estado) {
		this.estado = estado;
	}
	
	
	
}
