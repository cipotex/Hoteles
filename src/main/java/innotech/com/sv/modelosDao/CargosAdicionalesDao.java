package innotech.com.sv.modelosDao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import innotech.com.sv.modelos.CargosAdicionales;
import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelosreportes.CargosAdicionalesReporte;


public interface CargosAdicionalesDao  extends PagingAndSortingRepository<CargosAdicionales, Long>{
	 public List<CargosAdicionales> findByEmpresa(Empresa empresa);
	 
	 @Query(value ="select * from cargosadicionales p where p.empresa_id =?1 and p.estado=0", nativeQuery = true )
	 public Page<CargosAdicionales> findAllByEmpresa(Empresa empresa, Pageable pageable);
	  

	 @Query(value ="select * from cargosadicionales p where p.empresa_id =?1 and p.ocupacion_id=?2 and p.estado=0 ", nativeQuery = true )
	 public List<CargosAdicionales> findAllByEmpresaOcupacion(long empresa, long ocupacion);
	 
	 @Query(value ="select * from cargosadicionales p where p.empresa_id =?1 and p.ocupacion_id=?2 and p.estado=2 ", nativeQuery = true )
	 public List<CargosAdicionales> findAllByEmpresaFacturado(long empresa, long ocupacion);
	 
	/* @Query(value =" SELECT AD.id, AD.cantidad, AD.precio_unitario, AD.total , (SELECT DESCRIPCION FROM SERVICIOS B WHERE B.ID = AD.SERVICIO_ID) descripcion \r\n"
	 		       + " FROM CARGOSADICIONALES AD where AD.empresa_id =?1 and AD.ocupacion_id=?2 and AD.estado=0 ", nativeQuery = true )
	 		       */
	 @Query(value =" select AD.id, AD.cantidad, AD.precio_unitario, AD.total ,  null descripcion \r\n"
		       + " from cargosadicionales AD where AD.empresa_id =?1 and AD.ocupacion_id=?2 and AD.estado=0 ", nativeQuery = true )
	 public List<CargosAdicionalesReporte> findAllReporteOcupacion(long empresa, long ocupacion);
	 
}
 
