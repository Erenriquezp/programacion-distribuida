-- Crear tabla de autores
create table authors1 (
    id serial not null primary key,
    name varchar(255)
);

insert into authors1 (name) values ('Gabriel Garcia Marquez');
insert into authors1 (name) values ('Jorge Luis Borges');
insert into authors1 (name) values ('Mario Vargas Llosa');