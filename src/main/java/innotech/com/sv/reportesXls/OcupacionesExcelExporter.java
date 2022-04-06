
package innotech.com.sv.reportesXls;
import java.io.IOException;
import java.util.List;
 
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import innotech.com.sv.modelos.Ocupacion;

 
public class OcupacionesExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Ocupacion> listUsers;
     
    public OcupacionesExcelExporter(List<Ocupacion> listUsers) {
        this.listUsers = listUsers;
        workbook = new XSSFWorkbook();
    }
 
 
    private void writeHeaderLine() {
        sheet = workbook.createSheet("Ocupaciones");
         
        Row row = sheet.createRow(0);
         
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
         
        createCell(row, 0, "Reserva ID", style); 
        createCell(row, 1, "Empresa", style);    
        createCell(row, 2, "Estado Ocupacion", style);
        createCell(row, 3, "Tipo Habitacion", style);  
        createCell(row, 4, "Habitacion", style);        
        createCell(row, 5, "Cliente", style);
        createCell(row, 6, "Periodo Reserva", style);
        createCell(row, 7, "Fecha Inicio", style);
        createCell(row, 8, "Fecha Fin", style);
        createCell(row, 9, "Precio Reserva", style);
        createCell(row, 10, "Descuento", style);
        createCell(row, 11, "Dias Ocupacion", style);
        createCell(row, 12, "Estado Reserva", style);
        createCell(row, 13, "Precio con Descuento", style);
        createCell(row, 14, "Recurrente", style);
        createCell(row, 15, "Monto Deposito", style);        
    }
     
    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else if (value instanceof Long) {
            cell.setCellValue((long) value);
        } else if (value instanceof Double) {
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
                 
       for (Ocupacion user : listUsers) {
             Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
             
            createCell(row, columnCount++, String.valueOf(user.getId()), style);
            createCell(row, columnCount++, user.getEmpresa().getNombre(), style);
            createCell(row, columnCount++, user.getEstado().toString(), style);
            createCell(row, columnCount++, user.getReserva().getTipohabitacion().getDescripcion(), style);
            createCell(row, columnCount++, user.getReserva().getHabitacion().getCodigo(), style);
            createCell(row, columnCount++, user.getReserva().getCliente().getNombredui(), style);
            createCell(row, columnCount++, user.getReserva().getPeriodoReserva().toString(), style);
            createCell(row, columnCount++, user.getReserva().getFechaInicio().toString(), style);
            createCell(row, columnCount++, user.getReserva().getFechaFin().toString(), style);
            createCell(row, columnCount++, user.getReserva().getPrecioreserva(), style);
            createCell(row, columnCount++, user.getReserva().getDescuento(), style);
            createCell(row, columnCount++, user.getReserva().getDiasOcupacion(), style);
            createCell(row, columnCount++, user.getReserva().getEstadoReserva().name() , style);
            createCell(row, columnCount++, user.getReserva().getMontoReservaConDescuento() , style);            
            createCell(row, columnCount++, user.getReserva().getRecurrente() , style);
            createCell(row, columnCount++, user.getReserva().getMontoDeposito(), style);
            
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