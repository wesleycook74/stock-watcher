CREATE TABLE STOCK_WATCH (
    id bigserial primary key,
    stock_symbol varchar,
    created_date timestamp with time zone
);