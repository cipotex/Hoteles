package innotech.com.sv.controladores;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import innotech.com.sv.ProcesosServices.GeneraContabilidad;
import innotech.com.sv.ProcesosServices.IReserva;
import innotech.com.sv.ProcesosServices.Miscelaneos;
import innotech.com.sv.ProcesosServices.ReservaImp;
import innotech.com.sv.modelos.CargosAdicionales;
import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.EstadoCargoAdicionalEnum;
import innotech.com.sv.modelos.EstadoFacturasEnum;
import innotech.com.sv.modelos.EstadoRecibosEnum;
import innotech.com.sv.modelos.EstadosEnum;
import innotech.com.sv.modelos.Ocupacion;
import innotech.com.sv.modelos.PeriodoReservaEnum;
import innotech.com.sv.modelos.Recibos;
import innotech.com.sv.modelos.Reserva;
import innotech.com.sv.modelosreportes.CargosAdicionalesReporte;
import innotech.com.sv.modelosreportes.FacturacionEncReporte;
import innotech.com.sv.modelos.FacturacionEnc;
import innotech.com.sv.modelos.FacturacionDet;

import innotech.com.sv.servicios.ICargosAdicionales;
import innotech.com.sv.servicios.IFacturacionDet;
import innotech.com.sv.servicios.IFacturacionEnc;
import innotech.com.sv.servicios.IRecibos;
import innotech.com.sv.servicios.OcupacionImp;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@SessionAttributes({ "datos", "renovar" })
@Controller
@RequestMapping("/facturacion")
public class FacturacionController {

	@Value("${innotec.com.elementosPorPagina}")
	String elementos;

	@Value("${innotec.com.contabilizar}")
	String contabilizar;

	@Value("${innotec.com.ivaDebitoFiscal}")
	double ivadebitofiscal;

	@Autowired
	Empresa mieempresa;

	@Autowired
	OcupacionImp ocupacionServImp;

	@Autowired
	ReservaImp reservaServimp;

	@Autowired
	ICargosAdicionales cargosAdicionalesImp;

	@Autowired
	IFacturacionEnc FacturacionEnc;

	@Autowired
	IFacturacionDet FacturacionDet;

	@Autowired
	IReserva reservasImp;

	@Autowired
	IRecibos recibosImp;

	@Autowired
	GeneraContabilidad generacontabilidad;

	
    float montoCargosAdicionales = 0;
    float totalfacturacion = 0;
	
	@RequestMapping(value = "/checkout/{id}")
	public String check_out(@PathVariable(value = "id") Long idOcupacion, Map<String, Object> model,
			HttpServletRequest request) {

		//
//		System.out.println("Dentro de Check-out");
		Ocupacion ocupacion = ocupacionServImp.findById(idOcupacion);
		
//		System.out.println("Luego de ocupacion de Check-out");
		
		String renovar = "N";

		if (ocupacion.getReserva().getRecurrente() == "S") {
			renovar = "S";
		} else {
			renovar = "N";
		}
		;

		double montoCargosAdicionales = cargosAdicionales(idOcupacion, request);

//		System.out.println("Antes de llamar a la forma");
		
		double totalOcupacion = montoCargosAdicionales + ocupacion.getReserva().getMontoReservaConDescuento()
				- ocupacion.getReserva().getMontoPreReserva();

		
		
		HttpSession misession = request.getSession(true);
		mieempresa = (Empresa) misession.getAttribute("empresaCart");

		model.put("titulo", "FACTURACION DE HABITACION ");
		model.put("datos", ocupacion);
		model.put("cargosadicionales", montoCargosAdicionales);
		model.put("totalocupacion", totalOcupacion);
		model.put("renovar", renovar);
		//
		
		return "facturacion/facturacion";

	}

