package innotech.com.sv.servicios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.Habitacion;
import innotech.com.sv.modelos.TiposHabitacion;

public interface IHabitacion {
	public List<Habitacion> findAll();

	public Page<Habitacion> findAll(Pageable pageable);

	public Habitacion findById(Long id);

	public Habitacion save(Habitacion habitacion);

	public void delete(Long id);
	
	public List<Habitacion> findByEmpresa(Empresa empresa);
	
	public Page<Habitacion> findByEmpresa(Empresa empresa, Pageable pageable);
	
	public List<Habitacion> findAllByEmpresaAndTipohabitacion(Empresa empresa, TiposHabitacion tipohabitacion);
}
