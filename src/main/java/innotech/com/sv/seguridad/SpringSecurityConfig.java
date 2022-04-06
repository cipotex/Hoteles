package innotech.com.sv.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import innotech.com.sv.Authentication.LoginSuccessHandler;
import innotech.com.sv.servicios.JpaUserDetailsService;


@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled=true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{

	
	
	@Autowired
	private LoginSuccessHandler sucessHandler;
	
	@Autowired
	private JpaUserDetailsService userdetailsService;
	
	
	/*@Autowired
	private DataSource datasource;
	*/
	
	@Autowired
	private BCryptPasswordEncoder passwordencoder;
	
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {

      http.authorizeRequests().antMatchers("/","/css/**", "/js/**", "/image/**","/h2-console/**").permitAll()
     
      /*.antMatchers("/empresa/listar/**").hasAnyRole("USER")
      .antMatchers("/periodo/listar/**").hasAnyRole("USER")
      .antMatchers("/declaracion/listar/**").hasAnyRole("USER")
     
      .antMatchers("/uploads/**").hasAnyRole("ADMIN")
      .antMatchers("/empresa/**").hasAnyRole("ADMIN")
      .antMatchers("/cliente/**").hasAnyRole("ADMIN")
      .antMatchers("/declaracion/**").hasAnyRole("ADMIN")
      .antMatchers("/compra/**").hasAnyRole("ADMIN")
      */      
      .anyRequest().authenticated()
      .and()
	      .formLogin()
	         .successHandler(sucessHandler)
	         .loginPage("/login")
	      .permitAll()
      .and()
      .csrf().disable() //ca para que funcione h2 con seguridad
      .headers().frameOptions().disable() //ca para que funcione h2 con seguridad
      .and()
      .logout().permitAll()
      .and()
      .exceptionHandling().accessDeniedPage("/error_403")      
      ;
      
    
     
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(userdetailsService)		
		.passwordEncoder(passwordencoder);
		
		/*PasswordEncoder encoder = passwordEncoder();
		
		UserBuilder users = User.builder().passwordEncoder( password -> {
			return encoder.encode(password);
		});
		
		auth.inMemoryAuthentication()
		.withUser(users.username("admin").password("12345").roles("ADMIN","USER"))
		.withUser(users.username("cavalos").password("12345").roles("USER"))
		;
		*/
	}

}
