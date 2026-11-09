create table books (
    isbn  text primary key,
    title text not null
);

insert into books (isbn, title) values
    ('9977+9-898989', 'El quijote'),
    ('978-0-00-000000-1', 'Cien años de soledad');
