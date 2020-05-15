DROP TABLE IF EXISTS AIRPLANE_ASSESSMENTS;
DROP TABLE IF EXISTS CAR_ASSESSMENTS;
DROP TABLE IF EXISTS ASSESSED_VALUE;
DROP TABLE IF EXISTS AIRPLANE;
DROP TABLE IF EXISTS CAR;

create table CAR (
  id IDENTITY primary key,
  brand VARCHAR2(150),
  model VARCHAR2(200),
  power DOUBLE,
  year_of_issue YEAR
);

create table AIRPLANE (
  id IDENTITY primary key,
  brand VARCHAR2(150),
  model VARCHAR2(200),
  year_of_issue YEAR,
  manufacturer VARCHAR2(500),
  fuel_capacity INT,
  seats INT
);

create table ASSESSED_VALUE (
    id IDENTITY primary key,
    assessed_value  DEC(20),
    assessment_date TIMESTAMP
);

create table CAR_ASSESSMENTS (
    CAR_ID BIGINT not null,
    ASSESSMENTS_ID BIGINT not null,
    primary key (CAR_ID, ASSESSMENTS_ID),
    constraint assessment_car_idx
        foreign key (ASSESSMENTS_ID) references ASSESSED_VALUE (ID),
    constraint car_idx
        foreign key (CAR_ID) references CAR (ID) on delete cascade
);

create table AIRPLANE_ASSESSMENTS (
    AIRPLANE_ID    BIGINT not null,
    ASSESSMENTS_ID BIGINT not null,
    primary key (AIRPLANE_ID, ASSESSMENTS_ID),
    constraint airplane_idx
        foreign key (AIRPLANE_ID) references AIRPLANE (ID),
    constraint assessment_airplane_idx
        foreign key (ASSESSMENTS_ID) references ASSESSED_VALUE (ID) on delete cascade
);