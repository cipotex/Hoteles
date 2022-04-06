package innotech.com.sv.controladores;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

import innotech.com.sv.ProcesosServices.Miscelaneos;
import innotech.com.sv.modelos.ClasificacionGastoEnum;
import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.EstadoGastos;
import innotech.com.sv.modelos.FacturacionEnc;
import innotech.com.sv.modelos.Gastos;
import innotech.com.sv.modelos.Habitacion;
import innotech.com.sv.modelos.TipoFacturaGastoEnum;
import innotech.com.sv.modelosDao.FacturacionDaoJdbc;
import innotech.com.sv.modelosDao.GastosDaoJdbc;
import innotech.com.sv.modelosreportes.FacturacionEncReporte;
import innotech.com.sv.modelosreportes.GastosPeriodo;
import innotech.com.sv.modelosreportes.UtilidadReportePeriodo;
import innotech.com.sv.paginator.PageRender;
import innotech.com.sv.servicios.IGastos;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@SessionAttributes({"datos","empresatipos",})
@RequestMapping("/gastos")
public class GastosController {

	@Value("${innotec.com.elementosPorPagina}")
	String elementos;
	
	@Autowired
	Empresa mieempresa ;
	
	@Autowired
	IGastos gastosDao;
	
	@Autowired
	GastosDaoJdbc gastosDaoJdbc;
	
	@Autowired
	FacturacionDaoJdbc facturacionDaoJdbc;

