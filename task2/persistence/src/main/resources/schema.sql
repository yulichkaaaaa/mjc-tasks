CREATE TABLE gift_certificate
(
    id               bigint(20) auto_increment primary key,
    name             varchar(40) not null,
    description      varchar(200) not null,
    price            decimal not null,
    duration         int,
    create_date      datetime,
    last_update_date datetime
);

CREATE TABLE tag
(
    id bigint(20) auto_increment primary key,
    name varchar(40) not null
);

CREATE TABLE tag_m2m_gift_certificate
(
    id             bigint(20) auto_increment primary key ,
    gift_certificate_id bigint(20) references gift_certificate (id),
    tag_id         bigint(20) references tag (id)
)


