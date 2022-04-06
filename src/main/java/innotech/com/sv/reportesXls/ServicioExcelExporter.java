
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

import innotech.com.sv.modelos.Servicio;

 
public class ServicioExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Servicio> listUsers;
     
    public ServicioExcelExporter(List<Servicio> listUsers) {
        this.listUsers = listUsers;
        workbook = new XSSFWorkbook();
    }
 
 
    private void writeHeaderLine() {
        sheet = workbook.createSheet("Servicios");
         
        Row row = sheet.createRow(0);
         
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
         
        createCell(row, 0, "Nombre Empresa", style);      
        createCell(row, 1, "Nombre Servicio ", style);       
        createCell(row, 2, "Descripcion", style);    
        createCell(row, 3, "Precio Unitario", style);
       // createCell(row, 4, "Promocion", style);
        createCell(row, 4, "Estado", style);
         
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
                 
       for (Servicio user : listUsers) {
             Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
             
            createCell(row, columnCount++, String.valueOf(user.getId()), style);
            createCell(row, columnCount++, user.getNombre(), style);
            createCell(row, columnCount++, user.getDescripcion(), style);
            createCell(row, columnCount++, user.getPrecioUnitario(), style);
            //createCell(row, columnCount++, user.getPromocion(), style);
            createCell(row, columnCount++, user.getEstado().toString(), style);
        }
    }
     
    public void export(HttpServletResponse response) throws IOException {
        
    	//System.out.println("Dentro de empresaExcelController");
    	
    	writeHeaderLine();
        writeDataLines();
         
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
         
        outputStream.close();
         
    }
}