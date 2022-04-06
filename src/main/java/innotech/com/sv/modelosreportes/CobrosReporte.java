package innotech.com.sv.modelosreportes;

public class CobrosReporte {
	private long idocupacion;
	private String tipohabitacion;
	private String codigohabitacion;
	private String periodocobro;
	private float montofacturado;
    private String nombrecliente;
	
    public CobrosReporte() {
		super();
	}
    
    public CobrosReporte(long idocupacion, String tipohabitacion, String codigohabitacion, String periodocobro,
			float montofacturado, String nombrecliente) {
		super();
		this.idocupacion = idocupacion;
		this.tipohabitacion = tipohabitacion;
		this.codigohabitacion = codigohabitacion;
		this.periodocobro = periodocobro;
		this.montofacturado = montofacturado;
		this.nombrecliente = nombrecliente;
	}
	//
    public long getIdocupacion() {
		return idocupacion;
	}
	public void setIdocupacion(long idocupacion) {
		this.idocupacion = idocupacion;
	}
	public String getTipohabitacion() {
		return tipohabitacion;
	}
	public void setTipohabitacion(String tipohabitacion) {
		this.tipohabitacion = tipohabitacion;
	}
	public String getCodigohabitacion() {
		return codigohabitacion;
	}
	public void setCodigohabitacion(String codigohabitacion) {
		this.codigohabitacion = codigohabitacion;
	}
	public String getPeriodocobro() {
		return periodocobro;
	}
	public void setPeriodocobro(String periodocobro) {
		this.periodocobro = periodocobro;
	}
	public float getMontofacturado() {
		return montofacturado;
	}
	public void setMontofacturado(float montofacturado) {
		this.montofacturado = montofacturado;
	}
	public String getNombrecliente() {
		return nombrecliente;
	}
	public void setNombrecliente(String nombrecliente) {
		this.nombrecliente = nombrecliente;
	}
    

	

}
