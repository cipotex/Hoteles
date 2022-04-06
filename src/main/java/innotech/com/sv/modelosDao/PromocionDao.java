package innotech.com.sv.modelosDao;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;


import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.Promocion;

public interface PromocionDao  extends PagingAndSortingRepository<Promocion, Long>{
	
	@Query(value ="select * from promociones p where p.empresa_id= ?1 and p.fechafin >= CURRENT_DATE", nativeQuery = true )
	public List<Promocion> findAllByEmpresa(long empresa);
	
	//@Query(" select p from promocion p where  p.empresa =?1 and  p.fechafin<= ?3" )
	public Page<Promocion> findAllByEmpresa(Empresa empresa, Pageable pageable ); //, Date fecha
	
	
}
