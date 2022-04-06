package innotech.com.sv.modelosDao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.Servicio;

public interface ServicioDao  extends PagingAndSortingRepository<Servicio, Long>{
	   public List<Servicio> findByEmpresa(Empresa empresa);
	
	   //solo reservas pendientes y cuya fecha de finazacion de la misma aun no se halla alcanzado
		@Query(value ="select * from servicios p where p.empresa_id= ?1 and p.estado=0", nativeQuery = true )
		public Page<Servicio> findAllByEmpresaPendientes(long empresa, Pageable pageable);
		
		@Query(value ="select * from servicios p where p.nombre like %?1% and p.estado=0", nativeQuery = true )
		 public List<Servicio> findByNombre(String term);
}
