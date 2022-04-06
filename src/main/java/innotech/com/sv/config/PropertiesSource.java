package innotech.com.sv.config;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import innotech.com.sv.Authentication.LoginSuccessHandler;
import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.Usuarios;
import innotech.com.sv.modelos.UsuariosBycia;
import innotech.com.sv.modelosDao.UsuarioDao;
import innotech.com.sv.modelosDao.UsuariosByciaDao;

@Configuration	
@PropertySource("classpath:configuraciones.properties")
public class PropertiesSource {

	@Autowired
	private UsuariosByciaDao usuarioBycia;
	
	@Autowired
	private UsuarioDao  usuarioDao;
	
	private final static Logger logger = LoggerFactory.getLogger(LoginSuccessHandler.class);
	
	@Bean
	@Qualifier("usuarioG")
	public Usuarios Gusuario() {
		return new Usuarios();
	}
	
	@Bean
	@Qualifier("empresaG")
	public Empresa Gemoresa() {
		return new Empresa();
	}
	
	
	// @Bean
	// @Qualifier("empresaG")
	 public Empresa empresaG(Authentication authentication) {
		Empresa empresa = new Empresa();
		Usuarios usuario = usuarioDao.findByUsername( authentication.getName() );
		
		//Empresa empresa = new Empresa();
		//--
        List<UsuariosBycia> empresaList = new ArrayList<UsuariosBycia>();	
        
		empresaList = usuarioBycia.findByUsuario(usuario);		
		for (UsuariosBycia emp : empresaList ) {
			empresa = emp.getEmpresa();
			break;
		}
		
		logger.info("desde SelectEmpresa Empresa = " + empresa.getNombre());
		
	    return empresa;
	 }
	
	
}
