package innotech.com.sv.modelosreportes;

import java.io.Serializable;

import innotech.com.sv.modelos.ClasificacionGastoEnum;
import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.EstadoGastos;
import innotech.com.sv.modelos.TipoFacturaGastoEnum;

public class  GastosPeriodo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int empresa;
	private int tipogasto;
	private int tipofactura;
	private int estadogastos;
	private int  eventos;
	private float monto;
	//
	private Empresa empresa2;
    private TipoFacturaGastoEnum tipofactura2;
	
	private ClasificacionGastoEnum tipogasto2;
	private EstadoGastos estadogastos2;
	private String nombreempresa;
	//
	private String tipogasto3;
	private String estadogastos3;
	 private String tipofactura3;
	//
	public int getEmpresa() {
		return empresa;
	}
	public GastosPeriodo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public GastosPeriodo(int empresa, int tipogasto, int tipofactura, int estadogastos, int eventos, float monto,
			Empresa empresa2, TipoFacturaGastoEnum tipofactura2, ClasificacionGastoEnum tipogasto2,
			EstadoGastos estadogastos2, String nombreempresa, String tipogasto3, String estadogastos3, String tipofactura3) {
		super();
		this.empresa = empresa;
		this.tipogasto = tipogasto;
		this.tipofactura = tipofactura;
		this.estadogastos = estadogastos;
		this.eventos = eventos;
		this.monto = monto;
		this.empresa2 = empresa2;
		this.tipofactura2 = tipofactura2;
		this.tipogasto2 = tipogasto2;
		this.estadogastos2 = estadogastos2;
		this.nombreempresa = nombreempresa;
		this.tipogasto3 = tipogasto3;
		this.estadogastos3 = estadogastos3;
        this.tipofactura3 = tipofactura3;
	}
	public void setEmpresa(int empresa) {
		this.empresa = empresa;
	}
	public int getTipogasto() {
		return tipogasto;
	}
	public void setTipogasto(int tipogasto) {
		this.tipogasto = tipogasto;
	}
	public int getTipofactura() {
		return tipofactura;
	}
	public void setTipofactura(int tipofactura) {
		this.tipofactura = tipofactura;
	}
	public int getEstadogastos() {
		return estadogastos;
	}
	public void setEstadogastos(int estadogastos) {
		this.estadogastos = estadogastos;
	}
	public int getEventos() {
		return eventos;
	}
	public void setEventos(int eventos) {
		this.eventos = eventos;
	}
	public float getMonto() {
		return monto;
	}
	public void setMonto(float monto) {
		this.monto = monto;
	}
	public Empresa getEmpresa2() {
		return empresa2;
	}
	public void setEmpresa2(Empresa empresa2) {
		this.empresa2 = empresa2;
	}
	public TipoFacturaGastoEnum getTipofactura2() {
		return tipofactura2;
	}
	public void setTipofactura2(TipoFacturaGastoEnum tipofactura2) {
		this.tipofactura2 = tipofactura2;
	}
	public ClasificacionGastoEnum getTipogasto2() {
		return tipogasto2;
	}
	public void setTipogasto2(ClasificacionGastoEnum tipogasto2) {
		this.tipogasto2 = tipogasto2;
	}
	public EstadoGastos getEstadogastos2() {
		return estadogastos2;
	}
	public void setEstadogastos2(EstadoGastos estadogastos2) {
		this.estadogastos2 = estadogastos2;
	}
	public String getNombreempresa() {
		return nombreempresa;
	}
	public void setNombreempresa(String nombreempresa) {
		this.nombreempresa = nombreempresa;
	}
	public String getTipogasto3() {
		return tipogasto3;
	}
	public void setTipogasto3(String tipogasto3) {
		this.tipogasto3 = tipogasto3;
	}
	public String getEstadogastos3() {
		return estadogastos3;
	}
	public void setEstadogastos3(String estadogastos3) {
		this.estadogastos3 = estadogastos3;
	}
	public String getTipofactura3() {
		return tipofactura3;
	}
	public void setTipofactura3(String tipofactura3) {
		this.tipofactura3 = tipofactura3;
	}	
	
}
