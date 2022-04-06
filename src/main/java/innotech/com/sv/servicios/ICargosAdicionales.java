package innotech.com.sv.servicios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import innotech.com.sv.modelos.CargosAdicionales;
import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.Servicio;
import innotech.com.sv.modelosreportes.CargosAdicionalesReporte;

public interface ICargosAdicionales {
	public List<CargosAdicionales> findAll();

	public Page<CargosAdicionales> findAll(Pageable pageable);

	public CargosAdicionales findById(Long id);

	public CargosAdicionales save(CargosAdicionales cargosAdicionales);

	public void delete(Long id);
	
	public List<CargosAdicionales> findByEmpresa(Empresa empresa);
	
	public Page<CargosAdicionales> findByEmpresa(Empresa empresa, Pageable pageable);
	
	public List<Servicio> findByTermino(String term);
	
	public List<CargosAdicionales> findByEmpresaOcupacion(long empresa, long ocupacion);
	
	public List<CargosAdicionales> findByEmpresaFacturado(long empresa, long ocupacion);
	
	public List<CargosAdicionalesReporte> findAllReporteOcupacion(long empresa, long ocupacion);
}
