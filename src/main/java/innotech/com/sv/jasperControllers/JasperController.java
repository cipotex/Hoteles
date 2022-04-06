package innotech.com.sv.jasperControllers;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;
import net.sf.jasperreports.view.JasperViewer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import innotech.com.sv.servicios.IClientes;
import innotech.com.sv.modelos.Cliente;
import innotech.com.sv.modelos.Recibos;
import innotech.com.sv.modelosreportes.CargosAdicionalesReporte;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.http.HttpHeaders;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
@Controller
@RequestMapping("/jasper")
public class JasperController {
    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private IClientes clientesServ;
 
    @RequestMapping(value="/{reportName}")
   // @RequestMapping("/")
    public void demoReport1(@PathVariable("reportName") String reportName,
    		                @RequestParam(required = false) Map<String, Object> parameters,
                            HttpServletResponse response, HttpServletRequest request) throws  Exception{
    	
    	System.out.println("Dentro de controller jasper-");
    	
        parameters = parameters == null ? new HashMap<>() : parameters;
        
        final InputStream stream = this.getClass().getResourceAsStream("/jaspers" + File.separator + reportName + ".jrxml");
        //System.out.println(stream.toString());
     
        // Compile the Jasper report from .jrxml to .japser
        final JasperReport report = JasperCompileManager.compileReport(stream);
         
        List<Cliente> clientes = new ArrayList();
        
        clientes = clientesServ.findAll();
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(clientes);
        
        JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, ds);
         
       // Vista previa de PDF en línea
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=ReporteClientes.pdf");
        final OutputStream outputStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
      
        
        // Generar documento pdf
        /*String fileName = "F:/filename.pdf";
        JasperExportManager.exportReportToPdfFile(jasperPrint,fileName);*/
 
                 // Generar documento de Word
       /* String fileName = "F:/filename.doc";
        JRRtfExporter docExporter = new JRRtfExporter();
        docExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,fileName);
        docExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        docExporter.exportReport();*/
 
                 // Generar documento de Excel
        /*JRXlsExporter excel = new JRXlsExporter();
        System.out.println(request.getServletContext().getRealPath("jaspers/demoreport1.jasper"));
        String fileName = "F://filename.xls";
        excel.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,fileName);
        excel.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        excel.exportReport();*/
 
    }
    
    
    @RequestMapping(value="/report/{reportName}")
    public void reporte(@PathVariable("reportName") String reportName,
            @RequestParam(required = false) Map<String, Object> parameters,
            HttpServletResponse response, HttpServletRequest request) throws JRException, IOException {
    	
    	System.out.println("Entramos a generar reporte "+reportName);
    	
    	if (parameters == null) {
    		 parameters = new HashMap<String, Object>();
    	};
    	
    	
    	 parameters.put("createdBy", "javacodegeek.com");
         List<Cliente> clientes = new ArrayList();
         
         clientes = clientesServ.findAll();         
    	
        final InputStream stream = this.getClass().getResourceAsStream("/jaspers" + File.separator + reportName + ".jrxml");
        //System.out.println(stream.toString());
     
        // Compile the Jasper report from .jrxml to .japser
        final JasperReport report = JasperCompileManager.compileReport(stream);
        
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(clientes);
        
        // Filling the report with the employee data and additional parameters information.
        JasperPrint print = JasperFillManager.fillReport(report, parameters, ds);
        
       // response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline;");
        
         final String filePath = "C:\\Users\\User\\Desktop\\";
        // Export the report to a PDF file.
        JasperExportManager.exportReportToPdfFile(print, filePath + "Employee_report.pdf");
        
        //JasperViewer ver = new JasperViewer(print,false);
        
    }
    /*
    public void imprimirReporte(List<ReporteReversa> lista, Map<String, Object> parametros) 
    		throws JRException, IOException {
    		FacesContext context = FacesContext.getCurrentInstance();
    		try {
    		String reporteProc = null;
    		ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
    		reporteProc = (String) servletContext.getRealPath("/resources/reportes/RptReversas.jasper");
    		File jasper = new File(reporteProc);
    		JasperPrint jasperPrint;
    		jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros,
    		new JRBeanCollectionDataSource(lista));
    		
    		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
    		.getResponse();
    		
    		response.addHeader("Content-disposition", "attachment; filename=RptReversas.pdf");
    		ServletOutputStream stream = response.getOutputStream();
    		JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
    		stream.flush();
    		stream.close();
    		FacesContext.getCurrentInstance().responseComplete();
    		} catch (Exception ex) {
    		logger.error(ex.getMessage(), ex);
    		context.addMessage("growl",
    		new FacesMessage(FacesMessage.SEVERITY_INFO, MensajeError.ERROR_GENERICO.getMensaje(), ""));
    		}
    		}
*/
    
    //Generando el recibo de la facturacion
     public void generareciboFactura(List<Recibos> recibo, String reportName,
     		                         Map<String, Object> parameters,
     		                         HttpServletResponse response) throws  Exception{
     	
         parameters = parameters == null ? new HashMap<>() : parameters;
         
         final InputStream stream = this.getClass().getResourceAsStream("/jaspers" + File.separator + reportName + ".jrxml");
         //System.out.println(stream.toString());
      
         // Compile the Jasper report from .jrxml to .japser
         final JasperReport report = JasperCompileManager.compileReport(stream);
          
//         List<Cliente> clientes = new ArrayList();
//         List<Recibos> data = new ArrayList();
//         
//         clientes = clientesServ.findAll();
         JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(recibo);
         
         JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, ds);
          
        // Vista previa de PDF en línea
         response.setContentType("application/pdf");
         response.setHeader("Content-Disposition", "attachment; filename=ReporteClientes.pdf");
         final OutputStream outputStream = response.getOutputStream();
         JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
     }
     
   //Generando el recibo de la facturacion
     public void reporteCargosAdicionales(List<CargosAdicionalesReporte> datos, String reportName,
     		                         Map<String, Object> parameters,
     		                         HttpServletResponse response) throws  Exception{
     	
         parameters = parameters == null ? new HashMap<>() : parameters;
         
         final InputStream stream = this.getClass().getResourceAsStream("/jaspers" + File.separator + reportName + ".jrxml");
      
         // Compile the Jasper report from .jrxml to .japser
         final JasperReport report = JasperCompileManager.compileReport(stream);
          

         JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(datos);
         
         JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, ds);
          
        // Vista previa de PDF en línea
         response.setContentType("application/pdf");
         response.setHeader("Content-Disposition", "attachment; filename=ReporteCargosAdicionales.pdf");
         final OutputStream outputStream = response.getOutputStream();
         JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
     }
     
    
}