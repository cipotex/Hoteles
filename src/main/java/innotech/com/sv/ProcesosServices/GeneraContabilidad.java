package innotech.com.sv.ProcesosServices;

import java.util.List;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import innotech.com.sv.modelos.CargosAdicionales;
import innotech.com.sv.modelos.ClaseServicio;
import innotech.com.sv.modelos.DetalleContable;
import innotech.com.sv.modelos.EstadoCargoAdicionalEnum;
import innotech.com.sv.modelos.FacturacionDet;
import innotech.com.sv.modelos.Ocupacion;
import innotech.com.sv.modelos.Recibos;
import innotech.com.sv.modelos.Reserva;
import innotech.com.sv.modelos.TiposHabitacion;
import innotech.com.sv.servicios.ICargosAdicionales;
import innotech.com.sv.servicios.IClaseServicio;
import innotech.com.sv.servicios.IDetalleContable;
import innotech.com.sv.servicios.TipoHabitacionImp;

@Service
public class GeneraContabilidad {

	@Value("${innotec.com.ivaDebitoFiscal}")
	double ivadebitofiscal;
	
	@Autowired
	TipoHabitacionImp tipoHabitacionServ;

	@Autowired
	IDetalleContable detalleContableServ;
	
	@Autowired
	ICargosAdicionales cargosAdicionalesImp;
	
	@Autowired
	IClaseServicio claseServicioImp;
	
	float cargosHbitacion = 0;

	// Validando que esten llenas todas las cuentas contables para ese tipo de
	// habitacion
	private boolean validacuentas(TiposHabitacion tipoHabitacion) {

		if (!(tipoHabitacion.getCtaanticipoclientes() == null)
				//
				&& !(tipoHabitacion.getCtagarantiaclientes() == null) && !(tipoHabitacion.getOtrosIngresos() == null)
				&& !(tipoHabitacion.getAnulacionRecibos() == null)
				//
				&& !(tipoHabitacion.getCaja() == null) && !(tipoHabitacion.getCuentasporcobrar() == null)
				//
				&& !(tipoHabitacion.getIvadebito() == null) && !(tipoHabitacion.getVentas() == null)) {
			return true;
		} else
			return false;
	}

	// Generacion del anticipo
	public void registraAnticipoReserva(Reserva reserva, Ocupacion ocupacion) {

		TiposHabitacion tipoHabitacion = tipoHabitacionServ.findById(reserva.getTipohabitacion().getId());

		System.out.println("Validando si tiene las cuentas = " + validacuentas(tipoHabitacion));

		// Si las cuentas contables estan definidas se procedera a generar transacciones
		if (validacuentas(tipoHabitacion)) {
			DetalleContable cargos = new DetalleContable();
			DetalleContable abonos = new DetalleContable();

			Double MontoOperacion = reserva.getMontoPreReserva();
			//
			String CtaCargo = tipoHabitacion.getCaja();
			String CtaAbono = tipoHabitacion.getCtaanticipoclientes();
			String mensaje = " Anticipo de reserva: empresa = " + reserva.getEmpresa().getNombre() + " cliente= "
					+ reserva.getCliente().getNombredui() + " id_reserva= " + reserva.getId() + " Periodo de reserva "
					+ reserva.getFechaInicio() + " Al " + reserva.getFechaFin();
			// Efectuando los cargos
			cargos.setEmpresa(reserva.getEmpresa());
			cargos.setCuenta(CtaCargo);
			cargos.setMontocargo(MontoOperacion);
			cargos.setOcupacion(ocupacion);
			cargos.setMontoabono(0);
			cargos.setDescripcion(mensaje);
			detalleContableServ.save(cargos);

			// Efectuando los abonos
			abonos.setEmpresa(reserva.getEmpresa());
			abonos.setCuenta(CtaAbono);
			abonos.setOcupacion(ocupacion);
			abonos.setMontocargo(0);
			abonos.setMontoabono(MontoOperacion);
			abonos.setDescripcion(mensaje);
			detalleContableServ.save(abonos);

		}

	};

