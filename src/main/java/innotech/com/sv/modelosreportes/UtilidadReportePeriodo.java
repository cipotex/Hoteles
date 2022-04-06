package innotech.com.sv.modelosreportes;

import java.util.Date;

public class UtilidadReportePeriodo {
	
	private float totalfacturacion;	
	private float cargosadicionales;		 
	private float impuestos;	
	private float montoocupacion;	
	private double montoprereserva;	
	private float totalgastos;
	
	//
	
	public UtilidadReportePeriodo(float totalfacturacion, float cargosAdicionales, float impuestos, float montoocupacion,
			double montoreserva, float totalgastos) {
		
		this.totalfacturacion = totalfacturacion;
		this.cargosadicionales = cargosAdicionales;
		this.impuestos = impuestos;
		this.montoocupacion = montoocupacion;
		this.montoprereserva = montoreserva;
		this.totalgastos = totalgastos;
	}
	public UtilidadReportePeriodo() {
		super();
	}
	public float getTotalfacturacion() {
		return totalfacturacion;
	}
	public void setTotalfacturacion(float totalfacturacion) {
		this.totalfacturacion = totalfacturacion;
	}
	public float getCargosadicionales() {
		return cargosadicionales;
	}
	public void setCargosadicionales(float cargosadicionales) {
		this.cargosadicionales = cargosadicionales;
	}
	public float getImpuestos() {
		return impuestos;
	}
	public void setImpuestos(float impuestos) {
		this.impuestos = impuestos;
	}
	public float getMontoocupacion() {
		return montoocupacion;
	}
	public void setMontoocupacion(float montoocupacion) {
		this.montoocupacion = montoocupacion;
	}
	public double getMontoprereserva() {
		return montoprereserva;
	}
	public void setMontoprereserva(double montoprereserva) {
		this.montoprereserva = montoprereserva;
	}
	public float getTotalgastos() {
		return totalgastos;
	}
	public void setTotalgastos(float totalgastos) {
		this.totalgastos = totalgastos;
	}
	
	///
	
}
