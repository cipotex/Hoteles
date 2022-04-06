package innotech.com.sv.servicios;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.Gastos;
import innotech.com.sv.modelosDao.GastosDao;
import innotech.com.sv.modelosreportes.GastosPeriodo;

@Service
public class GastosImp implements IGastos {

	@Autowired
	GastosDao gastosDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Gastos> findAll() {
		// TODO Auto-generated method stub
		return (List<Gastos>) gastosDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Gastos> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return gastosDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Gastos findById(Long id) {
		// TODO Auto-generated method stub
		return gastosDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = false)
	public Gastos save(Gastos gastos) {
		// TODO Auto-generated method stub
		return gastosDao.save(gastos);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Long id) {
		// TODO Auto-generated method stub
		gastosDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Gastos> findByEmpresa(Empresa empresa) {
		// TODO Auto-generated method stub
		return gastosDao.findByEmpresa(empresa);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Gastos> findAllByEmpresa(Empresa empresa, Pageable pageable) {
		// TODO Auto-generated method stub
		return gastosDao.findAllByEmpresa(empresa, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Gastos> findGastosPeriodo(Long empresa, Date fechaInicio, Date fechaFin) {
		// TODO Auto-generated method stub
		return gastosDao.findGastosPeriodo(empresa, fechaInicio, fechaFin);
	}

	
	
	
}
