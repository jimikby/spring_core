CREATE TABLE CUSTOMER
(
CUSTOMER_ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
FIRST_NAME VARCHAR(255),
LAST_NAME VARCHAR(255),
BIRTHDAY TIMESTAMP,
EMAIL VARCHAR(255),
PASSWORD VARCHAR(255),
ROLE VARCHAR(255),
CONSTRAINT PK_CUSTOMER_ID PRIMARY KEY (CUSTOMER_ID),
CONSTRAINT UK_EMAIL UNIQUE (EMAIL)
);  

CREATE TABLE CUSTOMER_ACCOUNT
(
CUSTOMER_ACCOUNT_ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
CUSTOMER_ID INTEGER NOT NULL CONSTRAINT FK1_CUSTOMER_ID REFERENCES CUSTOMER(CUSTOMER_ID),
CUSTOMER_MONEY DOUBLE NOT NULL,
CONSTRAINT PK_CUSTOMER_ACCOUNT_ID PRIMARY KEY (CUSTOMER_ACCOUNT_ID)
);

CREATE TABLE COUNTER 
(
COUNTER_ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
TYPE_NAME VARCHAR(255),
TYPE_ID INTEGER NOT NULL,
COUNTER_NAME VARCHAR(255),
COUNTER_VALUE INTEGER NOT NULL,
CONSTRAINT PK_COUNTER_ID PRIMARY KEY (COUNTER_ID)
);

CREATE TABLE EVENT 
(
EVENT_ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
BASE_PRICE DOUBLE NOT NULL,
EVENT_NAME VARCHAR(255),
EVENT_RATING VARCHAR(255),
CONSTRAINT PK_EVENT_ID PRIMARY KEY (EVENT_ID),
CONSTRAINT UK_EVENT_NAME UNIQUE (EVENT_NAME)
);

CREATE TABLE EVENT_SCHEDULE
(
EVENT_SCHEDULE_ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
EVENT_DATE TIMESTAMP,
EVENT_ID INTEGER NOT NULL CONSTRAINT FK_EVENT_ID REFERENCES EVENT(EVENT_ID),
AUDITORIUM_ID INTEGER NOT NULL,
CONSTRAINT PK_EVENT_SCHDEULE_ID PRIMARY KEY (EVENT_SCHEDULE_ID)
);

CREATE TABLE TICKET
(
TICKET_ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
CUSTOMER_ID INTEGER NOT NULL CONSTRAINT FK_CUSTOMER_ID REFERENCES CUSTOMER(CUSTOMER_ID),
TICKET_COST DOUBLE NOT NULL,
SEAT INTEGER NOT NULL,
EVENT_SCHEDULE_ID INTEGER NOT NULL,
DISCOUNT DOUBLE NOT NULL,
CONSTRAINT PK_TICKET_ID PRIMARY KEY (TICKET_ID)

);
