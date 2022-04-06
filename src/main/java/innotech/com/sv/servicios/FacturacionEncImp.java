package innotech.com.sv.servicios;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.FacturacionEnc;
import innotech.com.sv.modelosDao.FacturacionEncDao;

@Service
public class FacturacionEncImp implements IFacturacionEnc {

	@Autowired
	FacturacionEncDao facturacionDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<FacturacionEnc> findAll() {		
		return (List<FacturacionEnc>) facturacionDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<FacturacionEnc> findAll(Pageable pageable) {		
		return facturacionDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public FacturacionEnc findById(Long id) {		
		return facturacionDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public FacturacionEnc save(FacturacionEnc facturacionEnc) {	
		return facturacionDao.save(facturacionEnc);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		facturacionDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<FacturacionEnc> findByEmpresa(Empresa empresa) {
		// TODO Auto-generated method stub
		return facturacionDao.findByEmpresa(empresa);
	}

	@Override
	@Transactional(readOnly = true)
	public FacturacionEnc findByEmpresaOcupacion(long IdEmpresa, long OcupacionId) {		
		return facturacionDao.findByEmpresaOcupacion(IdEmpresa, OcupacionId);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteEmpresaOcupacion(long IdEmpresa, long OcupacionId) {
		 facturacionDao.deleteEmpresaOcupacion(IdEmpresa, OcupacionId);
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<FacturacionEnc> findFacturacionPeriodo(long IdEmpresa, Date fechaInicio, Date fechaFin) {
		// TODO Auto-generated method stub
		return facturacionDao.facturacionPeriodo(IdEmpresa, fechaInicio, fechaFin);
	}

}
