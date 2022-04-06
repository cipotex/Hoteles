package innotech.com.sv.modelosDao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.Usuarios;
import innotech.com.sv.modelos.UsuariosBycia;

public interface UsuariosByciaDao  extends PagingAndSortingRepository<UsuariosBycia, Long>{


    public List<UsuariosBycia> findByEmpresa(Empresa empresa);
	
	public List<UsuariosBycia> findByUsuario(Usuarios usuario);
}
