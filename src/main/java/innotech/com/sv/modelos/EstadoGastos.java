package innotech.com.sv.modelos;

public enum EstadoGastos {
	Pendiente(0), Aprovado(1), Rechazado(2);

	private int estado;

	EstadoGastos(int estado) {
		this.estado = estado;
	}

	public int getEstado() {
		return estado;
	}

	public static EstadoGastos byOrdinal(int ord) { 
		for (EstadoGastos m : EstadoGastos.values()) 
		  { if (m.estado == ord) 
		  { return m; } 
		  }
		return null;
	}

		
}
