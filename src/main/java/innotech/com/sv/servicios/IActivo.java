package innotech.com.sv.servicios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import innotech.com.sv.modelos.Activo;
import innotech.com.sv.modelos.Empresa;


public interface IActivo {
	public List<Activo> findAll();

	public Page<Activo> findAll(Pageable pageable);

	public Activo findById(Long id);

	public Activo save(Activo inventario);

	public void delete(Long id);
	
	public List<Activo> findByEmpresa(Empresa empresa);
	
	public Page<Activo> findByEmpresa(Empresa empresa, Pageable pageable);
}
