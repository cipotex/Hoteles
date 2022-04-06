
package innotech.com.sv.controladores;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import innotech.com.sv.modelos.CargosAdicionales;
import innotech.com.sv.modelos.ClaseServicio;
import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.EstadoCargoAdicionalEnum;
import innotech.com.sv.modelos.Habitacion;
import innotech.com.sv.modelos.Ocupacion;
import innotech.com.sv.modelos.Promocion;
import innotech.com.sv.modelos.Servicio;
import innotech.com.sv.paginator.PageRender;
import innotech.com.sv.servicios.ActivoImp;
import innotech.com.sv.servicios.ICargosAdicionales;
import innotech.com.sv.servicios.IClaseServicio;
import innotech.com.sv.servicios.IOcupacion;
import innotech.com.sv.servicios.IPromocion;
import innotech.com.sv.servicios.IServicio;


@Controller
@SessionAttributes({"cargosadicionales","empresatipos","ocupaciones","reserva","habitacion","claseservicio","promocion","descuento"})
@RequestMapping("/cargosadicionales")
public class CargosAdicionalesController {
	
	@Value("${innotec.com.elementosPorPagina}")
	String elementos ;
	
	@Autowired
	ActivoImp inventarioImp;
	
	@Autowired
	Empresa mieempresa ;
	
	@Autowired
	ICargosAdicionales cargosAdicionalesimp;
	
	@Autowired
	IOcupacion ocupacionServImp;
	
	@Autowired
	IClaseServicio claseservImp;
	
	@Autowired
	IServicio servicioImp;
	
	@Autowired
	IPromocion promocionServ;
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
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
		//List<CargosAdicionales> habitaciones = cargosAdicionalesimp.findByEmpresa(mieempresa);
		
		Page<CargosAdicionales> cargosadicionales = cargosAdicionalesimp.findByEmpresa(mieempresa, pageRequest);//   findAllByEmpresa(mieempresa, pageRequest);
		//
		
		PageRender<CargosAdicionales> pageRender = new PageRender<>("/cargosadicionales/listar", cargosadicionales, elemento);
		//		
		List<Ocupacion> Ocupacion = ocupacionServImp.findByEmpresa(mieempresa);
		//
		List<Habitacion> habitaciones = new ArrayList<Habitacion>();
		//
		for(Ocupacion hab: Ocupacion) {
			habitaciones.add(hab.getReserva().getHabitacion());
		}
		//
		List<ClaseServicio>  claseServicio = claseservImp.findByEmpresa(mieempresa);
		for(ClaseServicio temp :claseServicio ) {
			System.out.println(temp.getDescripcion());
		};
		//
		modelo.addAttribute("mensaje", mensaje);	
		modelo.addAttribute("titulo", "Listado de Cargos Adicionales");
		modelo.addAttribute("cargosadicionales", cargosadicionales);		
		modelo.addAttribute("empresatipos", mieempresa);		
		modelo.addAttribute("ocupaciones", Ocupacion);
		modelo.addAttribute("reserva", Ocupacion);
		modelo.addAttribute("page", pageRender);
		modelo.addAttribute("habitacion",habitaciones );
		modelo.addAttribute("claseservicio", claseServicio);
		modelo.addAttribute("descuento", 0);
		
