package innotech.com.sv.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.Servicio;
import innotech.com.sv.modelosDao.ServicioDao;

@Service
public class ServicioImp implements IServicio {

	@Autowired
	ServicioDao servicioDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Servicio> findAll() {
		return (List<Servicio>) servicioDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Servicio> findAll(Pageable pageable) {
		return servicioDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Servicio findById(Long id) {
		return servicioDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Servicio save(Servicio servicio) {
		return servicioDao.save(servicio);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		servicioDao.deleteById(id);

	}

	@Override
	@Transactional(readOnly = true)
	public List<Servicio> findByEmpresa(Empresa empresa) {
		// TODO Auto-generated method stub
		return servicioDao.findByEmpresa(empresa);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Servicio> findAllByEmpresaPendientes(long empresa, Pageable pageable) {
		// TODO Auto-generated method stub
		return servicioDao.findAllByEmpresaPendientes(empresa, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Servicio> findByTermino(String term) {
		// TODO Auto-generated method stub
		return servicioDao.findByNombre(term);
	}

}