	// Reversion del anticipo
	public void revierteAnticipoReserva(Reserva reserva, Ocupacion ocupacion) {
		TiposHabitacion tipoHabitacion = tipoHabitacionServ.findById(reserva.getTipohabitacion().getId());

		// Si las cuentas contables estan definidas se procedera a generar transacciones
		if (validacuentas(tipoHabitacion)) {
			DetalleContable cargos = new DetalleContable();
			DetalleContable abonos = new DetalleContable();

			Double MontoOperacion = reserva.getMontoPreReserva();
			//
			String CtaCargo = tipoHabitacion.getCaja();
			String CtaAbono = tipoHabitacion.getCtaanticipoclientes();
			String mensaje = " Revierte Anticipo de reserva:  empresa = " + reserva.getEmpresa().getNombre()
					+ " cliente= " + reserva.getCliente().getNombredui() + " id_reserva= " + reserva.getId()
					+ " Periodo de reserva " + reserva.getFechaInicio() + " Al " + reserva.getFechaFin();

			// Efectuando los cargos
			cargos.setEmpresa(reserva.getEmpresa());
			cargos.setCuenta(CtaCargo);
			cargos.setMontocargo(0);
			cargos.setOcupacion(ocupacion);
			cargos.setMontoabono(MontoOperacion);
			cargos.setDescripcion(mensaje);
			detalleContableServ.save(cargos);

			// Efectuando los abonos
			abonos.setEmpresa(reserva.getEmpresa());
			abonos.setCuenta(CtaAbono);
			abonos.setOcupacion(ocupacion);
			abonos.setMontocargo(MontoOperacion);
			abonos.setMontoabono(0);
			abonos.setDescripcion(mensaje);
			detalleContableServ.save(abonos);

		}

	}

	// Generacion del anticipo
	public void registraGarantia(Reserva reserva, Ocupacion ocupacion) {
		TiposHabitacion tipoHabitacion = tipoHabitacionServ.findById(reserva.getTipohabitacion().getId());

		// Si las cuentas contables estan definidas se procedera a generar transacciones
		if (validacuentas(tipoHabitacion)) {
			DetalleContable cargos = new DetalleContable();
			DetalleContable abonos = new DetalleContable();

			Double MontoOperacion = reserva.getMontoDeposito();
			//
			String CtaCargo = tipoHabitacion.getCaja();
			String CtaAbono = tipoHabitacion.getCtagarantiaclientes();
			String mensaje = " Garantia de reserva:  empresa = " + reserva.getEmpresa().getNombre() + " cliente= "
					+ reserva.getCliente().getNombredui() + " id_reserva= " + reserva.getId() + " Periodo de reserva "
					+ reserva.getFechaInicio() + " Al " + reserva.getFechaFin();

			// Efectuando los cargos
			cargos.setEmpresa(reserva.getEmpresa());
			cargos.setOcupacion(ocupacion);
			cargos.setCuenta(CtaCargo);
			cargos.setMontocargo(MontoOperacion);
			cargos.setMontoabono(0);
			cargos.setDescripcion(mensaje);
			detalleContableServ.save(cargos);

			// Efectuando los abonos
			abonos.setEmpresa(reserva.getEmpresa());
			abonos.setOcupacion(ocupacion);
			abonos.setCuenta(CtaAbono);
			abonos.setMontocargo(0);
			abonos.setDescripcion(mensaje);
			abonos.setMontoabono(MontoOperacion);
			detalleContableServ.save(abonos);
		}

	};

	// Reversion del anticipo
	public void revierteGarantia(Reserva reserva, Ocupacion ocupacion) {
		TiposHabitacion tipoHabitacion = tipoHabitacionServ.findById(reserva.getTipohabitacion().getId());

		// Si las cuentas contables estan definidas se procedera a generar transacciones
		if (validacuentas(tipoHabitacion)) {
			DetalleContable cargos = new DetalleContable();
			DetalleContable abonos = new DetalleContable();

			Double MontoOperacion = reserva.getMontoDeposito();
			//
			String CtaCargo = tipoHabitacion.getCaja();
			String CtaAbono = tipoHabitacion.getCtagarantiaclientes();
			String mensaje = " ReversionGarantia de reserva: empresa = " + reserva.getEmpresa().getNombre()
					+ " cliente= " + reserva.getCliente().getNombredui() + " id_reserva= " + reserva.getId()
					+ " Periodo de reserva " + reserva.getFechaInicio() + " Al " + reserva.getFechaFin();

			// Efectuando los cargos
			cargos.setEmpresa(reserva.getEmpresa());
			cargos.setOcupacion(ocupacion);
			cargos.setCuenta(CtaCargo);
			cargos.setMontocargo(0);
			cargos.setDescripcion(mensaje);
			cargos.setMontoabono(MontoOperacion);
			detalleContableServ.save(cargos);

			// Efectuando los abonos
			abonos.setEmpresa(reserva.getEmpresa());
			abonos.setOcupacion(ocupacion);
			abonos.setCuenta(CtaAbono);
			abonos.setMontocargo(MontoOperacion);
			abonos.setMontoabono(0);
			abonos.setDescripcion(mensaje);
			detalleContableServ.save(abonos);

		}

	}

