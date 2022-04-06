package innotech.com.sv.servicios;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import innotech.com.sv.modelos.Disponibilidad;
import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.Reserva;
import innotech.com.sv.modelosDao.DisponibilidadDao;

@Service
public class DisponibilidadImp implements IDisponibilidad {

	@Autowired
	DisponibilidadDao disponibilidadDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Disponibilidad> findAll() {		
		return (List<Disponibilidad>) disponibilidadDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Disponibilidad> findAll(Pageable pageable) {		
		return disponibilidadDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Disponibilidad findById(Long id) {		
		return disponibilidadDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Disponibilidad save(Disponibilidad disponibilidad) {		
		return disponibilidadDao.save(disponibilidad);
	}

	@Override
	@Transactional(readOnly = true)
	public void delete(Long id) {
		disponibilidadDao.deleteById(id);

	}

	@Override
	@Transactional(readOnly = true)
	public List<Disponibilidad> findByEmpresa(Empresa empresa) {
		return disponibilidadDao.findByEmpresa(empresa);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Disponibilidad> findOcupacionEmpresa(long empresa, Date fechaini, Date fechaFin) {
		return disponibilidadDao.findOcupacionEmpresa(empresa, fechaini, fechaFin);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Disponibilidad> findOcupacionByEmpresaHabita(long empresa, long habitacion, Date fechaini,
			Date fechaFin) {
		return disponibilidadDao.findOcupacionByEmpresaHabita(empresa, habitacion, fechaini, fechaFin);
	}

	@Override
	@Transactional
	public long deleteByReserva(Reserva reserva) {
		// TODO Auto-generated method stub
		return disponibilidadDao.deleteByReserva(reserva);
	}

}
