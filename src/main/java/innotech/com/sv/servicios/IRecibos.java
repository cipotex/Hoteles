package innotech.com.sv.servicios;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import innotech.com.sv.modelos.Recibos;
import innotech.com.sv.modelos.Activo;
import innotech.com.sv.modelos.Empresa;


public interface IRecibos {
	public List<Recibos> findAll();

	public Page<Recibos> findAll(Pageable pageable);

	public Recibos findById(Long id);

	public Recibos save(Recibos recibos);

	public void delete(Long id);
	
	public List<Recibos> findByEmpresa(Empresa empresa);
	
	public Recibos findByEmpresaFactura(long IdEmpresa, long facturaid);
	
	public Page<Recibos> findAllByEmpresa(Empresa empresa, Pageable pageable);
	
	public List<Recibos> recibosPagados(long IdEmpresa, Date fechaInicio, Date fechaFin);
}