	// Generacion de partida de ventas
	public void registraVenta(Reserva reserva, Ocupacion ocupacion, 
			double ncuentasxCobrar, 
			double montoCargosAdicionales,
			double nanticipos,
			double nventas, 
			double nivaDebito) {

		TiposHabitacion tipoHabitacion = tipoHabitacionServ.findById(reserva.getTipohabitacion().getId());
		
		
		// Si las cuentas contables estan definidas se procedera a generar transacciones
		if (validacuentas(tipoHabitacion)) {
			DetalleContable cargos = new DetalleContable();

			Double MontoOperacion = ncuentasxCobrar;
			//
			String CtaCargo = tipoHabitacion.getCuentasporcobrar();
			String CtaAbono = tipoHabitacion.getVentas();
			String mensaje = " Registro de ventas:  empresa = " + reserva.getEmpresa().getNombre() + " cliente= "
					+ reserva.getCliente().getNombredui() + " id_reserva= " + reserva.getId() + " Periodo de reserva "
					+ reserva.getFechaInicio() + " Al " + reserva.getFechaFin();

			// Efectuando los cargos
			cargos.setEmpresa(reserva.getEmpresa());
			cargos.setOcupacion(ocupacion);
			cargos.setCuenta(CtaCargo);
			cargos.setMontocargo(MontoOperacion);
			cargos.setMontoabono(0);
			cargos.setDescripcion(mensaje);
			detalleContableServ.save(cargos);
			//
			// Si la operacion tiene anticipos
			if (nanticipos > 0) {
				MontoOperacion = nanticipos;
				//

				CtaCargo = tipoHabitacion.getCtaanticipoclientes();

				mensaje = " Anticipo por ventas:  empresa = " + reserva.getEmpresa().getNombre() + " cliente= "
						+ reserva.getCliente().getNombredui() + " id_reserva= " + reserva.getId()
						+ " Periodo de reserva " + reserva.getFechaInicio() + " Al " + reserva.getFechaFin();

				// Efectuando los cargos
				cargos = new DetalleContable();

				cargos.setEmpresa(reserva.getEmpresa());
				cargos.setOcupacion(ocupacion);
				cargos.setCuenta(CtaCargo);
				cargos.setMontocargo(MontoOperacion);
				cargos.setMontoabono(0);
				cargos.setDescripcion(mensaje);
				detalleContableServ.save(cargos);
			}

			//Efectuando los abonos por recargos de servicios de habitacion
			
			// Ahora vamos a leer todos los cargos adicionales y vamos a insertar el detalle
			// de la factura
			
			
			cargosHbitacion=0;
			
			if (montoCargosAdicionales>0) {

				List<CargosAdicionales> cargosadicionales = cargosAdicionalesImp.findByEmpresaFacturado(ocupacion.getEmpresa().getId(),
						ocupacion.getId());
				
				cargosadicionales.forEach(car -> {
					 float  ivaDebito = 0;
					 float  vtaTotal  = 0;
					 System.out.println("Monto cargos adicionale "+montoCargosAdicionales);
					//Si se tiene Iva, el monto debe ir sin iva, Caso contrario registrar el total.
					if (nivaDebito > 0) {
						ivaDebito = car.getTotal() - Math.round((car.getTotal()/(1+ivadebitofiscal)*100d/100d));
						vtaTotal = Math.round((car.getTotal()/(1+ivadebitofiscal)*100d/100d));
					} else {
						ivaDebito = 0;
						vtaTotal  = car.getTotal();
					};
					
					cargosHbitacion = cargosHbitacion + vtaTotal;
					
					//Buscando a que clase de servicio pertenece para sacar la cuenta contable
					ClaseServicio claseservicio = claseServicioImp.findById(car.getClaseservicio().getId());
					
					//Llenando la linea ventas por Cargos a habitacion
					DetalleContable abonos = new DetalleContable();					
					abonos.setEmpresa(car.getEmpresa());
					abonos.setOcupacion(car.getOcupacion());
					abonos.setCuenta(claseservicio.getVentas());
					abonos.setDescripcion(" Registro de ingresos por Cargo Habitacion. Empresa= "+car.getEmpresa().getNombre() + " Cliente= "+car.getOcupacion().getReserva().getCliente().getNombredui() +" Reserva id "+car.getOcupacion().getReserva().getId()+" Periodo de reserva del "+car.getOcupacion().getReserva().getFechaInicio() + " al "+car.getOcupacion().getReserva().getFechaFin() );
					abonos.setMontocargo(0);
					abonos.setMontoabono(vtaTotal);
					detalleContableServ.save(abonos);										
				});
			};
			
			// Efectuando los abonos  por cargo de habitacion, monto sin iva

			MontoOperacion = nventas-cargosHbitacion;

			DetalleContable abonos = new DetalleContable();

			mensaje = " Registro de ventas:  empresa = " + reserva.getEmpresa().getNombre() + " cliente= "
					+ reserva.getCliente().getNombredui() + " id_reserva= " + reserva.getId() + " Periodo de reserva "
					+ reserva.getFechaInicio() + " Al " + reserva.getFechaFin();

			abonos.setEmpresa(reserva.getEmpresa());
			abonos.setOcupacion(ocupacion);
			abonos.setCuenta(CtaAbono);
			abonos.setDescripcion(mensaje);
			abonos.setMontocargo(0);
			abonos.setMontoabono(MontoOperacion);
			detalleContableServ.save(abonos);
			//
			// Si es necesario registrar el impuesto sobre venta
			if (nivaDebito > 0) {
				MontoOperacion = nivaDebito;
				CtaAbono = tipoHabitacion.getIvadebito();

				abonos = new DetalleContable();

				mensaje = " Iva debito Fiscal:  empresa = " + reserva.getEmpresa().getNombre() + " cliente= "
						+ reserva.getCliente().getNombredui() + " id_reserva= " + reserva.getId()
						+ " Periodo de reserva " + reserva.getFechaInicio() + " Al " + reserva.getFechaFin();

				abonos.setEmpresa(reserva.getEmpresa());
				abonos.setOcupacion(ocupacion);
				abonos.setCuenta(CtaAbono);
				abonos.setDescripcion(mensaje);
				abonos.setMontocargo(0);
				abonos.setMontoabono(MontoOperacion);
				detalleContableServ.save(abonos);
			}

		}

	};

