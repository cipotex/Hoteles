package innotech.com.sv.servicios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.Promocion;

public interface IPromocion {
	public List<Promocion> findAll();

	public Page<Promocion> findAll(Pageable pageable);

	public Promocion findById(Long id);

	public Promocion save(Promocion promocion);

	public void delete(Long id);
	
	public List<Promocion> findByEmpresa(long empresa);
	public Page<Promocion> findAllByEmpresa(Empresa empresa, Pageable pageable);
	//public List<Promocion> findByEmpresaId(long empresa ); //, Date fecha
	
}
