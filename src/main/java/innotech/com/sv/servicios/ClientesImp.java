package innotech.com.sv.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import innotech.com.sv.modelos.Cliente;
import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelosDao.ClientesDao;

@Service
public class ClientesImp implements IClientes {

	@Autowired
	ClientesDao clienteDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		// TODO Auto-generated method stub
		return (List<Cliente>) clienteDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return clienteDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findById(Long id) {
		// TODO Auto-generated method stub
		return clienteDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Cliente save(Cliente cliente) {
		// TODO Auto-generated method stub
		return clienteDao.save(cliente);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		clienteDao.deleteById(id);

	}

	@Override
	public Page<Cliente> findAllbyEmpresa(long empresaid, Pageable pageable) {
		// TODO Auto-generated method stub
		return clienteDao.findAllByEmpresa(empresaid, pageable);
	}

	@Override
	public List<Cliente> findAllByEmpresa(Empresa empresa) {
		// TODO Auto-generated method stub
		return clienteDao.findByEmpresa(empresa);
	}

}
