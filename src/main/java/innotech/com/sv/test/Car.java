package innotech.com.sv.test;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


public class Car {

	private @Getter @Setter String brand;
	private @Getter @Setter String model;
	
	public Car() {
		
	}
	
	public Car(String brand, String model) {		
		this.brand = brand;
		this.model = model;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	
	
}