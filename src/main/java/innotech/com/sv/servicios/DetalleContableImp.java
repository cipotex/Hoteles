package innotech.com.sv.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import innotech.com.sv.modelos.DetalleContable;
import innotech.com.sv.modelosDao.DetalleContableDao;

@Service
public class DetalleContableImp implements IDetalleContable {

	@Autowired
	DetalleContableDao detalleContableDao;

	@Override
	@Transactional(readOnly = true)
	public List<DetalleContable> findAll() {
		// TODO Auto-generated method stub
		return (List<DetalleContable>) detalleContableDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<DetalleContable> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return detalleContableDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public DetalleContable findById(Long id) {
		// TODO Auto-generated method stub
		return detalleContableDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = false)
	public DetalleContable save(DetalleContable detalle) {
		// TODO Auto-generated method stub
		return detalleContableDao.save(detalle);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Long id) {
		// TODO Auto-generated method stub
		detalleContableDao.deleteById(id);
	}
	

}
