package innotech.com.sv.controladores;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import innotech.com.sv.modelos.Activo;
import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.reportesXls.ActivosExcelExporter;
import innotech.com.sv.servicios.ActivoImp;

 
@Controller
public class ActivosExcelController {
  
   
    @Autowired
    private ActivoImp activosServ;
     
    @Autowired
	Empresa mieempresa ;
     
    @GetMapping("/activos/export/excel")
    public void exportToExcel(HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=activos_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        //
        HttpSession misession= request.getSession(true);		 
		mieempresa = (Empresa) misession.getAttribute("empresaCart");
       
        List<Activo> listEmpresa = activosServ.findByEmpresa(mieempresa);
         
        ActivosExcelExporter excelExporter = new ActivosExcelExporter(listEmpresa);
         
        excelExporter.export(response);    
    }  
 
}
