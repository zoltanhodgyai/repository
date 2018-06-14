drop table address if exists;
drop table customer if exists;
drop table location if exists;
drop table order_detail if exists;
drop table order_table if exists;
drop table product if exists;
drop table product_category if exists;
drop table revenue if exists;
drop table shop_order if exists;
drop table stock if exists;
drop table supplier if exists;

drop sequence if exists hibernate_sequence;

create sequence hibernate_sequence start with 1 increment by 1;

create table address (
  id integer not null,
  city varchar(255),
  country varchar(255),
  county varchar(255),
  street_address varchar(255),
  primary key (id));

create table customer (
  id integer not null,
  first_name varchar(255),
  last_name varchar(255),
  password varchar(255),
  username varchar(255),
  primary key (id));

create table location (
  id integer not null,
  name varchar(255),
  address integer,
  primary key (id));

create table order_detail (
  id integer not null,
  quantity integer,
  order_number integer not null,
  product_number integer not null,
  primary key (id));

create table order_table (
  id integer not null,
  order_date_time timestamp,
  address integer not null,
  customer integer not null,
  shipped_from integer not null,
  primary key (id));

create table product (
  id integer not null,
  description varchar(255),
  name varchar(255),
  price decimal(19,2),
  weight double,
  product_category integer not null,
  supplier integer not null,
  primary key (id));

create table product_category (
  id integer not null,
  description varchar(255),
  name varchar(255),
  primary key (id));

create table revenue (
  id integer not null,
  date timestamp,
  sum decimal(19,2),
  location integer,
  primary key (id));

create table shop_order (
  id integer not null,
  address_city varchar(255),
  address_country varchar(255),
  address_county varchar(255),
  address_number integer,
  address_street varchar(255),
  customer integer,
  shipped_from integer,
  primary key (id));

create table stock (
  id integer not null,
  quantity integer,
  location_number integer not null,
  product_number integer not null,
  primary key (id));

create table supplier (
  id integer not null,
  name varchar(255),
  primary key (id));
