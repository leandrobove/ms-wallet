set foreign_key_checks = 0;

delete from transaction;
delete from account;
delete from client;

set foreign_key_checks = 1;

alter table transaction auto_increment = 1;
alter table account auto_increment = 1;
alter table client auto_increment = 1;

# client
insert into client(id, name, email, cpf, created_at, updated_at) values('45a7e9406f6b4c7f9710be173816fb11', 'Maria Silva', 'maria@gmail.com', '01625118090', utc_timestamp, utc_timestamp);
insert into client(id, name, email, cpf, created_at, updated_at) values('5ff7cc10e1ed49f080915e95d606d84f', 'João Silva', 'joao@gmail.com', '18426303005', utc_timestamp, utc_timestamp);

# account
insert into account(id, balance, created_at, updated_at, client_id) values('bdd17153c64746d6bf340f3e8df6169f', 100.00, utc_timestamp, utc_timestamp, '45a7e9406f6b4c7f9710be173816fb11');
insert into account(id, balance, created_at, updated_at, client_id) values('5066e0290fbf4c6494296d2608f05135', 0.00, utc_timestamp, utc_timestamp, '5ff7cc10e1ed49f080915e95d606d84f');