	// Reversion del anticipo
	public void registroCobro(Recibos recibo) {

		Reserva reserva = recibo.getFactura().getOcupacion().getReserva();

		TiposHabitacion tipoHabitacion = tipoHabitacionServ.findById(reserva.getTipohabitacion().getId());

		// Si las cuentas contables estan definidas se procedera a generar transacciones
		if (validacuentas(tipoHabitacion)) {

			DetalleContable cargos = new DetalleContable();
			DetalleContable abonos = new DetalleContable();

			Double MontoOperacion = (double) recibo.getMontoRecibo();
			//
			String CtaCargo = tipoHabitacion.getCaja();
			String CtaAbono = tipoHabitacion.getCuentasporcobrar();

			String mensaje = " Pago de recibo:  empresa = " + recibo.getEmpresa().getNombre() + " cliente= "
					+ recibo.getCliente().getNombredui() + " id_reserva= " + reserva.getId() + " Periodo de reserva "
					+ reserva.getFechaInicio() + " Al " + reserva.getFechaFin();

			// Efectuando los cargos
			cargos.setEmpresa(reserva.getEmpresa());
			cargos.setOcupacion(recibo.getFactura().getOcupacion());
			cargos.setCuenta(CtaCargo);
			cargos.setMontocargo(MontoOperacion);
			cargos.setDescripcion(mensaje);
			cargos.setMontoabono(0);
			detalleContableServ.save(cargos);

			// Efectuando los abonos
			abonos.setEmpresa(reserva.getEmpresa());
			abonos.setOcupacion(recibo.getFactura().getOcupacion());
			abonos.setCuenta(CtaAbono);
			abonos.setMontocargo(0);
			abonos.setMontoabono(MontoOperacion);
			abonos.setDescripcion(mensaje);
			detalleContableServ.save(abonos);
			
			//Validando si se tienen cobros de otros ingresos
			if (recibo.getRecargo()>0) {
				 cargos = new DetalleContable();
				 abonos = new DetalleContable();

				MontoOperacion = (double) recibo.getRecargo();
				//
				CtaCargo = tipoHabitacion.getCaja();
				CtaAbono = tipoHabitacion.getOtrosIngresos();

				 mensaje = " Registro de otros Ingresos x recargos en recibos:  empresa = " + recibo.getEmpresa().getNombre() + " cliente= "
						+ recibo.getCliente().getNombredui() + " id_reserva= " + reserva.getId() + " Periodo de reserva "
						+ reserva.getFechaInicio() + " Al " + reserva.getFechaFin()+"  " +recibo.getComentarios();

				// Efectuando los cargos
				cargos.setEmpresa(reserva.getEmpresa());
				cargos.setOcupacion(recibo.getFactura().getOcupacion());
				cargos.setCuenta(CtaCargo);
				cargos.setMontocargo(MontoOperacion);
				cargos.setDescripcion(mensaje);
				cargos.setMontoabono(0);
				detalleContableServ.save(cargos);

				// Efectuando los abonos
				abonos.setEmpresa(reserva.getEmpresa());
				abonos.setOcupacion(recibo.getFactura().getOcupacion());
				abonos.setCuenta(CtaAbono);
				abonos.setMontocargo(0);
				abonos.setMontoabono(MontoOperacion);
				abonos.setDescripcion(mensaje);
				detalleContableServ.save(abonos);				
			}
			
			//Validando si se tienen descuentos ingresos
			if (recibo.getDescuento()>0) {
				 cargos = new DetalleContable();
				 abonos = new DetalleContable();
				MontoOperacion = (double) recibo.getDescuento();
				//
				CtaAbono = tipoHabitacion.getCuentasporcobrar();
				CtaCargo = tipoHabitacion.getOtrosIngresos();

				 mensaje = " Registro de devoluciones saldo x disminuciones en recibos:  empresa = " + recibo.getEmpresa().getNombre() + " cliente= "
						+ recibo.getCliente().getNombredui() + " id_reserva= " + reserva.getId() + " Periodo de reserva "
						+ reserva.getFechaInicio() + " Al " + reserva.getFechaFin() +"  " +recibo.getComentarios();

				// Efectuando los cargos
				cargos.setEmpresa(reserva.getEmpresa());
				cargos.setOcupacion(recibo.getFactura().getOcupacion());
				cargos.setCuenta(CtaCargo);
				cargos.setMontocargo(MontoOperacion);
				cargos.setDescripcion(mensaje);
				cargos.setMontoabono(0);
				detalleContableServ.save(cargos);

				// Efectuando los abonos
				abonos.setEmpresa(reserva.getEmpresa());
				abonos.setOcupacion(recibo.getFactura().getOcupacion());
				abonos.setCuenta(CtaAbono);
				abonos.setMontocargo(0);
				abonos.setMontoabono(MontoOperacion);
				abonos.setDescripcion(mensaje);
				detalleContableServ.save(abonos);
			}
			
		}
	};

