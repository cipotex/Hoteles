package innotech.com.sv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class AdministraHotelApplication implements CommandLineRunner{

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(AdministraHotelApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
        String clave = "12345";
		
		for (int i= 1; i<=2; i++) {
			String passEncoder = passwordEncoder.encode(clave);
			System.out.println(passEncoder);
		}
		
	}

}
