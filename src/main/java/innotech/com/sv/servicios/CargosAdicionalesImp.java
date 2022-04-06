package innotech.com.sv.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import innotech.com.sv.modelos.CargosAdicionales;
import innotech.com.sv.modelos.Empresa;

import innotech.com.sv.modelos.Servicio;
import innotech.com.sv.modelosDao.CargosAdicionalesDao;
import innotech.com.sv.modelosreportes.CargosAdicionalesReporte;

@Service
public class CargosAdicionalesImp implements ICargosAdicionales {

	
	@Autowired
	CargosAdicionalesDao cargosAdicionalesDao;
	
	@Autowired
	IServicio servicioImp;
	
	@Override
	@Transactional(readOnly = true)
	public List<CargosAdicionales> findAll() {
		return (List<CargosAdicionales>) cargosAdicionalesDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<CargosAdicionales> findAll(Pageable pageable) {		
		return cargosAdicionalesDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public CargosAdicionales findById(Long id) {		
		return cargosAdicionalesDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public CargosAdicionales save(CargosAdicionales cargosAdicionales) {	
		return cargosAdicionalesDao.save(cargosAdicionales);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		cargosAdicionalesDao.deleteById(id);

	}

	@Override
	@Transactional(readOnly = true)
	public List<CargosAdicionales> findByEmpresa(Empresa empresa) {
		return cargosAdicionalesDao.findByEmpresa(empresa);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<CargosAdicionales> findByEmpresaOcupacion(long empresaid, long ocupacionid) {
		return cargosAdicionalesDao.findAllByEmpresaOcupacion(empresaid, ocupacionid);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<CargosAdicionales> findByEmpresa(Empresa empresa, Pageable pageable) {
		return cargosAdicionalesDao.findAllByEmpresa(empresa, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Servicio> findByTermino(String term) {
		return servicioImp.findByTermino(term);
	}
  
	@Override
	@Transactional(readOnly = true)
	public List<CargosAdicionales> findByEmpresaFacturado(long empresa, long ocupacion) {
		// TODO Auto-generated method stub
		return cargosAdicionalesDao.findAllByEmpresaFacturado(empresa, ocupacion);
	}

	@Override
	@Transactional(readOnly = true)
	public List<CargosAdicionalesReporte> findAllReporteOcupacion(long empresa, long ocupacion) {
		return cargosAdicionalesDao.findAllReporteOcupacion(empresa, ocupacion);
	}

}
