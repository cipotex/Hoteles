package innotech.com.sv.servicios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import innotech.com.sv.modelos.Moneda;

public interface IMoneda {
	public List<Moneda> findAll();

	public Page<Moneda> findAll(Pageable pageable);

	public Moneda findById(Long id);

	public Moneda save(Moneda moneda);

	public void delete(Long id);
}
