package innotech.com.sv.controladores;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import innotech.com.sv.ProcesosServices.NumberToLetterConverter;
import innotech.com.sv.jasperControllers.JasperController;
import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.EstadoRecibosEnum;
import innotech.com.sv.modelos.FacturacionEnc;
import innotech.com.sv.modelos.Ocupacion;
import innotech.com.sv.modelos.Recibos;
import innotech.com.sv.modelosreportes.CobrosReporte;
import innotech.com.sv.modelosreportes.FacturacionEncReporte;
import innotech.com.sv.paginator.PageRender;
import innotech.com.sv.servicios.IRecibos;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@SessionAttributes("datos")
@RequestMapping("/recibos")
public class RecibosController {

	@Autowired
	private IRecibos recibosImp;

	@Value("${innotec.com.elementosPorPagina}")
	String elementos;

	@Autowired
	Empresa mieempresa;

	@Value("${innotec.com.contabilizar}")
	String contabilizar;

	@Value("${innotec.com.ivaDebitoFiscal}")
	double ivadebitofiscal;

	@Autowired
	GeneraContabilidad generacontabilidad;

	@Autowired
	JasperController reportes;
	
	float totalcobros = 0;
	
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
		Page<Recibos> recibos = recibosImp.findAllByEmpresa(mieempresa, pageRequest);

		PageRender<Recibos> pageRender = new PageRender<>("/recibos/listar", recibos, elemento);
		//

		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("titulo", "Listado de Cobros pendientes");
		modelo.addAttribute("datos", recibos);
		modelo.addAttribute("empresatipos", mieempresa);
		modelo.addAttribute("page", pageRender);

