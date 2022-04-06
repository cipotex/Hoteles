package innotech.com.sv.modelos;

public enum TipoFacturaGastoEnum {
  CreditoFiscal(0), Factura(1), Recibo(2);
	
	private int id;
	
	TipoFacturaGastoEnum(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public static TipoFacturaGastoEnum byOrdinal(int ord) { 
		for (TipoFacturaGastoEnum m : TipoFacturaGastoEnum.values()) 
		  { if (m.id == ord) 
		  { return m; } 
		  }
		return null;
	}
	
	
	
}
