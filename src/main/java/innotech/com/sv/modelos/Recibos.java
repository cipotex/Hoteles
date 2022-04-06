package innotech.com.sv.modelos;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "recibos", uniqueConstraints= {@UniqueConstraint(columnNames= {"empresa_id","factura_id"})})
public class Recibos implements Serializable {

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
	
	@OneToOne
    @NotNull
	private FacturacionEnc factura;
	
	@ManyToOne
    @NotNull
	private Cliente cliente;
	
	private String comentarios;
		
	private EstadoRecibosEnum estadoRecibo;
    
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaPago; 

	private float montofactura;
	
	private float descuento;
	
	private float recargo;
	
	private float montoRecibo;

	///**************************************
	
	@PreUpdate
	public void update() {		
		actualizatotal();
	}
	public void actualizatotal() {
		this.montoRecibo = this.montofactura + this.recargo - this.descuento;
	}
	
	///**********************
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

	public FacturacionEnc getFactura() {
		return factura;
	}

	public void setFactura(FacturacionEnc factura) {
		this.factura = factura;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public EstadoRecibosEnum getEstadoRecibo() {
		return estadoRecibo;
	}

	public void setEstadoRecibo(EstadoRecibosEnum estadoRecibo) {
		this.estadoRecibo = estadoRecibo;
	}

	public Date getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	public float getMontofactura() {
		return montofactura;
	}

	public void setMontofactura(float montofactura) {
		this.montofactura = montofactura;
	}

	public float getDescuento() {
		return descuento;
	}

	public void setDescuento(float descuento) {
		this.descuento = descuento;
	}

	public float getRecargo() {
		return recargo;
	}

	public void setRecargo(float recargo) {
		this.recargo = recargo;
	}

	public float getMontoRecibo() {
		return montoRecibo;
	}

	public void setMontoRecibo(float montoRecibo) {
		this.montoRecibo = montoRecibo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	
	
}
