package innotech.com.sv.servicios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.FacturacionDet;

public interface IFacturacionDet {
	public List<FacturacionDet> findAll();

	public Page<FacturacionDet> findAll(Pageable pageable);

	public FacturacionDet findById(Long id);

	public FacturacionDet save(FacturacionDet facturacionDet);

	public void delete(Long id);
	
	public List<FacturacionDet> findByEmpresa(Empresa empresa);
	
	public List<FacturacionDet> findByEmpresaFactura(long empresaid, long facturaId);
	
	public void deleteByEmpresaFactura(long empresaid, long facturaId);
}
