create table clientes(
    dni char(9) primary key,
    nombre varchar(50),
    fechaNac date,
    pais varchar(50),
    premium boolean
    );