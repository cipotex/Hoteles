package innotech.com.sv.modelos;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class Gastos  implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
    @NotNull
	private Empresa empresa;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecha_ins;
	
	@PrePersist
	public void preinsert() {
		this.fecha_ins = new Date();
		this.estadogastos = EstadoGastos.Pendiente;
		//
		// Obteniendo el usuario que inserto el registro
		Authentication auth = SecurityContextHolder
	            .getContext()
	            .getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
	    this.usuarioRegistro = userDetail.getUsername();
	    //
	}
		
	private TipoFacturaGastoEnum tipofactura;
	
	private ClasificacionGastoEnum tipogasto;
	
	@NotNull
	private String nofactura;
	
	private EstadoGastos estadogastos;
	
	private String nombreproveedor;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaaprovacion;
	
	private String usuarioaprovo;
	
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechafactura;
	
	@NotNull
	private String descripcion;
	
	@NotNull
	private float monto;

	private String usuarioRegistro;
		
	public ClasificacionGastoEnum getTipogasto() {
		return tipogasto;
	}


	public void setTipogasto(ClasificacionGastoEnum tipogasto) {
		this.tipogasto = tipogasto;
	}


	public TipoFacturaGastoEnum getTipofactura() {
		return tipofactura;
	}


	public void setTipofactura(TipoFacturaGastoEnum tipofactura) {
		this.tipofactura = tipofactura;
	}


	public String getNofactura() {
		return nofactura;
	}


	public void setNofactura(String nofactura) {
		this.nofactura = nofactura;
	}


	public EstadoGastos getEstadogastos() {
		return estadogastos;
	}


	public void setEstadogastos(EstadoGastos estadogastos) {
		this.estadogastos = estadogastos;
	}


	public String getNombreproveedor() {
		return nombreproveedor;
	}


	public void setNombreproveedor(String nombreproveedor) {
		this.nombreproveedor = nombreproveedor;
	}


	public Date getFechafactura() {
		return fechafactura;
	}


	public void setFechafactura(Date fechafactura) {
		this.fechafactura = fechafactura;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public float getMonto() {
		return monto;
	}


	public void setMonto(float monto) {
		this.monto = monto;
	}
	
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}
	
	public Empresa getEmpresa() {
		return empresa;
	}


	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	
	public Date getFecha_ins() {
		return fecha_ins;
	}


	public void setFecha_ins(Date fecha_ins) {
		this.fecha_ins = fecha_ins;
	}
	
	
	
	public Date getFechaaprovacion() {
		return fechaaprovacion;
	}


	public void setFechaaprovacion(Date fechaaprovacion) {
		this.fechaaprovacion = fechaaprovacion;
	}


	public String getUsuarioaprovo() {
		return usuarioaprovo;
	}


	public void setUsuarioaprovo(String usuarioaprovo) {
		this.usuarioaprovo = usuarioaprovo;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getUsuarioRegistro() {
		return usuarioRegistro;
	}


	public void setUsuarioRegistro(String usuarioRegistro) {
		this.usuarioRegistro = usuarioRegistro;
	}

	
}
