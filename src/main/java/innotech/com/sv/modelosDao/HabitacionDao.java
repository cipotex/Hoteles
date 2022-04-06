package innotech.com.sv.modelosDao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.Habitacion;
import innotech.com.sv.modelos.TiposHabitacion;

public interface HabitacionDao extends PagingAndSortingRepository<Habitacion, Long>{
	 public List<Habitacion> findAllByEmpresa(Empresa empresa);
	 public Page<Habitacion> findAllByEmpresa(Empresa empresa, Pageable pageable);
	 public List<Habitacion> findAllByEmpresaAndTipohabitacion(Empresa empresa, TiposHabitacion tipohabitacion);
}
