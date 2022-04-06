package innotech.com.sv.modelosreportes;

import java.util.Date;

public class OcupacionReporte {
	
	private String nombreempresa;
	private String codigohabitacion;
	private Date fechainicio;
	private Date fechafin;
	private int diasanalizados;
	private int diasocupados;
	private long porcocupacion;
	//
	
	
	public long getPorcocupacion() {
		return porcocupacion;
	}
	public void setPorcocupacion(long porcocupacion) {
		this.porcocupacion = porcocupacion;
	}
	//
	public String getNombreempresa() {
		return nombreempresa;
	}
	public OcupacionReporte(String nombreempresa, String codigohabitacion, Date fechainicio, Date fechafin,
			int diasanalizados, int diasocupados, long porcocupacion) {
		super();
		this.nombreempresa = nombreempresa;
		this.codigohabitacion = codigohabitacion;
		this.fechainicio = fechainicio;
		this.fechafin = fechafin;
		this.diasanalizados = diasanalizados;
		this.diasocupados = diasocupados;
		this.porcocupacion = porcocupacion;
	}
	
	public OcupacionReporte() {
		super();
		
	}
	
	public void setNombreempresa(String nombreempresa) {
		this.nombreempresa = nombreempresa;
	}
	public String getCodigohabitacion() {
		return codigohabitacion;
	}
	public void setCodigohabitacion(String codigohabitacion) {
		this.codigohabitacion = codigohabitacion;
	}
	public Date getFechainicio() {
		return fechainicio;
	}
	public void setFechainicio(Date fechainicio) {
		this.fechainicio = fechainicio;
	}
	public Date getFechafin() {
		return fechafin;
	}
	public void setFechafin(Date fechafin) {
		this.fechafin = fechafin;
	}
	public int getDiasanalizados() {
		return diasanalizados;
	}
	public void setDiasanalizados(int diasanalizados) {
		this.diasanalizados = diasanalizados;
	}
	public int getDiasocupados() {
		return diasocupados;
	}
	public void setDiasocupados(int diasocupados) {
		this.diasocupados = diasocupados;
	}
	
	
	//
	
	
	
   
}
