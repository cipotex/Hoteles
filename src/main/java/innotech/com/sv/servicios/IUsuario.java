package innotech.com.sv.servicios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import innotech.com.sv.modelos.Usuarios;

public interface IUsuario {
	public List<Usuarios> findAll();

	public Page<Usuarios> findAll(Pageable pageable);

	public Usuarios findById(Long id);

	public Usuarios save(Usuarios ssuarios);

	public void delete(Long id);
	
}
