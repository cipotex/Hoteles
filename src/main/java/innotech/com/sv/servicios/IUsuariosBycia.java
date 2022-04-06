package innotech.com.sv.servicios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.Usuarios;
import innotech.com.sv.modelos.UsuariosBycia;

public interface IUsuariosBycia {
	
	public List<UsuariosBycia> findByEmpresa(Empresa empresa);
	
	public List<UsuariosBycia> findByUsuario(Usuarios usuario);
	
	public List<UsuariosBycia> findAll();

	public Page<UsuariosBycia> findAll(Pageable pageable);

	public UsuariosBycia findById(Long id);

	public UsuariosBycia save(UsuariosBycia usuariosBycia);

	public void delete(Long id);
}