	float totalgastos =0;
	
	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model modelo,
			HttpServletRequest request ) {
	
		int elemento = Integer.parseInt(this.elementos);
		Pageable pageRequest = PageRequest.of(page, elemento);
		//		
		HttpSession misession= request.getSession(true);		 
		mieempresa = (Empresa) misession.getAttribute("empresaCart");
		String mensaje  =   (String) misession.getAttribute("mensaje");
		//	
		Page<Gastos> gastos = gastosDao.findAllByEmpresa(mieempresa, pageRequest) ;//   findAllByEmpresa(mieempresa, pageRequest);		
		
		PageRender<Gastos> pageRender = new PageRender<>("/gastos/listar", gastos, elemento);
		//		
		modelo.addAttribute("mensaje", mensaje);	
		modelo.addAttribute("titulo", "Listado de Gastos");
		modelo.addAttribute("empresatipos", mieempresa);
		modelo.addAttribute("datos", gastos);
		modelo.addAttribute("page", pageRender);
		
		return "gastos/listar";
	}


	@RequestMapping(value="/form") 
	public String form (Model modelo) {	
		Gastos gastos  = new Gastos();
		//---
		modelo.addAttribute("titulo","Registro de Gastos");	
		modelo.addAttribute("datos",gastos);
		
		return "gastos/form";
	};
	
	
	@RequestMapping(value="/form", method=RequestMethod.POST)
	public String salvar (@Valid @ModelAttribute(value="datos") Gastos gastos, BindingResult result, Model model, 
			RedirectAttributes flash, SessionStatus status) {	
		
		if (result.hasErrors()) {
			model.addAttribute("titulo","Registro de Gastos");						
			return "gastos/form";
		} else {
			String mensajeFlash =  ( String.valueOf(gastos.getId()) != null)? "Gasto Editado con éxito" : " Gasto guardado con éxito "  ;
			gastosDao.save(gastos);
			model.addAttribute("titulo","Creación de Gastos");
		    status.setComplete();
		    flash.addFlashAttribute("success", mensajeFlash );
		
		return "redirect:/gastos/listar";
		}
	};
	
	@RequestMapping(value="/form/{id}")
	public String editar(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		
		Gastos gasto = null;
		
		if(id > 0) {
			gasto = gastosDao.findById(id);
			if (gasto == null) {
				flash.addFlashAttribute("error", " El codigo del gasto no existe en la Base de datos");
				return "redirect:/gastos/listar";
			}
		} else {
			flash.addFlashAttribute("error", " Codigo de Gasto no existe");
			return "redirect:/gasto/listar";
		}
		
		model.put("datos", gasto);
		model.put("titulo", "Editar Codigo de Gasto");
		
		flash.addFlashAttribute("success", " Codigo de Gasto guardado con éxito");
		return "gastos/form";
	}
	
	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id > 0) {
			try {
				gastosDao.delete(id);
				flash.addFlashAttribute("success", " Gasto eliminado con éxito");
			} catch (Exception e) {
				System.out.println("error al borrar " +e.getMessage());
				flash.addFlashAttribute("error", " Error al intentar eliminar Gasto "+e.getMessage());
			}			
		}
		
		return "redirect:/gastos/listar";
	}


	@RequestMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Gastos gasto = gastosDao.findById(id);		
		
		if (gasto == null) {
			flash.addAttribute("error", "El Gasto no existe en la Base de datos");
			return "redirect:/gastos/listar";
		}
		//
		model.put("titulo", "DETALLE DE GASTOS");
		model.put("datos", gasto);
		//
		return "gastos/ver";
	}
	
	@RequestMapping(value = "/aprovar/{id}")
	public String aprovar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Gastos gasto = gastosDao.findById(id);		
		
		if (gasto == null) {
			flash.addAttribute("error", "El Gasto no existe en la Base de datos");
			return "redirect:/gastos/listar";
		}
		//
		// Obteniendo el usuario que inserto el registro
		Authentication auth = SecurityContextHolder
	            .getContext()
	            .getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		///
		gasto.setEstadogastos(EstadoGastos.Aprovado);
		gasto.setFechaaprovacion(new Date());
		gasto.setUsuarioaprovo(userDetail.getUsername());
		gastosDao.save(gasto);
		//
		flash.addFlashAttribute("success", " Gasto Aprovado con éxito");
			
		//
		return "redirect:/gastos/listar";
	}
	
	
	@RequestMapping(value="/gastosperiodo")
	public String reportefacturacion(Map<String, Object> model,
			HttpServletRequest request) {
		

		HttpSession misession = request.getSession(true);
		mieempresa = (Empresa) misession.getAttribute("empresaCart");

		model.put("titulo", "GASTOS POR PERIODO");
		model.put("fechainicio", new Date());
		model.put("fechafin", new Date());
		return "gastos/reportegastos";
	}
	
	
	@RequestMapping(value="/gastosperiodopost", method = RequestMethod.POST)
	public void reportegastospost(Map<String, Object> model, RedirectAttributes flash, HttpServletRequest request,HttpServletResponse response,
	SessionStatus status) throws JRException, IOException {

		
		// Ocupacion ocupacion = ocupacionServImp.findById(idOcupacion);
				String fechainicio = request.getParameter("fechainicio");
				
				String fechafin = request.getParameter("fechafin");
				
				Date fechainicio2=  Miscelaneos.ParseFecha(fechainicio) ;
				Date fechafin2=     Miscelaneos.ParseFecha(fechafin) ;
				
//				System.out.println("Fecha inicio "+ fechainicio + " fecha final " + fechafin + " empresa_id "+mieempresa.getId());
//				System.out.println("Fecha inicio "+fechainicio2+ " fecha final " + fechafin2 + " empresa_id "+mieempresa.getId());

				
				List <GastosPeriodo> datos = gastosDaoJdbc.gastosperiodo(mieempresa.getId(), fechainicio2, fechafin2);
				
				//System.out.println(" registros" + datos.size());
					
				List<GastosPeriodo> datos2 = new ArrayList();
				totalgastos =0;
				
				datos.forEach(dat -> {	
					//System.out.println("nombre servicio getTipofactura2=" + dat.getTipofactura2() + " tipogasto2 -->" + dat.getTipogasto2() + " getEstadogastos2()-->" + dat.getEstadogastos2() );
					totalgastos += dat.getMonto();
					//
					datos2.add( new GastosPeriodo(dat.getEmpresa(), dat.getTipogasto(), dat.getTipofactura(), dat.getEstadogastos(), 
							dat.getEventos(), dat.getMonto(),
							mieempresa, dat.getTipofactura2(), dat.getTipogasto2(),
							dat.getEstadogastos2(), mieempresa.getNombre(),
							dat.getTipogasto2().toString(), 
                            dat.getEstadogastos2().toString(), 
                            dat.getTipofactura2().toString()
							) 

							);
								
				});
				
	            Map<String, Object> parametros = new HashedMap();
				parametros.put("nombrereporte", "Reporte de Gastos"  );
				parametros.put("periodo", " Del: " +fechainicio+ " Al:" +fechafin );
				parametros.put("totalfacturacion", totalgastos );
				parametros.put("empresa",   new String(mieempresa.getNombre()).toUpperCase());
				
				final InputStream stream = this.getClass().getResourceAsStream("/jaspers" + File.separator + "GastosPeriodo.jrxml");
		        //System.out.println(stream.toString());
		      
		        // Compile the Jasper report from .jrxml to .japser
		        final JasperReport report = JasperCompileManager.compileReport(stream);

		        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource( datos2);
		         
		        JasperPrint jasperPrint = JasperFillManager.fillReport(report, parametros, ds);
		          
		        // Vista previa de PDF en línea
		         response.setContentType("application/pdf");
		         response.setHeader("Content-Disposition", "attachment; filename=GastosPorPeriodo.pdf");
		         final OutputStream outputStream = response.getOutputStream();
		         JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

	}

	@RequestMapping(value="/utilidadperiodo")
	public String reporteutilidad(Map<String, Object> model,
			HttpServletRequest request) {
		

		HttpSession misession = request.getSession(true);
		mieempresa = (Empresa) misession.getAttribute("empresaCart");

		model.put("titulo", "UTILIDAD POR PERIODO");
		model.put("fechainicio", new Date());
		model.put("fechafin", new Date());
		return "gastos/reporteutilidad";
	}

	
	@RequestMapping(value="/utilidadperiodopost", method = RequestMethod.POST)
	public void reporteutilidadpost(Map<String, Object> model, RedirectAttributes flash, HttpServletRequest request,HttpServletResponse response,
	SessionStatus status) throws JRException, IOException {

		
		// Ocupacion ocupacion = ocupacionServImp.findById(idOcupacion);
				String fechainicio = request.getParameter("fechainicio");
				
				String fechafin = request.getParameter("fechafin");
				
				Date fechainicio2=  Miscelaneos.ParseFecha(fechainicio) ;
				Date fechafin2=     Miscelaneos.ParseFecha(fechafin) ;
				
				System.out.println("Utilidad por periodo: Fecha inicio "+ fechainicio + " fecha final " + fechafin + " empresa_id "+mieempresa.getId());
				System.out.println("Fecha inicio "+fechainicio2+ " fecha final " + fechafin2 + " empresa_id "+mieempresa.getId());			
				//
				FacturacionEnc facturacion =  facturacionDaoJdbc.facturacionperiodo(mieempresa.getId(), fechainicio2, fechafin2);
				String gastos = gastosDaoJdbc.gastoAprobperiodo(mieempresa.getId(), fechainicio2, fechafin2);
				float totalgastos =0;
				System.out.println(" gastos=" +gastos+ " Total totalgastos = "+ totalgastos + " facturacion Cargos adicionales= "+facturacion.getMontoCargosAdicionales());
				//				
				if (gastos == null) {
					totalgastos = 0;
				}else {
					
					totalgastos = Float.valueOf(gastos);
				}
				//
				//System.out.println(" gastos2=" +gastos+ " Total totalgastos = "+ totalgastos + " facturacion Cargos adicionales= "+facturacion.getMontoCargosAdicionales());
				
				List<UtilidadReportePeriodo> data = new ArrayList <UtilidadReportePeriodo>();
				data.add(new UtilidadReportePeriodo(
								facturacion.getTotalFacturacion(),
								facturacion.getMontoCargosAdicionales(),
								facturacion.getMontoImpuestos(),
								facturacion.getMontoOcupacion(),
								facturacion.getMontoPreReserva(),
								totalgastos						 
						));
				
				//long totalfacturacion, long cargosAdicionales, long impuestos, long montoocupacion,
				//long montoprereserva, long totalgastos
				
				//List<GastosPeriodo> datos2 = new ArrayList();
				System.out.println(" Registros -->" + data.size());
							
				if ( !data.isEmpty() ) {
					Map<String, Object> parametros = new HashedMap();
					parametros.put("nombrereporte", " Reporte de Utilidad del Periodo "  );
					parametros.put("periodo", " Del: " +fechainicio+ " Al:" +fechafin );
					parametros.put("totalfacturacion", totalgastos );
					parametros.put("empresa",   new String(mieempresa.getNombre()).toUpperCase());
					parametros.put("utilidad",   facturacion.getTotalFacturacion() - totalgastos);
				
					final InputStream stream = this.getClass().getResourceAsStream("/jaspers" + File.separator + "UtilidadPeriodo.jrxml");
			        //System.out.println(stream.toString());
			      
			        // Compile the Jasper report from .jrxml to .japser
			        final JasperReport report = JasperCompileManager.compileReport(stream);

			        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource( data);
			         
			        JasperPrint jasperPrint = JasperFillManager.fillReport(report, parametros, ds);
			          
			        // Vista previa de PDF en línea
			         response.setContentType("application/pdf");
			         response.setHeader("Content-Disposition", "attachment; filename=UtilidadPeriodo.pdf");
			         final OutputStream outputStream = response.getOutputStream();
			         JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);	
				}
	}

}
