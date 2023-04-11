create database "order"
    with owner postgres;

create database seller
    with owner postgres;


create table order.batch_job_instance
(
    job_instance_id bigint       not null
        primary key,
    version         bigint,
    job_name        varchar(100) not null,
    job_key         varchar(32)  not null,
    constraint job_inst_un
        unique (job_name, job_key)
);

alter table order.batch_job_instance
    owner to postgres;


-- auto-generated definition
create table order.batch_job_execution
(
    job_execution_id           bigint    not null
        primary key,
    version                    bigint,
    job_instance_id            bigint    not null
        constraint job_inst_exec_fk
            references batch_job_instance,
    create_time                timestamp not null,
    start_time                 timestamp,
    end_time                   timestamp,
    status                     varchar(10),
    exit_code                  varchar(2500),
    exit_message               varchar(2500),
    last_updated               timestamp,
    job_configuration_location varchar(2500)
);

alter table order.batch_job_execution
    owner to postgres;


create table order.batch_job_execution_context
(
    job_execution_id   bigint        not null
        primary key
        constraint job_exec_ctx_fk
            references order.batch_job_execution,
    short_context      varchar(2500) not null,
    serialized_context text
);

alter table order.batch_job_execution_context
    owner to postgres;



create table order.batch_job_execution_params
(
    job_execution_id bigint       not null
        constraint job_exec_params_fk
            references batch_job_execution,
    type_cd          varchar(6)   not null,
    key_name         varchar(100) not null,
    string_val       varchar(250),
    date_val         timestamp,
    long_val         bigint,
    double_val       double precision,
    identifying      char         not null
);

alter table order.batch_job_execution_params
    owner to postgres;




-- auto-generated definition






-- auto-generated definition
create table order.batch_step_execution
(
    step_execution_id  bigint       not null
        primary key,
    version            bigint       not null,
    step_name          varchar(100) not null,
    job_execution_id   bigint       not null
        constraint job_exec_step_fk
            references order.batch_job_execution,
    start_time         timestamp    not null,
    end_time           timestamp,
    status             varchar(10),
    commit_count       bigint,
    read_count         bigint,
    filter_count       bigint,
    write_count        bigint,
    read_skip_count    bigint,
    write_skip_count   bigint,
    process_skip_count bigint,
    rollback_count     bigint,
    exit_code          varchar(2500),
    exit_message       varchar(2500),
    last_updated       timestamp
);

alter table order.batch_step_execution
    owner to postgres;




-- auto-generated definition
create table order.batch_step_execution_context
(
    step_execution_id  bigint        not null
        primary key
        constraint step_exec_ctx_fk
            references order.batch_step_execution,
    short_context      varchar(2500) not null,
    serialized_context text
);

alter table order.batch_step_execution_context
    owner to postgres;




create sequence order.batch_job_execution_seq;


create sequence order.batch_job_seq;


create sequence order.batch_step_execution_seq;