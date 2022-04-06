INSERT INTO monedas (codigo, descripcion)  VALUES ('Usd$', 'Dollar Estado Unidense');
INSERT INTO monedas (codigo, descripcion)  VALUES ('Mx', 'Peso Mexicano');
INSERT INTO monedas (codigo, descripcion)  VALUES ('Slv', 'Colon Salvadoreño');

INSERT INTO empresas (nombre, direccion, nit, nrc, nomb_representante, moneda_id)  VALUES ('LAS PALMAS',    'Colonia Escalon',  '0715-12141975-101', '123-56', 'PEPE TORO',1);
INSERT INTO empresas (nombre, direccion, nit, nrc, nomb_representante, moneda_id)  VALUES ('SURF CITY',   'Colonia Escalon', '0715-12141975',     '123-56', 'LA MUJER MARAVILLA',1);
INSERT INTO empresas (nombre, direccion, nit, nrc, nomb_representante, moneda_id)  VALUES ('INNOVACION', 'Colonia Escalon', '0715-12141975',     '123-56', 'EL GIGANTE',2);
INSERT INTO empresas (nombre, direccion, nit, nrc, nomb_representante, moneda_id)  VALUES ('UCA', 'Colonia Escalon', '0715-12141975',     '123-56', 'EL GIGANTE',3);

INSERT INTO promociones (descripcion, empresa_id, fechainicio, fechafin, porcdescuento)  VALUES ('Promocion Navideña FUDEM/2020', 1, '2021-12-01',     '2021-12-31', 50);
INSERT INTO promociones (descripcion, empresa_id, fechainicio, fechafin, porcdescuento)  VALUES ('Promocion Navideña GALAXIA/2020', 2, '2021-12-01',     '2021-12-31', 50);
INSERT INTO promociones (descripcion, empresa_id, fechainicio, fechafin, porcdescuento)  VALUES ('Promocion Navideña INNOVAVION/2020', 3, '2020-12-01',     '2020-12-31', 50);
INSERT INTO promociones (descripcion, empresa_id, fechainicio, fechafin, porcdescuento)  VALUES ('Promocion Dia Emamorados FUDEM/2021', 1, '2020-12-01',     '2020-12-31', 50);
INSERT INTO promociones (descripcion, empresa_id, fechainicio, fechafin, porcdescuento)  VALUES ('Promocion Dia de la Madre FUDEM/2021', 1, '2020-12-01',     '2020-12-31', 50);
INSERT INTO promociones (descripcion, empresa_id, fechainicio, fechafin, porcdescuento)  VALUES ('Promocion Dia del Padre FUDEM/2021', 1, '2020-12-01',     '2020-12-31', 50);
INSERT INTO promociones (descripcion, empresa_id, fechainicio, fechafin, porcdescuento)  VALUES ('Promocion Navideña FUDEM/2021', 1, '2020-12-01',     '2020-12-31', 50);

INSERT INTO users(username, password, enabled)  VALUES ('admin', '$2a$10$s8wmTOcaMR.oBbBHdaLO4Ot8gbfaxobqVEwB8yzfkeTel5FNuj21C', true);
INSERT INTO users(username, password, enabled)  VALUES ('cavalos', '$2a$10$SNTkRWGE/WNsk7OkIU/tv.6WcqKOYEft9zeiqA.ubwm/33EvniHiO', true);

INSERT INTO authorities(user_id, authority)  VALUES (1, 'ROLE_ADMIN');
INSERT INTO authorities(user_id, authority)  VALUES (1, 'ROLE_USER');
INSERT INTO authorities(user_id, authority)  VALUES (2, 'ROLE_USER');

INSERT INTO USUARIOS_BY_CIA (usuario_id, empresa_id)  VALUES (1, 1);
INSERT INTO USUARIOS_BY_CIA (usuario_id, empresa_id)  VALUES (1, 2);
INSERT INTO USUARIOS_BY_CIA (usuario_id, empresa_id)  VALUES (1, 3);
INSERT INTO USUARIOS_BY_CIA (usuario_id, empresa_id)  VALUES (1, 4);
INSERT INTO USUARIOS_BY_CIA (usuario_id, empresa_id)  VALUES (2, 3);

