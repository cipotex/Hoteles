package innotech.com.sv.controladores;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import innotech.com.sv.ProcesosServices.GeneraContabilidad;
import innotech.com.sv.ProcesosServices.Miscelaneos;
import innotech.com.sv.ProcesosServices.ReservaImp;
import innotech.com.sv.modelos.*;
import innotech.com.sv.modelosreportes.FacturacionEncReporte;
import innotech.com.sv.modelosreportes.OcupacionReporte;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import innotech.com.sv.paginator.PageRender;
import innotech.com.sv.servicios.DisponibilidadImp;
import innotech.com.sv.servicios.HabitacionImp;
import innotech.com.sv.servicios.OcupacionImp;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@SessionAttributes({ "reserva", "empresa", "ocupacion", "datos" })
@Controller
@RequestMapping("/ocupacion")
public class OcupacionesController {

	@Value("${innotec.com.elementosPorPagina}")
	String elementos;
	
	@Value("${innotec.com.contabilizar}")
	String contabilizar;
	
	@Value("${innotec.com.ivaDebitoFiscal}")
	String ivadebitofiscal;
	
	@Autowired
	Empresa mieempresa;

	@Autowired
	OcupacionImp ocupacionServImp;

	@Autowired
	ReservaImp reservaServimp;
	
	@Autowired
	GeneraContabilidad generacontabilidad;
	
	@Autowired
	DisponibilidadImp disponibilidadServImp;
	
