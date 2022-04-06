package innotech.com.sv.servicios;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.Ocupacion;
import innotech.com.sv.modelosDao.OcupacionDao;

@Service
public class OcupacionImp implements IOcupacion {

	@Autowired
	OcupacionDao ocupacionDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Ocupacion> findAll() {		
		return (List<Ocupacion>) ocupacionDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Ocupacion> findAll(Pageable pageable) {		
		return ocupacionDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Ocupacion findById(Long id) {		
		return ocupacionDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Ocupacion save(Ocupacion ocupacion) {		
		return ocupacionDao.save(ocupacion);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		ocupacionDao.deleteById(id);;

	}

	@Override
	@Transactional(readOnly = true)
	public List<Ocupacion> findByEmpresa(Empresa empresa) {
		// TODO Auto-generated method stub
		return ocupacionDao.findByEmpresa(empresa);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Ocupacion> findAllByEmpresa(long empresa, Pageable pageable) {
		// TODO Auto-generated method stub
		return ocupacionDao.findAllByEmpresa(empresa, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Ocupacion> ocupacionPeriodo(long empresa, Date fechainicio, Date fechafin) {
		// TODO Auto-generated method stub
		return ocupacionDao.ocupacionPeriodo(empresa, fechainicio, fechafin);
	}

}
