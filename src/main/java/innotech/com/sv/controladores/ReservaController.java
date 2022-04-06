
package innotech.com.sv.controladores;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import innotech.com.sv.ProcesosServices.GeneraContabilidad;
import innotech.com.sv.ProcesosServices.Miscelaneos;
import innotech.com.sv.ProcesosServices.ReservaImp;
import innotech.com.sv.modelos.Cliente;
import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.EstadoReservasEnum;
import innotech.com.sv.modelos.EstadosEnum;
import innotech.com.sv.modelos.Habitacion;
import innotech.com.sv.modelos.Ocupacion;
import innotech.com.sv.modelos.PeriodoReservaEnum;
import innotech.com.sv.modelos.Promocion;
import innotech.com.sv.modelos.Reserva;
import innotech.com.sv.modelos.TiposHabitacion;
import innotech.com.sv.paginator.PageRender;
import innotech.com.sv.servicios.ClientesImp;
import innotech.com.sv.servicios.DisponibilidadImp;
import innotech.com.sv.servicios.EmpresaServiceImp;
import innotech.com.sv.servicios.HabitacionImp;
import innotech.com.sv.servicios.OcupacionImp;
import innotech.com.sv.servicios.PromocionImp;
import innotech.com.sv.servicios.TipoHabitacionImp;

@Controller
@SessionAttributes({ "reserva", "empresa", "promocion", "tiposhabitaciones", "habitaciones", "precio", "clientes" })
@RequestMapping("/reserva")
public class ReservaController {
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	PromocionImp promocionServ;

	@Value("${innotec.com.contabilizar}")
	String contabilizar;

	@Value("${innotec.com.elementosPorPagina}")
	String elementos;

	@Autowired
	EmpresaServiceImp empresaServ;

	@Autowired
	Empresa mieempresa;

	@Autowired
	ReservaImp reservaServimp;

	@Autowired
	HabitacionImp habitacionServImp;

	@Autowired
	TipoHabitacionImp tipoHabitacionServImp;

	@Autowired
	DisponibilidadImp disponibilidadServImp;

	@Autowired
	ClientesImp clientesServImp;

	@Autowired
	OcupacionImp ocupacionServImp;

	@Autowired
	GeneraContabilidad generacontabilidad;

	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public String inicial(@RequestParam(name = "page", defaultValue = "0") int page, Model modelo,
			HttpServletRequest request) {

		int elemento = Integer.parseInt(this.elementos);

		HttpSession misession = request.getSession(true);
		mieempresa = (Empresa) misession.getAttribute("empresaCart");

		Pageable pageRequest = PageRequest.of(page, elemento);

		// Page<Reserva> reserva = reservaServimp.findAllByEmpresa(mieempresa,
		// pageRequest) ;
		Page<Reserva> reserva = reservaServimp.findAllByEmpresaPendientes(mieempresa.getId(), pageRequest);

		List<Habitacion> habitacion = null;

		List<Promocion> promociones = promocionServ.findByEmpresa(mieempresa.getId());

		List<Cliente> cliente = clientesServImp.findAllByEmpresa(mieempresa);

		for (Promocion promo : promociones) {
			System.out.println(" id promocion -->" + promo.getId());
		}

		PageRender<Reserva> pageRender = new PageRender<>("/reserva/listar", reserva, elemento);

		List<TiposHabitacion> tiposHabitacion = tipoHabitacionServImp.findByEmpresa(mieempresa);

		System.out.println(mieempresa.getNombre());

		String mensaje = (String) misession.getAttribute("mensaje");

		modelo.addAttribute("mensaje", mensaje);

		modelo.addAttribute("titulo", "Mantenimiento de Reservas");
		modelo.addAttribute("datos", reserva);
		modelo.addAttribute("empresa", mieempresa);
		modelo.addAttribute("clientes", cliente);
		modelo.addAttribute("page", pageRender);
		modelo.addAttribute("habitaciones", habitacion);
		modelo.addAttribute("promocion", promociones);
		modelo.addAttribute("tiposhabitaciones", tiposHabitacion);
		modelo.addAttribute("pendiente", EstadoReservasEnum.Pendiente);
		return "reserva/listar";
	};

