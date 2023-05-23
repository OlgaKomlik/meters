create table if not exists public.c_deal_type
(
    deal_type_id bigserial
        primary key
        unique,
    type_name    varchar(30)  not null,
    created      timestamp(6) not null,
    changed      timestamp(6) not null,
    is_deleted   boolean      not null
);

alter table public.c_deal_type
    owner to postgres;

create sequence public.managers_id_seq;

create sequence public.persons_id_seq;

create sequence public.companies_id_seq;

create sequence public.real_estates_id_seq;

create sequence public.rents_id_seq;

create sequence public.sales_id_seq;

create sequence public.deals_id_seq;

create sequence public.roles_role_id_seq;

create sequence public.l_managers_roles_id_seq;

create sequence public.locations_location_id_seq;

create sequence public.object_type_object_type_id_seq;

create table if not exists public.c_object_type
(
    object_type_id   bigint default nextval('object_type_object_type_id_seq'::regclass) not null
        constraint object_type_pkey
            primary key
        constraint object_type_object_type_id_key
            unique,
    object_type_name varchar(30)                                                        not null,
    created          timestamp(6)                                                       not null,
    changed          timestamp(6)                                                       not null,
    is_deleted       boolean                                                            not null
);

alter table public.c_object_type
    owner to postgres;

create index object_type_object_type_name_index
    on public.c_object_type (object_type_name);

create table if not exists public.roles
(
    role_id    bigserial
        primary key
        unique,
    role_name  varchar(30)  not null
        unique,
    created    timestamp(6) not null,
    changed    timestamp(6) not null,
    is_deleted boolean      not null
);

create unique index roles_role_name_uindex
    on public.roles (role_name);

alter table public.roles
    owner to postgres;

create table if not exists public.managers
(
    manager_id        bigint default nextval('managers_id_seq'::regclass) not null
        primary key
        unique,
    manager_name      varchar(20)                                         not null,
    surname           varchar(30)                                         not null,
    birth_date        timestamp(6)                                        not null,
    created           timestamp(6)                                        not null,
    changed           timestamp(6)                                        not null,
    is_deleted        boolean                                             not null,
    email             varchar(60)                                         not null
        unique,
    manager_password  varchar(100)                                        not null,
    gender            varchar(20)                                         not null,
    manager_full_name varchar(50)                                         not null
);

alter table public.managers
    owner to postgres;

create index managers_manager_full_name_index
    on public.managers (manager_full_name);

create index managers_surname_index
    on public.managers (surname);

create table if not exists public.locations
(
    location_id bigserial
        primary key
        unique,
    city        varchar(50)  not null,
    district    varchar(50)  not null,
    region      varchar(100) not null,
    created     timestamp(6) not null,
    changed     timestamp(6) not null,
    is_deleted  boolean      not null,
    country     varchar(50)  not null
);


alter table public.locations
    owner to postgres;

create index locations_region_index
    on public.locations (region);

create index locations_city_index
    on public.locations (city);

create index locations_city_district_index
    on public.locations (city, district);

create table if not exists public.companies
(
    company_id          bigint default nextval('companies_id_seq'::regclass) not null
        primary key,
    company_name        varchar(100)                                         not null,
    unp_num             varchar(30)                                          not null
        unique,
    director_full_name  varchar(100)                                         not null,
    phone_num           varchar(20)                                          not null,
    created             timestamp(6)                                         not null,
    changed             timestamp(6)                                         not null,
    is_deleted          boolean                                              not null,
    date_create_company timestamp(6)                                         not null,
    address             varchar(100)                                         not null,
    director_name       varchar(30),
    director_surname    varchar(30),
    checking_account    varchar(50)                                          not null,
    bank_name           varchar(50)                                          not null
);

alter table public.companies
    owner to postgres;

create index companies_company_name_index
    on public.companies (company_name);

create unique index companies_company_id_uindex
    on public.companies (company_id);

create unique index companies_unp_num_uindex
    on public.companies (unp_num);

create table if not exists public.persons
(
    person_id        bigint default nextval('persons_id_seq'::regclass) not null
        primary key,
    person_name      varchar(30)                                        not null,
    surname          varchar(30)                                        not null,
    birth_date       timestamp(6)                                       not null,
    passport_num     varchar(20)                                        not null
        unique,
    phone_num        varchar(20)                                        not null,
    created          timestamp(6)                                       not null,
    changed          timestamp(6)                                       not null,
    is_deleted       boolean                                            not null,
    person_full_name varchar(100)                                       not null
);

alter table public.persons
    owner to postgres;

create index persons_surname_index
    on public.persons (surname);

create unique index persons_id_index
    on public.persons (person_id);

create unique index persons_passport_num_uindex
    on public.persons (passport_num);

create index persons_person_full_name_index
    on public.persons (person_full_name);

