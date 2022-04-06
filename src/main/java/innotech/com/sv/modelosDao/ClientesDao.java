package innotech.com.sv.modelosDao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;


import innotech.com.sv.modelos.Cliente;
import innotech.com.sv.modelos.Empresa;


public interface ClientesDao  extends PagingAndSortingRepository<Cliente, Long>{

   public List<Cliente> findByEmpresa(Empresa empresa);
	
	@Query(value ="select * from clientes p where p.empresa_id= ?1 ", nativeQuery = true )
	public Page<Cliente> findAllByEmpresa(long empresa, Pageable pageable);
	
	
	
	
}