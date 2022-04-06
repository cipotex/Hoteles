package innotech.com.sv.servicios;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.Gastos;
import innotech.com.sv.modelosreportes.GastosPeriodo;


public interface IGastos {
	public List<Gastos> findAll();

	public Page<Gastos> findAll(Pageable pageable);

	public Gastos findById(Long id);

	public Gastos save(Gastos Gastos);

	public void delete(Long id);
	
	public List<Gastos> findByEmpresa(Empresa empresa);
	
	public Page<Gastos> findAllByEmpresa(Empresa empresa, Pageable pageable ); 
	
	//Gastos aprobados por periodo
	public List<Gastos> findGastosPeriodo(Long empresa, Date fechaInicio, Date fechaFin);
	
	
}
