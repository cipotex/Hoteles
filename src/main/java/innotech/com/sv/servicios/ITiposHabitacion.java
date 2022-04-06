package innotech.com.sv.servicios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.TiposHabitacion;

public interface ITiposHabitacion {
	public List<TiposHabitacion> findAll();

	public Page<TiposHabitacion> findAll(Pageable pageable);

	public TiposHabitacion findById(Long id);

	public TiposHabitacion save(TiposHabitacion tiposHabitacion);

	public void delete(Long id);
	
	public List<TiposHabitacion> findByEmpresa(Empresa empresa);
	
	public Page<TiposHabitacion> findAllByEmpresa(Empresa empresa, Pageable pageable);
	
	
}
