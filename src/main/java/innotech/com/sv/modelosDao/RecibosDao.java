package innotech.com.sv.modelosDao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import innotech.com.sv.modelos.Activo;
import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.FacturacionEnc;
import innotech.com.sv.modelos.Recibos;

public interface RecibosDao extends PagingAndSortingRepository<Recibos, Long>{
	 
	public List<Recibos> findByEmpresa(Empresa empresa);
	
	@Query(value = "SELECT * FROM RECIBOS WHERE empresa_id = ?1 and estado_recibo=0", nativeQuery = true)
	public Page<Recibos> findAllByEmpresa(Empresa empresa, Pageable pageable);
	 
	 @Query(value = "SELECT * FROM RECIBOS WHERE empresa_id = ?1 and factura_id=?2", nativeQuery = true)
	 public Recibos findByEmpresaFactura(long IdEmpresa, long facturaid);
	 
	 @Query(value = "SELECT *  FROM RECIBOS WHERE empresa_id = ?1 and fecha_pago>=?2 and fecha_pago<=?3 and estado_recibo=2", nativeQuery = true)
	 public List<Recibos> recibosPagados(long IdEmpresa, Date fechaInicio, Date fechaFin);
	 
	/* @Modifying 
	 @Query(value = "DELETE FROM FACTURACIONENC WHERE empresa_id = ?1 and ocupacion_id=?2", nativeQuery = true)
	 public void deleteEmpresaOcupacion(long IdEmpresa, long OcupacionId);*/
}
