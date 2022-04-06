
package innotech.com.sv.controladores;

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

import innotech.com.sv.modelos.ClaseServicio;
import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.paginator.PageRender;
import innotech.com.sv.servicios.ClaseServicioImp;
import innotech.com.sv.servicios.EmpresaServiceImp;
import innotech.com.sv.servicios.MonedaImp;


@Controller
@SessionAttributes({"empresa","claseservicio"})
@RequestMapping("/claseservicio")
public class ClaseServiciosController {
	
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	EmpresaServiceImp empresaServ;
	
	@Autowired
	MonedaImp monedaServ;
	
	@Autowired
	ClaseServicioImp servicioImp;
	
	@Autowired
	Empresa mieempresa ;
	
	@Value("${innotec.com.elementosPorPagina}")
	String elementos ;
	
	
	@RequestMapping(value="/listar", method = RequestMethod.GET)
	public String inicial (@RequestParam(name="page", defaultValue="0") int page,   Model modelo, 			  
			   HttpServletRequest request ) {
		
		int elemento = Integer.parseInt(this.elementos);
		Pageable pageRequest = PageRequest.of(page, elemento);
		//		
		HttpSession misession= request.getSession(true);		 
		mieempresa = (Empresa) misession.getAttribute("empresaCart");
		//System.out.println("miempresa = " + mieempresa.getNombre());
		String mensaje  =   (String) misession.getAttribute("mensaje");
		//
		Page<ClaseServicio> servicio = servicioImp.findAllByEmpresaActivos (mieempresa.getId(), pageRequest);//   findAllByEmpresa(mieempresa, pageRequest);
		
		PageRender<ClaseServicio> pageRender = new PageRender<>("/claseservicio/listar", servicio, elemento);
		//		
		modelo.addAttribute("mensaje", mensaje);	
		modelo.addAttribute("titulo","Mantenimiento Clases De Servicios de Habitación");	
		modelo.addAttribute("claseservicio",servicio);
		modelo.addAttribute("page",pageRender);
		modelo.addAttribute("empresa",mieempresa);
		//
		return "claseservicio/listar";
	};
	
	
	@RequestMapping(value="/ver/{id}")
	public String ver(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		ClaseServicio servicio = servicioImp.findById(id);
		
		if (servicio==null) {
			flash.addAttribute("error", "La Clase de Servicio no existe en la Base de datos");
			model.put("empresa",mieempresa);
			return "redirect:/servicio/listar";
		}
		//

		model.put("titulo", "Detalle de la Clase de Servicio: ");
		model.put("datos",servicio);
		model.put("empresa",mieempresa);

		//
		return "claseservicio/ver";
	}
	
	@RequestMapping(value="/form") 
	public String form (Model modelo) {	
		ClaseServicio servicio = new ClaseServicio();
		//servicio.setEmpresa(mieempresa);
		
		//System.out.println("Estamos en formss...");
		//empresa.setNombre("Carlitos Avalos");
		//---
		modelo.addAttribute("titulo","Creación de Clases de Servicios");	
		modelo.addAttribute("claseservicio",servicio);
		
		
		return "claseservicio/form";
	};
	
	@RequestMapping(value="/form", method=RequestMethod.POST)
	public String salvar (@Valid @ModelAttribute(value="claseservicio") ClaseServicio servicio, BindingResult result, Model model, 
			RedirectAttributes flash, SessionStatus status) {	
		
		if (result.hasErrors()) {
			model.addAttribute("titulo","Creación de Clase de Servicios");		
			model.addAttribute("empresa",mieempresa);		
			System.out.println("Estamos en errores!!!");
			return "claseservicio/form";
		} else {
			String mensajeFlash =  ( String.valueOf(servicio.getId()) == null)? "Clase de Servicio Editado con éxito" : "Clase de Servicio guardado con éxito "  ;
			servicioImp.save(servicio);
			 model.addAttribute("titulo","Creación de Clase de Servicio Para Habitaciones");
		    status.setComplete();
		    flash.addFlashAttribute("success", mensajeFlash );
		
		return "redirect:/claseservicio/listar";
		}
	}; 
	
	@RequestMapping(value="/form/{id}")
	public String editar(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash ) {
		
		ClaseServicio servicio = null;
		/*List<Moneda> moneda = new ArrayList<Moneda>();			
		moneda = monedaServ.findAll();
		*/
		
		if(id > 0) {
			servicio = servicioImp.findById(id);
			if (servicio == null) {
				flash.addFlashAttribute("error", " La Clase Servicio no existe en la Base de datos");
				return "redirect:claseservicio/listar";
			}
		} else {
			flash.addFlashAttribute("error", " Clase de Servicio no existe");
			return "redirect:/claseservicio/listar";
		}

		model.put("claseservicio", servicio);		
		model.put("titulo", "Editar Clase de Servicio");
		
		flash.addFlashAttribute("success", " Clase De Servicio guardado con éxito");
		return "claseservicio/form";
	}
		
	@RequestMapping(value="/eliminar/{id}")
	public String eliminar(@PathVariable(value="id") Long id, RedirectAttributes flash) {
		
		if(id > 0) {
			try {
				servicioImp.delete(id);
				flash.addFlashAttribute("success", " Clase De Servicio eliminado con éxito");
			} catch (Exception e) {
				System.out.println("error al borrar " +e.getMessage());
				flash.addFlashAttribute("error", " Error al intentar eliminar servicio "+e.getMessage());
			}
		}
		return "redirect:/claseservicio/listar";
	}
	

}
