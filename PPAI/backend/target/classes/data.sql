-- 1. Estados
INSERT INTO estados (nombre, descripcion) VALUES ('Creado', '');
INSERT INTO estados (nombre, descripcion) VALUES ('Cerrado', '');
INSERT INTO estados (nombre, descripcion) VALUES ('DeBaja', '');
INSERT INTO estados (nombre, descripcion) VALUES ('Enviado', '');
INSERT INTO estados (nombre, descripcion) VALUES ('Recibido', '');

-- 2. Roles
INSERT INTO roles (nombre, descripcion) VALUES ('Encargado de Bolsines', '');
INSERT INTO roles (nombre, descripcion) VALUES ('Gerente de Comision Medica', '');

-- 3. Usuarios
INSERT INTO usuarios (nombre, contrasena) VALUES ('encargado1', '1234');
INSERT INTO usuarios (nombre, contrasena) VALUES ('gerente_ros02', '1234');
INSERT INTO usuarios (nombre, contrasena) VALUES ('gerente_juj01', '1234');
INSERT INTO usuarios (nombre, contrasena) VALUES ('gerente_sla01', '1234');
INSERT INTO usuarios (nombre, contrasena) VALUES ('gerente_mdq01', '1234');

-- 4. Comisiones Médicas
INSERT INTO comisiones_medicas (codigo, nombre, direccion, email, telefono)
VALUES ('CM-CBA-01', 'Comisión Médica Córdoba 1', 'Av. Colón 123', 'cmcba01@srt.gob.ar', '3514001111');

INSERT INTO comisiones_medicas (codigo, nombre, direccion, email, telefono)
VALUES ('CM-CBA-02', 'Comisión Médica Córdoba 2', 'Bv. Chacabuco 450', 'cmcba02@srt.gob.ar', '3514001122');

INSERT INTO comisiones_medicas (codigo, nombre, direccion, email, telefono)
VALUES ('CM-ROS-02', 'Comisión Médica Rosario 2', 'Avellaneda 82', 'cmros02@srt.gob.ar', '3414034322');

INSERT INTO comisiones_medicas (codigo, nombre, direccion, email, telefono)
VALUES ('CM-MDZ-03', 'Comisión Médica Mendoza 3', 'San Martín 789', 'cmmdz03@srt.gob.ar', '2614003333');

INSERT INTO comisiones_medicas (codigo, nombre, direccion, email, telefono)
VALUES ('CM-BA-01', 'Comisión Médica C.A.B.A 1', 'Av. 9 de Julio 167', 'cmba01@srt.gob.ar', '1140174661');

INSERT INTO comisiones_medicas (codigo, nombre, direccion, email, telefono)
VALUES ('CM-TUC-01', 'Comisión Médica Tucumán 1', 'Sarmiento 456', 'cmtuc01@srt.gob.ar', '3814002222');

INSERT INTO comisiones_medicas (codigo, nombre, direccion, email, telefono)
VALUES ('CM-SLA-01', 'Comisión Médica Salta 1', 'Belgrano 320', 'cmsla01@srt.gob.ar', '3874005555');

INSERT INTO comisiones_medicas (codigo, nombre, direccion, email, telefono)
VALUES ('CM-JUJ-01', 'Comisión Médica Jujuy 1', 'Lavalle 110', 'cmjuj01@srt.gob.ar', '3884006666');

INSERT INTO comisiones_medicas (codigo, nombre, direccion, email, telefono)
VALUES ('CM-NQN-01', 'Comisión Médica Neuquén 1', 'Av. Argentina 540', 'cmnqn01@srt.gob.ar', '2994007777');

INSERT INTO comisiones_medicas (codigo, nombre, direccion, email, telefono)
VALUES ('CM-MDQ-01', 'Comisión Médica Mar del Plata 1', 'Av. Luro 2100', 'cmmdq01@srt.gob.ar', '2234008888');

INSERT INTO comisiones_medicas (codigo, nombre, direccion, email, telefono)
VALUES ('CM-LPL-01', 'Comisión Médica La Plata 1', 'Av. Rivadavia 25', 'cmlpl01@srt.gob.ar', '2214073236');

-- 5. Empleados
INSERT INTO empleados (nombre, apellido, email, rol_nombre, comision_medica_codigo, usuario_nombre)
VALUES ('Juan', 'Perez', 'jperez@srt.gob.ar', 'Encargado de Bolsines', 'CM-CBA-01', 'encargado1');

INSERT INTO empleados (nombre, apellido, email, rol_nombre, comision_medica_codigo, usuario_nombre)
VALUES ('Carlos', 'Gomez', 'cgomez@srt.gob.ar', 'Gerente de Comision Medica', 'CM-ROS-02', 'gerente_ros02');