		return "recibos/listar";
	}

	@RequestMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Recibos recibos = recibosImp.findById(id);
		if (recibos == null) {
			flash.addAttribute("error", "El recibo no existe en la Base de datos");
			return "redirect:/recibos/listar";
		}
		//
		model.put("titulo", "Detalle Recibo " );
		model.put("datos", recibos);
		//
		return "recibos/ver";
	}

	@RequestMapping(value = "/pago/{id}")
	public String pagar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Recibos recibos = recibosImp.findById(id);
		if (recibos == null) {
			flash.addAttribute("error", "El recibo no existe en la Base de datos");
			return "redirect:/recibos/listar";
		}
		//
		// Cambiando el estado del recibo a pagado
		recibos.setEstadoRecibo(EstadoRecibosEnum.Pagado);
		recibos.setFechaPago(new Date());
		recibosImp.save(recibos);
		//

		// Validando si se esta contabilizando
		if (contabilizar.equalsIgnoreCase("S")) {
			generacontabilidad.registroCobro(recibos);
		};

		return "redirect:/recibos/listar";
	}
	
	@RequestMapping(value = "/anular/{id}")
	public String anular(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Recibos recibos = recibosImp.findById(id);
		if (recibos == null) {
			flash.addAttribute("error", "El recibo no existe en la Base de datos");
			return "redirect:/recibos/listar";
		}
		//
		// Cambiando el estado del recibo a pagado
		recibos.setEstadoRecibo(EstadoRecibosEnum.Anulado);		
		recibosImp.save(recibos);
		//

		// Validando si se esta contabilizando
		if (contabilizar.equalsIgnoreCase("S")) {
			generacontabilidad.registroAnulacion(recibos);
		};

		return "redirect:/recibos/listar";
	} 
	
	@RequestMapping(value = "/aumentasaldo", method = RequestMethod.POST)
	public String aumentasaldo(RedirectAttributes flash, Model modelo, HttpServletRequest request) {
		
		float monto =  Float.parseFloat(request.getParameter("sumamonto"));
		long reciboid =  Long.parseLong(request.getParameter("recibo"));
		String descripcion = request.getParameter("descripcion");
		
		Recibos recibos = recibosImp.findById(reciboid);
		
		if (!(recibos == null)){
			recibos.setRecargo(monto);
			recibos.setComentarios(descripcion);
			recibosImp.save(recibos);
			
			// Validando si se esta contabilizando
			if (contabilizar.equalsIgnoreCase("S")) {
				generacontabilidad.registroAumentaSaldo(recibos);
			};

		}
		
		return "redirect:/recibos/listar";
	}
	

	@RequestMapping(value = "/disminuyesaldo", method = RequestMethod.POST)
	public String disminuyesaldo(RedirectAttributes flash, Model modelo, HttpServletRequest request) {
		
		float monto =  Float.parseFloat(request.getParameter("sumamonto"));
		long reciboid =  Long.parseLong(request.getParameter("recibo"));
		String descripcion = request.getParameter("descripcion");
		
		Recibos recibos = recibosImp.findById(reciboid);
		
		if (!(recibos == null)){
			recibos.setDescuento(monto);
			recibos.setComentarios(descripcion);
			recibosImp.save(recibos);
			
			// Validando si se esta contabilizando
			if (contabilizar.equalsIgnoreCase("S")) {
				generacontabilidad.registroDisminuyeSaldo(recibos);
			};

		}
		
		return "redirect:/recibos/listar";
	}

	@RequestMapping(value = "/imprimerecibo/{id}")
	public void imprimerecibo(
			@PathVariable(value = "id") Long id, RedirectAttributes flash,
			HttpServletRequest request,HttpServletResponse response,
			SessionStatus status) throws Exception{
		
		    Recibos recibo        =  recibosImp.findById(id);
		    List<Recibos> recibos = new ArrayList();		    
		    recibos.add(recibo);
		    
		    // *****************************************************
			// GENERANDO EL RECIBO PARA COBRO
			// ****************************************************
		 
		    Calendar fecha = Calendar.getInstance();
		    
			Map<String, Object> parametros = new HashedMap();
			parametros.put("NombreHotel", recibo.getEmpresa().getNombre());
			parametros.put("NombreCliente", recibo.getCliente().getNombredui());
			parametros.put("monto", Float.toString(recibo.getMontoRecibo()));
			parametros.put("montoletras",  NumberToLetterConverter.convertNumberToLetter( Float.toString( recibo.getMontoRecibo())));
			parametros.put("concepto", "Renta de habitacion del " + recibo.getFactura().getOcupacion().getFechaInicioOcupacion() + " Al " + recibo.getFactura().getOcupacion().getFechaFinOcupacion() );
			parametros.put("montofacturacion",Float.toString(recibo.getMontofactura()));
			parametros.put("descuento", Float.toString(recibo.getDescuento()));
			parametros.put("recargos", Float.toString(recibo.getRecargo()));
			parametros.put("dia", String.valueOf(fecha.get(Calendar.DATE))) ;
			parametros.put("mes", String.valueOf(fecha.get(Calendar.MONTH)+1)) ;
			parametros.put("anno", String.valueOf(fecha.get(Calendar.YEAR))) ;
			
			
			final InputStream stream = this.getClass().getResourceAsStream("/jaspers" + File.separator + "Recibo_cobro_1.jrxml");
	         //System.out.println(stream.toString());
	      
	         // Compile the Jasper report from .jrxml to .japser
	        final JasperReport report = JasperCompileManager.compileReport(stream);

            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource( recibos);
	         
	         JasperPrint jasperPrint = JasperFillManager.fillReport(report, parametros, ds);
	          
	        // Vista previa de PDF en línea
	         response.setContentType("application/pdf");
	         response.setHeader("Content-Disposition", "attachment; filename=ReporteClientes.pdf");
	         final OutputStream outputStream = response.getOutputStream();
	         JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

	}
	
	@RequestMapping(value="/cobrosperiodo")
	public String reportefacturacion(Map<String, Object> model,
			HttpServletRequest request) {
		

		HttpSession misession = request.getSession(true);
		mieempresa = (Empresa) misession.getAttribute("empresaCart");

		model.put("titulo", "COBROS POR PERIODO");
		model.put("fechainicio", new Date());
		model.put("fechafin", new Date());
		return "recibos/reportecobros";
	}
	
	@RequestMapping(value="/cobrosperiododopost", method = RequestMethod.POST)
	public void reportefacturacionpost(Map<String, Object> model, RedirectAttributes flash, HttpServletRequest request,HttpServletResponse response,
	SessionStatus status) throws JRException, IOException {

		
		// Ocupacion ocupacion = ocupacionServImp.findById(idOcupacion);
				String fechainicio = request.getParameter("fechainicio");
				
				String fechafin = request.getParameter("fechafin");
				
				Date fechainicio2=  Miscelaneos.ParseFecha(fechainicio) ;
				Date fechafin2=     Miscelaneos.ParseFecha(fechafin) ;
				
				System.out.println("Fecha inicio "+ fechainicio + " fecha final " + fechafin + " empresa_id "+mieempresa.getId());
				System.out.println("Fecha inicio "+fechainicio2+ " fecha final " + fechafin2 + " empresa_id "+mieempresa.getId());

				List<Recibos> datos= recibosImp.recibosPagados(mieempresa.getId(), fechainicio2, fechafin2)	;	
				
				
				System.out.println("Cantidad registros "+ datos.size());
				
				
				List<CobrosReporte> datos2 = new ArrayList();
				totalcobros =0;
				
				
				datos.forEach(dat -> {	
					System.out.println("nombre servicio " + dat.getFactura().getOcupacion().getId() );
					totalcobros += dat.getMontoRecibo();
					//
					datos2.add( new  CobrosReporte(
							dat.getFactura().getOcupacion().getId(),
							dat.getFactura().getOcupacion().getReserva().getHabitacion().getTipohabitacion().getDescripcion(), 
							dat.getFactura().getOcupacion().getReserva().getHabitacion().getCodigo(),
							Miscelaneos.ParseDateToString(dat.getFechaPago()),
							dat.getMontoRecibo(),
							dat.getFactura().getOcupacion().getReserva().getCliente().getNombredui()
							));			
				});
				
 				Map<String, Object> parametros = new HashedMap();
				parametros.put("nombrereporte", "Reporte de Recuperaciones"  );
				parametros.put("periodo", " Del: " +fechainicio+ " Al:" +fechafin );
				parametros.put("totalfacturacion", totalcobros );
				parametros.put("empresa",   new String(mieempresa.getNombre()).toUpperCase());
				
				final InputStream stream = this.getClass().getResourceAsStream("/jaspers" + File.separator + "CobrosEnc.jrxml");
		         //System.out.println(stream.toString());
		      
		         // Compile the Jasper report from .jrxml to .japser
		        final JasperReport report = JasperCompileManager.compileReport(stream);

		        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource( datos2);
		         
		        JasperPrint jasperPrint = JasperFillManager.fillReport(report, parametros, ds);
		          
		        // Vista previa de PDF en línea
		         response.setContentType("application/pdf");
		         response.setHeader("Content-Disposition", "attachment; filename=ReporteRecuperaciones.pdf");
		         final OutputStream outputStream = response.getOutputStream();
		         JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

	}
}
