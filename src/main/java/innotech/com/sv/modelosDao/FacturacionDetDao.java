package innotech.com.sv.modelosDao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;


import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.FacturacionDet;

public interface FacturacionDetDao extends PagingAndSortingRepository<FacturacionDet, Long>{
	
	public List<FacturacionDet> findByEmpresa(Empresa empresa);
	 
	@Query(value = "SELECT * FROM FACTURACIONDET WHERE empresa_id = ?1 and facturacion_enc_id=?2", nativeQuery = true)
	public List<FacturacionDet> findByEmpresaFactura(long empresaid, long facturaId);
	
	@Modifying 
	@Query(value = "DELETE FROM FACTURACIONDET WHERE empresa_id = ?1 and facturacion_enc_id=?2", nativeQuery = true)
	public void deleteByEmpresaFactura(long empresaid, long facturaId);
	
}