	@Autowired
	HabitacionImp habitacionImp;

	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model modelo,
			HttpServletRequest request) {

		int elemento = Integer.parseInt(this.elementos);
		Pageable pageRequest = PageRequest.of(page, elemento);
		//
		HttpSession misession = request.getSession(true);
		mieempresa = (Empresa) misession.getAttribute("empresaCart");
		String mensaje = (String) misession.getAttribute("mensaje");
		//
		Page<Ocupacion> ocupacion = ocupacionServImp.findAllByEmpresa(mieempresa.getId(), pageRequest);//
		//
		PageRender<Ocupacion> pageRender = new PageRender<>("/inventario/listar", ocupacion, elemento);
		//
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("titulo", "Listado de Ocupaciones");

		//
		modelo.addAttribute("empresatipos", mieempresa);
		modelo.addAttribute("page", pageRender);
		modelo.addAttribute("datos", ocupacion);
		//
		return "ocupacion/listar";
	}

	@RequestMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Ocupacion ocupacion = ocupacionServImp.findById(id);
		if (ocupacion == null) {
			flash.addAttribute("error", "La ocupación no existe en la Base de datos");
			return "redirect:/ocupacion/listar";
		}
		//

		model.put("titulo", "DETALLE DE LA OCUPACION Cliente: " + ocupacion.getReserva().getCliente().getNombredui());
		model.put("datos", ocupacion);
		//
		return "ocupacion/ver";
	}

	

	@RequestMapping(value = "/modificar/{id}", method = RequestMethod.GET)
	public String modificar(@PathVariable(value = "id") long id, Map<String, Object> model) {

		Ocupacion ocupacion = ocupacionServImp.findById(id);

		model.put("titulo", "Extender ocupacion");
		model.put("datos", ocupacion);
		return "ocupacion/editar";
	}

	@RequestMapping(value = "/anular/{id}", method = RequestMethod.GET)
	public String anular(@PathVariable(value = "id") long id, RedirectAttributes flash) {

		Ocupacion ocupacion = ocupacionServImp.findById(id);
		// anulando el estado
		ocupacion.setEstado(EstadosEnum.Anulado);
		//
		ocupacionServImp.save(ocupacion);
				
		Reserva reserva = ocupacion.getReserva();
		
		//Colocando la habitacion como disponible.
		disponibilidadServImp.deleteByReserva(reserva);
		reservaServimp.cancelar(reserva.getEmpresa(), reserva);
		
		// Validando si se esta contabilizando
		if (contabilizar.equalsIgnoreCase("S")) {
			// Validando si se tiene Anticipo de Reserva
			if (reserva.getMontoPreReserva() > 0) {				
				generacontabilidad.revierteAnticipoReserva(reserva, ocupacion);
			}

			// Validando si se tiene Garantia
			if (reserva.getMontoDeposito() > 0) {				
				generacontabilidad.revierteGarantia(reserva, ocupacion);
			}
		}
		;
		
		flash.addAttribute("success", "ID de Ocupacion anulado");

		return "redirect:/ocupacion/listar";
	}

	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String cambiarfechas(HttpServletRequest request, 
			HttpServletResponse response,
			// BindingResult result,
			Model model, 
			RedirectAttributes flash, 
			SessionStatus status) throws ParseException {

		if (request.getParameter("nuevafecha") == null) {
			model.addAttribute("titulo", "Extender ocupacion");
			flash.addFlashAttribute("error", "La fecha es obligatoria");
			return "ocupacion/editar";
		}
		;

		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		Date nvaFechaFin = formato.parse(request.getParameter("nuevafecha"));

		long reservaid = Long.parseLong(request.getParameter("reserva"));
		long ocupacionid = Long.parseLong(request.getParameter("ocupacion"));

		// Buscando ocupacion
		 Ocupacion ocupacion = ocupacionServImp.findById(ocupacionid);
		
		// Buscando la reserva
		Reserva reserva = reservaServimp.findById(reservaid);
		Date fechafinOld = reserva.getFechaFin();
		//
		// Validando que el periodo no sea Mes o Semana
		if (reserva.getPeriodoreserva() == PeriodoReservaEnum.Mes
				|| reserva.getPeriodoreserva() == PeriodoReservaEnum.Semana) {
			String mensajeFlash = "En periodo de cobro Semana o Mes no se puede extender la fecha de finalizacion de ocupacion. Genere otra ocupacion.";
			flash.addFlashAttribute("error", mensajeFlash);
			model.addAttribute("error", mensajeFlash);
			return "ocupacion/editar";
		}
		;

//		System.out.println("fecha fin "+reserva.getFechaFin() +" nv fecha fin "+nvaFechaFin);
		
		// La nueva fecha de reserva debe ser mayor a la fecha actual
		if ((Miscelaneos.restafechas(reserva.getFechaFin(), nvaFechaFin)) <= 0) {
			String mensajeFlash = "La fecha de modificacion debe ser mayor a la fecha final de la ocupacion";
			flash.addFlashAttribute("error", mensajeFlash);
			model.addAttribute("error", mensajeFlash);
			return "ocupacion/editar";
		}
		;

		// Validando disponibilidad de la habitacion
		boolean disponible = reservaServimp.valida_disponibilidad(reserva.getEmpresa().getId(),
				reserva.getHabitacion().getId(), reserva.getFechaFin(), nvaFechaFin);
		//
		System.out.println("Disponible habitacion " + disponible + " fecha inicio" + reserva.getFechaFin()
				+ "nva fecha fin " + nvaFechaFin);
		if (!disponible) {
			String mensajeFlash = "Para el periodo de tiempo especificado la habitacion "
					+ reserva.getHabitacion().getCodigo() + " no esta Disponible";
			flash.addFlashAttribute("error", mensajeFlash);
			model.addAttribute("error", mensajeFlash);
			model.addAttribute("titulo", "Edicion de Reservas");
			return "ocupacion/editar";
		}

		// Si la habitacion esta disponible.
		// registrar en la tabla de ocupaciones y recalcular monto a pagar

		// Calculando dias ocupacion
		System.out.println( reserva.getFechaInicio() + " <-> "+ nvaFechaFin);
		reserva.setDiasOcupacion(Miscelaneos.restafechas(reserva.getFechaInicio(), nvaFechaFin));
		reserva.setFechaFin(nvaFechaFin);

		//Guardando la reserva
		reservaServimp.save(reserva);
		
		// Agregando 1 dia a la fecha final
		 Calendar c = Calendar.getInstance();
		 c.setTime(fechafinOld);
		 c.add(Calendar.DATE, 1);
		 fechafinOld = c.getTime();
		 
		// ahora que ya se guardo el encabezado de la reserva procedemos a guardar la ocupacion
		 // Se llenaran los dias de fecha fin + 1 ala nueva fecha modificada
        String resp = reservaServimp.reservar(reserva.getId(), reserva.getEmpresa().getId(), reserva.getHabitacion().getId(), fechafinOld, nvaFechaFin);
		
        // ahora tenemos que actualizar la fecha fin de la ocupacion.
        ocupacion.setFechaFinOcupacion(nvaFechaFin);
        ocupacionServImp.save(ocupacion);
		
        String mensajeFlash = "Fecha modificada con exito ";
		flash.addFlashAttribute("success", mensajeFlash);
		
		return "redirect:/ocupacion/listar";
	};

	@RequestMapping(value="/ocupacionperiodo")
	public String reportefacturacion(Map<String, Object> model,
			HttpServletRequest request) {
		

		HttpSession misession = request.getSession(true);
		mieempresa = (Empresa) misession.getAttribute("empresaCart");

		model.put("titulo", "REPORTE DE OCUPACION POR PERIODO");
		model.put("fechainicio", new Date());
		model.put("fechafin", new Date());
		return "ocupacion/reporteocupacion";
	}
	
	@RequestMapping(value="/ocupacionperiodopost", method = RequestMethod.POST)
	public void reportefacturacionpost(Map<String, Object> model, RedirectAttributes flash, HttpServletRequest request,HttpServletResponse response,
	SessionStatus status) throws JRException, IOException {

		
		// Ocupacion ocupacion = ocupacionServImp.findById(idOcupacion);
				String fechainicio = request.getParameter("fechainicio");
				
				String fechafin = request.getParameter("fechafin");
				
				Date fechainicio2=  Miscelaneos.ParseFecha(fechainicio) ;
				Date fechafin2=     Miscelaneos.ParseFecha(fechafin) ;
				
//				System.out.println("Fecha inicio "+ fechainicio + " fecha final " + fechafin + " empresa_id "+mieempresa.getId());
//				System.out.println("Fecha inicio "+fechainicio2+ " fecha final " + fechafin2 + " empresa_id "+mieempresa.getId());

			    List<Ocupacion> datos= ocupacionServImp.ocupacionPeriodo(mieempresa.getId(), fechainicio2, fechafin2);	
			    List<Habitacion> habitaciones = habitacionImp.findByEmpresa(mieempresa);
				//
			    
				
				System.out.println("Cantidad registros "+ datos.size());
				
				List<OcupacionReporte> reporte = new ArrayList<OcupacionReporte>();
				//totalfacturacion =0;
				Boolean entro = false;
				int diasocupados =0;
				int diasanalizados = Miscelaneos.restafechas(fechainicio2, fechafin2);
				long ocupacion = 0L;
				
				for (Habitacion hab :habitaciones) {
					//
					diasocupados =0;
					//
					for(Ocupacion ocupa: datos) {
						if (hab.getCodigo().equalsIgnoreCase(ocupa.getReserva().getHabitacion().getCodigo())) {
							diasocupados =+ Miscelaneos.restafechas(ocupa.getFechaInicioOcupacion(), ocupa.getFechaFinOcupacion());
						}		
					}
					//ahora que ya se tienen los dias ocupados
					//proceder a calcular la data del reporte
					if (diasocupados>0) {
						ocupacion = diasanalizados/diasocupados;
					}else {
						ocupacion = 0;
					}
					
					reporte.add(new OcupacionReporte(mieempresa.getNombre(), hab.getCodigo(),  fechainicio2,
							 fechafin2, diasanalizados, diasocupados, ocupacion)
							 ) ;
				}			
 								
	            Map<String, Object> parametros = new HashedMap();
				parametros.put("nombrereporte", "Reporte de Ocupacion"  );
				parametros.put("periodo", " Del: " +fechainicio+ " Al:" +fechafin );
				parametros.put("empresa",   new String(mieempresa.getNombre()).toUpperCase());
											
				final InputStream stream = this.getClass().getResourceAsStream("/jaspers" + File.separator + "PorcOcupacion.jrxml");
		         //System.out.println(stream.toString());
		      
		         // Compile the Jasper report from .jrxml to .japser
		        final JasperReport report = JasperCompileManager.compileReport(stream);

		        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource( reporte);
		         
		        JasperPrint jasperPrint = JasperFillManager.fillReport(report, parametros, ds);
		          
		        // Vista previa de PDF en línea
		         response.setContentType("application/pdf");
		         response.setHeader("Content-Disposition", "attachment; filename=ReporteOcupacion.pdf");
		         final OutputStream outputStream = response.getOutputStream();
		         JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

	}
}
