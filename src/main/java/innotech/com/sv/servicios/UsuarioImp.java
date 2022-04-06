package innotech.com.sv.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import innotech.com.sv.modelos.Usuarios;
import innotech.com.sv.modelosDao.UsuarioDao;

public class UsuarioImp implements IUsuario {

	@Autowired
    UsuarioDao usuarioDao;

	@Override
	@Transactional(readOnly = true)
	public List<Usuarios> findAll() {
		// TODO Auto-generated method stub
		return (List<Usuarios>) usuarioDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Usuarios> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return usuarioDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Usuarios findById(Long id) {
		// TODO Auto-generated method stub
		return usuarioDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Usuarios save(Usuarios ssuarios) {
		// TODO Auto-generated method stub
		return usuarioDao.save(ssuarios);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		usuarioDao.deleteById(id);
	}

	
}