INSERT INTO clientes (empresa_id, nombredui, telefonocontacto, lugartrabajo, email, telefonotrabajo, dui, direcciondui, contacto1, contacto2, fecha_nacimiento) values (1, 'Luis Miguel Novellino', '7888-52522', 'SISA-8', 'cipotex1@hotmail.com', '788852521' , '00867376-81','Residencial San Jacinto Av. San Jacinto Edif. p-6', 'Martha Morena de Avalos', 'Jose Ernesto Avalos' , '2020-12-31');
INSERT INTO clientes (empresa_id, nombredui, telefonocontacto, lugartrabajo, email, telefonotrabajo, dui, direcciondui, contacto1, contacto2, fecha_nacimiento) values (1, 'Carlos Avalos', '7888-52523', 'SISA-8', 'cipotex2@hotmail.com', '788852522' , '00867376-82','Residencial San Jacinto Av. San Jacinto Edif. p-6', 'Martha Morena de Avalos', 'Jose Ernesto Avalos', '2020-12-31' );
INSERT INTO clientes (empresa_id, nombredui, telefonocontacto, lugartrabajo, email, telefonotrabajo, dui, direcciondui, contacto1, contacto2, fecha_nacimiento) values (2, 'Maria Durcio de la Paz', '7888-52524', 'SISA-8', 'cipotex3@hotmail.com', '788852523' , '00867376-83','Residencial San Jacinto Av. San Jacinto Edif. p-6', 'Martha Morena de Avalos', 'Jose Ernesto Avalos', '2020-12-31' );
INSERT INTO clientes (empresa_id, nombredui, telefonocontacto, lugartrabajo, email, telefonotrabajo, dui, direcciondui, contacto1, contacto2, fecha_nacimiento) values (3, 'El Coyote Sanguinario', '7888-52525', 'SISA-8', 'cipotex4@hotmail.com', '788852524' , '00867376-84','Residencial San Jacinto Av. San Jacinto Edif. p-6', 'Martha Morena de Avalos', 'Jose Ernesto Avalos', '2020-12-31' );


INSERT INTO tiposHabitaciones (DESCRIPCION,  PRECIODIA,  PRECIOSEMANA, PRECIOMES, EMPRESA_ID, CTAANTICIPOCLIENTES,  CTAGARANTIACLIENTES ,CAJA, CUENTASPORCOBRAR, VENTAS, IVADEBITO, OTROS_INGRESOS, ANULACION_RECIBOS ) VALUES('Single', 25, 130, 250, 1,'1001-1',' 1001-2', '1001-3', '1001-4','1001-5', '1001-6', '1001-7', '1001-8');
INSERT INTO tiposHabitaciones (DESCRIPCION,  PRECIODIA,  PRECIOSEMANA, PRECIOMES, EMPRESA_ID ) VALUES('Double', 45, 140, 275, 1);
INSERT INTO tiposHabitaciones (DESCRIPCION,  PRECIODIA,  PRECIOSEMANA, PRECIOMES,  EMPRESA_ID ) VALUES('Multiple', 65, 150, 375, 1);
INSERT INTO tiposHabitaciones (DESCRIPCION,  PRECIODIA,  PRECIOSEMANA, PRECIOMES,  EMPRESA_ID ) VALUES('Multiple2', 60, 145, 370, 1);
INSERT INTO tiposHabitaciones (DESCRIPCION,  PRECIODIA,  PRECIOSEMANA, PRECIOMES,  EMPRESA_ID ) VALUES('Multiple3', 55, 140, 365, 2);

INSERT INTO habitaciones (CODIGO, ESTADO, EMPRESA_ID, TIPOHABITACION_ID) VALUES ('FH01', 0, 1,1);
INSERT INTO habitaciones (CODIGO, ESTADO, EMPRESA_ID, TIPOHABITACION_ID) VALUES ('FH02', 0, 1,1);
INSERT INTO habitaciones (CODIGO, ESTADO, EMPRESA_ID, TIPOHABITACION_ID) VALUES ('FH03', 1, 1,2);
INSERT INTO habitaciones (CODIGO, ESTADO, EMPRESA_ID, TIPOHABITACION_ID) VALUES ('FH04', 1, 1,2);

INSERT INTO activos (CODIGO, COMENTARIOS, DESCRIPCION, ESTADO, EMPRESA_ID, HABITACION_ID,costoadquisisicion  ) VALUES ('ACT001', 'COMPRADO EN ENERO 2021 CF#2334', 'VENTILADOR DE PARED', 0, 1, 1, 10);
INSERT INTO activos (CODIGO, COMENTARIOS, DESCRIPCION, ESTADO, EMPRESA_ID, costoadquisisicion  ) VALUES ('ACT002', 'COMPRADO EN ENERO 2021 CF#2334', 'VENTILADOR DE PARED', 0, 1, 20 );

