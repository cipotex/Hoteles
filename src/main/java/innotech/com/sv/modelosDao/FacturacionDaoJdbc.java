package innotech.com.sv.modelosDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import innotech.com.sv.modelos.ClasificacionGastoEnum;
import innotech.com.sv.modelos.Empresa;
import innotech.com.sv.modelos.EstadoGastos;
import innotech.com.sv.modelos.FacturacionEnc;
import innotech.com.sv.modelos.TipoFacturaGastoEnum;
import innotech.com.sv.modelosreportes.GastosPeriodo;

@Service
public class FacturacionDaoJdbc {

	@Autowired
	private JdbcTemplate template;
	
	private static final String FACTURACION_PERIODO = " select sum(total_facturacion)total_facturacion,\r\n"
			+ "  sum(monto_cargos_adicionales)monto_cargos_adicionales,\r\n"
			+ "  sum(monto_impuestos)monto_impuestos,\r\n"
			+ "  sum(monto_ocupacion)monto_ocupacion,\r\n"
			+ "  sum(monto_pre_reserva)monto_pre_reserva\r\n"
			+ "from hotel.facturacionenc\r\n"
			+ " where empresa_id = ? "
			+ "  and estado_factura =1 "			
			+ "  and fecha_ins between ? and ? ";
			

	public FacturacionEnc facturacionperiodo(long empresa, Date fechaini, Date fechafin) {
		FacturacionEnc facturacion = 
				 this.template.queryForObject (this.FACTURACION_PERIODO, new Object[]{empresa, fechaini, fechafin}, new RowMapper<FacturacionEnc>() {
					
					@Override
					public FacturacionEnc mapRow(ResultSet rs, int rowNum) throws SQLException {
						// TODO Auto-generated method stub
						FacturacionEnc s = new FacturacionEnc();
				
						s.setTotalFacturacion(rs.getFloat("total_facturacion"));
						s.setMontoCargosAdicionales(rs.getFloat("monto_cargos_adicionales"));
						s.setMontoImpuestos(rs.getFloat("monto_impuestos"));
						s.setMontoOcupacion(rs.getFloat("monto_ocupacion"));
						s.setMontoPreReserva(rs.getFloat("monto_pre_reserva"));
						
						return s;
					}

				
    });
      return facturacion;
	}
	
}
