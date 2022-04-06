package innotech.com.sv.modelosDao;

import org.springframework.data.repository.PagingAndSortingRepository;

import innotech.com.sv.modelos.Moneda;

public interface MonedasDao  extends PagingAndSortingRepository<Moneda, Long>{
	
}