	@RequestMapping(value="/facturacionperiodo")
	public String reportefacturacion(Map<String, Object> model,
			HttpServletRequest request) {
		

		HttpSession misession = request.getSession(true);
		mieempresa = (Empresa) misession.getAttribute("empresaCart");

		model.put("titulo", "FACTURACION POR PERIODO");
		model.put("fechainicio", new Date());
		model.put("fechafin", new Date());
		return "facturacion/reportefacturacion";
	}
	
	@RequestMapping(value="/facturacionperiodopost", method = RequestMethod.POST)
	public void reportefacturacionpost(Map<String, Object> model, RedirectAttributes flash, HttpServletRequest request,HttpServletResponse response,
	SessionStatus status) throws JRException, IOException {

		
		// Ocupacion ocupacion = ocupacionServImp.findById(idOcupacion);
				String fechainicio = request.getParameter("fechainicio");
				
				String fechafin = request.getParameter("fechafin");
				
				Date fechainicio2=  Miscelaneos.ParseFecha(fechainicio) ;
				Date fechafin2=     Miscelaneos.ParseFecha(fechafin) ;
				
//				System.out.println("Fecha inicio "+ fechainicio + " fecha final " + fechafin + " empresa_id "+mieempresa.getId());
//				System.out.println("Fecha inicio "+fechainicio2+ " fecha final " + fechafin2 + " empresa_id "+mieempresa.getId());

				List<FacturacionEnc> datos= FacturacionEnc.findFacturacionPeriodo(mieempresa.getId(), fechainicio2, fechafin2);		
				
				
				//System.out.println("Cantidad registros "+ datos.size());
				
				List<FacturacionEncReporte> datos2 = new ArrayList();
				totalfacturacion =0;
				
				datos.forEach(dat -> {	
					System.out.println("nombre servicio " + dat.getOcupacion().getId() );
					totalfacturacion += dat.getTotalFacturacion();
					//
					datos2.add( new  FacturacionEncReporte(
							dat.getOcupacion().getId(),
							dat.getOcupacion().getReserva().getHabitacion().getTipohabitacion().getDescripcion(), 
							dat.getOcupacion().getReserva().getHabitacion().getCodigo(),
							"Del: "   + dat.getOcupacion().getReserva().getFechaInicio() + " Al: " + dat.getOcupacion().getReserva().getFechaFin(),
							dat.getTotalFacturacion(),
							dat.getOcupacion().getReserva().getCliente().getNombredui()
							));			
				});
				
 								
	            Map<String, Object> parametros = new HashedMap();
				parametros.put("nombrereporte", "Reporte de Facturacion"  );
				parametros.put("periodo", " Del: " +fechainicio+ " Al:" +fechafin );
				parametros.put("totalfacturacion", totalfacturacion );
				parametros.put("empresa",   new String(mieempresa.getNombre()).toUpperCase());
				
				

				final InputStream stream = this.getClass().getResourceAsStream("/jaspers" + File.separator + "FacuracionEnc.jrxml");
		         //System.out.println(stream.toString());
		      
		         // Compile the Jasper report from .jrxml to .japser
		        final JasperReport report = JasperCompileManager.compileReport(stream);

		        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource( datos2);
		         
		        JasperPrint jasperPrint = JasperFillManager.fillReport(report, parametros, ds);
		          
		        // Vista previa de PDF en línea
		         response.setContentType("application/pdf");
		         response.setHeader("Content-Disposition", "attachment; filename=ReporteFacturacion.pdf");
		         final OutputStream outputStream = response.getOutputStream();
		         JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

	}
	
