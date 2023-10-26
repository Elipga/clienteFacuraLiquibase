create table facturas(
    codFactura varchar(50) primary key,
    total decimal(10,2) not null,
    mes tinyint not null,
    anyo tinyint not null,
    dni char(9) not null,
    foreign key (dni) references clientes (dni)
    );