package innotech.com.sv.ProcesosServices;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import innotech.com.sv.modelos.Disponibilidad;
import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.EstadoReservasEnum;
import innotech.com.sv.modelos.Habitacion;
import innotech.com.sv.modelos.Reserva;
import innotech.com.sv.modelosDao.ReservaDao;
import innotech.com.sv.servicios.DisponibilidadImp;
import innotech.com.sv.servicios.EmpresaServiceImp;
import innotech.com.sv.servicios.HabitacionImp;

@Service
public class ReservaImp implements IReserva {

	@Autowired
	private DisponibilidadImp dispobilidadServicio;

	@Autowired
	private HabitacionImp habitacionesServicio;

	@Autowired
	private EmpresaServiceImp empresaServicio;

	@Autowired
	private ReservaImp reservaImpServ;

	@Autowired
	private ReservaDao reservasDao;

	@Override
	@Transactional
	public String reservar(long reserva, long empresa, long habitacion, Date fechaini, Date fechafin) {

		if (this.valida_disponibilidad(empresa, habitacion, fechaini, fechafin) == true) {
			// Se procede a insertar en la reserva

			// System.out.println("Control...1");
			Empresa empresaSer        = empresaServicio.findById(empresa);
			Reserva reservaServ       = reservaImpServ.findById(reserva);
			Habitacion habitacionServ = habitacionesServicio.findById(habitacion);
			EstadoReservasEnum estado = EstadoReservasEnum.Pendiente;

			//System.out.println("Control...2");
			
			Date fechavar = fechaini;
			//
			//System.out.println("Control...3");
			
			//
			
			
			GregorianCalendar cal = new GregorianCalendar();
			
			
			while (fechavar.compareTo(fechafin) < 0) {	
				System.out.println("Control...5");
				Disponibilidad disponibilidad = new Disponibilidad();
				disponibilidad.setEmpresa(empresaSer);
				disponibilidad.setEstado(estado);   
				disponibilidad.setReserva(reservaServ);
				disponibilidad.setHabitacion(habitacionServ);
				disponibilidad.setFecha(fechavar);
				//				
				disponibilidad.setH0("X");disponibilidad.setH1("X");disponibilidad.setH2("X");disponibilidad.setH3("X");
				disponibilidad.setH4("X");disponibilidad.setH5("X");disponibilidad.setH6("X");disponibilidad.setH7("X");
				disponibilidad.setH8("X");disponibilidad.setH9("X");disponibilidad.setH10("X");disponibilidad.setH11("X");
				disponibilidad.setH12("X");disponibilidad.setH13("X");disponibilidad.setH14("X");disponibilidad.setH15("X");
				disponibilidad.setH16("X");disponibilidad.setH17("X");disponibilidad.setH18("X");disponibilidad.setH19("X");
				disponibilidad.setH20("X");disponibilidad.setH21("X");disponibilidad.setH22("X");disponibilidad.setH23("X");
				dispobilidadServicio.save(disponibilidad);
				//
				//Sumando 1 dia
				cal.setTime(fechavar);
				cal.add(Calendar.DATE, 1);
				fechavar = cal.getTime();
				
			};
			System.out.println("Control...6");
			// Si la fecha es igual, se hara la reserva hasta la hora que se hace el check out.
			if (fechavar.compareTo(fechafin)==0) {	
				System.out.println("Control...7");
				Disponibilidad disponibilidad = new Disponibilidad();
				disponibilidad.setEmpresa(empresaSer);
				disponibilidad.setEstado(estado);   
				disponibilidad.setReserva(reservaServ);
				disponibilidad.setHabitacion(habitacionServ);
				disponibilidad.setFecha(fechafin);
				//
				disponibilidad.setFecha(fechafin);
				disponibilidad.setH0("X");disponibilidad.setH1("X");disponibilidad.setH2("X");disponibilidad.setH3("X");
				disponibilidad.setH4("X");disponibilidad.setH5("X");disponibilidad.setH6("X");disponibilidad.setH7("X");
				disponibilidad.setH8("X");disponibilidad.setH9("X");disponibilidad.setH10("X");disponibilidad.setH11("X");
				disponibilidad.setH12("X");disponibilidad.setH13("X");disponibilidad.setH14("X");disponibilidad.setH15("");
				disponibilidad.setH16("");disponibilidad.setH17("");disponibilidad.setH18("");disponibilidad.setH19("");
				disponibilidad.setH20("");disponibilidad.setH21("");disponibilidad.setH22("");disponibilidad.setH23("");
				dispobilidadServicio.save(disponibilidad);
			}
			//

		} else {
			System.out.println("La habitación no se encuentra disponible para el periodo indicado");
			return "La habitación no se encuentra disponible para el periodo indicado";

		}
		return null;
	}

