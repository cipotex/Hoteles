package innotech.com.sv.controladores;

import innotech.com.sv.modelos.CargosAdicionales;
import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.reportesXls.CargosAdicionalesExcelExporter;
import innotech.com.sv.servicios.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Controller
public class CargosAdicionalesExcelController {

    @Value("${innotec.com.elementosPorPagina}")
    String elementos ;


    @Autowired
    Empresa mieempresa ;

    @Autowired
    ICargosAdicionales cargosAdicionalesimp;

    @Autowired
    IOcupacion ocupacionServImp;

    @Autowired
    IPromocion promocionServ;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/cargosadicionales/export/excel")
    public void exportToExcel(HttpServletResponse response, HttpServletRequest request ) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=cargosAdicionales_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        HttpSession misession= request.getSession(true);
        mieempresa = (Empresa) misession.getAttribute("empresaCart");

        List<CargosAdicionales> listReserva = cargosAdicionalesimp.findByEmpresa(mieempresa);

        listReserva.stream().forEach( x-> System.out.println(x.getId() + " " +x.getEstado().toString()));

        CargosAdicionalesExcelExporter excelExporter = new CargosAdicionalesExcelExporter(listReserva);

        excelExporter.export(response);
    }
}
