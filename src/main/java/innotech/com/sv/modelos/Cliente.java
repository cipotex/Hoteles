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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="clientes", uniqueConstraints= {@UniqueConstraint(columnNames= {"dui"})}   )
public class Cliente implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date fecha_nacimiento;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date fecha_ins;
	
	@PrePersist
	public void preinsert() {
		this.fecha_ins = new Date();
	}
	
	@ManyToOne
    @NotNull
	private Empresa empresa;

	
	@NotBlank(message = "Nombre no puede estar vacio")
	private String nombredui;
	
	private String telefonocontacto;
	
	private String lugartrabajo;
	
	@Email
	private String email;
	
	private String telefonotrabajo;
	
	@NotBlank(message = "No. Dui no puede estar vacio")
	private String dui;
	
	@NotBlank(message = "Dirección según Dui no puede estar vacio")
	private String direcciondui;
	
	private String contacto1;
	
	private String contacto2;
	
	private String foto;

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
	public String getNombredui() {
		return nombredui;
	}
	public void setNombredui(String nombredui) {
		this.nombredui = nombredui;
	}
	public String getTelefonocontacto() {
		return telefonocontacto;
	}
	public void setTelefonocontacto(String telefonocontacto) {
		this.telefonocontacto = telefonocontacto;
	}
	public String getLugartrabajo() {
		return lugartrabajo;
	}
	public void setLugartrabajo(String lugartrabajo) {
		this.lugartrabajo = lugartrabajo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefonotrabajo() {
		return telefonotrabajo;
	}
	public void setTelefonotrabajo(String telefonotrabajo) {
		this.telefonotrabajo = telefonotrabajo;
	}
	public String getDui() {
		return dui;
	}
	public void setDui(String dui) {
		this.dui = dui;
	}
	public String getDirecciondui() {
		return direcciondui;
	}
	public void setDirecciondui(String direcciondui) {
		this.direcciondui = direcciondui;
	}
	public String getContacto1() {
		return contacto1;
	}
	public void setContacto1(String contacto1) {
		this.contacto1 = contacto1;
	}
	public String getContacto2() {
		return contacto2;
	}
	public void setContacto2(String contacto2) {
		this.contacto2 = contacto2;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	public Date getFecha_nacimiento() {
		return fecha_nacimiento;
	}
	public void setFecha_nacimiento(Date fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}
	
	
	
}
