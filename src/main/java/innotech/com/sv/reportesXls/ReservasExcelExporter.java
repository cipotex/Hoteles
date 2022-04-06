
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

import innotech.com.sv.modelos.Reserva;

 
public class ReservasExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Reserva> listUsers;
     
    public ReservasExcelExporter(List<Reserva> listUsers) {
        this.listUsers = listUsers;
        workbook = new XSSFWorkbook();
    }
 
 
    private void writeHeaderLine() {
        sheet = workbook.createSheet("Reservas");
         
        Row row = sheet.createRow(0);
         
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
         
        createCell(row, 0, "Reserva ID", style); 
        createCell(row, 1, "Empresa", style);    
        createCell(row, 2, "Tipo Habitacion", style);  
        createCell(row, 3, "Habitacion", style);        
        createCell(row, 4, "Cliente", style);
        createCell(row, 5, "Periodo Reserva", style);
        createCell(row, 6, "Fecha Inicio", style);
        createCell(row, 7, "Fecha Fin", style);
        createCell(row, 8, "Precio Reserva", style);
        createCell(row, 9, "Descuento", style);
        createCell(row, 10, "Dias Ocupacion", style);
        createCell(row, 11, "Estado Reserva", style);
        createCell(row, 12, "Precio con Descuento", style);
        createCell(row, 13, "Recurrente", style);
        createCell(row, 14, "Monto Deposito", style);        
    }
     
    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        }  else if (value instanceof Long) {
            cell.setCellValue((long) value);
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
                 
       for (Reserva user : listUsers) {
             Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
             
            createCell(row, columnCount++, String.valueOf(user.getId()), style);
            createCell(row, columnCount++, user.getEmpresa().getNombre(), style);
            createCell(row, columnCount++, user.getTipohabitacion().getDescripcion(), style);
            createCell(row, columnCount++, user.getHabitacion().getCodigo(), style);
            createCell(row, columnCount++, user.getCliente().getNombredui(), style);
            
            createCell(row, columnCount++, user.getPeriodoReserva().toString(), style);
            createCell(row, columnCount++, user.getFechaInicio().toString(), style);
            createCell(row, columnCount++, user.getFechaFin().toString(), style);
            createCell(row, columnCount++, user.getPrecioreserva(), style);
            createCell(row, columnCount++, user.getDescuento(), style);
            createCell(row, columnCount++, user.getDiasOcupacion(), style);
            createCell(row, columnCount++, user.getEstadoReserva().name() , style);
            createCell(row, columnCount++, user.getMontoReservaConDescuento() , style);            
            createCell(row, columnCount++, user.getRecurrente() , style);
            createCell(row, columnCount++, user.getMontoDeposito(), style);
            
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