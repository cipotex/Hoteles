package innotech.com.sv.test;

import java.util.List;

public interface CarService {

	List<String> findAllBrands();
	List<String> findAllModelsByBrand(String brand);
}