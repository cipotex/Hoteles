package innotech.com.sv.modelos;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import innotech.com.sv.ProcesosServices.Miscelaneos;

@Entity
@Table(name = "reservas")
public class Reserva implements Serializable {

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
		//this.descuento = (double) this.promocionvigente.getPorcdescuento();
		actualizatotal();	
	}
	
	@PreUpdate
	public void update() {		
		actualizatotal();
	}
	
	//Procedimiento para insertar el total de la reserva
	public void actualizatotal() {
		double promocion = 0;
		if  (this.promocionvigente != null  ) {			
			this.descuento =  this.promocionvigente.getPorcdescuento();
		}else {this.descuento =  0; };

		//
		//System.out.println("Descuento --> " + descuento);
		if (this.periodoreserva == PeriodoReservaEnum.Dia ) {
			  //System.out.println("Descuento Dia--> " );

					this.montoReservaConDescuento = (long) (( this.precioreserva * this.diasOcupacion )-( this.precioreserva * this.diasOcupacion *this.descuento/100 )) ;
			}else if (this.periodoreserva == PeriodoReservaEnum.Semana) {
				//System.out.println("Descuento Semana--> " );
					this.montoReservaConDescuento =   (long) (( this.precioreserva * this.diasOcupacion / 7)-( this.precioreserva * this.diasOcupacion/7 *this.descuento/100 )) ;
			}else if (this.periodoreserva == PeriodoReservaEnum.Mes) {								
				int mesesReserva = Miscelaneos.calcularMesesAFecha(this.fechaInicio, this.fechaFin) ;		
				//System.out.println("Descuento Mes--> " +mesesReserva);				
				this.montoReservaConDescuento =  (long) (( mesesReserva *  this.precioreserva) - ( mesesReserva *  this.precioreserva * this.descuento/100));

		};
	};
	
	@ManyToOne
    @NotNull
	private Empresa empresa;
	
	@ManyToOne
	@NotNull
	private TiposHabitacion tipohabitacion;
	
	@ManyToOne
	@NotNull
	private Habitacion habitacion;
	
	@ManyToOne
	@NotNull
	private Cliente cliente;
	
	@NotNull
	private PeriodoReservaEnum periodoreserva;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@NotNull
	private Date fechaInicio;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@NotNull
	private Date fechaFin;
	
	@NotNull
	private Double precioreserva;
	
	@ManyToOne
	private Promocion promocionvigente = null;
	
	private int diasOcupacion;
	
	private EstadoReservasEnum estadoReserva;
	
	private long montoReservaConDescuento =  0;
	
	private long descuento =  0;
	
	public long getDescuento() {
		return descuento;
	}

	public void setDescuento(long descuento) {
		this.descuento = descuento;
	}

	public long getMontoReservaConDescuento() {
		return montoReservaConDescuento;
	}
	public void setMontoReservaConDescuento(long montoReservaConDescuento) {

		this.montoReservaConDescuento = montoReservaConDescuento;
	}

	@NotNull
	private String recurrente;
	
	@NotNull
	private Double montoDeposito;

	@NotNull
	private Double montoPreReserva;
	
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
	public Habitacion getHabitacion() {
		return habitacion;
	}
	public void setHabitacion(Habitacion habitacion) {
		this.habitacion = habitacion;
	}
	public PeriodoReservaEnum getPeriodoReserva() {
		return periodoreserva;
	}
	public void setPeriodoReserva(PeriodoReservaEnum periodoReserva) {
		this.periodoreserva = periodoReserva;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public Double getPrecioreserva() {
		return precioreserva;
	}
	public void setPrecioreserva(Double precioreserva) {
		this.precioreserva = precioreserva;
	}
	public Promocion getPromocionvigente() {
		return promocionvigente;
	}
	public void setPromocionvigente(Promocion promocionvigente) {
		this.promocionvigente = promocionvigente;
	}
	public int getDiasOcupacion() {
		return diasOcupacion;
	}
	public void setDiasOcupacion(int diasOcupacion) {
		this.diasOcupacion = diasOcupacion;
	}
	public EstadoReservasEnum getEstadoReserva() {
		return estadoReserva;
	}
	public void setEstadoReserva(EstadoReservasEnum estadoReserva) {
		this.estadoReserva = estadoReserva;
	}
	public String getRecurrente() {
		return recurrente;
	}
	public void setRecurrente(String recurrente) {
		this.recurrente = recurrente;
	}
	public Double getMontoDeposito() {
		return montoDeposito;
	}
	public void setMontoDeposito(Double montoDeposito) {
		this.montoDeposito = montoDeposito;
	}
	public TiposHabitacion getTipohabitacion() {
		return tipohabitacion;
	}
	public void setTipohabitacion(TiposHabitacion tipohabitacion) {
		this.tipohabitacion = tipohabitacion;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public PeriodoReservaEnum getPeriodoreserva() {
		return periodoreserva;
	}
	public void setPeriodoreserva(PeriodoReservaEnum periodoreserva) {
		this.periodoreserva = periodoreserva;
	}

	public Double getMontoPreReserva() {
		return montoPreReserva;
	}

	public void setMontoPreReserva(Double montoPreReserva) {
		this.montoPreReserva = montoPreReserva;
	}
	
	
	
}