INSERT INTO reservas (empresa_id, cliente_id, tipohabitacion_id, habitacion_id, fecha_Inicio, fecha_Fin , dias_ocupacion, estado_Reserva, monto_Deposito, periodoreserva, precioreserva, recurrente, monto_reserva_con_descuento, descuento, monto_Pre_Reserva) VALUES (1, 1, 1, 1, '2021-10-01',  '2021-10-10', 3, 3, 100, 0, 15, 'N', 45, 0, 0);
INSERT INTO reservas (empresa_id, cliente_id, tipohabitacion_id, habitacion_id, fecha_Inicio, fecha_Fin , dias_ocupacion, estado_Reserva, monto_Deposito, periodoreserva, precioreserva, recurrente, monto_reserva_con_descuento, descuento, monto_Pre_Reserva) VALUES (1, 2, 1, 1, '2021-03-01',  '2021-03-10', 19, 0, 100, 0, 15, 'N', 285, 0, 0);
INSERT INTO reservas (empresa_id, cliente_id, tipohabitacion_id, habitacion_id, fecha_Inicio, fecha_Fin , dias_ocupacion, estado_Reserva, monto_Deposito, periodoreserva, precioreserva, recurrente, monto_reserva_con_descuento, descuento, monto_Pre_Reserva ) VALUES (1, 2, 1, 1, '2021-09-24',  '2021-09-30', 19, 0, 100, 0, 15, 'N', 285, 0, 0);


INSERT INTO DISPONIBILIDAD  (RESERVA_ID, EMPRESA_ID, HABITACION_ID, ESTADO, FECHA, FECHA_INS ) VALUES (1, 1, 1, 0, '2021-01-10',current_date );
INSERT INTO DISPONIBILIDAD  (RESERVA_ID, EMPRESA_ID, HABITACION_ID, ESTADO, FECHA, FECHA_INS ) VALUES (1, 1, 1, 0, '2021-02-10',current_date );
INSERT INTO DISPONIBILIDAD  (RESERVA_ID, EMPRESA_ID, HABITACION_ID, ESTADO, FECHA, FECHA_INS ) VALUES (1, 1, 1, 0, '2021-03-10',current_date );
INSERT INTO DISPONIBILIDAD  (RESERVA_ID, EMPRESA_ID, HABITACION_ID, ESTADO, FECHA, FECHA_INS ) VALUES (1, 1, 1, 0, '2021-04-10',current_date );
INSERT INTO DISPONIBILIDAD  (RESERVA_ID, EMPRESA_ID, HABITACION_ID, ESTADO, FECHA, FECHA_INS ) VALUES (1, 1, 1, 0, '2021-05-10',current_date );
INSERT INTO DISPONIBILIDAD  (RESERVA_ID, EMPRESA_ID, HABITACION_ID, ESTADO, FECHA, FECHA_INS ) VALUES (1, 1, 1, 0, '2021-06-10',current_date );
INSERT INTO DISPONIBILIDAD  (RESERVA_ID, EMPRESA_ID, HABITACION_ID, ESTADO, FECHA, FECHA_INS ) VALUES (1, 1, 1, 0, '2021-07-10',current_date );
INSERT INTO DISPONIBILIDAD  (RESERVA_ID, EMPRESA_ID, HABITACION_ID, ESTADO, FECHA, FECHA_INS ) VALUES (1, 1, 1, 0, '2021-08-10',current_date );
INSERT INTO DISPONIBILIDAD  (RESERVA_ID, EMPRESA_ID, HABITACION_ID, ESTADO, FECHA, FECHA_INS ) VALUES (1, 1, 1, 0, '2021-09-10',current_date );
INSERT INTO DISPONIBILIDAD  (RESERVA_ID, EMPRESA_ID, HABITACION_ID, ESTADO, FECHA, FECHA_INS ) VALUES (1, 1, 1, 0, '2021-10-10',current_date );



INSERT INTO DISPONIBILIDAD  (RESERVA_ID, EMPRESA_ID, HABITACION_ID, ESTADO, FECHA, FECHA_INS ) VALUES (2, 1, 2, 0, '2021-01-03',current_date );
INSERT INTO DISPONIBILIDAD  (RESERVA_ID, EMPRESA_ID, HABITACION_ID, ESTADO, FECHA, FECHA_INS ) VALUES (2, 1, 2, 0, '2021-02-03',current_date );
INSERT INTO DISPONIBILIDAD  (RESERVA_ID, EMPRESA_ID, HABITACION_ID, ESTADO, FECHA, FECHA_INS ) VALUES (2, 1, 2, 0, '2021-03-03',current_date );
INSERT INTO DISPONIBILIDAD  (RESERVA_ID, EMPRESA_ID, HABITACION_ID, ESTADO, FECHA, FECHA_INS ) VALUES (2, 1, 2, 0, '2021-04-03',current_date );
INSERT INTO DISPONIBILIDAD  (RESERVA_ID, EMPRESA_ID, HABITACION_ID, ESTADO, FECHA, FECHA_INS ) VALUES (2, 1, 2, 0, '2021-05-03',current_date );
INSERT INTO DISPONIBILIDAD  (RESERVA_ID, EMPRESA_ID, HABITACION_ID, ESTADO, FECHA, FECHA_INS ) VALUES (2, 1, 2, 0, '2021-06-03',current_date );
INSERT INTO DISPONIBILIDAD  (RESERVA_ID, EMPRESA_ID, HABITACION_ID, ESTADO, FECHA, FECHA_INS ) VALUES (2, 1, 2, 0, '2021-07-03',current_date );
INSERT INTO DISPONIBILIDAD  (RESERVA_ID, EMPRESA_ID, HABITACION_ID, ESTADO, FECHA, FECHA_INS ) VALUES (2, 1, 2, 0, '2021-08-03',current_date );
INSERT INTO DISPONIBILIDAD  (RESERVA_ID, EMPRESA_ID, HABITACION_ID, ESTADO, FECHA, FECHA_INS ) VALUES (2, 1, 2, 0, '2021-09-03',current_date );
INSERT INTO DISPONIBILIDAD  (RESERVA_ID, EMPRESA_ID, HABITACION_ID, ESTADO, FECHA, FECHA_INS ) VALUES (2, 1, 2, 0, '2021-10-03',current_date );

