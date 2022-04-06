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
public class GastosDaoJdbc {

	@Autowired
	private JdbcTemplate template;
	
	private static final String GASTOS_PERIODO = "select empresa_id, tipogasto, tipofactura, estadogastos, "
			+ " count(1) eventos, sum(monto) monto "
			+ " from hotel.gastos "			
			+ " where empresa_id = ? "
			+ " and fechafactura between ? and ? "
			+ " group by empresa_id, tipogasto, tipofactura, estadogastos ";
	
	private static final String GASTOS_APROBPERIODO = "select  sum(COALESCE(monto,0)) monto "
			+ " from hotel.gastos "			
			+ " where empresa_id = ? "
			+ " and estadogastos = 1 "
			+ " and fechafactura between ? and ? " ;		

	public List<GastosPeriodo> gastosperiodo(long empresa, Date fechaini, Date fechafin) {
		List<GastosPeriodo> gastos = 
				 this.template.query (this.GASTOS_PERIODO, new Object[]{empresa, fechaini, fechafin}, new RowMapper<GastosPeriodo>() {
					
					@Override
					public GastosPeriodo mapRow(ResultSet rs, int rowNum) throws SQLException {
						// TODO Auto-generated method stub
						GastosPeriodo s = new GastosPeriodo();
						
						s.setEmpresa(rs.getInt("empresa_id"));
						s.setTipogasto(rs.getInt("tipogasto"));
						s.setTipofactura(rs.getInt("tipofactura"));
						s.setEstadogastos(rs.getInt("estadogastos"));
						s.setEventos(rs.getInt("eventos"));
						s.setMonto(rs.getFloat("monto"));
						//						
						s.setEstadogastos2( EstadoGastos.byOrdinal(rs.getInt("estadogastos")) );
						s.setTipofactura2(TipoFacturaGastoEnum.byOrdinal(rs.getInt("tipofactura")));
						s.setTipogasto2(ClasificacionGastoEnum.byOrdinal(rs.getInt("tipogasto")));
						//s.setEstadogastos2(  ) ;
						
						return s;
					}

				
    });
      return gastos;
	}
	
	
	public String gastoAprobperiodo(long empresa, Date fechaini, Date fechafin) {
				
		return this.template.queryForObject(
				GASTOS_APROBPERIODO, new Object[]{empresa, fechaini, fechafin}, String.class);

	}
	
	
}
