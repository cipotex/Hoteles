package innotech.com.sv.modelosDao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.TiposHabitacion;

public interface TiposHabitacionDao  extends PagingAndSortingRepository<TiposHabitacion, Long>{
	public List<TiposHabitacion> findByEmpresa(Empresa empresa);
	public Page<TiposHabitacion> findAllByEmpresa(Empresa empresa, Pageable pageable);
}
