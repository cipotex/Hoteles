package innotech.com.sv.servicios;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.Role;
import innotech.com.sv.modelos.Usuarios;
import innotech.com.sv.modelos.UsuariosBycia;
import innotech.com.sv.modelosDao.UsuarioDao;

@Service("jpaDetailsService")
public class JpaUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioDao usuarioDao;

	private Logger logger = LoggerFactory.getLogger(JpaUserDetailsService.class);
	@Value("${innotec.com.IdEmpresaDefault}")
	long empresaId;

	@Autowired
	HttpServletRequest request;

	@Autowired
	private UsuariosByciaImp usuariosByCiaImp;

	@Autowired
	private Empresa empresa;
	 
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		HttpSession misession= request.getSession(true);
		
		if (username == null) {
			logger.error(" No existe el usuario: ".concat(username));
			throw new UsernameNotFoundException("Usuario No Existe " + username);
		}

		Usuarios usuario = usuarioDao.findByUsername(username);
		System.out.println("Desde clase JpaUserDetailService usuario= " + usuario.getUsername());

		List<UsuariosBycia> usuariosByCia = usuariosByCiaImp.findByUsuario(usuario);
       
        
		for (UsuariosBycia item : usuariosByCia) {
			System.out.println(" salida " + item.getEmpresa().getNombre());
			empresa = item.getEmpresa();
			break;
		}
		
		 misession.setAttribute("empresaCart",empresa);
		 misession.setAttribute("usuarioCart",usuario);
		 misession.setAttribute("mensaje","Empresa " + empresa.getId() + " - " +empresa.getNombre() );
		 
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		for (Role role : usuario.getRoles()) {
			logger.info("Role: ".concat(role.getAuthority()));
			authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
		}

		if (authorities.isEmpty()) {
			logger.error("Error en el Login, usuario: ".concat(username).concat(" no tiene roles asociados"));
			throw new UsernameNotFoundException(
					"Error en Login. Usuario:  " + username + "  No tiene roles Asociados. ");
		}

		return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true,
				authorities);
	}

}
