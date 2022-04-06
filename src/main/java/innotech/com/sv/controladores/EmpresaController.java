package innotech.com.sv.controladores;

import java.util.ArrayList;
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
import innotech.com.sv.modelos.Moneda;
import innotech.com.sv.paginator.PageRender;
import innotech.com.sv.servicios.EmpresaServiceImp;
import innotech.com.sv.servicios.MonedaImp;


@Controller
@SessionAttributes({"empresa","moneda","datos"})
@RequestMapping("/empresa")
public class EmpresaController {
	
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	EmpresaServiceImp empresaServ;
	
	@Autowired
	MonedaImp monedaServ;
	
	@Value("${innotec.com.elementosPorPagina}")
	String elementos ;
	
	@Value("${innotec.com.IdEmpresaDefault}")
	long empresaId ;
	
	@RequestMapping(value="/listar", method = RequestMethod.GET)
	public String inicial (@RequestParam(name="page", defaultValue="0") int page,   Model modelo, 			  
			   HttpServletRequest request ) {
		
		int elemento = Integer.parseInt(this.elementos);  
		
		//Pageable  pageRequest =  PageRequest.of(page, elemento);
		PageRequest  pageRequest =  PageRequest.of(page, elemento);
		Page<Empresa> empresas = empresaServ.findAll(pageRequest);
		
		PageRender<Empresa> pageRender = new PageRender<>("/empresa/listar", empresas, elemento) ;
        
		List<Moneda> moneda = new ArrayList<Moneda>();		
		moneda = monedaServ.findAll();
		
		  HttpSession misession= (HttpSession) request.getSession();	        

	      String mensaje  =   (String) misession.getAttribute("mensaje");
	      
	      modelo.addAttribute("mensaje", mensaje);
	      
		//modelo.addAttribute("mensaje","hola desde thymeleaf");		
		modelo.addAttribute("titulo","Mantenimiento de Empresa");	
		modelo.addAttribute("datos",empresas);
		modelo.addAttribute("page",pageRender);
		modelo.addAttribute("moneda",moneda);
		return "empresa/listar";
	};
	
	
	@RequestMapping(value="/ver/{id}")
	public String ver(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Empresa empresa = empresaServ.findById(id);
		
		if (empresa==null) {
			flash.addAttribute("error", "La empresa no existe en la Base de datos");
			return "redirect:/empresa/listar";
		}
		//
		model.put("empresa", empresa);
		model.put("titulo", "Detalle Empresa: "+empresa.getNombre());
		model.put("datos",empresa);

		//
		return "empresa/ver";
	}
	
	@RequestMapping(value="/form") 
	public String form (Model modelo) {	
		Empresa empresa = new Empresa();
		
		//empresa.setNombre("Carlitos Avalos");
		//---
		modelo.addAttribute("titulo","Creación de Empresa");	
		modelo.addAttribute("empresa",empresa);
		
		return "empresa/form";
	};
	
	@RequestMapping(value="/form", method=RequestMethod.POST)
	public String salvar (@Valid @ModelAttribute(value="empresa") Empresa empresa, BindingResult result, Model model, 
			RedirectAttributes flash, SessionStatus status) {	
		
		if (result.hasErrors()) {
			model.addAttribute("titulo","Creación de Empresa");	
			
			List<Moneda> moneda = new ArrayList<Moneda>();			
			moneda = monedaServ.findAll();
			
			return "empresa/form";
		} else {
			String mensajeFlash =  ( String.valueOf(empresa.getId()) == null)? "Empresa Editada con éxito" : " Empresa guardada con éxito "  ;
		     empresaServ.save(empresa);
			 model.addAttribute("titulo","Creación de Empresa");
		    status.setComplete();
		    flash.addFlashAttribute("success", mensajeFlash );
		
		return "redirect:/empresa/listar";
		}
	}; 
	
	@RequestMapping(value="/form/{id}")
	public String editar(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash ) {
		
		Empresa empresa = null;
		List<Moneda> moneda = new ArrayList<Moneda>();			
		moneda = monedaServ.findAll();
		
		
		if(id > 0) {
			empresa = empresaServ.findById(id);
			if (empresa == null) {
				flash.addFlashAttribute("error", " La empresa no existe en la Base de datos");
				return "redirect:empresa/listar";
			}
		} else {
			flash.addFlashAttribute("error", " Empresa no existe");
			return "redirect:/empresa/listar";
		}

		model.put("empresa", empresa);
		model.put("moneda",moneda);
		model.put("titulo", "Editar Empresa");
		
		flash.addFlashAttribute("success", " Empresa guardada con éxito");
		return "empresa/form";
	}
	
	
	@RequestMapping(value="/eliminar/{id}")
	public String eliminar(@PathVariable(value="id") Long id, RedirectAttributes flash) {
		
		if(id > 0) {
			try {
				empresaServ.delete(id);
				flash.addFlashAttribute("success", " Empresa eliminada con éxito");
			} catch (Exception e) {
				System.out.println("error al borrar " +e.getMessage());
				flash.addFlashAttribute("error", " Error al intentar eliminar empresa "+e.getMessage());
			}
		}
		return "redirect:/empresa/listar";
	}
	

}
