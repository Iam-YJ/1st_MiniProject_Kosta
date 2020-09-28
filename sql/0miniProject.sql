-----------------------------------MEMBER-----------------------------------

    DROP TABLE MEMBER;
    SELECT*FROM MEMBER;

  --���̺� ����
    CREATE TABLE MEMBER(
    USER_NO NUMBER NOT NULL PRIMARY KEY,           --������ -> 'USER_NO_SEQ'
    USER_ID VARCHAR2(50) NOT NULL UNIQUE,                     
    PASSWORD VARCHAR2(50) NOT NULL,
    NICKNAME VARCHAR2(50) NOT NULL UNIQUE,
    POINTS NUMBER DEFAULT 0 NOT NULL
);

--test user (USER_NO_SEQ @ 1)
INSERT INTO MEMBER VALUES(USER_NO_SEQ.nextval, 'test', 'test', 'TEST', 0);
--admin (USER_NO_SEQ @ 2)
INSERT INTO MEMBER VALUES(USER_NO_SEQ.nextval, 'admin', 'admin', 'ADMIN', 0);
INSERT INTO ADMIN VALUES(2);


--������ ���� (�����ѹ� ������)
    DROP SEQUENCE USER_NO_SEQ;
    
    CREATE SEQUENCE USER_NO_SEQ   
    START WITH 1
    NOCYCLE
    NOMAXVALUE;


    
--�ڸ�Ʈ
    COMMENT ON TABLE MEMBER IS ' ȸ������ ���̺�';
    COMMENT ON COLUMN MEMBER.USER_NO IS '�����ѹ�';
    COMMENT ON COLUMN MEMBER.USER_ID IS '���̵�';
    COMMENT ON COLUMN MEMBER.PASSWORD IS '��й�ȣ';
    COMMENT ON COLUMN MEMBER.NICKNAME IS '�г���';

------------------------- ADMIN -----------------------------

DROP TABLE ADMIN;
SELECT*FROM ADMIN;

--���̺� ����
CREATE TABLE ADMIN AS
SELECT USER_NO FROM MEMBER;          -- ������ -> 'USER_NO_SEQ'

--�ڸ�Ʈ
COMMENT ON TABLE ADMIN IS ' ������ ���̺�';
COMMENT ON COLUMN ADMIN.USER_NO IS '��ȣ';

-----------------------------WORD-----------------------------
DROP TABLE WORD;
DELETE FROM WORD;
SELECT*FROM WORD;

--���̺� ����
CREATE TABLE WORD( 
    WORD_NO NUMBER NOT NULL PRIMARY KEY,                    --������ -> 'WORD_NO_SEQ' 
    WORD_LEVEL VARCHAR2(1) NOT NULL,
    WORD_ENG VARCHAR2(50) NOT NULL,
    WORD_KOR VARCHAR2(50) NOT NULL,
    WORD_PART VARCHAR2(10) NOT NULL
    );

--������ ����
    DROP SEQUENCE WORD_NO_SEQ;

    CREATE SEQUENCE WORD_NO_SEQ
    START WITH 1
    INCREMENT BY 1
    NOMAXVALUE;


--�ڸ�Ʈ
COMMENT ON TABLE WORD IS ' ���� ����ܾ� ���̺�';
COMMENT ON COLUMN WORD.WORD_NO IS '�ܾ� ��ȣ';
COMMENT ON COLUMN WORD.WORD_LEVEL IS '�ܾ� ����(H:�� , M:�� , L:��)';
COMMENT ON COLUMN WORD.WORD_ENG IS '����ܾ�';
COMMENT ON COLUMN WORD.WORD_KOR IS '���� �ѱ۶�';
COMMENT ON COLUMN WORD.WORD_PART IS 'ǰ��';

-----------------------------------USER_WORD------------------------------

DROP TABLE USER_WORD;
SELECT*FROM USER_WORD;

--���̺� ����
CREATE TABLE USER_WORD AS
SELECT USER_NO FROM MEMBER;              --������ -> 'USER_NO_SEQ'

ALTER TABLE USER_WORD ADD USER_WORD_NO NUMBER NOT NULL PRIMARY KEY;      -- ������-> 'WORD_NO_SEQ'
ALTER TABLE USER_WORD ADD USER_WORD_LEVEL VARCHAR2(1) NOT NULL;
ALTER TABLE USER_WORD ADD USER_ENG VARCHAR2(50) NOT NULL;
ALTER TABLE USER_WORD ADD USER_KOR VARCHAR2(50) NOT NULL;
ALTER TABLE USER_WORD ADD USER_PART VARCHAR2(10) NOT NULL;


--�ڸ�Ʈ
COMMENT ON TABLE USER_WORD IS ' ���� ����ܾ� ���̺�';
COMMENT ON COLUMN USER_WORD.USER_NO IS '���� ��ȣ';
COMMENT ON COLUMN USER_WORD.USER_WORD_NO IS '���� �ܾ� ��ȣ';
COMMENT ON COLUMN USER_WORD.USER_WORD_LEVEL IS '���� �ܾ� ����(H:�� , M:�� , L:��)';
COMMENT ON COLUMN USER_WORD.USER_ENG IS '���� �ܾ�';
COMMENT ON COLUMN USER_WORD.USER_KOR IS '���� �ѱ۶�';
COMMENT ON COLUMN USER_WORD.USER_PART IS '���� �ܾ� ǰ��';

-----------------------------------TEST -----------------------------------

DROP TABLE TEST;
SELECT*FROM TEST;

--���̺� ����
CREATE TABLE TEST AS
SELECT USER_NO FROM MEMBER;              --������ -> 'USER_NO_SEQ'

ALTER TABLE TEST ADD TEST_ROUND NUMBER NOT NULL;
ALTER TABLE TEST ADD TEST_DATE VARCHAR2(20) NOT NULL;
ALTER TABLE TEST ADD TEST_LEVEL VARCHAR2(1) NOT NULL;
ALTER TABLE TEST ADD CORRECT_NUMBER NUMBER;
ALTER TABLE TEST ADD WRONG_NUMBER NUMBER;
ALTER TABLE TEST ADD WRONG_WORD VARCHAR2(100);


--�ڸ�Ʈ
COMMENT ON TABLE TEST IS '�������� ���̺�';
COMMENT ON COLUMN TEST.USER_NO IS '��ȣ';
COMMENT ON COLUMN TEST.TEST_ROUND IS '����ȸ��';
COMMENT ON COLUMN TEST.TEST_DATE IS '���ܾ�';
COMMENT ON COLUMN TEST.CORRECT_NUMBER IS '���䰹��';
COMMENT ON COLUMN TEST.WRONG_NUMBER IS '���䰹��';
COMMENT ON COLUMN TEST.WRONG_WORD IS 'Ʋ�� �ܾ�';



--------------------------------------------------------------------------------
select * from member;
select * from admin;
select * from user_word;
select * from test;
select * from word;