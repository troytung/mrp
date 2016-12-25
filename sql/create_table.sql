--DROP DATABASE SPIDERMAN
CREATE DATABASE SPIDERMAN CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

USE SPIDERMAN
--DROP TABLE TOPIC
CREATE TABLE TOPIC (
    ID bigint(20) unsigned AUTO_INCREMENT NOT NULL,
    SITE varchar(10) NOT NULL,
    FORUM_ID varchar(10) NOT NULL,
    BRAND varchar(10) NULL,
    MODEL varchar(20) NULL,
    TOPIC varchar(100) NOT NULL,
    TOPIC_ID varchar(20) NOT NULL,
    LASTEST_POSTER varchar(20) NOT NULL,
    REPLY_CNT int(10) unsigned NOT NULL,
    VIEW_CNT int(10) unsigned NOT NULL,
    MODIFY_DATE datetime NOT NULL,
    PRIMARY KEY(SITE, FORUM_ID, TOPIC_ID),
    UNIQUE KEY `TOPIC_UNIQUE_ID` (ID)
)
ENGINE = InnoDB DEFAULT CHARSET=utf8

--DROP TABLE POST
CREATE TABLE POST (
    ID 	bigint(20) unsigned AUTO_INCREMENT NOT NULL,
    HASH_ID varchar(60) NOT NULL,
    SITE varchar(10) NOT NULL,
    TOPIC_ID varchar(20) NOT NULL,
    POST_ID varchar(20) NULL,
    REPLY tinyint(1) NOT NULL,
    POST_NUMBER int(10) unsigned NOT NULL,
    TEXT mediumtext NOT NULL,
    POSTER varchar(20) NOT NULL,
    POSTER_ID varchar(20) NOT NULL,
    POST_DATE datetime NOT NULL,
    PRIMARY KEY(HASH_ID),
    UNIQUE KEY `POST_UNIQUE_ID` (ID)
)
ENGINE = InnoDB DEFAULT CHARSET=utf8

--DROP TABLE CATEGORY
CREATE TABLE CATEGORY (
    ID bigint(20) unsigned AUTO_INCREMENT NOT NULL,
    NAME varchar(50) NOT NULL,
    DISPLAY_QUERY varchar(400) NOT NULL,
    QUERY varchar(400) NOT NULL,
    SEPARATED_QUERY varchar(800) NOT NULL,
    COLOR varchar(6) NULL,
    EMAIL bit(1) NOT NULL DEFAULT 0,
    PRIMARY KEY(ID)
)
ENGINE = InnoDB DEFAULT CHARSET=utf8

--ALTER TABLE CATEGORY ADD COLUMN EMAIL bit(1) NOT NULL DEFAULT 0

--DROP TABLE USER
CREATE TABLE USER (
    USER_ID varchar(20) NOT NULL,
    USER_NAME varchar(30) NOT NULL,
    EMAIL varchar(50) NOT NULL,
    SALT varchar(128) NOT NULL,
    PASSWORD varchar(256) NOT NULL,
    ADMIN bit(1) NOT NULL,
    ACTIVE bit(1) NOT NULL,
    RECEIVE_EMAIL bit(1) NOT NULL DEFAULT 0,
    CREATE_DATE datetime NOT NULL,
    MODIFY_DATE datetime NULL,
    PRIMARY KEY(USER_ID)
)
ENGINE = InnoDB DEFAULT CHARSET=utf8

--ALTER TABLE USER ADD COLUMN RECEIVE_EMAIL bit(1) NOT NULL DEFAULT 0