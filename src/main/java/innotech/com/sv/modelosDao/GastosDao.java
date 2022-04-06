package innotech.com.sv.modelosDao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.Gastos;
import innotech.com.sv.modelosreportes.GastosPeriodo;

public interface GastosDao extends PagingAndSortingRepository<Gastos, Long>{

	public List<Gastos> findByEmpresa(Empresa empresa);
	
	@Query(value ="select * from gastos p where p.empresa_id= ?1 and p.estadogastos=0", nativeQuery = true )
	public Page<Gastos> findAllByEmpresa(Empresa empresa, Pageable pageable);
	
	//Gastos por periodo de una empresa APROVADOS
	@Query(value ="select * from gastos p where p.empresa_id= ?1 and  p.fechafactura >= ?2 and  p.fechafactura <= ?3 and p.estadogastos=1", nativeQuery = true )
	public List<Gastos> findGastosPeriodo(Long empresa, Date fechaInicio, Date fechaFin);
	
	
	
}