	@Override
	@Transactional
	public String cancelar(Empresa empresa, Reserva reserva) {
		
		//
		String mensaje = null;
			Reserva reservaServ       = reservaImpServ.findById(reserva.getId());
		if (reservaServ.getEstadoReserva() == EstadoReservasEnum.Pendiente) {
			reservaServ.setEstadoReserva(EstadoReservasEnum.Cancelada);
			reservaImpServ.save(reservaServ);
			mensaje = "";
		} else {
			mensaje = "La reserva debe estar pendiente para Cancelarse";
		};
		return mensaje;
	}

	/*
	 * Objetivo: Indicar si la habitacion para la empresa esta disponible o no en el
	 * periodo de tiempo que se indica valor retorno: True si esta disponible,
	 * False: Si esta ocupada
	 */

	@Override
	@Transactional
	public boolean valida_disponibilidad(long empresa, long habitacion, Date fecha_ini, Date fecha_fin) {
		// TODO Auto-generated method stub

		Iterable<Disponibilidad> disponible = dispobilidadServicio.findOcupacionByEmpresaHabita(empresa, habitacion,
				fecha_ini, fecha_fin);

		boolean entro = true;

		for (Disponibilidad dis : disponible) {
			// System.out.println(" id= " + dis.getId() + " Fecha= " + dis.getFecha());
			entro = false;
		}

		return entro;
	}

	/*
	 * Objetivo: Retornar el listado de habitaciones disponibles en un periodo de
	 * tiempo de la empresa que se indique. Salida: Listado de habitaciones
	 * disponibles para la compañia en el periodo especificado.
	 */
	@Override
	public List<Habitacion> listado_disponibles(long empresa, Date fecha_ini, Date fecha_fin) {
		Empresa empresas = empresaServicio.findById(empresa);

		// Listado de habitaciones de la emrpesa
		List<Habitacion> listado = habitacionesServicio.findByEmpresa(empresas);

		// Listado de habitaciones ocupadas
		List<Disponibilidad> ocupada = dispobilidadServicio.findOcupacionEmpresa(empresas.getId(), fecha_ini,
				fecha_fin);

		// Se retornara listado de disponibles
		List<Habitacion> Disponibles = new ArrayList<Habitacion>();

		boolean usada = false;
		// Validando que la habitacion no se encuentre dentro de las ocupadas
		for (Habitacion list : listado) {
			usada = false;

			for (Disponibilidad ocupa : ocupada) {
				if (list.getCodigo() == ocupa.getHabitacion().getCodigo()) {
					usada = true;
				}
			}

			// Si la habitacion no sale como usada hay que agregarla en las disponibles
			if (!usada) {
				Disponibles.add(list);
			}

		}

		return Disponibles;
	}

	@Override
	@Transactional(readOnly = true)
	public Reserva findById(Long id) {
		// TODO Auto-generated method stub
		return reservasDao.findById(id).orElse(null);

	}

	@Override
	@Transactional
	public Reserva save(Reserva reserva) {
		// TODO Auto-generated method stub
		return reservasDao.save(reserva);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		reservasDao.deleteById(id);

	}

	@Override
	@Transactional(readOnly = true)
	public List<Reserva> findByEmpresa(Empresa empresa) {
		// TODO Auto-generated method stub
		return reservasDao.findByEmpresa(empresa);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Reserva> findAllByEmpresa(Empresa empresa, Pageable pageable) {
		// TODO Auto-generated method stub
		return reservasDao.findAllByEmpresa(empresa, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Reserva> findAllByEmpresaPendientes(long empresa, Pageable pageable) {
		// TODO Auto-generated method stub
		return  reservasDao.findAllByEmpresaPendientes(empresa, pageable);
	}

	@Override
	@Transactional
	public String procesa_reserva(long reserva, long empresa, long habitacion, Date fechaini, Date fechafin) {
		// TODO Auto-generated method stub
		return null;
	}

}
