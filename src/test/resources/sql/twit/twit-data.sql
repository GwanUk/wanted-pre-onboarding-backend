DROP TABLE IF EXISTS TWIT;
DROP TABLE IF EXISTS MEMBER;

CREATE TABLE MEMBER
(
    MEMBER_ID BIGINT AUTO_INCREMENT,
    EMAIL     VARCHAR(255),
    PASSWORD  VARCHAR(255),
    CONSTRAINT MEMBER_PK PRIMARY KEY (MEMBER_ID)
);

CREATE TABLE TWIT
(
    TWIT_ID   BIGINT AUTO_INCREMENT,
    CONTENT   TEXT,
    MEMBER_ID BIGINT,
    CONSTRAINT TWIT_PK PRIMARY KEY (TWIT_ID),
    CONSTRAINT TWIT_FK_01 FOREIGN KEY (MEMBER_ID) REFERENCES MEMBER (MEMBER_ID) ON DELETE SET NULL
);

INSERT INTO MEMBER(EMAIL, PASSWORD) VALUES('user1@naver.com', 'user1234');
INSERT INTO MEMBER(EMAIL, PASSWORD) VALUES('user2@naver.com', 'user1234');
INSERT INTO MEMBER(EMAIL, PASSWORD) VALUES('user3@naver.com', 'user1234');

INSERT INTO TWIT(CONTENT, MEMBER_ID) VALUES('user1 writing test a', 1);
INSERT INTO TWIT(CONTENT, MEMBER_ID) VALUES('user1 writing test b', 1);
INSERT INTO TWIT(CONTENT, MEMBER_ID) VALUES('user2 writing test c', 2);
INSERT INTO TWIT(CONTENT, MEMBER_ID) VALUES('user2 writing test d', 2);
INSERT INTO TWIT(CONTENT, MEMBER_ID) VALUES('user3 writing test e', 3);
INSERT INTO TWIT(CONTENT, MEMBER_ID) VALUES('user3 writing test f', 3);