INSERT INTO empleados (nombre, apellido, email, rol_nombre, comision_medica_codigo, usuario_nombre)
VALUES ('Laura', 'Fernandez', 'lfernandez@srt.gob.ar', 'Gerente de Comision Medica', 'CM-JUJ-01', 'gerente_juj01');

INSERT INTO empleados (nombre, apellido, email, rol_nombre, comision_medica_codigo, usuario_nombre)
VALUES ('Martin', 'Sosa', 'msosa@srt.gob.ar', 'Gerente de Comision Medica', 'CM-SLA-01', 'gerente_sla01');

INSERT INTO empleados (nombre, apellido, email, rol_nombre, comision_medica_codigo, usuario_nombre)
VALUES ('Valeria', 'Diaz', 'vdiaz@srt.gob.ar', 'Gerente de Comision Medica', 'CM-MDQ-01', 'gerente_mdq01');

-- 6. Bolsines
INSERT INTO bolsines (numero_bolsin, numero_precinto, fecha, peso, cm_origen_codigo, cm_destino_codigo)
VALUES (101, 5001, '2026-11-22 09:00:00', 2.5, 'CM-CBA-01', 'CM-ROS-02');

INSERT INTO bolsines (numero_bolsin, numero_precinto, fecha, peso, cm_origen_codigo, cm_destino_codigo)
VALUES (102, 5002, '2026-11-22 09:10:00', 1.9, 'CM-ROS-02', 'CM-MDZ-03');

INSERT INTO bolsines (numero_bolsin, numero_precinto, fecha, peso, cm_origen_codigo, cm_destino_codigo)
VALUES (103, 5003, '2026-11-22 09:20:00', 3.4, 'CM-MDZ-03', 'CM-BA-01');

INSERT INTO bolsines (numero_bolsin, numero_precinto, fecha, peso, cm_origen_codigo, cm_destino_codigo)
VALUES (104, 5004, '2026-11-22 09:30:00', 3.1, 'CM-BA-01', 'CM-CBA-01');

INSERT INTO bolsines (numero_bolsin, numero_precinto, fecha, peso, cm_origen_codigo, cm_destino_codigo)
VALUES (105, 5005, '2026-11-22 09:40:00', 2.0, 'CM-TUC-01', 'CM-SLA-01');

INSERT INTO bolsines (numero_bolsin, numero_precinto, fecha, peso, cm_origen_codigo, cm_destino_codigo)
VALUES (106, 5006, '2026-11-22 09:50:00', 2.8, 'CM-SLA-01', 'CM-JUJ-01');

INSERT INTO bolsines (numero_bolsin, numero_precinto, fecha, peso, cm_origen_codigo, cm_destino_codigo)
VALUES (107, 5007, '2026-11-22 10:00:00', 1.5, 'CM-NQN-01', 'CM-MDQ-01');

INSERT INTO bolsines (numero_bolsin, numero_precinto, fecha, peso, cm_origen_codigo, cm_destino_codigo)
VALUES (108, 5008, '2026-11-22 10:10:00', 4.0, 'CM-MDQ-01', 'CM-BA-01');

INSERT INTO bolsines (numero_bolsin, numero_precinto, fecha, peso, cm_origen_codigo, cm_destino_codigo)
VALUES (109, 5009, '2026-11-22 10:15:00', 1.8, 'CM-LPL-01', 'CM-CBA-01');

INSERT INTO bolsines (numero_bolsin, numero_precinto, fecha, peso, cm_origen_codigo, cm_destino_codigo)
VALUES (110, 5010, '2026-11-22 10:25:00', 2.7, 'CM-CBA-02', 'CM-ROS-02');

INSERT INTO bolsines (numero_bolsin, numero_precinto, fecha, peso, cm_origen_codigo, cm_destino_codigo)
VALUES (111, 5011, '2026-03-07 10:00:00', 2.3, 'CM-CBA-01', 'CM-JUJ-01');

INSERT INTO bolsines (numero_bolsin, numero_precinto, fecha, peso, cm_origen_codigo, cm_destino_codigo)
VALUES (112, 5012, '2026-03-07 10:15:00', 3.1, 'CM-CBA-01', 'CM-SLA-01');

INSERT INTO bolsines (numero_bolsin, numero_precinto, fecha, peso, cm_origen_codigo, cm_destino_codigo)
VALUES (113, 5013, '2026-03-07 10:30:00', 2.6, 'CM-CBA-01', 'CM-MDQ-01');

-- 7. Cambios de Estado (historial completo: Creado -> Cerrado -> Enviado para los 10 bolsines)

