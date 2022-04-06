package innotech.com.sv.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.FacturacionDet;
import innotech.com.sv.modelosDao.FacturacionDetDao;

@Service
public class FacturacionDetImp implements IFacturacionDet {

	
	@Autowired
	FacturacionDetDao facturacionDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<FacturacionDet> findAll() {		
		return (List<FacturacionDet>) facturacionDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<FacturacionDet> findAll(Pageable pageable) {		
		return facturacionDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public FacturacionDet findById(Long id) {		
		return facturacionDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public FacturacionDet save(FacturacionDet facturacionDet) {		
		return facturacionDao.save(facturacionDet);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		facturacionDao.deleteById(id);

	}

	@Override
	@Transactional(readOnly = true)
	public List<FacturacionDet> findByEmpresa(Empresa empresa) {
		// TODO Auto-generated method stub
		return facturacionDao.findByEmpresa(empresa);
	}

	@Override
	@Transactional(readOnly = true)
	public List<FacturacionDet> findByEmpresaFactura(long empresaid, long facturaId) {
		// TODO Auto-generated method stub
		return facturacionDao.findByEmpresaFactura(empresaid, facturaId);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteByEmpresaFactura(long empresaid, long facturaId) {
		facturacionDao.deleteByEmpresaFactura(empresaid, facturaId);
		
	}

	

}
