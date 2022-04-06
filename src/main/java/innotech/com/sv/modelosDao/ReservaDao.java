package innotech.com.sv.modelosDao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.Reserva;

public interface ReservaDao  extends PagingAndSortingRepository<Reserva, Long>{
	public List<Reserva> findByEmpresa(Empresa empresa);
	
	public Page<Reserva> findAllByEmpresa(Empresa empresa, Pageable pageable);

	//solo reservas pendientes y cuya fecha de finazacion de la misma aun no se halla alcanzado
	@Query(value ="select * from reservas p where p.empresa_id= ?1 and p.fecha_fin >= CURRENT_DATE and p.estado_reserva=0", nativeQuery = true )
	public Page<Reserva> findAllByEmpresaPendientes(long empresa, Pageable pageable);
}
