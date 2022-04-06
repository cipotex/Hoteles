package innotech.com.sv.modelos;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
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
@Table(name = "disponibilidad")
public class Disponibilidad implements Serializable {

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
	private Habitacion habitacion;

	@ManyToOne
	@NotNull
	private Reserva reserva;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date fecha;

	//@NotBlank(message = "Estado no puede estar vacio")
	private EstadoReservasEnum estado;

	@Column(length = 1)
	String h0;
	@Column(length = 1)
	String h1;
	@Column(length = 1)
	String h2;
	@Column(length = 1)
	String h3;
	@Column(length = 1)
	String h4;
	@Column(length = 1)
	String h5;
	@Column(length = 1)
	String h6;
	@Column(length = 1)
	String h7;
	@Column(length = 1)
	String h8;
	@Column(length = 1)
	String h9;
	@Column(length = 1)
	String h10;
	@Column(length = 1)
	String h11;
	@Column(length = 1)
	String h12;
	@Column(length = 1)
	String h13;
	@Column(length = 1)
	String h14;
	@Column(length = 1)
	String h15;
	@Column(length = 1)
	String h16;
	@Column(length = 1)
	String h17;
	@Column(length = 1)
	String h18;
	@Column(length = 1)
	String h19;
	@Column(length = 1)
	String h20;
	@Column(length = 1)
	String h21;
	@Column(length = 1)
	String h22;
	@Column(length = 1)
	String h23;

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
	public Reserva getReserva() {
		return reserva;
	}
	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public EstadoReservasEnum getEstado() {
		return estado;
	}
	public void setEstado(EstadoReservasEnum estado) {
		this.estado = estado;
	}
	public String getH0() {
		return h0;
	}
	public void setH0(String h0) {
		this.h0 = h0;
	}
	public String getH1() {
		return h1;
	}
	public void setH1(String h1) {
		this.h1 = h1;
	}
	public String getH2() {
		return h2;
	}
	public void setH2(String h2) {
		this.h2 = h2;
	}
	public String getH3() {
		return h3;
	}
	public void setH3(String h3) {
		this.h3 = h3;
	}
	public String getH4() {
		return h4;
	}
	public void setH4(String h4) {
		this.h4 = h4;
	}
	public String getH5() {
		return h5;
	}
	public void setH5(String h5) {
		this.h5 = h5;
	}
	public String getH6() {
		return h6;
	}
	public void setH6(String h6) {
		this.h6 = h6;
	}
	public String getH7() {
		return h7;
	}
	public void setH7(String h7) {
		this.h7 = h7;
	}
	public String getH8() {
		return h8;
	}
	public void setH8(String h8) {
		this.h8 = h8;
	}
	public String getH9() {
		return h9;
	}
	public void setH9(String h9) {
		this.h9 = h9;
	}
	public String getH10() {
		return h10;
	}
	public void setH10(String h10) {
		this.h10 = h10;
	}
	public String getH11() {
		return h11;
	}
	public void setH11(String h11) {
		this.h11 = h11;
	}
	public String getH12() {
		return h12;
	}
	public void setH12(String h12) {
		this.h12 = h12;
	}
	public String getH13() {
		return h13;
	}
	public void setH13(String h13) {
		this.h13 = h13;
	}
	public String getH14() {
		return h14;
	}
	public void setH14(String h14) {
		this.h14 = h14;
	}
	public String getH15() {
		return h15;
	}
	public void setH15(String h15) {
		this.h15 = h15;
	}
	public String getH16() {
		return h16;
	}
	public void setH16(String h16) {
		this.h16 = h16;
	}
	public String getH17() {
		return h17;
	}
	public void setH17(String h17) {
		this.h17 = h17;
	}
	public String getH18() {
		return h18;
	}
	public void setH18(String h18) {
		this.h18 = h18;
	}
	public String getH19() {
		return h19;
	}
	public void setH19(String h19) {
		this.h19 = h19;
	}
	public String getH20() {
		return h20;
	}
	public void setH20(String h20) {
		this.h20 = h20;
	}
	public String getH21() {
		return h21;
	}
	public void setH21(String h21) {
		this.h21 = h21;
	}
	public String getH22() {
		return h22;
	}
	public void setH22(String h22) {
		this.h22 = h22;
	}
	public String getH23() {
		return h23;
	}
	public void setH23(String h23) {
		this.h23 = h23;
	}

	
	

	
}
