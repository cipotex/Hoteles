package innotech.com.sv.servicios;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.Ocupacion;

public interface IOcupacion {
	public List<Ocupacion> findAll();

	public Page<Ocupacion> findAll(Pageable pageable);

	public Ocupacion findById(Long id);

	public Ocupacion save(Ocupacion ocupacion);

	public void delete(Long id);
	
	public List<Ocupacion> findByEmpresa(Empresa empresa);
	
	public Page<Ocupacion> findAllByEmpresa(long empresa, Pageable pageable ); 
	
	public List<Ocupacion> ocupacionPeriodo(long empresa, Date fechainicio, Date fechafin); 
}