	@RequestMapping(value = "/facturar", method = RequestMethod.POST)
	public String facturar(@Valid @ModelAttribute(value = "datos") Ocupacion ocupacion, Map<String, Object> model,
			RedirectAttributes flash, HttpServletRequest request,HttpServletResponse response,
			SessionStatus status) throws Exception {

		// Ocupacion ocupacion = ocupacionServImp.findById(idOcupacion);
		String renovar = request.getParameter("renovar");
		
//		System.out.println(" ocupacion= " + ocupacion.getId() + " renovar= " + renovar);

		HttpSession misession = request.getSession(true);
		mieempresa = (Empresa) misession.getAttribute("empresaCart");

		// validando que se tenga encabezado de facturas
		FacturacionEnc facturacion = FacturacionEnc.findByEmpresaOcupacion(mieempresa.getId(), ocupacion.getId());
        
		
		// Si la tabla de facturacion tiene datos en pendiente hay que borrarlos
		if (!(facturacion == null)) {

			List<FacturacionDet> facturacionDet = FacturacionDet.findByEmpresaFactura(mieempresa.getId(),
					facturacion.getId());

			// Si no esta vacia el detalle de facturacion para esa factura, Borrarla
			if (!facturacionDet.isEmpty()) {
				FacturacionDet.deleteByEmpresaFactura(mieempresa.getId(), facturacion.getId());
			}
			;

			// Ahora que ya se borro el detalle se procedera a borrar encabezado
			FacturacionEnc.deleteEmpresaOcupacion(mieempresa.getId(), ocupacion.getId());

		}

		// Antes de proceder a insertar vamos a validar si se quiere reservar nuevamente
		// si es asi se verificara que exista disponibilidad

		if (ocupacion.getReserva().getRecurrente().equalsIgnoreCase("S") && renovar.equalsIgnoreCase("S")) {

			Reserva reserva = ocupacion.getReserva();

			if (reserva.getPeriodoreserva() == PeriodoReservaEnum.Semana) {
				// Calculando la fecha de inicio y final nuevas
				Date fechaInicio = reserva.getFechaFin();
				Date fechafin = (Date) reserva.getFechaFin();
				int diasReserva = reserva.getDiasOcupacion();
				Date Nvafechafin = Miscelaneos.sumarDiasAFecha(fechafin, diasReserva);

				// Validando que la habitacion este disponible para las fechas seleccionadas.
				boolean disponible = reservasImp.valida_disponibilidad(ocupacion.getEmpresa().getId(), diasReserva,
						fechaInicio, Nvafechafin);

				// Si la habitacion no esta disponible
				if (!disponible) {
					flash.addAttribute("error",
							"No se puede reservar la habitacion, no esta disponible para ese periodo de tiempo");
					return "redirect:/facturacion/facturacion";
				}
			}
			;

			// Si el periodo de reserva es mes.
			if (reserva.getPeriodoreserva() == PeriodoReservaEnum.Mes) {
				Date fechaInicio = reserva.getFechaFin();
				Date fechafin = Miscelaneos.SumaMesFecha(fechaInicio);
				int diasReserva = Miscelaneos.restafechas(fechaInicio, fechafin);

				// Validando que la habitacion este disponible para las fechas seleccionadas.
				boolean disponible = reservasImp.valida_disponibilidad(ocupacion.getEmpresa().getId(), diasReserva,
						fechaInicio, fechafin);

				// Si la habitacion no esta disponible
				if (!disponible) {
					flash.addAttribute("error",
							"No se puede reservar la habitacion, no esta disponible para ese periodo de tiempo");
					return "redirect:/facturacion/facturacion";
				}
				;
			}

		}
		;

		// ************************************************************************************
		// ************************************************************************************
		// Ahora que ya se eliminaron facturas que estaban pendientes, se procedera a
		// insertar una
		// nueva factura y detalle.
		// ************************************************************************************
		float montoCargosAdicionales = 0;
		 
			
		FacturacionEnc facturaEncabezado = new FacturacionEnc();
		facturaEncabezado.setEmpresa(mieempresa);
		facturaEncabezado.setOcupacion(ocupacion);
		facturaEncabezado.setEstadoFactura(EstadoFacturasEnum.Generada);
		//
		facturaEncabezado.setMontoImpuestos(0);
		//
		montoCargosAdicionales = cargosAdicionales(ocupacion.getId(), request);
		Double montoReserva = ocupacion.getReserva().getMontoPreReserva();

		float totalOcupacion = (float) (montoCargosAdicionales + ocupacion.getReserva().getMontoReservaConDescuento()
				- montoReserva);
		//
		facturaEncabezado.setMontoCargosAdicionales(montoCargosAdicionales);
		facturaEncabezado.setTotalFacturacion(totalOcupacion);
		facturaEncabezado.setMontoOcupacion(ocupacion.getReserva().getMontoReservaConDescuento());
		facturaEncabezado.setMontoPreReserva(ocupacion.getReserva().getMontoPreReserva());

		//
		FacturacionEnc.save(facturaEncabezado);
		//

		// Ahora vamos a leer todos los cargos adicionales y vamos a insertar el detalle
		// de la factura
		List<CargosAdicionales> cargosadicionales = cargosAdicionalesImp.findByEmpresaOcupacion(mieempresa.getId(),
				ocupacion.getId());
		//
		cargosadicionales.forEach(car -> {
			FacturacionDet facturacionDet = new FacturacionDet();

			facturacionDet.setEmpresa(car.getEmpresa());
			facturacionDet.setFacturacionEnc(facturaEncabezado);

			facturacionDet.setCantidad(car.getCantidad());
			facturacionDet.setDescripcion(car.getServicio().getDescripcion());
			facturacionDet.setPrecioUnitario(car.getPrecioUnitario());
			facturacionDet.setDescuento(car.getDescuento());
			facturacionDet.setTotal(car.getTotal());
			facturacionDet.setServicio(car.getServicio());			
			
			// Almacenando el total del detalle
			FacturacionDet.save(facturacionDet);

			// Cambiando estado de cargos adicionales
			car.setEstado(EstadoCargoAdicionalEnum.Facturado);
			cargosAdicionalesImp.save(car);

		});
		
		double vtaTotal  = montoCargosAdicionales + ocupacion.getReserva().getMontoReservaConDescuento();		
		double ivaDebito = 0.00;
		
		// Ahora vamos a cambiar el estado de la ocupacion a Facturada.
		ocupacion.setEstado(EstadosEnum.Facturado);
		ocupacionServImp.save(ocupacion);

		// Ahora vamos a insertar un recibo en la tabla de recibos
		Recibos recibo = new Recibos();

		recibo.setEmpresa(mieempresa);
		recibo.setFactura(facturaEncabezado);
		recibo.setMontofactura(totalOcupacion);
		recibo.setMontoRecibo(totalOcupacion);
		recibo.setEstadoRecibo(EstadoRecibosEnum.Pendiente);
		recibo.setCliente(ocupacion.getReserva().getCliente());
		recibo.setDescuento(0);
		recibo.setRecargo(0);

		recibosImp.save(recibo);

		// Cambiando el estado a la ocupacion para que salga facturada
		ocupacion.setEstado(EstadosEnum.Facturado);
		ocupacionServImp.save(ocupacion);

		// Ahora vamos a validar si la reservacion es recursiva, si es asi proceder a
		// crear una nueva reservacion para el mismo periodo
		// System.out.println(" El valor de renovar es " + renovar +" Recurrente?= "+
		// ocupacion.getReserva().getRecurrente() );
		if (ocupacion.getReserva().getRecurrente().equalsIgnoreCase("S") && renovar.equalsIgnoreCase("S")) {
//			System.out.println(
//					"La ocupacion es recurrente!!!.. hay que implementar logica de volver a agregar otra reserva");
			Reserva reserva = ocupacion.getReserva();
			if (reserva.getPeriodoreserva() == PeriodoReservaEnum.Semana) {

				// Calculando la fecha de inicio y final nuevas
				Date fechaInicio = reserva.getFechaFin();
				Date fechafin = (Date) reserva.getFechaFin();
				int diasReserva = reserva.getDiasOcupacion();
				Date Nvafechafin = Miscelaneos.sumarDiasAFecha(fechafin, diasReserva);

				// Validando que la habitacion este disponible para las fechas seleccionadas.
				boolean disponible = reservasImp.valida_disponibilidad(ocupacion.getEmpresa().getId(), diasReserva,
						fechaInicio, Nvafechafin);

				// Si la habitacion no esta disponible
				if (!disponible) {
					flash.addAttribute("error",
							"No se puede reservar la habitacion, no esta disponible para ese periodo de tiempo");
					return "redirect:/facturacion/facturacion";
				}
				;

				// Si la habitacion esta disponible.
				reserva.setFechaInicio(fechaInicio);
				reserva.setFechaFin(Nvafechafin);
				reserva.setMontoPreReserva((double) 0);

				// Almacenando la reserva con las nuevas fechas
				reservaServimp.save(reserva);

				// insertando que la habitacion estara ocupada en la tabla de control de tiempos
				reservasImp.reservar(ocupacion.getReserva().getId(), ocupacion.getEmpresa().getId(),
						ocupacion.getReserva().getHabitacion().getId(), fechaInicio, Nvafechafin);

				// Ahora se tiene que insertar una nueva Ocupacion con las nuevas fechas.
				Ocupacion newOcupacion = new Ocupacion();
				newOcupacion.setEmpresa(ocupacion.getEmpresa());
				newOcupacion.setReserva(ocupacion.getReserva());
				newOcupacion.setEstado(EstadosEnum.Activo);
				newOcupacion.setFechaInicioOcupacion(fechaInicio);
				newOcupacion.setFechaFinOcupacion(Nvafechafin);
				//
				// Almacenando la nueva Ocupacion.
				ocupacionServImp.save(newOcupacion);

			}
			;

			if (reserva.getPeriodoreserva() == PeriodoReservaEnum.Mes) {
				Date fechaInicio = reserva.getFechaFin();
				Date fechafin = Miscelaneos.SumaMesFecha(fechaInicio);
				int diasReserva = Miscelaneos.restafechas(fechaInicio, fechafin);

				// Validando que la habitacion este disponible para las fechas seleccionadas.
				boolean disponible = reservasImp.valida_disponibilidad(ocupacion.getEmpresa().getId(), diasReserva,
						fechaInicio, fechafin);

				// Si la habitacion no esta disponible
				if (!disponible) {
					flash.addAttribute("error",
							"No se puede reservar la habitacion, no esta disponible para ese periodo de tiempo");
					return "redirect:/facturacion/facturacion";
				}
				;

				// Si la habitacion esta disponible.
				reserva.setFechaInicio(fechaInicio);
				reserva.setFechaFin(fechafin);
				reserva.setDiasOcupacion(diasReserva);

				// Almacenando la reserva con las nuevas fechas
				reservaServimp.save(reserva);

				// insertando que la habitacion estara ocupada en la tabla de control de tiempos
				reservasImp.reservar(ocupacion.getReserva().getId(), ocupacion.getEmpresa().getId(),
						ocupacion.getReserva().getHabitacion().getId(), fechaInicio, fechafin);

				// Ahora se tiene que insertar una nueva Ocupacion con las nuevas fechas.
				Ocupacion newOcupacion = new Ocupacion();
				newOcupacion.setEmpresa(ocupacion.getEmpresa());
				newOcupacion.setReserva(ocupacion.getReserva());
				newOcupacion.setEstado(EstadosEnum.Activo);
				newOcupacion.setFechaInicioOcupacion(fechaInicio);
				newOcupacion.setFechaFinOcupacion(fechafin);
				//
				// Almacenando la nueva Ocupacion.
				ocupacionServImp.save(newOcupacion);

			}
			;

		}
		;

		// Ahora se proceder a generar la distribucion contable de la partida de ventas
		// Si se tiene configurado el sistema para tal fin.
		// Validando si se esta contabilizando
		if (contabilizar.equalsIgnoreCase("S")) {
			// Validando si se tiene Anticipo de Reserva
			
			//
			if (ivadebitofiscal > 0) {
				//Si se tiene registrado que se contabilizara impuesto				
					ivaDebito = vtaTotal - Math.round((vtaTotal/(1+ivadebitofiscal)*100d/100d));
					vtaTotal = Math.round((vtaTotal/(1+ivadebitofiscal)*100d/100d));
			}
//			System.out.println("ocupacion.getReserva()="+ocupacion.getReserva() + " ocupacion= " + ocupacion.getId() + " totalOcupacion="+ totalOcupacion
//					+ " montoReserva="+montoReserva +" vtaTotal="+vtaTotal+" ivaDebito"+ivaDebito);
			generacontabilidad.registraVenta(ocupacion.getReserva(), ocupacion, 
					totalOcupacion,         //monto total de factura con iva
					montoCargosAdicionales, //monto de cargos adicionales con iva
					montoReserva,           //monto de reserva
					vtaTotal,               //total factura sin iva
					ivaDebito              //iva debito fiscal
					);
			 
		};

		
		
		//	Limpiando las variables
		status.setComplete();
		
		return "redirect:/ocupacion/listar";
	};

