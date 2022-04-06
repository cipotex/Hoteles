package innotech.com.sv.controladores;

import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import innotech.com.sv.modelos.Empresa;



@Controller
@SessionAttributes({"mensaje"})
public class LoginController {

	private final static Logger loger = LoggerFactory.getLogger(LoginController.class);
	
	
	@GetMapping("/")
	public String inicio ( Model model,  HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) {
		
		  HttpSession misession= (HttpSession) request.getSession();	        
	     
		  Empresa empresa = (Empresa) misession.getAttribute("empresaCart");
	      String mensaje  =   (String) misession.getAttribute("mensaje");
	      
	      model.addAttribute("mensaje", mensaje);
	      
		  //System.out.println("Desde el controlador de index.. cavalos " +   model.getAttribute("mensaje"));
		  //System.out.println("Desde el controlador de index.. mensaje " +mensaje);
		  //---
		  if (empresa != null) {
		      System.out.println("Desde el controlador de index.. usuarioCart " +empresa.getNombre());
		      
		  };
		
		return "index";
	}
	@GetMapping("/login")
	public String login( @RequestParam(value= "error", required = false) String error,  
			             @RequestParam(value= "logout", required = false) String logout, 
			             Model model, Principal principal, RedirectAttributes flash) {
		
		//si es diferente a null ya habia iniciado sesion, para evitar que haga otro inicio de sesion
		if (principal != null) {
			flash.addFlashAttribute("info","Ya ha iniciado sesion anteriomente");
			 model.addAttribute("mensaje","Empresa seleccionada : Carlitos de la virgen"  );
			 loger.warn("Dentro de controller Login --> Carlitos de la virgen");
			return "redirect:/";
		}
		
		if (error != null) {
			model.addAttribute("error"," Error en el Login usuario o contraseña incorrecto. Vuelva a intentarlo");		
		}
		
		if (logout != null) {
			model.addAttribute("info"," Sesion terminada con éxito.");		
		}
		
		return "login";
	}
	
	
	
}
