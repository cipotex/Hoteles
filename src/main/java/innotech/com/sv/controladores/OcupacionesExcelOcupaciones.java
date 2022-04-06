
package innotech.com.sv.controladores;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.Ocupacion;
import innotech.com.sv.reportesXls.OcupacionesExcelExporter;
import innotech.com.sv.servicios.IOcupacion;

 
@Controller
public class OcupacionesExcelOcupaciones {
 
   
	@Autowired
	Empresa mieempresa ;
    
    @Autowired
    IOcupacion reservaImpServ;
     
    @GetMapping("/ocupaciones/export/excel")
    public void exportToExcel(HttpServletResponse response,  HttpServletRequest request ) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=ocupaciones_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
         
        HttpSession misession= request.getSession(true);		 
		mieempresa = (Empresa) misession.getAttribute("empresaCart");
		
        List<Ocupacion> listReserva = reservaImpServ.findByEmpresa(mieempresa);
         
        OcupacionesExcelExporter excelExporter = new OcupacionesExcelExporter(listReserva);
         
        excelExporter.export(response);    
    }  
 
    @GetMapping("/ocupacion/solicitud/excel/{id}")
    public void SolicitudReserva(@PathVariable(value="id") Long id,  Map<String, Object> model, HttpServletResponse response,  HttpServletRequest request, RedirectAttributes flash ) throws IOException {
      
    	
    	if(id > 0) {
			Ocupacion reserva = reservaImpServ.findById(id);
			//
			response.setContentType("application/octet-stream");
	        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	        String currentDateTime = dateFormatter.format(new Date());
	         
	        String headerKey = "Content-Disposition";
	        String headerValue = "attachment; filename=ContratoReserva_" + currentDateTime + ".xlsx";
	        response.setHeader(headerKey, headerValue);
	         
	        /*HttpSession misession= request.getSession(true);		 
			mieempresa = (Empresa) misession.getAttribute("empresaCart");
			
	        List<Reserva> listReserva = reservaImpServ.findByEmpresa(mieempresa);
	         */
	        OcupacionesExcelExporter excelExporter = new OcupacionesExcelExporter((List<Ocupacion>) reserva);
	         
	        excelExporter.export(response);    
			
		}
    	
    	
    	

    }  
    
}
