package innotech.com.sv.servicios;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.FacturacionEnc;

public interface IFacturacionEnc {
	public List<FacturacionEnc> findAll();

	public Page<FacturacionEnc> findAll(Pageable pageable);

	public FacturacionEnc findById(Long id);

	public FacturacionEnc save(FacturacionEnc facturacionEnc);

	public void delete(Long id);
	
	public List<FacturacionEnc> findByEmpresa(Empresa empresa);
	
	public FacturacionEnc findByEmpresaOcupacion(long IdEmpresa, long OcupacionId);
	
	public void deleteEmpresaOcupacion(long IdEmpresa, long OcupacionId);
	
	public List<FacturacionEnc> findFacturacionPeriodo(long IdEmpresa, Date fechaInicio, Date fechaFin);
}