	private float cargosAdicionales(long IdOcupacion, HttpServletRequest request) {
		
		montoCargosAdicionales = 0;

		HttpSession misession = request.getSession(true);
		mieempresa = (Empresa) misession.getAttribute("empresaCart");
		// Buscar Cargos Adicionales que esten pendientes y totalizarlos
		List<CargosAdicionales> cargosadicionales = cargosAdicionalesImp.findByEmpresaOcupacion(mieempresa.getId(),
				IdOcupacion);
		//
		
		cargosadicionales.forEach(car -> {
			montoCargosAdicionales = (montoCargosAdicionales + car.getTotal());
		});
 
		return montoCargosAdicionales;
	}
	
	@RequestMapping( value="/cargosadicionesImp/{id}", method = RequestMethod.GET)
	private void reporteCargosAdicionales(@PathVariable(value = "id") Long idOcupacion, Map<String, Object> model,
			HttpServletRequest request,
			HttpServletResponse response,
			SessionStatus status) throws JRException, IOException {
		
		
		List<CargosAdicionales> datos= cargosAdicionalesImp.findByEmpresaOcupacion (mieempresa.getId(),	idOcupacion);
		
		//System.out.println( " total de datos " + datos.size());
		
		List<CargosAdicionalesReporte> datos2 = new ArrayList<>();
		
		
		datos.forEach(dat -> {	
			//System.out.println("nombre servicio " + dat.getServicio().getDescripcion());
			datos2.add( new  CargosAdicionalesReporte(dat.getId(), dat.getServicio().getNombre(), dat.getCantidad(), dat.getPrecioUnitario(), dat.getTotal()));			
		});
		
		
		Map<String, Object> parametros = new HashedMap();
		parametros.put("nombrereporte", "Reporte de Cargos Adicionales");
		parametros.put("empresa",   new String(mieempresa.getNombre()).toUpperCase());
		parametros.put("TotalCargosAdicionales", montoCargosAdicionales);
		
		

		final InputStream stream = this.getClass().getResourceAsStream("/jaspers" + File.separator + "CargosAdicionales.jrxml");
         //System.out.println(stream.toString());
      
         // Compile the Jasper report from .jrxml to .japser
        final JasperReport report = JasperCompileManager.compileReport(stream);

        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource( datos2);
         
        JasperPrint jasperPrint = JasperFillManager.fillReport(report, parametros, ds);
          
        // Vista previa de PDF en línea
         response.setContentType("application/pdf");
         response.setHeader("Content-Disposition", "attachment; filename=ReporteCargosAdicionales.pdf");
         final OutputStream outputStream = response.getOutputStream();
         JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
         
//		return "redirect:/facturacion/checkout/"+idOcupacion;
	}
	
}
