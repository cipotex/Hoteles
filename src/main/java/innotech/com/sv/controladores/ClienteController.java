package innotech.com.sv.controladores;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import innotech.com.sv.modelos.ClaseServicio;
import innotech.com.sv.modelos.Cliente;
import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.paginator.PageRender;
import innotech.com.sv.servicios.ClientesImp;
import innotech.com.sv.servicios.IUploadFileService;

@Controller
@SessionAttributes({"cliente","empresa"})
@RequestMapping("/cliente")
public class ClienteController {
	@Autowired
	private ClientesImp clienteService;
	
	@Autowired
	private IUploadFileService uploadFileService;

	@Value("${innotec.com.elementosPorPagina}")
	String elementos;

	@Autowired
	Empresa mieempresa ;
	
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Secured("ROLE_USER")
	@RequestMapping(value = "/uploads/{filename:.+}")
	public ResponseEntity<Resource> verfoto(@PathVariable String filename) {
		Path pathfoto = Paths.get("uploads").resolve(filename).toAbsolutePath();
		log.info("pathfoto: " + pathfoto);

		Resource recurso = null;
		try {
			recurso = uploadFileService.load(filename);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename= \"" + recurso.getFilename() + "\"")
				.body(recurso);
	}

	@RequestMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Cliente cliente = clienteService.findById(id);
		if (cliente == null) {
			flash.addAttribute("error", "El Tercero no existe en la Base de datos");
			return "redirect:/cliente/listar";
		}
		//
		model.put("cliente", cliente);
		model.put("titulo", "Detalle Cliente " + cliente.getNombredui());
		//
		return "cliente/ver";

	}

	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model modelo, 
			    HttpServletRequest request) {
		// System.out.println("elementos= "+ this.elementos);
		
		int elemento = Integer.parseInt(this.elementos);
		Pageable pageRequest = PageRequest.of(page, elemento);
		
		HttpSession misession= request.getSession(true);		 
		mieempresa = (Empresa) misession.getAttribute("empresaCart");
		String mensaje  =   (String) misession.getAttribute("mensaje");
		  
		//Pageable pageRequest = PageRequest.of(page, elemento);
		Page<Cliente> clientes = clienteService.findAllbyEmpresa(mieempresa.getId(), pageRequest) ;
		
		PageRender<Cliente> pageRender = new PageRender<>("/cliente/listar", clientes, elemento);
		
		modelo.addAttribute("mensaje", mensaje);
	    modelo.addAttribute("titulo", "Listado de terceros");
		modelo.addAttribute("cliente", clientes);
		modelo.addAttribute("empresa",mieempresa);
		modelo.addAttribute("page", pageRender);
		return "cliente/listar";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/form")
	public String crear(Map<String, Object> model) {

		Cliente cliente = new Cliente();
		model.put("cliente", cliente);
		model.put("titulo", "Formulario de Cliente");

		return "cliente/form";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/form/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Cliente cliente = null;

		if (id > 0) {
			cliente = clienteService.findById(id);
			if (cliente == null) {
				flash.addFlashAttribute("error", " Tercero no existe en la Base de datos");
				return "redirect:/cliente/listar";
			}

		} else {
			flash.addFlashAttribute("error", " Tercero no existe");
			return "redirect:cliente/listar";
		}
		model.put("cliente", cliente);
		model.put("titulo", "Editar Cliente");
		flash.addFlashAttribute("success", " Tercero guardado con éxito");
		return "cliente/form";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String guardar(@Valid Cliente cliente, BindingResult result, Model model,
			@RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) {

		if (result.hasErrors()) {
			System.out.println("con errores");
			model.addAttribute("titulo", "Formulario de Cliente");
			return "cliente/form";
		}

		if (!foto.isEmpty()) {

			if (String.valueOf(cliente.getId()) != null && cliente.getId() > 0 && cliente.getFoto() != null
					&& cliente.getFoto().length() > 0) {

				uploadFileService.delete(cliente.getFoto());
				log.info("delete: " + cliente.getFoto());
			}
			;

			String uniqueFilename = null;

			try {
				uniqueFilename = uploadFileService.copy(foto);
				log.info("Archivo cargado: " + uniqueFilename);
				cliente.setFoto(uniqueFilename);
				flash.addFlashAttribute("info", "Se ha subido correctamente " + uniqueFilename);

			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}

		clienteService.save(cliente);
		status.setComplete();

		String mensajeFlash = (String.valueOf(cliente.getId()) != null) ? "Tercero Editado con éxito"
				: " Tercero guardado con éxito ";
		flash.addFlashAttribute("success", mensajeFlash);

		return "redirect:/cliente/listar";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id > 0) {
			Cliente cliente = clienteService.findById(id);
			clienteService.delete(id);
			flash.addFlashAttribute("success", " Tercero eliminado con éxito");

			if (cliente.getFoto() != null) {

				if (uploadFileService.delete(cliente.getFoto())) {
					flash.addFlashAttribute("info", " Archivo eliminado con exito " + cliente.getFoto());
				}
				;

			}

		}

		return "redirect:/cliente/listar";
	}

}
