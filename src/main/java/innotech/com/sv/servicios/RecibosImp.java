package innotech.com.sv.servicios;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import innotech.com.sv.modelos.Activo;
import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelosDao.RecibosDao;
import innotech.com.sv.modelos.Recibos;

@Service
public class RecibosImp implements IRecibos {

	@Autowired
    RecibosDao recibos;	
	
	@Override
	@Transactional(readOnly = true)
	public List<Recibos> findAll() {
		// TODO Auto-generated method stub
		return (List<Recibos>) recibos.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Recibos> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return recibos.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Recibos findById(Long id) {
		// TODO Auto-generated method stub
		return  recibos.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = false)
	public Recibos save(Recibos recibos) {
		// TODO Auto-generated method stub
		return this.recibos.save(recibos);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Long id) {
		// TODO Auto-generated method stub
		recibos.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Recibos> findByEmpresa(Empresa empresa) {
		// TODO Auto-generated method stub
		return recibos.findByEmpresa(empresa);
	}

	@Override
	@Transactional(readOnly = true)
	public Recibos findByEmpresaFactura(long IdEmpresa, long facturaid) {
		// TODO Auto-generated method stub
		return recibos.findByEmpresaFactura(IdEmpresa, facturaid);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Recibos> findAllByEmpresa(Empresa empresa, Pageable pageable) {
		// TODO Auto-generated method stub
		return recibos.findAllByEmpresa(empresa, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Recibos> recibosPagados(long IdEmpresa, Date fechaInicio, Date fechaFin) {
		
		return recibos.recibosPagados(IdEmpresa, fechaInicio, fechaFin);
	}
}
