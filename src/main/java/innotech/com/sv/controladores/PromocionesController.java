package innotech.com.sv.controladores;

import java.util.Date;

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

import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.Promocion;
import innotech.com.sv.paginator.PageRender;
import innotech.com.sv.servicios.EmpresaServiceImp;
import innotech.com.sv.servicios.PromocionImp;

@Controller
@SessionAttributes({"promocion","empresa"})
@RequestMapping("/promocion")
public class PromocionesController {
protected final Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	PromocionImp promocionServ;
	
	@Value("${innotec.com.elementosPorPagina}")
	String elementos ;
	
	@Autowired
	EmpresaServiceImp empresaServ;
	
	@Autowired
	Empresa mieempresa ;
	
	
	@RequestMapping(value="/listar", method = RequestMethod.GET)
	public String inicial (@RequestParam(name="page", defaultValue="0") int page,   Model modelo, 			  
			   HttpServletRequest request ) {
		
		int elemento = Integer.parseInt(this.elementos);  
				
		HttpSession misession= request.getSession(true);		 
		mieempresa = (Empresa) misession.getAttribute("empresaCart");
				 
		Pageable  pageRequest =  PageRequest.of(page, elemento);
		
		//Page<Promocion> promocion = promocionServ.findAll(pageRequest);
		Page<Promocion> promocion = promocionServ.findAllByEmpresa(mieempresa, pageRequest)  ;		
		
		PageRender<Promocion> pageRender = new PageRender<>("/promocion/listar", promocion, elemento) ;
		//List<Empresa> empresa = new ArrayList<Empresa>();
		 
		System.out.println(mieempresa.getNombre());
		 
		//empresa = empresaServ.findAll();
		 
		 
	     String mensaje  =   (String) misession.getAttribute("mensaje");
	     
	    /* for( Promocion prom : promocion ) {
	    	 System.out.println(" salida -->" + prom.getDescripcion());
	     }
	     */
	     modelo.addAttribute("mensaje", mensaje);
	     
		//modelo.addAttribute("mensaje","hola desde thymeleaf");		
		modelo.addAttribute("titulo","Mantenimiento de Promociones");	
		modelo.addAttribute("datos",promocion);
		modelo.addAttribute("empresa",mieempresa);
		modelo.addAttribute("page",pageRender);
		return "promocion/listar";
	};
	
	
	@RequestMapping(value="/ver/{id}")
	public String ver(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Promocion promocion = promocionServ.findById(id);
		if (promocion==null) {
			flash.addAttribute("error", "El descuento no existe en la Base de datos");
			return "redirect:/promocion/listar";
		}
		//
		model.put("promocion", promocion);
		model.put("titulo", "Detalle Promocion: "+promocion.getDescripcion());
		model.put("datos",promocion);
		//
		return "promocion/ver";
	}
	
	@RequestMapping(value="/form") 
	public String form (Model modelo) {	
		Promocion promocion = new Promocion();
		//---
		modelo.addAttribute("titulo","Creación de Descuentos");	
		modelo.addAttribute("promocion",promocion);
		
		return "promocion/form";
	};
	
	@RequestMapping(value="/form", method=RequestMethod.POST)
	public String salvar (@Valid @ModelAttribute(value="promocion") Promocion promocion, BindingResult result, Model model, 
			RedirectAttributes flash, SessionStatus status) {	
		
		if (result.hasErrors()) {
			model.addAttribute("titulo","Creación de Descuentos");						
			return "promocion/form";
		} else {
			//Validaciones fechas de la reserva
			Date fechaini = promocion.getFechainicio();
			Date fechafin = promocion.getFechafin();
			if(fechaini.compareTo(fechafin)>=0) {
				String mensajeFlash ="La fecha final debe ser mayor a la inicial";
				 flash.addFlashAttribute("error",  mensajeFlash);
				 model.addAttribute("error",mensajeFlash);		
				 model.addAttribute("titulo","Creación de Descuentos");						
				 return "promocion/form";
			};
			// Validando que la fecha inicial debe ser mayor a la fecha actual
			Date fechaactual = new Date();
			if(fechaini.compareTo(fechaactual)<0) {
				String mensajeFlash ="La fecha Inicial debe ser mayor o igual a la fecha Actual";
				 flash.addFlashAttribute("error",  mensajeFlash);
				 model.addAttribute("error",mensajeFlash);		
				 model.addAttribute("titulo","Creación de Descuentos");						
				 return "promocion/form";
			};
			//
			String mensajeFlash =  ( String.valueOf(promocion.getId()) != null)? "Descuento Editado con éxito" : " Descuento guardado con éxito "  ;
		     promocionServ.save(promocion);
			model.addAttribute("titulo","Creación de Descuentos");
		    status.setComplete();
		    flash.addFlashAttribute("success", mensajeFlash );
		
		return "redirect:/promocion/listar";
		}
	};
	
	@RequestMapping(value="/form/{id}")
	public String editar(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		
		Promocion promocion = null;
		
		if(id > 0) {
			promocion = promocionServ.findById(id);
			if (promocion == null) {
				flash.addFlashAttribute("error", " El Descuento no existe en la Base de datos");
				return "redirect:promocion/listar";
			}
		} else {
			flash.addFlashAttribute("error", " Descuento no existe");
			return "redirect:/promocion/listar";
		}
		
		
		model.put("promocion", promocion);
		model.put("titulo", "Editar Promocion");
		flash.addFlashAttribute("success", " Descuento guardado con éxito");
		return "promocion/form";
	}
	
	
	@RequestMapping(value="/eliminar/{id}")
	public String eliminar(@PathVariable(value="id") Long id, RedirectAttributes flash) {
		
		if(id > 0) {
			try {
				promocionServ.delete(id);
				flash.addFlashAttribute("success", " Descuento eliminado con éxito");
			} catch (Exception e) {
				System.out.println("error al borrar " +e.getMessage());
				flash.addFlashAttribute("error", " Error al intentar eliminar el descuento "+e.getMessage());
			}			
		}		
		return "redirect:/promociones/listar";
	}
	
}