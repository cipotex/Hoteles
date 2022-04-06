package innotech.com.sv.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.Habitacion;
import innotech.com.sv.modelos.TiposHabitacion;
import innotech.com.sv.modelosDao.HabitacionDao;

@Service
public class HabitacionImp implements IHabitacion {

	@Autowired
	HabitacionDao habitacionDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Habitacion> findAll() {		
		return (List<Habitacion>) habitacionDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Habitacion> findAll(Pageable pageable) {		
		return habitacionDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Habitacion findById(Long id) {		
		return habitacionDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Habitacion save(Habitacion habitacion) {		
		return habitacionDao.save(habitacion);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		habitacionDao.deleteById(id);

	}

	@Override
	@Transactional(readOnly = true)
	public List<Habitacion> findByEmpresa(Empresa empresa) {
		// TODO Auto-generated method stub
		return habitacionDao.findAllByEmpresa(empresa);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Habitacion> findByEmpresa(Empresa empresa, Pageable pageable) {		
		return habitacionDao.findAllByEmpresa(empresa, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Habitacion> findAllByEmpresaAndTipohabitacion(Empresa empresa, TiposHabitacion tipohabitacion) {		
		return habitacionDao.findAllByEmpresaAndTipohabitacion(empresa, tipohabitacion);
	}

}
