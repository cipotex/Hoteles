package innotech.com.sv.controladores;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import innotech.com.sv.Authentication.LoginSuccessHandler;
import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.Usuarios;
import innotech.com.sv.modelos.UsuariosBycia;
import innotech.com.sv.modelosDao.UsuarioDao;
import innotech.com.sv.servicios.EmpresaServiceImp;
import innotech.com.sv.servicios.UsuariosByciaImp;

@Controller
@SessionAttributes({"gempresa"})
public class SelectEmpresaController {

	private final static Logger logger = LoggerFactory.getLogger(LoginSuccessHandler.class);
	
	@Autowired
	private UsuarioDao  usuarioDao;
	
	@Autowired
	@Qualifier("usuarioG")
	private Usuarios GUsuario;
	
	@Autowired
	private Empresa empresa;
	
	@Autowired
	private EmpresaServiceImp empresaServiceImp;
	
	@Autowired
	private UsuariosByciaImp usuariosByCiaImp;
	
	@Autowired
	private List<Empresa> empresas;
	
	@RequestMapping(value="/cambio_empresa")
	public String cambioEmpresa (Model model,  Authentication authentication, 
			                     HttpServletRequest request, HttpServletResponse response ){	
		
        Usuarios    usuario = usuarioDao.findByUsername( authentication.getName());
        HttpSession misession= (HttpSession) request.getSession();
        
        Empresa empresa = (Empresa) misession.getAttribute("empresaCart");
        
        System.out.println(" Empresa desde SelectEmpresaController empresa=" + empresa.getNombre());
        
		List<UsuariosBycia> usuariosByCia = usuariosByCiaImp.findByUsuario(usuario)	 ;
		
		empresas.clear();
		
	    for (UsuariosBycia item : usuariosByCia ) {
	    	System.out.println( " salida " + item.getEmpresa().getNombre());
	    	empresas.add(item.getEmpresa());
	    	
	    }
	    model.addAttribute("titulo","Selección de Empresa");
	    model.addAttribute("gempresa", empresa);
	    model.addAttribute("datos", empresas);
		
		return "cambioEmpresa";
		
	};
	
	
	@RequestMapping(value="/form", method=RequestMethod.POST)
	public String salvar (@Valid @ModelAttribute(value="gempresa") Empresa gempresa,
			              BindingResult      result,  Model         model, 
			              RedirectAttributes flash,   SessionStatus status,
			              HttpServletRequest request, HttpServletResponse response,
			              Authentication authentication) {	
		
		logger.info("Dentro del post de Seleccionar empresa");
		
		if (result.hasErrors()) {
			model.addAttribute("titulo","Selección de Empresa");	
		
			
			return "cambio_empresa";
		} else {
			
			HttpSession misession= (HttpSession) request.getSession();
			
			
			
			this.empresa = empresaServiceImp.findById(gempresa.getId());
			
			System.out.println(" Empresa desde Select Controller ="+ this.empresa.getId() +" " +this.empresa.getNombre());
			
			//Removiendo codigo anterio
			//request.removeAttribute("empresaCart");
			
			//colocando nuevo codigo de empresa seleccioada
			//empresa = empresaDao.findById(gempresa.getEmpresa().getId());
			
			misession.removeAttribute("empresaCart");
			misession.setAttribute("empresaCart",this.empresa);
			
			misession.removeAttribute("mensaje");
			misession.setAttribute("mensaje","Empresa " + this.empresa.getId() + " - " +this.empresa.getNombre() );
			
			
			String mensajeFlash =   " Empresa Seleccionada con éxito "+ gempresa.getNombre()  ;
			
		    // status.setComplete();
		    flash.addFlashAttribute("success", mensajeFlash );
		
		return "redirect:/empresa/listar";
		}
	}; 
	
	
}