	// Registrando Otros Ingresos por el recibo
	public void registraCargoRecibo(Recibos recibo, double ncargo) {
		Reserva reserva = recibo.getFactura().getOcupacion().getReserva();
		//

		TiposHabitacion tipoHabitacion = tipoHabitacionServ.findById(reserva.getTipohabitacion().getId());

		// Si las cuentas contables estan definidas se procedera a generar transacciones
		if (validacuentas(tipoHabitacion)) {

			DetalleContable cargos = new DetalleContable();
			DetalleContable abonos = new DetalleContable();

			Double MontoOperacion = ncargo;
			//
			String CtaCargo = tipoHabitacion.getCuentasporcobrar();
			String CtaAbono = tipoHabitacion.getOtrosIngresos();

			String mensaje = " Ingreso x Otros ingresos:  empresa = " + recibo.getEmpresa().getNombre() + " cliente= "
					+ recibo.getCliente().getNombredui() + " id_reserva= " + reserva.getId() + " Periodo de reserva "
					+ reserva.getFechaInicio() + " Al " + reserva.getFechaFin();

			// Efectuando los cargos
			cargos.setEmpresa(reserva.getEmpresa());
			cargos.setOcupacion(recibo.getFactura().getOcupacion());
			cargos.setCuenta(CtaCargo);
			cargos.setMontocargo(MontoOperacion);
			cargos.setDescripcion(mensaje);
			cargos.setMontoabono(0);
			detalleContableServ.save(cargos);

			// Efectuando los abonos
			abonos.setEmpresa(reserva.getEmpresa());
			abonos.setOcupacion(recibo.getFactura().getOcupacion());
			abonos.setCuenta(CtaAbono);
			abonos.setMontocargo(0);
			abonos.setMontoabono(MontoOperacion);
			abonos.setDescripcion(mensaje);
			detalleContableServ.save(abonos);
		}
	}