		return "cargosadicionales/listar";
	}
	
		@RequestMapping(value="/ajaxservicio")
		public String ajaxBrands(Model modelo,	HttpServletRequest request) {
			//long tipo = (long) 1;
			System.out.println("Mensaje desde /ajaxservicio");
			
			modelo.addAttribute("descuento", 0);
			
			return "cargosadicionales/form :: descuen";

		}
		
	
	@RequestMapping(value="/form") 
	public String form (Model modelo, HttpServletRequest request) {	
		CargosAdicionales cargos  = new CargosAdicionales();
		//---
		HttpSession misession= request.getSession(true);		 
		mieempresa = (Empresa) misession.getAttribute("empresaCart");
		//
		List<Promocion> promociones = promocionServ.findByEmpresa(mieempresa.getId());
		
		modelo.addAttribute("titulo","Creación de Cargos Adicionales");	
		modelo.addAttribute("cargosadicionales",cargos);
		modelo.addAttribute("promocion",promociones);
		//modelo.addAttribute("reserva", mieempresa);
		
		return "cargosadicionales/form";
	};
	
	@RequestMapping(value="/crear/{id}")
	public String crear(@PathVariable(value="id") Long id, Map<String, Object> model, 
			            RedirectAttributes flash, Model modelo, HttpServletRequest request) {
				
		CargosAdicionales cargos  = new CargosAdicionales();
             //		
		HttpSession misession= request.getSession(true);		 
		mieempresa = (Empresa) misession.getAttribute("empresaCart");
		//
		List<Promocion> promociones = promocionServ.findByEmpresa(mieempresa.getId());
		//
		Ocupacion ocupacion = ocupacionServImp.findById(id) ;
		
		cargos.setOcupacion(ocupacion);
		cargos.setEmpresa(mieempresa);
		cargos.setEstado(EstadoCargoAdicionalEnum.Pendiente);
		cargos.setRecurrente("N");
		System.out.println(cargos.getOcupacion().getReserva().getHabitacion().getCodigo());
		//---
		modelo.addAttribute("titulo","Creación de Cargos Adicionales");	
		modelo.addAttribute("cargosadicionales",cargos);
		modelo.addAttribute("promocion",promociones);
		modelo.addAttribute("descuento", 0);
		
		return "cargosadicionales/form";
				
	}

	@RequestMapping(value="/ajaxpromo")
	public String ajaxpromo(@RequestParam("promocion") long id, Model modelo, HttpServletRequest request) {
		//long tipo = (long) 1;
		
		System.out.println("Mensaje desde ajaxpromo id= "+id);
		//
		if (id >0) {
			HttpSession misession= request.getSession(true);		 
			mieempresa = (Empresa) misession.getAttribute("empresaCart");
			//
			//List<Promocion> promociones = promocionServ.findByEmpresa(mieempresa.getId());
			//
			Promocion promocion = promocionServ.findById(id);
			//System.out.println(" ProcDescuento" + promocion.getPorcdescuento());
			//
			modelo.addAttribute("descuento",promocion.getPorcdescuento());		
			//
		}else {
			modelo.addAttribute("descuento",0);	
		}

		
		return "cargosadicionales/form :: descuen";
		
	}
	
	@RequestMapping(value="/form", method=RequestMethod.POST)
	public String salvar (@Valid @ModelAttribute(value="cargosadicionales") CargosAdicionales cargosadicionales, 
			BindingResult result, Model model, 
			@RequestParam(name = "item_id[]", required = false) Long[] itemId,
			@RequestParam(name = "cantidad[]", required = false) Integer[] cantidad, 			
			@RequestParam(name = "clase_id[]", required = false) Long[] claseId,
			@RequestParam(name = "promocion_id[]", required = false) Long[] promocionId,
			RedirectAttributes flash, SessionStatus status) {	
		
		if (itemId == null || itemId.length == 0) {
			model.addAttribute("titulo", "Creación de Cargos Adicionales");
			model.addAttribute("error", "Error: La factura NO puede ir sin líneas!");
			return "cargosadicionales/form";
		}
				
		
		for (int i = 0; i < itemId.length; i++) {
		
			ClaseServicio claseserv = claseservImp.findById( claseId[i] );
			Servicio      servicio  = servicioImp.findById(itemId[i]);	
			Promocion     promocion = null;
			//
			
			if (promocionId.length >0 ) {
			    System.out.println(" Carlitos...: longitud promocion "+ promocionId.length);
			    if (promocionId[i] != null) {
			    	promocion = promocionServ.findById(promocionId[i]); 
			    }			    
			}	
			//
			cargosadicionales.setClaseservicio(claseserv);
			cargosadicionales.setServicio(servicio);
			//
			cargosadicionales.setPrecioUnitario(servicio.getPrecioUnitario());
			cargosadicionales.setCantidad(cantidad[i]);
			
			cargosadicionales.setPromociono(promocion);
			
			cargosAdicionalesimp.save(cargosadicionales);
			
			log.info("ID: " + itemId[i].toString() + " Precio Unitario "+servicio.getPrecioUnitario() +", cantidad: " + cantidad[i].toString() + " ClaseId: "+claseId[i]  );
		}

		//clienteService.saveFactura(factura);
		status.setComplete();

		flash.addFlashAttribute("success", "Cargo Adicional creado con éxito!");
		
		
		
		return "redirect:/cargosadicionales/listar";
		
	};
	

	
	@RequestMapping(value="/form/{id}")
	public String editar(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		
		CargosAdicionales cargoadicional = null;
		
		if(id > 0) {
			cargoadicional = cargosAdicionalesimp.findById(id);
			if (cargoadicional == null) {
				flash.addFlashAttribute("error", " El codigo del Cargo Adicional no existe en la Base de datos");
				return "redirect:/cargosadicionales/listar";
			}
		} else {
			flash.addFlashAttribute("error", " Codigo de Activo no existe");
			return "redirect:/cargosadicionales/listar";
		}
		
		model.put("cargosadicionales", cargoadicional);
		model.put("titulo", "Editar Cargos Adicionales");
		
		flash.addFlashAttribute("success", " Cargo Adicional guardado con éxito");
		return "cargosadicionales/form";
	}
	
	
	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id > 0) {
			try {
				cargosAdicionalesimp.delete(id);
				flash.addFlashAttribute("success", " Cargo Adicional eliminado con éxito");
			} catch (Exception e) {
				System.out.println("error al borrar " +e.getMessage());
				flash.addFlashAttribute("error", " Error al intentar eliminar Cargo Adicional "+e.getMessage());
			}			
		}
		
		return "redirect:/cargosadicionales/listar";
	}
	
	@RequestMapping(value = "/deshabilitar/{id}")
	public String deshabilitar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		
		CargosAdicionales cargoadicional = null;
		
		if (id > 0) {
			try {
				cargoadicional = cargosAdicionalesimp.findById(id) ;
				cargoadicional.setEstado(EstadoCargoAdicionalEnum.Anulado);
				cargosAdicionalesimp.save(cargoadicional);
				flash.addFlashAttribute("success", " Cargo Adicional deshabilitado con éxito");
				//
			} catch (Exception e) {
				System.out.println("error al borrar " +e.getMessage());
				flash.addFlashAttribute("error", " Error al intentar deshabilitar Cargo Adicional "+e.getMessage());
			}			
		}
		
		return "redirect:/cargosadicionales/listar";
	}
	
	  @GetMapping(value="/cargar_servicios/{term}", produces={"application/json"})
	  public @ResponseBody List<Servicio> cargar_servicios(@PathVariable String term  ){
	    	
		  return cargosAdicionalesimp.findByTermino(term);
	    }
    
	@RequestMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		CargosAdicionales cargo = cargosAdicionalesimp.findById(id);		
		
		if (cargo == null) {
			flash.addAttribute("error", "El Cargo Adicional no existe en la Base de datos");
			return "redirect:/cargosadicionales/listar";
		}
		//
		model.put("titulo", "Cargos Adicionales ");
		model.put("cargosadicionales", cargo);
		//
		return "cargosadicionales/ver";
	}
	
}
