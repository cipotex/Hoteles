package innotech.com.sv.servicios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import innotech.com.sv.modelos.ClaseServicio;
import innotech.com.sv.modelos.Empresa;

public interface IClaseServicio {
	public List<ClaseServicio> findAll();

	public Page<ClaseServicio> findAll(Pageable pageable);

	public ClaseServicio findById(Long id);

	public ClaseServicio save(ClaseServicio claseServicio);

	public void delete(Long id);
	
	public List<ClaseServicio> findByEmpresa(Empresa empresa);
	
	public Page<ClaseServicio> findAllByEmpresaActivos(long empresa, Pageable pageable);
}
