package innotech.com.sv.servicios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import innotech.com.sv.modelos.DetalleContable;

public interface IDetalleContable {
	public List<DetalleContable> findAll();

	public Page<DetalleContable> findAll(Pageable pageable);

	public DetalleContable findById(Long id);

	public DetalleContable save(DetalleContable detalle);

	public void delete(Long id);
}
