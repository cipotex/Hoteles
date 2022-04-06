package innotech.com.sv.servicios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.Reserva;

public interface IReserva {
	public List<Reserva> findAll();

	public Page<Reserva> findAll(Pageable pageable);

	public Reserva findById(Long id);

	public Reserva save(Reserva reserva);

	public void delete(Long id);
	
	public List<Reserva> findByEmpresa(Empresa empresa);
}
