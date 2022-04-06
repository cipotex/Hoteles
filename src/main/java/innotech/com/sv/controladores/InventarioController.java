package innotech.com.sv.controladores;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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

import innotech.com.sv.modelos.Activo;
import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.Habitacion;
import innotech.com.sv.paginator.PageRender;
import innotech.com.sv.servicios.ActivoImp;
import innotech.com.sv.servicios.HabitacionImp;

@Controller
@SessionAttributes({"datos","inventario","empresatipos","habitaciones"})
@RequestMapping("/inventario")
public class InventarioController {
	
	@Value("${innotec.com.elementosPorPagina}")
	String elementos ;
	
	@Autowired
	ActivoImp inventarioImp;
	
	@Autowired
	Empresa mieempresa ;
	
	@Autowired
	HabitacionImp habitacionImp;
	
	
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
		List<Habitacion> habitaciones = habitacionImp.findByEmpresa(mieempresa);
		
		Page<Activo> inventario = inventarioImp.findByEmpresa(mieempresa, pageRequest);//   findAllByEmpresa(mieempresa, pageRequest);
		
		
		PageRender<Activo> pageRender = new PageRender<>("/inventario/listar", inventario, elemento);
		//		
		
		modelo.addAttribute("mensaje", mensaje);	
		modelo.addAttribute("titulo", "Listado de Activos Fijos");
		modelo.addAttribute("datos", inventario);
		modelo.addAttribute("habitaciones", habitaciones);
		modelo.addAttribute("empresatipos", mieempresa);
		modelo.addAttribute("page", pageRender);
		
		return "inventario/listar";
	}
	
	@RequestMapping(value="/form") 
	public String form (Model modelo) {	
		Activo activo  = new Activo();
		//---
		modelo.addAttribute("titulo","Creación de Activos");	
		modelo.addAttribute("datos",activo);
		
		return "inventario/form";
	};
	
	
	@RequestMapping(value="/form", method=RequestMethod.POST)
	public String salvar (@Valid @ModelAttribute(value="datos") Activo activo, BindingResult result, Model model, 
			RedirectAttributes flash, SessionStatus status) {	
		
		if (result.hasErrors()) {
			model.addAttribute("titulo","Creación de Activos");						
			return "inventario/form";
		} else {
			String mensajeFlash =  ( String.valueOf(activo.getId()) != null)? "Habitacion Editada con éxito" : " Habitación guardada con éxito "  ;
			inventarioImp.save(activo);
			model.addAttribute("titulo","Creación de Activos");
		    status.setComplete();
		    flash.addFlashAttribute("success", mensajeFlash );
		
		return "redirect:/inventario/listar";
		}
	};
	

	
	@RequestMapping(value="/form/{id}")
	public String editar(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		
		Activo habitacion = null;
		
		if(id > 0) {
			habitacion = inventarioImp.findById(id);
			if (habitacion == null) {
				flash.addFlashAttribute("error", " El codigo de activo no existe en la Base de datos");
				return "redirect:/inventario/listar";
			}
		} else {
			flash.addFlashAttribute("error", " Codigo de Activo no existe");
			return "redirect:/inventario/listar";
		}
		
		model.put("datos", habitacion);
		model.put("titulo", "Editar Codigo de Activo");
		
		flash.addFlashAttribute("success", " Codigo de Activo guardado con éxito");
		return "inventario/form";
	}
	
	
	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id > 0) {
			try {
				inventarioImp.delete(id);
				flash.addFlashAttribute("success", " Activo eliminado con éxito");
			} catch (Exception e) {
				System.out.println("error al borrar " +e.getMessage());
				flash.addFlashAttribute("error", " Error al intentar eliminar Activo "+e.getMessage());
			}			
		}
		
		return "redirect:/inventario/listar";
	}
	

	@RequestMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Activo activo = inventarioImp.findById(id);		
		
		if (activo == null) {
			flash.addAttribute("error", "El Activo no existe en la Base de datos");
			return "redirect:/inventario/listar";
		}
		//
		model.put("titulo", "Código de Activo: " + activo.getCodigo());
		model.put("datos", activo);
		//
		return "inventario/ver";
	}
	
}
