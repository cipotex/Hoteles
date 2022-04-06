package innotech.com.sv.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping
public class ControllerTest {
		
	   @Autowired
	   private CarService carService;

		@Autowired
		public ControllerTest(CarService carService) {
			this.carService = carService;
		}

		@RequestMapping("/test")
		public String index(Model model) {
			List<String> brands = carService.findAllBrands();
			List <Car> car = new ArrayList<Car>();
			//"Audi", "BMW", "Citroën", "Ford", "Mercedes", "Reanult", "Seat"
			car.add(new Car("Audi","Audi Ls"));
			car.add(new Car("Audi","Audi Xl"));
			car.add(new Car("Audi","Audi Fe"));
			//---			
			car.add(new Car("BMW","BMW Fe"));
			//---
			car.add(new Car("Citroën","Citroën Ls"));
			car.add(new Car("Citroën","Citroën Xl"));
			car.add(new Car("Citroën","Citroën Fe"));
			//--
			car.add(new Car("Citroën","Mercedes Fe"));
			//--
			car.add(new Car("Reanult","Reanult Ls"));
			car.add(new Car("Reanult","Reanult Fe"));
			//--
			car.add(new Car("Seat","Seat Ls"));
			//--
			
			model.addAttribute("car", car);
			model.addAttribute("brands", brands);
			
			return "reserva/pruebas";
		}

		@RequestMapping("/ajax/brands")
		public String ajaxBrands(@RequestParam("brand") String brand, Model model) {
			List<String> models = carService.findAllModelsByBrand(brand);
			model.addAttribute("models", models);
			return "reserva/pruebas :: models";
		}
}