	// Disminuyendo Otros Ingresos por el recibo
	public void registraAbonoRecibo(Recibos recibo, double ncargo) {
		Reserva reserva = recibo.getFactura().getOcupacion().getReserva();
		//

		TiposHabitacion tipoHabitacion = tipoHabitacionServ.findById(reserva.getTipohabitacion().getId());

		// Si las cuentas contables estan definidas se procedera a generar transacciones
		if (validacuentas(tipoHabitacion)) {

			DetalleContable cargos = new DetalleContable();
			DetalleContable abonos = new DetalleContable();

			Double MontoOperacion = ncargo;
			//
			String CtaCargo = tipoHabitacion.getCuentasporcobrar();
			String CtaAbono = tipoHabitacion.getOtrosIngresos();

			String mensaje = " Devolucion x Otros ingresos:  empresa = " + recibo.getEmpresa().getNombre()
					+ " cliente= " + recibo.getCliente().getNombredui() + " id_reserva= " + reserva.getId()
					+ " Periodo de reserva " + reserva.getFechaInicio() + " Al " + reserva.getFechaFin();

			// Efectuando los cargos
			cargos.setEmpresa(reserva.getEmpresa());
			cargos.setOcupacion(recibo.getFactura().getOcupacion());
			cargos.setCuenta(CtaCargo);
			cargos.setMontocargo(0);
			cargos.setDescripcion(mensaje);
			cargos.setMontoabono(MontoOperacion);
			detalleContableServ.save(cargos);

			// Efectuando los abonos
			abonos.setEmpresa(reserva.getEmpresa());
			abonos.setOcupacion(recibo.getFactura().getOcupacion());
			abonos.setCuenta(CtaAbono);
			abonos.setMontocargo(MontoOperacion);
			abonos.setMontoabono(0);
			abonos.setDescripcion(mensaje);
			detalleContableServ.save(abonos);
		}
	}

	// ANULACION DE RECIBOS DE FACTURAS
	public void registroAnulacion(Recibos recibo) {

		Reserva reserva = recibo.getFactura().getOcupacion().getReserva();

		TiposHabitacion tipoHabitacion = tipoHabitacionServ.findById(reserva.getTipohabitacion().getId());

		// Si las cuentas contables estan definidas se procedera a generar transacciones
		if (validacuentas(tipoHabitacion)) {

			DetalleContable cargos = new DetalleContable();
			DetalleContable abonos = new DetalleContable();

			Double MontoOperacion = (double) recibo.getRecargo();
			//
			String CtaCargo = tipoHabitacion.getCuentasporcobrar();
			String CtaAbono = tipoHabitacion.getAnulacionRecibos();

			String mensaje = " Perdida Por Anulacion de facturas:  empresa = " + recibo.getEmpresa().getNombre()
					+ " cliente= " + recibo.getCliente().getNombredui() + " id_reserva= " + reserva.getId()
					+ " Periodo de reserva " + reserva.getFechaInicio() + " Al " + reserva.getFechaFin()
					+ " Factura_id= " + recibo.getFactura().getId() + " Recibo_id= " + recibo.getId();

			// Efectuando los cargos
			cargos.setEmpresa(reserva.getEmpresa());
			cargos.setOcupacion(recibo.getFactura().getOcupacion());
			cargos.setCuenta(CtaCargo);
			cargos.setMontocargo(MontoOperacion);
			cargos.setDescripcion(mensaje);
			cargos.setMontoabono(0);
			detalleContableServ.save(cargos);

			// Efectuando los abonos
			abonos.setEmpresa(reserva.getEmpresa());
			abonos.setOcupacion(recibo.getFactura().getOcupacion());
			abonos.setCuenta(CtaAbono);
			abonos.setMontocargo(0);
			abonos.setMontoabono(MontoOperacion);
			abonos.setDescripcion(mensaje);
			detalleContableServ.save(abonos);
		}
	};

	// AUMENTO DE SALDO DE RECIBOS DE FACTURAS
	public void registroAumentaSaldo(Recibos recibo) {

	}

	// DISMINUCION DE SALDO DE RECIBOS DE FACTURAS
	public void registroDisminuyeSaldo(Recibos recibo) {

	}
}
