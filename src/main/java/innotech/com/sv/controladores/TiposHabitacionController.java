package innotech.com.sv.controladores;



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

import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.Promocion;
import innotech.com.sv.modelos.TiposHabitacion;
import innotech.com.sv.paginator.PageRender;
import innotech.com.sv.servicios.PromocionImp;
import innotech.com.sv.servicios.TipoHabitacionImp;

@Controller
@SessionAttributes({"datos","empresatipos","promocionestipos"})
@RequestMapping("/tiposhabitacion")
public class TiposHabitacionController {
protected final Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	TipoHabitacionImp tipoHabitacionServ;
	
	@Value("${innotec.com.elementosPorPagina}")
	String elementos ;
	
	@Autowired
	PromocionImp promocionImp;
	
	@Autowired
	Empresa mieempresa ;
	
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
		//Page<TiposHabitacion> tiposHabitacion = tipoHabitacionServ.findAll(pageRequest);
		Page<TiposHabitacion> tiposHabitacion = tipoHabitacionServ.findAllByEmpresa(mieempresa, pageRequest);
		//Page<TiposHabitacion> tiposHabitacion = tipoHabitacionServ.find(mieempresa.getId(), pageRequest);
		
		PageRender<TiposHabitacion> pageRender = new PageRender<>("/tiposhabitacion/listar", tiposHabitacion, elemento);
		//		
		
		List<Promocion> promociones =  promocionImp.findByEmpresa(mieempresa.getId()) ;
		
				 
		/*System.out.println("mieempresa.getId() --> "+ mieempresa.getId());
		List<Promocion> promociones =  promocionImp.findByEmpresaId(mieempresa.getId()) ;
		for (Promocion prom : promociones) {
			System.out.println( " salida --> " + prom.getDescripcion());
		}*/
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("titulo", "Listado de Tipos de habitación");
		modelo.addAttribute("datos", tiposHabitacion);
		modelo.addAttribute("empresatipos", mieempresa);
		modelo.addAttribute("promocionestipos", promociones);		
		modelo.addAttribute("page", pageRender);
		
		return "tiposhabitacion/listar";
	}
	
	
	@RequestMapping(value="/ver/{id}")
	public String ver(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		
		TiposHabitacion promocion = tipoHabitacionServ.findById(id);
		
		if (promocion==null) {
			flash.addAttribute("error", "El tipo de habitación no existe en la Base de datos");
			return "redirect:tiposhabitacion/listar";
		}
		//
		model.put("titulo", "Detalle Habitacion: "+promocion.getDescripcion());
		model.put("datos",promocion);
		//
		return "tiposhabitacion/ver";
	}
	
	@RequestMapping(value="/form") 
	public String form (Model modelo) {	
		TiposHabitacion tiposhabitacion  = new TiposHabitacion();
		//---
		modelo.addAttribute("titulo","Creación de Tipos de Habitacion");	
		modelo.addAttribute("datos",tiposhabitacion);
		
		return "tiposhabitacion/form";
	};
	
	@RequestMapping(value="/form", method=RequestMethod.POST)
	public String salvar (@Valid @ModelAttribute(value="datos") TiposHabitacion tipohabitacion, BindingResult result, Model model, 
			RedirectAttributes flash, SessionStatus status) {	
		
		if (result.hasErrors()) {
			model.addAttribute("titulo","Creación de Tipos de Habitación");						
			return "tiposhabitacion/form";
		} else {
			String mensajeFlash =  ( String.valueOf(tipohabitacion.getId()) != null)? "Tipo de habitacion Editada con éxito" : " Tipo de habitación guardada con éxito "  ;
			tipoHabitacionServ.save(tipohabitacion);
			model.addAttribute("titulo","Creación de Tipos de Habitación");
		    status.setComplete();
		    flash.addFlashAttribute("success", mensajeFlash );
		
		return "redirect:/tiposhabitacion/listar";
		}
	};
	//Para Editar.	
	@RequestMapping(value="/form/{id}")
	public String editar(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		
		TiposHabitacion tipohabitacion = null;
		
		if(id > 0) {
			tipohabitacion = tipoHabitacionServ.findById(id);
			
			if (tipohabitacion == null) {
				flash.addFlashAttribute("error", " El tipo de habitación no existe en la Base de datos");
				return "redirect:/tiposhabitacion/listar";
			}
		} else {
			flash.addFlashAttribute("error", " Tipo de habitación no existe");
			return "redirect:tiposhabitacion/listar";
		}
		
		
		model.put("datos", tipohabitacion);
		model.put("titulo", "Editar Promocion");
		flash.addFlashAttribute("success", " Tipo de habitación guardada con éxito");
		return "tiposhabitacion/form";
	}
	
	
	@RequestMapping(value="/eliminar/{id}")
	public String eliminar(@PathVariable(value="id") Long id, RedirectAttributes flash) {
		
		if(id > 0) {			
			try {
				tipoHabitacionServ.delete(id);
				flash.addFlashAttribute("success", " Promoción eliminada con éxito");
			} catch (Exception e) {
				System.out.println("error al borrar " +e.getMessage());
				flash.addFlashAttribute("error", " Error al intentar eliminar tipo de habitación "+e.getMessage());
			}
						
		}
		return "redirect:/tiposhabitacion/listar";
	}
	
}