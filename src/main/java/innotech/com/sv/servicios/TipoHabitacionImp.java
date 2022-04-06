package innotech.com.sv.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.TiposHabitacion;
import innotech.com.sv.modelosDao.TiposHabitacionDao;

@Service
public class TipoHabitacionImp implements ITiposHabitacion {

	@Autowired
	TiposHabitacionDao tipoHabitacion;
	
	@Override
	@Transactional(readOnly = true)
	public List<TiposHabitacion> findAll() {
		return (List<TiposHabitacion>) tipoHabitacion.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<TiposHabitacion> findAll(Pageable pageable) {
		return tipoHabitacion.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public TiposHabitacion findById(Long id) {
		return tipoHabitacion.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public TiposHabitacion save(TiposHabitacion tiposHabitacion) {
		return tipoHabitacion.save(tiposHabitacion);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		tipoHabitacion.deleteById(id);

	}

	@Override
	@Transactional(readOnly = true)
	public List<TiposHabitacion> findByEmpresa(Empresa empresa) {
		// TODO Auto-generated method stub
		return tipoHabitacion.findByEmpresa(empresa);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<TiposHabitacion> findAllByEmpresa(Empresa empresa, Pageable pageable) {
		// TODO Auto-generated method stub
		return tipoHabitacion.findAllByEmpresa(empresa, pageable);
		//return null;
	}

	

}