create table if not exists public.real_estates
(
    real_estate_id    bigint default nextval('real_estates_id_seq'::regclass) not null
        primary key,
    square            integer                                                 not null,
    rooms             integer                                                 not null,
    floors            integer                                                 not null,
    garden_square     integer,
    garage            boolean,
    address           varchar(30)                                             not null,
    location_id       bigint                                                  not null
        constraint real_estates_locations_location_id_fk
            references public.locations,
    owner_person_id   bigint
        constraint real_estates_persons_id_fk
            references public.persons,
    created           timestamp(6)                                            not null,
    changed           timestamp(6)                                            not null,
    is_deleted        boolean                                                 not null,
    object_type_id    bigint                                                  not null
        constraint real_estates_c_object_type_object_type_id_fk
            references public.c_object_type,
    owner_company_id  bigint
        constraint real_estates_companies_id_fk
            references public.companies,
    owner_client_type varchar(30)                                             not null
);

alter table public.real_estates
    owner to postgres;

create unique index real_estates_id_uindex
    on public.real_estates (real_estate_id);

create index real_estates_location_id_index
    on public.real_estates (location_id);

create index real_estates_location_id_type_object_index
    on public.real_estates (location_id, object_type_id);

create index real_estates_type_id_index
    on public.real_estates (object_type_id);

create table if not exists public.rents
(
    rent_id        bigint default nextval('rents_id_seq'::regclass) not null
        primary key,
    real_estate_id bigint                                           not null
        constraint rents_real_estates_id_fk
            references public.real_estates,
    rent_per_month numeric(10, 2)                                   not null,
    created        timestamp(6)                                     not null,
    changed        timestamp(6)                                     not null,
    is_deleted     boolean                                          not null,
    min_period     integer
);

alter table public.rents
    owner to postgres;

create index rents_id_index
    on public.rents (rent_id);

create index rents_id_real_estate_index
    on public.rents (real_estate_id);

create index rents_rent_per_month_index
    on public.rents (rent_per_month);

create table if not exists public.sales
(
    sale_id        bigint default nextval('sales_id_seq'::regclass) not null
        primary key,
    real_estate_id bigint                                           not null
        constraint sales_real_estates_id_fk
            references public.real_estates,
    price          numeric(10, 2)                                   not null,
    created        timestamp(6)                                     not null,
    changed        timestamp(6)                                     not null,
    is_deleted     boolean                                          not null
);

alter table public.sales
    owner to postgres;

create index sales_id_real_estate_index
    on public.sales (real_estate_id);

create unique index sales_sale_id_uindex
    on public.sales (sale_id);

create table if not exists public.deals
(
    deal_id           bigint default nextval('deals_id_seq'::regclass) not null
        primary key,
    owner_person_id   bigint
        constraint deals_persons_id_fk_2
            references public.persons,
    owner_company_id  bigint
        constraint deals_companies_id_fk_2
            references public.companies,
    buyer_company_id  bigint
        constraint deals_companies_id_fk
            references public.companies,
    amount            numeric(10, 2)                                   not null,
    manager_id        bigint                                           not null
        constraint deals_managers_id_fk
            references public.managers,
    fee               numeric(10, 2)                                   not null,
    created           timestamp(6)                                     not null,
    changed           timestamp(6)                                     not null,
    is_deleted        boolean                                          not null,
    owner_client_type varchar(30)                                      not null,
    buyer_client_type varchar(30)                                      not null,
    buyer_person_id   bigint
        constraint deals_persons_id_fk
            references public.persons,
    deal_type_id      bigint                                           not null
        constraint deals_c_deal_type_deal_type_id_fk
            references public.c_deal_type,
    rent_id           bigint
        constraint deals_rents_id_fk
            references public.rents,
    sale_id           bigint
        constraint deals_sales_id_fk
            references public.sales,
    deal_date         timestamp(6)                                     not null
);

alter table public.deals
    owner to postgres;

create index deals_id_index
    on public.deals (deal_id);

create index deals_id_manager_fee_index
    on public.deals (manager_id, fee);

create index deals_amount_index
    on public.deals (amount);

create index deals_deal_type_index
    on public.deals (deal_type_id);

create index deals_manager_id_index
    on public.deals (manager_id);

create index c_deal_type_type_name_index
    on public.c_deal_type (type_name);

create table if not exists public.l_managers_roles
(
    id          bigint default nextval('l_managers_roles_id_seq'::regclass) not null
        primary key
        unique,
    manager_id bigint                  not null
        constraint l_managers_roles_managers_manager_id_fk
            references public.managers,
    role_id    bigint                  not null
        constraint l_managers_roles_roles_role_id_fk
            references public.roles,
    created    timestamp default now() not null
);

alter table public.l_managers_roles
    owner to postgres;

create index l_managers_roles_manager_id_index
    on public.l_managers_roles (manager_id);

create index l_managers_roles_role_id_index
    on public.l_managers_roles (role_id);

