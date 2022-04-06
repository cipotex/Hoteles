package innotech.com.sv.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import innotech.com.sv.modelos.ClaseServicio;
import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelosDao.ClaseServicioDao;

@Service
public class ClaseServicioImp implements IClaseServicio {

	
	@Autowired
	ClaseServicioDao servicio;
	
	@Override
	@Transactional(readOnly = true)
	public List<ClaseServicio> findAll() {
		// TODO Auto-generated method stub
		return  ( List<ClaseServicio> ) servicio.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ClaseServicio> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return servicio.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public ClaseServicio findById(Long id) {
		// TODO Auto-generated method stub
		return servicio.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public ClaseServicio save(ClaseServicio claseServicio) {
		// TODO Auto-generated method stub
		return servicio.save(claseServicio);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		servicio.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ClaseServicio> findByEmpresa(Empresa empresa) {
		// TODO Auto-generated method stub
		return servicio.findByEmpresa(empresa);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ClaseServicio> findAllByEmpresaActivos(long empresa, Pageable pageable) {
		// TODO Auto-generated method stub
		return servicio.findAllByEmpresaActivos(empresa, pageable);
	}

}

