package innotech.com.sv.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import innotech.com.sv.modelos.Activo;
import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelosDao.ActivoDao;


@Service
public class ActivoImp implements IActivo {

	@Autowired
	ActivoDao inventarioDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Activo> findAll() {		
		return (List<Activo>) inventarioDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Activo> findAll(Pageable pageable) {		
		return inventarioDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Activo findById(Long id) {		
		return inventarioDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Activo save(Activo inventario) {		
		return inventarioDao.save(inventario);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		inventarioDao.deleteById(id);

	}

	
	@Override
	@Transactional(readOnly = true)	
	public Page<Activo> findByEmpresa(Empresa empresa, Pageable pageable) {
		// TODO Auto-generated method stub
		return inventarioDao.findAllByEmpresa(empresa, pageable);
	}

	@Override
	@Transactional(readOnly = true)	
	public List<Activo> findByEmpresa(Empresa empresa) {
		// TODO Auto-generated method stub
		return inventarioDao.findByEmpresa(empresa);
	}

	


}
