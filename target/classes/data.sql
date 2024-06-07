insert into Torneo (Nombre,Fecha,Premio) values ('Torneo Inagural','2024-10-10', 500.00);
insert into Torneo (Nombre,Fecha,Premio) values ('Torneo de Primavera','2022-5-09', 1500.00);
insert into Torneo (Nombre,Fecha,Premio) values ('Torneo de Verano','2026-12-11', 3330.00);
insert into Torneo (Nombre,Fecha,Premio) values ('Torneo de Invierno','2026-8-22', 10000.00);


insert into Equipo (Premios, Nombre_equipo,FK_Torneo) values (2, 'Los Matados',1);
insert into Equipo (Premios, Nombre_equipo) values (20, 'Los hechos polvo');
insert into Equipo (Premios, Nombre_equipo) values (85, 'Los borrachos');

insert into Jugador (Nombre, Edad, Nivel, FK_Equipo) values ('Alejandro', 34, 'Medio', 1);
insert into Jugador (Nombre, Edad, Nivel, FK_Equipo) values ('Pablo', 15, 'Alto', 2);
insert into Jugador (Nombre, Edad, Nivel, FK_Equipo) values ('David', 18, 'Alto', 1);
insert into Jugador (Nombre, Edad, Nivel, FK_Equipo) values ('Alberto', 25, 'Alto', 2);
insert into Jugador (Nombre, Edad, Nivel, FK_Equipo) values ('Maria', 25, 'Alto', 3);
insert into Jugador (Nombre, Edad, Nivel, FK_Equipo) values ('Mario', 25, 'Medio',3);
insert into Jugador (Nombre, Edad, Nivel, FK_Equipo) values ('Isco', 25, 'Alto',1);
insert into Jugador (Nombre, Edad, Nivel, FK_Equipo) values ('Saleta', 17, 'Medio',3);
insert into Jugador (Nombre, Edad, Nivel, FK_Equipo) values ('Marcos', 16, 'Medio',1);
insert into Jugador (Nombre, Edad, Nivel, FK_Equipo) values ('Cristina', 15, 'Principiante',1);

insert into Jugador (Nombre, Edad, Nivel) values ('Alejandro Menendez', 16, 'Medio');
insert into Jugador (Nombre, Edad, Nivel) values ('Cristina Fernandez', 15, 'Principiante');
insert into Jugador (Nombre, Edad, Nivel) values ('Marcos Pintos', 16, 'Medio');
insert into Jugador (Nombre, Edad, Nivel) values ('Pedro Cuenca', 15, 'Principiante');

insert into Clase (dia, hora, Nivel) values ('Lunes', '16:30','Medio');
insert into Clase (dia, hora, Nivel) values ('Lunes', '16:30','Alto');
insert into Clase (dia, hora, Nivel) values ('Lunes', '18:30','Alto');
insert into Clase (dia, hora, Nivel) values ('Lunes', '19:30','Medio');
insert into Clase (dia, hora, Nivel) values ('Lunes', '20:30', 'Principiante');
insert into Clase (dia, hora, Nivel) values ('Martes', '16:30','Alto');
insert into Clase (dia, hora, Nivel) values ('Martes', '17:30','Alto');
insert into Clase (dia, hora, Nivel) values ('Martes', '18:30','Alto');
insert into Clase (dia, hora, Nivel) values ('Martes', '19:30','Medio');
insert into Clase (dia, hora, Nivel) values ('Miércoles', '18:30','Alto');
insert into Clase (dia, hora, Nivel) values ('Miércoles', '19:30','Alto');
insert into Clase (dia, hora, Nivel) values ('Miércoles', '20:30','Medio');
insert into Clase (dia, hora, Nivel) values ('Miércoles', '16:30','Medio');
insert into Clase (dia, hora, Nivel) values ('Jueves', '18:30','Alto');
insert into Clase (dia, hora, Nivel) values ('Jueves', '19:30','Alto');
insert into Clase (dia, hora, Nivel) values ('Jueves', '16:30','Medio');
insert into Clase (dia, hora, Nivel) values ('Jueves', '20:30','Medio');
insert into Clase (dia, hora, Nivel) values ('Jueves', '21:30', 'Principiante');
insert into Clase (dia, hora, Nivel) values ('Viernes', '16:30','Alto');
insert into Clase (dia, hora, Nivel) values ('Viernes', '18:30', 'Principiante');
insert into Clase (dia, hora, Nivel) values ('Viernes', '20:30', 'Principiante');
insert into Clase (dia, hora, Nivel) values ('Viernes', '21:30', 'Medio');
insert into Clase (dia, hora, Nivel) values ('Viernes', '14:30', 'Medio');


insert into Enmarca (jugador_id, clase_id) values (1, 4);
insert into Enmarca (jugador_id, clase_id) values (1, 9);
insert into Enmarca (jugador_id, clase_id) values (1, 12);
insert into Enmarca (jugador_id, clase_id) values (1, 13);
insert into Enmarca (jugador_id, clase_id) values (1, 17);


insert into Enmarca (jugador_id, clase_id) values (2, 2);
insert into Enmarca (jugador_id, clase_id) values (2, 3);
insert into Enmarca (jugador_id, clase_id) values (2, 6);
insert into Enmarca (jugador_id, clase_id) values (2, 7);
insert into Enmarca (jugador_id, clase_id) values (2, 8);

insert into Enmarca (jugador_id, clase_id) values (3, 2);
insert into Enmarca (jugador_id, clase_id) values (3, 3);


insert into Enmarca (jugador_id, clase_id) values (4, 2);
insert into Enmarca (jugador_id, clase_id) values (4, 3);

insert into Enmarca (jugador_id, clase_id) values (5, 2);
insert into Enmarca (jugador_id, clase_id) values (5, 3);





insert into usuario (usuario,password) values ('marcos','$2a$12$pxkU//pg10V55CwNOh/52.tCKg7SE/Y5vx6Dgs3v0wA.FaSHojbuC');
insert into usuario (usuario,password,jugador_id) values ('alejandro','$2a$12$rgiuFep5BGVMA3Y8.8XVAOZ28X1iORddXe/.QxTv742QQ2VXoxFkS',1);
insert into usuario (usuario,password,jugador_id) values ('pablo','$2a$12$B2VWFKuxS1gnrgd0RgWcBOc92m7Zi6yiWP8N/Mt69yKALUE98.wZ.',2);