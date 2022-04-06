package innotech.com.sv.ProcesosServices;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.Habitacion;
import innotech.com.sv.modelos.Reserva;

public interface IReserva {
	public String procesa_reserva(long reserva, long empresa, long habitacion, Date fechaini, Date fechafin);
	public String reservar(long reserva, long empresa, long habitacion, Date fechaini, Date fechafin) ;
	public String cancelar(Empresa empresa, Reserva reserva) ;
	public boolean valida_disponibilidad(long empresa, long habitacion, Date fecha_ini, Date fecha_fin);	
	public List<Habitacion> listado_disponibles(long empresa, Date fecha_ini, Date fecha_fin) ;
	//
	//public List<Servicio> findAll();

	//public Page<Servicio> findAll(Pageable pageable);

	public Reserva findById(Long id);

	public Reserva save(Reserva reserva);

	public void delete(Long id);
	
	public List<Reserva> findByEmpresa(Empresa empresa);
	
	public Page<Reserva> findAllByEmpresa(Empresa empresa, Pageable pageable);
	
	public Page<Reserva> findAllByEmpresaPendientes(long empresa, Pageable pageable);
}
