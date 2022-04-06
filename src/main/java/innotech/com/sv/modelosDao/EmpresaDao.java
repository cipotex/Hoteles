package innotech.com.sv.modelosDao;

import org.springframework.data.repository.PagingAndSortingRepository;

import innotech.com.sv.modelos.Empresa;

public interface EmpresaDao  extends PagingAndSortingRepository<Empresa, Long>{
	
}


