package innotech.com.sv.controladores;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
 
import javax.servlet.http.HttpServletResponse;
  
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import innotech.com.sv.modelos.Promocion;
import innotech.com.sv.reportesXls.PromocionExcelExporter;
import innotech.com.sv.servicios.PromocionImp;

 
@Controller
public class PromocionesExcelController {
 
   
    @Autowired
    private PromocionImp promocionServ;
     
     
    @GetMapping("/promocion/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=promociones_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
         
       
        List<Promocion> listEmpresa = promocionServ.findAll();
         
        PromocionExcelExporter excelExporter = new PromocionExcelExporter(listEmpresa);
         
        excelExporter.export(response);    
    }  
 
}
