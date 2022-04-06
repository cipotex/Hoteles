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

import innotech.com.sv.modelos.Activo;

 
public class ActivosExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Activo> listUsers;
     
    public ActivosExcelExporter(List<Activo> listUsers) {
        this.listUsers = listUsers;
        workbook = new XSSFWorkbook();
    }
 
 
    private void writeHeaderLine() {
        sheet = workbook.createSheet("Activos");
         
        Row row = sheet.createRow(0);
         
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
         
        createCell(row, 0, "Activo ID", style); 
        createCell(row, 1, "Empresa", style);    
        createCell(row, 2, "Codigo", style);  
        createCell(row, 3, "Descripcion", style);        
        createCell(row, 4, "Comentarios", style);
        createCell(row, 5, "Costo Adquisicion", style);
        createCell(row, 6, "Habitacion", style);
       
         
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
                 
       for (Activo user : listUsers) {
             Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
             
            createCell(row, columnCount++, String.valueOf(user.getId()), style);
            createCell(row, columnCount++, user.getEmpresa().getNombre(), style);
            createCell(row, columnCount++, user.getCodigo(), style);
            createCell(row, columnCount++, user.getDescripcion(), style);
           
            createCell(row, columnCount++, user.getComentarios(), style);
            createCell(row, columnCount++, user.getCostoadquisisicion(), style);
            if (user.getHabitacion() != null) {
            	createCell(row, columnCount++, user.getHabitacion().getCodigo(), style);
            }
            
        }
    }
     
    public void export(HttpServletResponse response) throws IOException {
        
    	System.out.println("Dentro de ListadoActivosExcelController");
    	
    	writeHeaderLine();
        writeDataLines();
       
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
         
        outputStream.close();
     
    }
}