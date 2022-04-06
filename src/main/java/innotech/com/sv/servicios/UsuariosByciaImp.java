package innotech.com.sv.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.Usuarios;
import innotech.com.sv.modelos.UsuariosBycia;
import innotech.com.sv.modelosDao.UsuariosByciaDao;

@Service
public class UsuariosByciaImp implements IUsuariosBycia {

	@Autowired
	UsuariosByciaDao usuariosByciaDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<UsuariosBycia> findByEmpresa(Empresa empresa) {
		// TODO Auto-generated method stub
		return usuariosByciaDao.findByEmpresa(empresa);
	}

	@Override
	@Transactional(readOnly = true)
	public List<UsuariosBycia> findByUsuario(Usuarios usuario) {
		// TODO Auto-generated method stub
		return usuariosByciaDao.findByUsuario(usuario);
	}

	@Override
	@Transactional(readOnly = true)
	public List<UsuariosBycia> findAll() {
		// TODO Auto-generated method stub
		return (List<UsuariosBycia>) usuariosByciaDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<UsuariosBycia> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return usuariosByciaDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public UsuariosBycia findById(Long id) {
		// TODO Auto-generated method stub
		return usuariosByciaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public UsuariosBycia save(UsuariosBycia usuariosBycia) {
		// TODO Auto-generated method stub
		return usuariosByciaDao.save(usuariosBycia);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		usuariosByciaDao.deleteById(id);

	}

}
