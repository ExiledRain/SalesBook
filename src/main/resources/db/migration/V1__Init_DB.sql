create sequence hibernate_sequence start 1 increment 1;

create table sale (
    id int8 not null,
     cat varchar(255),
      completed boolean not null,
       description varchar(255),
        email varchar(255),
         name varchar(255),
          total_cost int4 not null,
           primary key (id));