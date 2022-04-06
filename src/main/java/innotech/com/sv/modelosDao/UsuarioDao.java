package innotech.com.sv.modelosDao;

import org.springframework.data.repository.PagingAndSortingRepository;

import innotech.com.sv.modelos.Usuarios;

public interface UsuarioDao extends PagingAndSortingRepository<Usuarios, Long> {

	public Usuarios findByUsername(String username);
	
}
