package innotech.com.sv.modelosDao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;


import innotech.com.sv.modelos.Disponibilidad;
import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.Reserva;


public interface DisponibilidadDao  extends PagingAndSortingRepository<Disponibilidad, Long>{
	 
	public List<Disponibilidad> findByEmpresa(Empresa empresa);
	
	@Query(value = "select * from disponibilidad p where p.empresa_id= ?1  and p.fecha >?2 and  p.fecha<= ?3", nativeQuery = true)  //
	public List<Disponibilidad> findOcupacionEmpresa(long empresa, Date fechaini, Date fechaFin ); 
	
	@Query(value = "select * from disponibilidad p where p.empresa_id= ?1 and p.habitacion_id = ?2 and p.fecha >?3 and  p.fecha<= ?4" , nativeQuery = true)
	public List<Disponibilidad> findOcupacionByEmpresaHabita(long empresa, long habitacion, Date fechaini, Date fechaFin ); 

	public long deleteByReserva(Reserva reserva);
}

