insert into Torneo (Nombre,Fecha,Premio) values ('Torneo Inagural','2024-10-10', 500.00);
insert into Torneo (Nombre,Fecha,Premio) values ('Torneo de Primavera','2022-5-09', 1500.00);
insert into Torneo (Nombre,Fecha,Premio) values ('Torneo de Verano','2026-12-11', 3330.00);
insert into Torneo (Nombre,Fecha,Premio) values ('Torneo de Invierno','2026-8-22', 10000.00);


insert into Equipo (id_equipo, Premios, Nombre_equipo,FK_Torneo) values (1, 2, 'Los Matados',1);
insert into Equipo (id_equipo, Premios, Nombre_equipo) values (2, 20, 'Los hechos polvo');
insert into Equipo (id_equipo, Premios, Nombre_equipo) values (3, 100, 'Los borrachos');

insert into Jugador (Nombre, Edad, Nivel, FK_Equipo) values ('Alejandro', 34, 'Medio', 1);
insert into Jugador (Nombre, Edad, Nivel, FK_Equipo) values ('Pablo', 15, 'Alto', 2);
insert into Jugador (Nombre, Edad, Nivel, FK_Equipo) values ('David', 18, 'Alto', 1);
insert into Jugador (Nombre, Edad, Nivel, FK_Equipo) values ('Alberto', 25, 'Alto', 2);
insert into Jugador (Nombre, Edad, Nivel, FK_Equipo) values ('Maria', 25, 'Alto', 3);
insert into Jugador (Nombre, Edad, Nivel) values ('AAA', 25, 'Alto');
insert into Jugador (Nombre, Edad, Nivel) values ('BBB', 25, 'Alto');

insert into Clase (dia, hora) values ('Lunes', '16:30');
insert into Clase (dia, hora) values ('Martes', '16:30');
insert into Clase (dia, hora) values ('Miércoles', '18:30');

insert into Enmarca (jugador_id, clase_id) values (1, 1);
insert into Enmarca (jugador_id, clase_id) values (2, 1);
insert into Enmarca (jugador_id, clase_id) values (3, 1);
insert into Enmarca (jugador_id, clase_id) values (4, 1);
insert into Enmarca (jugador_id, clase_id) values (1, 2);
insert into Enmarca (jugador_id, clase_id) values (3, 3);
insert into Enmarca (jugador_id, clase_id) values (4, 2);
insert into Enmarca (jugador_id, clase_id) values (5, 3);



insert into usuario (usuario,password) values ('marcos','$2a$12$3fP2T8dyuujgKtm9Qi.W7.zMN510cFRL0jAdtZkK9IfUr8SMMF6ia');
insert into usuario (usuario,password) values ('alejandro','$2a$12$ULYuqubmC8lMaacQVs2YD.ChrGrHmfAqR.O3BO7dCB1yGim2ObTdO');