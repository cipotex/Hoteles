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
@Table(name="habitaciones", uniqueConstraints= {@UniqueConstraint(columnNames= {"empresa_id", "codigo"})})
public class Habitacion implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date fecha_ins;
	
	@PrePersist
	public void preinsert() {
		this.fecha_ins = new Date();
	}
	
	@NotBlank(message = "Debe indicar un codigo interno de la habitación")
	private String codigo;
	
	//@NotBlank(message = "Debe indicar el estado de la habitación")
	@NotNull(message = "Debe indicar el estado de la habitación")
	private EstadosEnum estado;
	
	
	@ManyToOne
    @NotNull
	private Empresa empresa;
	
	
	@ManyToOne
    @NotNull
	private TiposHabitacion tipohabitacion;
	
	
	
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



	public String getCodigo() {
		return codigo;
	}



	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}



	public EstadosEnum getEstado() {
		return estado;
	}



	public void setEstado(EstadosEnum estado) {
		this.estado = estado;
	}



	public Empresa getEmpresa() {
		return empresa;
	}



	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}



	public TiposHabitacion getTipohabitacion() {
		return tipohabitacion;
	}



	public void setTipohabitacion(TiposHabitacion tipohabitacion) {
		this.tipohabitacion = tipohabitacion;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
