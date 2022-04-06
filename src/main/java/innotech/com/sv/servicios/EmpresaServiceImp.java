package innotech.com.sv.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelosDao.EmpresaDao;



@Service
public class EmpresaServiceImp implements IEmpresaService {

	@Autowired
	EmpresaDao empresaDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Empresa> findAll() {
		return (List<Empresa>) empresaDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Empresa> findAll(Pageable pageable) {
		return empresaDao.findAll(pageable);
	}

	@Override
	@Transactional
	public Empresa findById(Long id) {
		return empresaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Empresa save(Empresa empresa) {
		return empresaDao.save(empresa);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		empresaDao.deleteById(id);

	}

}
