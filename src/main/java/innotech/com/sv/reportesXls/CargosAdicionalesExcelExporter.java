package innotech.com.sv.reportesXls;

import innotech.com.sv.modelos.CargosAdicionales;
import innotech.com.sv.modelos.Ocupacion;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class CargosAdicionalesExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<CargosAdicionales> listUsers;

    public CargosAdicionalesExcelExporter(List<CargosAdicionales> listUsers) {
        this.listUsers = listUsers;
        workbook = new XSSFWorkbook();
    }
 
 
    private void writeHeaderLine() {
        sheet = workbook.createSheet("CargosAdicionales");
         
        Row row = sheet.createRow(0);
         
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
         
        createCell(row, 0, "Reserva ID", style); 
        createCell(row, 1, "Empresa", style);    
        createCell(row, 2, "Fecha Servicio", style);
        createCell(row, 3, "Estado Cargo Adicional", style);
        createCell(row, 4, "Reserva ID", style);
        createCell(row, 5, "Habitacion", style);
        createCell(row, 6, "Cliente", style);
        createCell(row, 7, "Periodo", style);
        createCell(row, 8, "Fecha Inicio", style);
        createCell(row, 9, "Fecha Fin", style);
        createCell(row, 10, "Nombre Servicio", style);
        createCell(row, 11, "Cantidad", style);
        createCell(row, 12, "Precio Unitario", style);
        createCell(row, 13, "Descuento", style);
        createCell(row, 14, "Total", style);

    }
     
    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Long) {
            cell.setCellValue((long) value);
        }else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }
     
    private void writeDataLines() {
        int rowCount = 1;
 
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
                 
       for (CargosAdicionales user : listUsers) {
             Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
             //
           ///
            createCell(row, columnCount++, String.valueOf(user.getId()), style);
            createCell(row, columnCount++, user.getEmpresa().getNombre(), style);
            createCell(row, columnCount++,  (user.getFecha_ins() != null? user.getFecha_ins().toString() : "null") , style);
            createCell(row, columnCount++,  user.getEstado().toString() , style);
            createCell(row, columnCount++, String.valueOf(user.getOcupacion().getReserva().getId()), style);
            createCell(row, columnCount++, user.getOcupacion().getReserva().getHabitacion().getCodigo(), style);
            createCell(row, columnCount++, user.getOcupacion().getReserva().getCliente().getNombredui(), style);
            createCell(row, columnCount++, user.getOcupacion().getReserva().getPeriodoreserva().name(), style);
            createCell(row, columnCount++, user.getOcupacion().getReserva().getFechaInicio().toString(), style);
            createCell(row, columnCount++, user.getOcupacion().getReserva().getFechaFin().toString(), style);
            createCell(row, columnCount++, user.getServicio().getNombre(), style);
            createCell(row, columnCount++, user.getCantidad(), style);
            createCell(row, columnCount++, user.getPrecioUnitario(), style);
            createCell(row, columnCount++, (user.getPromocion() == null ? "null": Integer.toString(user.getPromocion().getPorcdescuento())), style);
            createCell(row, columnCount++, String.valueOf(user.getTotal()) , style);

        }
    }
     
    public void export(HttpServletResponse response) throws IOException {
        
    	//System.out.println("Dentro de ListadoActivosExcelController");
    	
    	writeHeaderLine();
        writeDataLines();
       
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
         
        outputStream.close();
     
    }
}