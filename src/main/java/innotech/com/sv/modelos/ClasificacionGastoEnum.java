package innotech.com.sv.modelos;

public enum ClasificacionGastoEnum {
  ActivoFijo(0), Agua(1), Alquires(2), Alimentos(3), Electricidad(4), Internet(5), 
  Mantenimiento(6),  Remodelaciones(7), Salarios(8), Varios(9);
	
	private int id;
	
	ClasificacionGastoEnum(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public static ClasificacionGastoEnum byOrdinal(int ord) { 
		for (ClasificacionGastoEnum m : ClasificacionGastoEnum.values()) 
		  { if (m.id == ord) 
		  { return m; } 
		  }
		return null;
	}
	

}
