create table client(
    id varchar(32) not null primary key,
    name varchar(255) not null,
    email varchar(255) not null,
    cpf varchar(11) not null,
    created_at timestamp not null,
    updated_at timestamp not null,
    unique(email, cpf)
);

create table account(
    id varchar(32) not null primary key,
    balance decimal(15,2) not null,
    created_at timestamp not null,
    updated_at timestamp not null,
    client_id varchar(32) not null,
    foreign key (client_id) references client(id)
);

create table transaction(
    id varchar(32) not null primary key,
    amount decimal(15,2) not null,
    created_at timestamp not null,
    account_from_id varchar(32) not null,
    account_to_id varchar(32) not null,
    foreign key(account_from_id) references account(id),
    foreign key(account_to_id) references account(id)
);