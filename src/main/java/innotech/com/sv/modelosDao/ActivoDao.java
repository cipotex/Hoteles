package innotech.com.sv.modelosDao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.Activo;

public interface ActivoDao  extends PagingAndSortingRepository<Activo, Long>{
	 public List<Activo> findByEmpresa(Empresa empresa);
	 public Page<Activo> findAllByEmpresa(Empresa empresa, Pageable pageable);
}
