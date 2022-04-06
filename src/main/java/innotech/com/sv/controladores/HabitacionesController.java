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

import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.Habitacion;
import innotech.com.sv.modelos.TiposHabitacion;
import innotech.com.sv.paginator.PageRender;

import innotech.com.sv.servicios.HabitacionImp;
import innotech.com.sv.servicios.TipoHabitacionImp;

@Controller
@SessionAttributes({ "datos", "habitaciones" ,"empresatipos","tipoHabitacion"})
@RequestMapping("/habitacion")
public class HabitacionesController {

	@Value("${innotec.com.elementosPorPagina}")
	String elementos;

	@Autowired
	HabitacionImp habitacionImp;

	@Autowired
	Empresa mieempresa;

	@Autowired
	TipoHabitacionImp tipoHabitacionServ;
	
	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model modelo,
			HttpServletRequest request) {

		int elemento = Integer.parseInt(this.elementos);
		Pageable pageRequest = PageRequest.of(page, elemento);
		//
		HttpSession misession = request.getSession(true);
		mieempresa = (Empresa) misession.getAttribute("empresaCart");
		String mensaje  =   (String) misession.getAttribute("mensaje");
		//

		Page<Habitacion> habitacion = habitacionImp.findByEmpresa(mieempresa, pageRequest);// findAllByEmpresa(mieempresa,
																							// pageRequest);

		PageRender<Habitacion> pageRender = new PageRender<>("/habitacion/listar", habitacion, elemento);
		//
		List<TiposHabitacion> tipoHabitacion = tipoHabitacionServ.findByEmpresa(mieempresa);
				
		modelo.addAttribute("mensaje", mensaje);		
		modelo.addAttribute("titulo", "Listado de habitaciones");
		modelo.addAttribute("datos", habitacion);
		modelo.addAttribute("tipoHabitacion", tipoHabitacion);
		modelo.addAttribute("empresatipos", mieempresa);		
		modelo.addAttribute("page", pageRender);

		return "habitacion/listar";
	}

	
	@RequestMapping(value="/form") 
	public String form (Model modelo) {	
		Habitacion habitacion  = new Habitacion();
		//---
		modelo.addAttribute("titulo","Creación de Habitaciones");	
		modelo.addAttribute("datos",habitacion);
		
		return "habitacion/form";
	};
	
	
	@RequestMapping(value="/form", method=RequestMethod.POST)
	public String salvar (@Valid @ModelAttribute(value="datos") Habitacion habitacion, BindingResult result, Model model, 
			RedirectAttributes flash, SessionStatus status) {	
		
		if (result.hasErrors()) {
			model.addAttribute("titulo","Creación de Tipos de Habitación");						
			return "habitacion/form";
		} else {
			String mensajeFlash =  ( String.valueOf(habitacion.getId()) != null)? "Habitacion Editada con éxito" : " Habitación guardada con éxito "  ;
			habitacionImp.save(habitacion);
			model.addAttribute("titulo","Creación de Tipos de Habitación");
		    status.setComplete();
		    flash.addFlashAttribute("success", mensajeFlash );
		
		return "redirect:/habitacion/listar";
		}
	};
	
	
	@RequestMapping(value="/form/{id}")
	public String editar(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		
		Habitacion habitacion = null;
		
		if(id > 0) {
			habitacion = habitacionImp.findById(id);
			if (habitacion == null) {
				flash.addFlashAttribute("error", " La habitación no existe en la Base de datos");
				return "redirect:/habitacion/listar";
			}
		} else {
			flash.addFlashAttribute("error", " Habitación no existe");
			return "redirect:/habitacion/listar";
		}
		
		model.put("datos", habitacion);
		model.put("titulo", "Editar Habitación");
		
		flash.addFlashAttribute("success", " Habitación guardada con éxito");
		return "habitacion/form";
	}
	
	
	
	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id > 0) {
			try {
				habitacionImp.delete(id);
				flash.addFlashAttribute("success", " Habitación eliminada con éxito");
			} catch (Exception e) {
				System.out.println("error al borrar " +e.getMessage());
				flash.addFlashAttribute("error", " Error al intentar eliminar habitación "+e.getMessage());
			}
		}
		return "redirect:/habitacion/listar";
	}

	@RequestMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Habitacion habitacion = habitacionImp.findById(id);

		if (habitacion == null) {
			flash.addAttribute("error", "La habitación no existe en la Base de datos");
			return "redirect:/habitacion/listar";
		}
		//
		model.put("titulo", "Código de Habitacion: " + habitacion.getCodigo());
		model.put("datos", habitacion);
		//
		return "habitacion/ver";
	}

}
