package innotech.com.sv.modelosDao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.FacturacionEnc;

public interface FacturacionEncDao extends PagingAndSortingRepository<FacturacionEnc, Long>{
	 
	public List<FacturacionEnc> findByEmpresa(Empresa empresa);
	 
	 @Query(value = "SELECT * FROM FACTURACIONENC WHERE empresa_id = ?1 and ocupacion_id=?2", nativeQuery = true)
	 public FacturacionEnc findByEmpresaOcupacion(long IdEmpresa, long OcupacionId);
	 
	 @Modifying 
	 @Query(value = "DELETE FROM FACTURACIONENC WHERE empresa_id = ?1 and ocupacion_id=?2", nativeQuery = true)
	 public void deleteEmpresaOcupacion(long IdEmpresa, long OcupacionId);
	 
	 @Query(value = "SELECT *  FROM FACTURACIONENC WHERE empresa_id = ?1 and fecha_ins>=?2 and fecha_ins<=?3 ", nativeQuery = true)
	 public List<FacturacionEnc> facturacionPeriodo(long IdEmpresa, Date fechaInicio, Date fechaFin);
	 	 
}