INSERT INTO CLASESERVICIOS  (DESCRIPCION, ESTADO, NOMBRE, EMPRESA_ID, VENTAS  ) VALUES ('ALIMENTACION', 0, 'ALIMENTACION', 1, 'V-01' );
INSERT INTO CLASESERVICIOS  (DESCRIPCION, ESTADO, NOMBRE, EMPRESA_ID, VENTAS  ) VALUES ('ACTIVO FIJO', 0, 'ACTIVO FIJO', 1, 'V-02' );
INSERT INTO CLASESERVICIOS  (DESCRIPCION, ESTADO, NOMBRE, EMPRESA_ID, VENTAS  ) VALUES ('MISCELANEOS', 0, 'MISCELANEOS', 1, 'V-03' );

INSERT INTO SERVICIOS  (DESCRIPCION, ESTADO, NOMBRE, PRECIO_UNITARIO, EMPRESA_ID, CLASE_SERVICIO_ID  ) VALUES ('SERVICIO TOALLAS', 0, 'SERVICIO TOALLA', 2.5, 1, 1);
INSERT INTO SERVICIOS  (DESCRIPCION, ESTADO, NOMBRE, PRECIO_UNITARIO, EMPRESA_ID, CLASE_SERVICIO_ID) VALUES ('RENTA VENTILADOR', 0, 'RENTA VENTILADOR', 3.75, 1, 2 );

INSERT INTO ocupaciones (empresa_id, reserva_id, estado, fecha_inicio_ocupacion, fecha_fin_ocupacion) values(1, 1, 0, '2021-10-01', '2021-10-10');

INSERT INTO cargosadicionales (empresa_id, ocupacion_id, cantidad, estado, claseservicio_id, servicio_id, precio_unitario, recurrente, total, fecha_ins, DESCUENTO ) values(1, 1, 1, 0, 1, 1, 2.5, 'N',2.5, current_date, 0);

INSERT INTO gastos(empresa_id, tipofactura, estadogastos, nofactura, fechafactura, descripcion, monto, tipogasto, nombreproveedor) VALUES(1, 1, 0, '123', '2022-03-01', 'FACTURA 1', 20.86, 1, 'proveedor 1');
INSERT INTO gastos(empresa_id, tipofactura, estadogastos, nofactura, fechafactura, descripcion, monto, tipogasto, nombreproveedor) VALUES(1, 2, 1, '124', '2022-03-02', 'FACTURA 2', 10.86, 2, 'proveedor 2');
INSERT INTO gastos(empresa_id, tipofactura, estadogastos, nofactura, fechafactura, descripcion, monto, tipogasto, nombreproveedor) VALUES(1, 0, 2, '126', '2022-03-03', 'FACTURA 3', 50.86, 3, 'proveedor 3');
INSERT INTO gastos(empresa_id, tipofactura, estadogastos, nofactura, fechafactura, descripcion, monto, tipogasto, nombreproveedor) VALUES(1, 1, 0, '127', '2022-03-01', 'FACTURA 1', 20.86, 1, 'proveedor 4');
INSERT INTO gastos(empresa_id, tipofactura, estadogastos, nofactura, fechafactura, descripcion, monto, tipogasto, nombreproveedor) VALUES(1, 2, 1, '128', '2022-03-02', 'FACTURA 2', 10.86, 2, 'proveedor 5');
INSERT INTO gastos(empresa_id, tipofactura, estadogastos, nofactura, fechafactura, descripcion, monto, tipogasto, nombreproveedor) VALUES(1, 0, 2, '129', '2022-03-03', 'FACTURA 3', 50.86, 3, 'proveedor 6');