-- Bolsín 101
INSERT INTO cambios_estado_bolsin (fecha_hora_inicio, fecha_hora_fin, estado, bolsin_numero)
VALUES ('2026-11-22 08:01:00', '2026-11-22 09:01:00', 'Creado', 101);
INSERT INTO cambios_estado_bolsin (fecha_hora_inicio, fecha_hora_fin, estado, bolsin_numero)
VALUES ('2026-11-22 09:01:00', '2026-11-22 10:01:00', 'Cerrado', 101);
INSERT INTO cambios_estado_bolsin (fecha_hora_inicio, fecha_hora_fin, estado, bolsin_numero)
VALUES ('2026-11-22 10:01:00', NULL, 'Enviado', 101);

-- Bolsín 102
INSERT INTO cambios_estado_bolsin (fecha_hora_inicio, fecha_hora_fin, estado, bolsin_numero)
VALUES ('2026-11-22 08:02:00', '2026-11-22 09:02:00', 'Creado', 102);
INSERT INTO cambios_estado_bolsin (fecha_hora_inicio, fecha_hora_fin, estado, bolsin_numero)
VALUES ('2026-11-22 09:02:00', '2026-11-22 10:02:00', 'Cerrado', 102);
INSERT INTO cambios_estado_bolsin (fecha_hora_inicio, fecha_hora_fin, estado, bolsin_numero)
VALUES ('2026-11-22 10:02:00', NULL, 'Enviado', 102);

-- Bolsín 103
INSERT INTO cambios_estado_bolsin (fecha_hora_inicio, fecha_hora_fin, estado, bolsin_numero)
VALUES ('2026-11-22 08:03:00', '2026-11-22 09:03:00', 'Creado', 103);
INSERT INTO cambios_estado_bolsin (fecha_hora_inicio, fecha_hora_fin, estado, bolsin_numero)
VALUES ('2026-11-22 09:03:00', '2026-11-22 10:03:00', 'Cerrado', 103);
INSERT INTO cambios_estado_bolsin (fecha_hora_inicio, fecha_hora_fin, estado, bolsin_numero)
VALUES ('2026-11-22 10:03:00', NULL, 'Enviado', 103);

-- Bolsín 104
INSERT INTO cambios_estado_bolsin (fecha_hora_inicio, fecha_hora_fin, estado, bolsin_numero)
VALUES ('2026-11-22 08:04:00', '2026-11-22 09:04:00', 'Creado', 104);
INSERT INTO cambios_estado_bolsin (fecha_hora_inicio, fecha_hora_fin, estado, bolsin_numero)
VALUES ('2026-11-22 09:04:00', '2026-11-22 10:04:00', 'Cerrado', 104);
INSERT INTO cambios_estado_bolsin (fecha_hora_inicio, fecha_hora_fin, estado, bolsin_numero)
VALUES ('2026-11-22 10:04:00', NULL, 'Enviado', 104);

-- Bolsín 105
INSERT INTO cambios_estado_bolsin (fecha_hora_inicio, fecha_hora_fin, estado, bolsin_numero)
VALUES ('2026-11-22 08:05:00', '2026-11-22 09:05:00', 'Creado', 105);
INSERT INTO cambios_estado_bolsin (fecha_hora_inicio, fecha_hora_fin, estado, bolsin_numero)
VALUES ('2026-11-22 09:05:00', '2026-11-22 10:05:00', 'Cerrado', 105);
INSERT INTO cambios_estado_bolsin (fecha_hora_inicio, fecha_hora_fin, estado, bolsin_numero)
VALUES ('2026-11-22 10:05:00', NULL, 'Enviado', 105);

-- Bolsín 106
INSERT INTO cambios_estado_bolsin (fecha_hora_inicio, fecha_hora_fin, estado, bolsin_numero)
VALUES ('2026-11-22 08:06:00', '2026-11-22 09:06:00', 'Creado', 106);
INSERT INTO cambios_estado_bolsin (fecha_hora_inicio, fecha_hora_fin, estado, bolsin_numero)
VALUES ('2026-11-22 09:06:00', '2026-11-22 10:06:00', 'Cerrado', 106);
INSERT INTO cambios_estado_bolsin (fecha_hora_inicio, fecha_hora_fin, estado, bolsin_numero)
VALUES ('2026-11-22 10:06:00', NULL, 'Enviado', 106);

-- Bolsín 107
INSERT INTO cambios_estado_bolsin (fecha_hora_inicio, fecha_hora_fin, estado, bolsin_numero)
VALUES ('2026-11-22 08:07:00', '2026-11-22 09:07:00', 'Creado', 107);
INSERT INTO cambios_estado_bolsin (fecha_hora_inicio, fecha_hora_fin, estado, bolsin_numero)
VALUES ('2026-11-22 09:07:00', '2026-11-22 10:07:00', 'Cerrado', 107);
INSERT INTO cambios_estado_bolsin (fecha_hora_inicio, fecha_hora_fin, estado, bolsin_numero)
VALUES ('2026-11-22 10:07:00', NULL, 'Enviado', 107);

