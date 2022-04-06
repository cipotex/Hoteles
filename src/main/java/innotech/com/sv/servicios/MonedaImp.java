package innotech.com.sv.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import innotech.com.sv.modelos.Moneda;
import innotech.com.sv.modelosDao.MonedasDao;

@Service
public class MonedaImp implements IMoneda {

	@Autowired
	MonedasDao monedasDao;
	
	@Override
	public List<Moneda> findAll() {
		// TODO Auto-generated method stub
		return (List<Moneda>) monedasDao.findAll();
	}

	@Override
	public Page<Moneda> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return monedasDao.findAll(pageable);
	}

	@Override
	public Moneda findById(Long id) {
		// TODO Auto-generated method stub
		return monedasDao.findById(id).orElse(null);
	}

	@Override
	public Moneda save(Moneda moneda) {
		// TODO Auto-generated method stub
		return monedasDao.save(moneda);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		monedasDao.deleteById(id);
	}

}
