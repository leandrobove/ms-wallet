create table client(
    id varchar(36) not null primary key,
    name varchar(255) not null,
    email varchar(255) not null,
    created_at timestamp not null,
    updated_at timestamp not null,
    unique(email)
);

create table account(
    id varchar(36) not null primary key,
    balance decimal(15,2) not null,
    created_at timestamp not null,
    updated_at timestamp not null,
    client_id varchar(36) not null,
    foreign key (client_id) references client(id)
);

create table transaction(
    id varchar(36) not null primary key,
    amount decimal(15,2) not null,
    created_at timestamp not null,
    account_from_id varchar(36) not null,
    account_to_id varchar(36) not null,
    foreign key(account_from_id) references account(id),
    foreign key(account_to_id) references account(id)
);