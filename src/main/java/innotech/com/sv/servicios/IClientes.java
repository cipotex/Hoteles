package innotech.com.sv.servicios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import innotech.com.sv.modelos.Cliente;
import innotech.com.sv.modelos.Empresa;

public interface IClientes {
	public List<Cliente> findAll();
	public List<Cliente> findAllByEmpresa(Empresa empresa);

	public Page<Cliente> findAllbyEmpresa (long empresa, Pageable pageable);
	
	public Page<Cliente> findAll(Pageable pageable);

	public Cliente findById(Long id);

	public Cliente save(Cliente cliente);

	public void delete(Long id);
}
