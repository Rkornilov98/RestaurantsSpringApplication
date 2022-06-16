create sequence SEQ$RESTAURANTS
increment 1
minvalue 1
maxvalue 9223372036854775807
start 1
cache 1;

/* Table: RESTAURANTS                                           */

create sequence SEQ$USER_RESTAURANTS
increment 1
minvalue 1
maxvalue 9223372036854775807
start 1
cache 1;

create table RESTAURANTS (
   ID                   INT8                 not null default nextval('SEQ$RESTAURANTS'::regclass),
   NAME                 VARCHAR(100)         not null,
   DISHES               VARCHAR(300)         not null,
   constraint PK_RESTAURANTS primary key (ID)
);

comment on table RESTAURANTS is
'Restaurants Application Database';

comment on column RESTAURANTS.ID is
'Идентификатор';

comment on column RESTAURANTS.NAME is
'Название';

comment on column RESTAURANTS.DISHES is
'Список блюд';


/* Table: USERS                                                 */

create table USERS (
   USERNAME             VARCHAR(30)          not null,
   PASSWORD             VARCHAR(255)         not null,
   ENABLED              BOOL                 null default true,
   constraint PK_USERS primary key (USERNAME)
);

comment on table USERS is
'USERS for Spring Security Test';

comment on column USERS.USERNAME is
'Имя пользователя';

comment on column USERS.PASSWORD is
'Пароль пользователя';

comment on column USERS.ENABLED is
'Вкл/выкл пользователя';

/*==============================================================*/
/* Index: U_ID_USERS                                            */
/*==============================================================*/
create unique index U_ID_USERS on USERS (
USERNAME
);

/* Table: AUTHORITIES                                           */

create table AUTHORITIES (
   USERNAME             VARCHAR(30)          null,
   AUTHORITY            VARCHAR(50)          not null
);

comment on table AUTHORITIES is
'Authorities for Spring Security test';

comment on column AUTHORITIES.USERNAME is
'Имя пользователя';

comment on column AUTHORITIES.AUTHORITY is
'Разрешения пользователя';

/*==============================================================*/
/* Index: ix_auth_username                                      */
/*==============================================================*/
create unique index ix_auth_username on AUTHORITIES (
USERNAME,
AUTHORITY
);

alter table AUTHORITIES
   add constraint FK_AUTHORITIES_U foreign key (USERNAME)
      references USERS (USERNAME)
      on delete restrict on update restrict;


* Table: USER_RESTAURANTS                                      */


create table USER_RESTAURANTS (
   ID                   INT8                 not null default nextval('SEQ$USER_RESTAURANTS'::regclass),
   USERNAME             VARCHAR(30)          not null,
   RESTAURANTS_ID       INT8                 not null,
   constraint PK_USER_RESTAURANTS primary key (ID)
);

comment on table USER_RESTAURANTS is
'User selected restaurant

';

comment on column USER_RESTAURANTS.ID is
'Идентификатор записи';

comment on column USER_RESTAURANTS.USERNAME is
'Имя пользователя';

comment on column USER_RESTAURANTS.RESTAURANTS_ID is
'Идентификатор ресторана';

/*==============================================================*/
/* Index: IX_FK_USER_RESTAURANTS_RES                            */
/*==============================================================*/
create  index IX_FK_USER_RESTAURANTS_RES on USER_RESTAURANTS (
RESTAURANTS_ID
);

/*==============================================================*/
/* Index: IX_FK_USER_RESTAURANTS_U                              */
/*==============================================================*/
create  index IX_FK_USER_RESTAURANTS_U on USER_RESTAURANTS (
USERNAME
);

alter table USER_RESTAURANTS
   add constraint FK_USER_RESTAURANTS_RES foreign key (RESTAURANTS_ID)
      references RESTAURANTS (ID)
      on delete cascade on update restrict;

alter table USER_RESTAURANTS
   add constraint FK_USER_RESTAURANTS_U foreign key (USERNAME)
      references USERS (USERNAME)
      on delete restrict on update restrict;

/* Create Admin and User/
INSERT INTO users (username, password) values ('admin', '$2a$12$GS48oVTSLDT.6eCieWr7de89bGeuPTV.oduFDtSUb/WUgFS.TJr.i'); /* login:admin password:admin*/
INSERT INTO users (username, password) values ('user', '$2a$12$ZUZ0JxibgaxrWvz8dgY50u5ptPjI5xY3pf3.zvce2xbhK7Q8R/6aS'); /* login: user password: user */