	@RequestMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Reserva reserva = reservaServimp.findById(id);
		if (reserva == null) {
			flash.addAttribute("error", "La reserva no existe en la Base de datos");
			return "redirect:/reserva/listar";
		}
		//
		model.put("reserva", reserva);
		model.put("titulo", "Detalle Reserva: " + reserva.getId());
		model.put("datos", reserva);
		//
		return "reserva/ver";
	}

	// @GetMapping(value="/ajax/habitaciones/{tipohabitacion}")
	@RequestMapping(value = "/ajaxhabita")
	public String ajaxBrands(@RequestParam("tipohabitacion") long tipo, Model modelo, HttpServletRequest request) {
		// long tipo = (long) 1;
		System.out.println("Mensaje desde /ajax/habitaciones");

		HttpSession misession = request.getSession(true);
		mieempresa = (Empresa) misession.getAttribute("empresaCart");

		TiposHabitacion tipohabitacion = tipoHabitacionServImp.findById(tipo);

		List<Habitacion> habitacion = habitacionServImp.findAllByEmpresaAndTipohabitacion(mieempresa, tipohabitacion);

		for (Habitacion habita : habitacion) {
			System.out.println("Desde Controller --> " + habita.getTipohabitacion().getId());
		}

		modelo.addAttribute("habitaciones", habitacion);

		// return "redirect:reserva/form";
		return "reserva/form :: models";
		// return "redirect:/reserva/listar";
	}

	@RequestMapping(value = "/ajaxprecio")
	public String ajaxPeriodoReserva(@RequestParam("tipohabitacion") long tipo,
			@RequestParam("periodoReserva") PeriodoReservaEnum periodo, Model modelo, HttpServletRequest request) {
		// long tipo = (long) 1;
		System.out.println("Mensaje desde /ajax/ajaxPeriodoReserva periodo= " + periodo);

		HttpSession misession = request.getSession(true);
		mieempresa = (Empresa) misession.getAttribute("empresaCart");

		TiposHabitacion tipohabitacion = tipoHabitacionServImp.findById(tipo);

		double precio;

		switch (periodo) {
		case Dia:
			precio = tipohabitacion.getPreciodia();
			break;
		case Semana:
			precio = tipohabitacion.getPreciosemana();
			break;
		case Mes:
			precio = tipohabitacion.getPreciomes();
			break;
		default:
			precio = 0;
			break;
		}
		// System.out.println(" tipohabitacion= " +tipohabitacion.getDescripcion() + "
		// Precio= " + precio);

		modelo.addAttribute("precio", precio);

		return "reserva/form :: precio";

	}

	@RequestMapping(value = "/procesar/{id}")
	public String procesar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		//
		if (id > 0) {
			Reserva reserva = reservaServimp.findById(id);
			if (reserva == null) {
				flash.addFlashAttribute("error", " El Id de la reserva no existe en la Base de datos");
				return "redirect:/reserva/listar";
			}

			if (reserva.getEstadoReserva() != EstadoReservasEnum.Pendiente) {
				flash.addFlashAttribute("error", " La reserva no esta en estado Pendiente y no se puede procesar");
				return "redirect:/reserva/listar";
			}

			if (reserva.getMontoReservaConDescuento() == 0) {
				flash.addFlashAttribute("error",
						" Error el total de Reserva con descuento esta en nulo. Genere nuevamente la reserva");
				return "redirect:/reserva/listar";
			}

			model.put("titulo", "Procesamiento de Ocupaciones");
			model.put("datos", reserva);
			return "/reserva/procesar";

		} else {
			flash.addFlashAttribute("error", id + " Id de Reserva no existe");
			return "redirect:/empresa/listar";
		}

	};

	@RequestMapping(value = "/activaocupacion/{id}")
	public String ActivaOcupcion(@PathVariable(value = "id") Long id, Map<String, Object> model,
			RedirectAttributes flash, SessionStatus status) {
		//
		if (id > 0) {
			Reserva reserva = reservaServimp.findById(id);
			//
			Ocupacion ocupacion = new Ocupacion();
			//
			reserva.setEstadoReserva(EstadoReservasEnum.Activa);
			// almacenando los valores para ocupacion
			ocupacion.setEmpresa(reserva.getEmpresa());
			ocupacion.setFechaInicioOcupacion(reserva.getFechaInicio());
			ocupacion.setFechaFinOcupacion(reserva.getFechaFin());
			ocupacion.setEstado(EstadosEnum.Activo);
			ocupacion.setReserva(reserva);
			//
			reservaServimp.save(reserva);
			ocupacionServImp.save(ocupacion);

			// Validando si se esta contabilizando
			if (contabilizar.equalsIgnoreCase("S")) {
				// Validando si se tiene Anticipo de Reserva
				if (reserva.getMontoPreReserva() > 0) {
					generacontabilidad.registraAnticipoReserva(reserva, ocupacion);
				}

				// Validando si se tiene Garantia
				if (reserva.getMontoDeposito() > 0) {
					generacontabilidad.registraGarantia(reserva, ocupacion);
				}
			}
			;

			status.setComplete();
			//

			flash.addFlashAttribute("success", " Reserva efectuada con éxito");
			return "redirect:/reserva/listar";

		} else {
			flash.addFlashAttribute("error", id + " Id de Reserva no existe");
			return "redirect:/reserva/listar";
		}

	};

	@RequestMapping(value = "/form")
	public String form(Model modelo) {
		Reserva reserva = new Reserva();
		// ---
		modelo.addAttribute("titulo", "Creación de Reservas");
		modelo.addAttribute("reserva", reserva);

		return "reserva/form";
	};

	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String salvar(@Valid @ModelAttribute(value = "reserva") Reserva reserva, BindingResult result, Model model,
			RedirectAttributes flash, SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Creación de Reservas");
			return "reserva/form";
		} else {
			// Validaciones fechas de la reserva
			Date fechaini = reserva.getFechaInicio();
			Date fechafin = reserva.getFechaFin();
			Date fechaactual = new Date();
			//
			if ((Miscelaneos.restafechas(fechaini, fechaactual)) > 0.5) {
				String mensajeFlash = "La fecha Inicial debe ser mayor o igual a la fecha Actual";
				flash.addFlashAttribute("error", mensajeFlash);
				model.addAttribute("error", mensajeFlash);
				model.addAttribute("titulo", "Edicion de Reservas");
				return "reserva/form";
			}
			;

			if (fechaini.compareTo(fechafin) >= 0) {
				String mensajeFlash = "La fecha final debe ser mayor a la inicial";
				flash.addFlashAttribute("success", mensajeFlash);
				model.addAttribute("error", mensajeFlash);
				model.addAttribute("titulo", "Edicion de Reservas");
				return "reserva/form";
			}
			;

			// validando que el periodo de cobro este acorde a las fechas
			// si el periodo son 7 dias, deben de haber 7 dias entre una fecha y otra
			// si es un mes deben de estar de dia del mes al dia del mes proximo.
			switch (reserva.getPeriodoreserva()) {
			case Semana:
				int diasreserva = Miscelaneos.restafechas(fechaini, fechafin);
				double dias = diasreserva % 7;
				if (dias != 0) {
					String mensajeFlash = "El periodo de cobro en Semana y las fechas de reserva no son consistentes (El periodo de reserva debe abarcar semanas completas).";
					flash.addFlashAttribute("success", mensajeFlash);
					model.addAttribute("error", mensajeFlash);
					model.addAttribute("titulo", "Edicion de Reservas");
					return "reserva/form";
				}
				;
				break;
			case Mes:
				int diafehainicial = fechaini.getDate();
				int diafehafinal = fechafin.getDate();
				if (diafehainicial != diafehafinal) {
					String mensajeFlash = "El periodo de cobro en Mes y las fechas de reserva no son consistentes (El periodo de reserva mensual debe abarcar el mismo dia del mes de inicio y final).";
					flash.addFlashAttribute("success", mensajeFlash);
					model.addAttribute("error", mensajeFlash);
					model.addAttribute("titulo", "Edicion de Reservas");
					return "reserva/form";
				}
				;
				break;
			default:
				break;

			}

			// Validando disponibilidad de la habitacion
			boolean disponible = reservaServimp.valida_disponibilidad(reserva.getEmpresa().getId(),
					reserva.getHabitacion().getId(), reserva.getFechaInicio(), reserva.getFechaFin());
			//
			// System.out.println( "Disponible habitacion " + disponible);
			if (!disponible) {
				String mensajeFlash = "Para el periodo de tiempo especificado la habitacion "
						+ reserva.getHabitacion().getCodigo() + " no esta Disponible";
				flash.addFlashAttribute("success", mensajeFlash);
				model.addAttribute("error", mensajeFlash);
				model.addAttribute("titulo", "Edicion de Reservas");
				return "reserva/form";
			}

			// Colocando reserva en Pendiente
			reserva.setEstadoReserva(EstadoReservasEnum.Pendiente);
			// Calculando dias ocupacion
			reserva.setDiasOcupacion(Miscelaneos.restafechas(fechaini, fechafin));

			String mensajeFlash = !(String.valueOf(reserva.getId()).isEmpty()) ? "Reserva Editada con éxito"
					: " Reserva guardada con éxito ";
			reservaServimp.save(reserva);
			// *****
			// ahora que ya se guardo el encabezado de la reserva procedemos a guardar la
			// ocupacion
			String resp = reservaServimp.reservar(reserva.getId(), reserva.getEmpresa().getId(),
					reserva.getHabitacion().getId(), fechaini, fechafin);
			// ****
			if (resp != null) {
				flash.addFlashAttribute("error", resp);
				flash.addFlashAttribute("success", resp);
				model.addAttribute("error", resp);
				model.addAttribute("titulo", "Edicion de Reservas");
				return "reserva/form";
			}
			;

			model.addAttribute("titulo", "Creación de Reservas");
			status.setComplete();
			flash.addFlashAttribute("success", mensajeFlash);

			return "redirect:/reserva/listar";
		}
	};

	@RequestMapping(value = "/form/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash,
			HttpServletRequest request) {

		Reserva reserva = null;
		if (id > 0) {
			reserva = reservaServimp.findById(id);
			if (reserva == null) {
				flash.addFlashAttribute("error", " La reserva no existe en la Base de datos");
				return "redirect:reserva/listar";
			}
		} else {
			flash.addFlashAttribute("error", " reserva no existe");
			return "redirect:/reserva/listar";
		}

		HttpSession misession = request.getSession(true);
		mieempresa = (Empresa) misession.getAttribute("empresaCart");
		reserva.setEstadoReserva(EstadoReservasEnum.Cancelada);

		List<Habitacion> habitacion = habitacionServImp.findByEmpresa(mieempresa);
		//
		// eliminando la ocupacion para volver a validar el periodo.
		disponibilidadServImp.deleteByReserva(reserva);
		model.put("precio", reserva.getPrecioreserva());
		model.put("habitaciones", habitacion);
		model.put("reserva", reserva);

		model.put("titulo", "Editar Reserva");
		flash.addFlashAttribute("success", " Reserva guardada con éxito");
		System.out.println("Desde Editar reservas");
		return "reserva/form";
	}

	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id > 0) {
			try {
				reservaServimp.delete(id);
				flash.addFlashAttribute("success", " Reservacion eliminada con éxito");
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("error al borrar " + e.getMessage());
				flash.addFlashAttribute("error", " Error al intentar eliminar la reserva " + e.getMessage());
			}

		}
		return "redirect:/reserva/listar";
	}

	@RequestMapping(value = "/cancelar/{id}")
	public String cancelar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id > 0) {
			try {
				Reserva reserva = null;
				reserva = reservaServimp.findById(id);
				disponibilidadServImp.deleteByReserva(reserva);
				reservaServimp.cancelar(reserva.getEmpresa(), reserva);
				/*
				 * reservaServimp.delete(id); flash.addFlashAttribute("success",
				 * " Reservacion eliminada con éxito");
				 */
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("error al borrar " + e.getMessage());
				flash.addFlashAttribute("error", " Error al intentar eliminar la reserva " + e.getMessage());
			}

		}
		return "redirect:/reserva/listar";
	}

}