-- Bolsín 108
INSERT INTO cambios_estado_bolsin (fecha_hora_inicio, fecha_hora_fin, estado, bolsin_numero)
VALUES ('2026-11-22 08:08:00', '2026-11-22 09:08:00', 'Creado', 108);
INSERT INTO cambios_estado_bolsin (fecha_hora_inicio, fecha_hora_fin, estado, bolsin_numero)
VALUES ('2026-11-22 09:08:00', '2026-11-22 10:08:00', 'Cerrado', 108);
INSERT INTO cambios_estado_bolsin (fecha_hora_inicio, fecha_hora_fin, estado, bolsin_numero)
VALUES ('2026-11-22 10:08:00', NULL, 'Enviado', 108);

-- Bolsín 109
INSERT INTO cambios_estado_bolsin (fecha_hora_inicio, fecha_hora_fin, estado, bolsin_numero)
VALUES ('2026-11-22 08:09:00', '2026-11-22 09:09:00', 'Creado', 109);
INSERT INTO cambios_estado_bolsin (fecha_hora_inicio, fecha_hora_fin, estado, bolsin_numero)
VALUES ('2026-11-22 09:09:00', '2026-11-22 10:09:00', 'Cerrado', 109);
INSERT INTO cambios_estado_bolsin (fecha_hora_inicio, fecha_hora_fin, estado, bolsin_numero)
VALUES ('2026-11-22 10:09:00', NULL, 'Enviado', 109);

-- Bolsín 110
INSERT INTO cambios_estado_bolsin (fecha_hora_inicio, fecha_hora_fin, estado, bolsin_numero)
VALUES ('2026-11-22 08:10:00', '2026-11-22 09:10:00', 'Creado', 110);
INSERT INTO cambios_estado_bolsin (fecha_hora_inicio, fecha_hora_fin, estado, bolsin_numero)
VALUES ('2026-11-22 09:10:00', '2026-11-22 10:10:00', 'Cerrado', 110);
INSERT INTO cambios_estado_bolsin (fecha_hora_inicio, fecha_hora_fin, estado, bolsin_numero)
VALUES ('2026-11-22 10:10:00', NULL, 'Enviado', 110);

-- Bolsín 111
INSERT INTO cambios_estado_bolsin (fecha_hora_inicio, fecha_hora_fin, estado, bolsin_numero)
VALUES ('2026-03-07 10:01:00', '2026-03-07 11:01:00', 'Creado', 111);
INSERT INTO cambios_estado_bolsin (fecha_hora_inicio, fecha_hora_fin, estado, bolsin_numero)
VALUES ('2026-03-07 11:01:00', '2026-03-07 12:31:00', 'Cerrado', 111);
INSERT INTO cambios_estado_bolsin (fecha_hora_inicio, fecha_hora_fin, estado, bolsin_numero)
VALUES ('2026-03-07 12:31:00', NULL, 'Enviado', 111);

-- Bolsín 112
INSERT INTO cambios_estado_bolsin (fecha_hora_inicio, fecha_hora_fin, estado, bolsin_numero)
VALUES ('2026-03-07 10:02:00', '2026-03-07 11:02:00', 'Creado', 112);
INSERT INTO cambios_estado_bolsin (fecha_hora_inicio, fecha_hora_fin, estado, bolsin_numero)
VALUES ('2026-03-07 11:02:00', '2026-03-07 12:32:00', 'Cerrado', 112);
INSERT INTO cambios_estado_bolsin (fecha_hora_inicio, fecha_hora_fin, estado, bolsin_numero)
VALUES ('2026-03-07 12:32:00', NULL, 'Enviado', 112);

-- Bolsín 113
INSERT INTO cambios_estado_bolsin (fecha_hora_inicio, fecha_hora_fin, estado, bolsin_numero)
VALUES ('2026-03-07 10:03:00', '2026-03-07 11:03:00', 'Creado', 113);
INSERT INTO cambios_estado_bolsin (fecha_hora_inicio, fecha_hora_fin, estado, bolsin_numero)
VALUES ('2026-03-07 11:03:00', '2026-03-07 12:33:00', 'Cerrado', 113);
INSERT INTO cambios_estado_bolsin (fecha_hora_inicio, fecha_hora_fin, estado, bolsin_numero)
VALUES ('2026-03-07 12:33:00', NULL, 'Enviado', 113);