
package innotech.com.sv.reportesXls;

import java.io.IOException;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import innotech.com.sv.modelos.Reserva;

public class ContratoReservaExcelExporter {
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private Reserva listUsers;

	public ContratoReservaExcelExporter(Reserva listUsers) {
		this.listUsers = listUsers;
		workbook = new XSSFWorkbook();
	}

	private void writeHeaderLine() {
		sheet = workbook.createSheet("Contrato");
		sheet.setColumnWidth(0, 50);
		sheet.setColumnWidth(1, 30);
		sheet.addMergedRegion(new CellRangeAddress(0,1,0,1));
	
		Row row = sheet.createRow(0);
		CellStyle style = workbook.createCellStyle();
		CellStyle styleHeader = workbook.createCellStyle();
		
		XSSFFont header = workbook.createFont();
		XSSFFont body = workbook.createFont();
		//
		//header.setBold(true);
		header.setFontHeight(20);
		header.setFontName("Arial");
		styleHeader.setFont(header);
		//
		createCell(row, 0, "SOLICITUD DE RESERVA DE HABITACION", styleHeader);
		//
		//body.setBold(false);
		body.setFontHeight(12);
		body.setFontName("Arial");
		style.setFont(body);
				
		style.setBorderBottom(BorderStyle.MEDIUM);
		style.setBorderTop(BorderStyle.MEDIUM);
		style.setBorderLeft(BorderStyle.MEDIUM);
		style.setBorderRight(BorderStyle.MEDIUM);
		
		//style.setBorderBottom(BoderStyle);
		//
		row = sheet.createRow(1);		
		createCell(row, 0, "", style);
		row = sheet.createRow(2);
		createCell(row, 0, "", style);
		//
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(14);
		//
		style.setFont(font);
		row = sheet.createRow(3);
		createCell(row, 0, "Reserva ID", style);
		createCell(row, 1, String.valueOf(this.listUsers.getId()), style);
		//
		row = sheet.createRow(4);
		createCell(row, 0, "Empresa", style);
		createCell(row, 1, this.listUsers.getEmpresa().getNombre(), style);
		//
		row = sheet.createRow(5);
		createCell(row, 0, "Tipo Habitacion", style);
		createCell(row, 1, this.listUsers.getTipohabitacion().getDescripcion(), style);
		//
		row = sheet.createRow(6);
		createCell(row, 0, "Habitacion", style);
		createCell(row, 1, this.listUsers.getHabitacion().getCodigo(), style);
		//
		row = sheet.createRow(7);
		createCell(row, 0, "Cliente", style);
		createCell(row, 1, this.listUsers.getCliente().getNombredui(), style);
		//
		row = sheet.createRow(8);
		createCell(row, 0, "Periodo Reserva", style);
		createCell(row, 1, this.listUsers.getPeriodoreserva().toString(), style);
		//
		row = sheet.createRow(9);
		createCell(row, 0, "Fecha Inicio", style);
		createCell(row, 1, this.listUsers.getFechaInicio().toString(), style);
		//
		row = sheet.createRow(10);
		createCell(row, 0, "Fecha Fin", style);
		createCell(row, 1, this.listUsers.getFechaInicio().toString(), style);
		//
		row = sheet.createRow(11);
		createCell(row, 0, "Precio Reserva", style);
		createCell(row, 1, this.listUsers.getPrecioreserva() + " X "+this.listUsers.getPeriodoreserva().toString(), style);
		//
		row = sheet.createRow(12);
		createCell(row, 0, "Descuento", style);
		createCell(row, 1, this.listUsers.getDescuento(), style);
		//
		row = sheet.createRow(13);
		createCell(row, 0, "Dias Ocupacion", style);
		createCell(row, 1, this.listUsers.getDiasOcupacion(), style);
		//
		row = sheet.createRow(14);
		createCell(row, 0, "Estado Reserva", style);
		createCell(row, 1, this.listUsers.getEstadoReserva().toString(), style);
		//
		row = sheet.createRow(15);
		createCell(row, 0, "Total Cancelar", style);
		createCell(row, 1, this.listUsers.getMontoReservaConDescuento(), style);
		//
		row = sheet.createRow(16);
		createCell(row, 0, "Recurrente", style);
		createCell(row, 1, this.listUsers.getRecurrente(), style);
		//
		row = sheet.createRow(17);
		createCell(row, 0, "Monto Deposito", style);
		createCell(row, 1, this.listUsers.getMontoDeposito(), style);
		
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
		} else {
			cell.setCellValue((String) value);
		}
		cell.setCellStyle(style);
	}

	

	public void export(HttpServletResponse response) throws IOException {

		// System.out.println("Dentro de ListadoActivosExcelController");

		writeHeaderLine();
		//writeDataLines();

		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();

		outputStream.close();

	}

}