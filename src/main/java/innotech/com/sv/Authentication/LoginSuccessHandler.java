package innotech.com.sv.Authentication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.Usuarios;
import innotech.com.sv.modelos.UsuariosBycia;
import innotech.com.sv.modelosDao.UsuarioDao;
import innotech.com.sv.modelosDao.UsuariosByciaDao;


@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Autowired
	UsuarioDao usuarioDao;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		      
		SessionFlashMapManager flashmanager = new SessionFlashMapManager();
		FlashMap flashmap = new FlashMap();
		
		logger.info(" hola '"+ authentication.getName() +"' iniciado sesión con éxito");
		flashmap.put ("success", "Hola '" + authentication.getName() +"' has iniciado sesión con éxito") ;
		
		flashmanager.saveOutputFlashMap(flashmap, request, response);
	     
		
		super.onAuthenticationSuccess(request, response, authentication);
	}

	

	
	
}
