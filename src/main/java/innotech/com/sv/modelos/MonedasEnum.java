package innotech.com.sv.modelos;

public enum MonedasEnum {
 UsdDollar, 
 MxDollar, 
 SlvColon;
	
	private int  value;
	
	private  void status(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
	
	
}
