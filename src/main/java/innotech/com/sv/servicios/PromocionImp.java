package innotech.com.sv.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.Promocion;
import innotech.com.sv.modelosDao.PromocionDao;

@Service
public class PromocionImp implements IPromocion {

	@Autowired
	PromocionDao promocionDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Promocion> findAll() {
		return (List<Promocion>) promocionDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Promocion> findAll(Pageable pageable) {
		return promocionDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Promocion findById(Long id) {
		return promocionDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Promocion save(Promocion promocion) {
		return promocionDao.save(promocion);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		promocionDao.deleteById(id);

	}

	@Override
	@Transactional(readOnly = true)
	public List<Promocion> findByEmpresa(long empresa) {
		// TODO Auto-generated method stub
		return promocionDao.findAllByEmpresa(empresa);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Promocion> findAllByEmpresa(Empresa empresa, Pageable pageable) {
		// TODO Auto-generated method stub
		//SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		//Date fecha = new Date();
		
		Page<Promocion> promo = promocionDao.findAllByEmpresa(empresa, pageable);
		
		return  promo;
	}

	

}
