SET PAGES 5000
SET LINES 500

DROP TABLE VALIDACAO;
DROP TABLE USUARIO;
DROP SEQUENCE USUARIO_SEQUENCE;
DROP SEQUENCE VALIDACAO_SEQUENCE;
DROP TRIGGER TRG_VALIDACAO_USUARIO;
DROP TRIGGER TRG_USUARIO_ROLE;

CREATE TABLE USUARIO (
	USUARIO_ID NUMBER(4) NOT NULL,
	EMAIL VARCHAR(50) NULL, 
	ROLE NUMBER(1) NULL,
	SENHA VARCHAR(30) NULL,
	CONSTRAINT USUARIO_PK PRIMARY KEY (USUARIO_ID)
);

CREATE TABLE VALIDACAO (
	VALIDACAO_ID NUMBER(4) NOT NULL,
	USUARIO_ID NUMBER(4) NULL,
	CONSTRAINT VALIDACAO_PK PRIMARY KEY (VALIDACAO_ID)
);

CREATE SEQUENCE usuario_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE validacao_sequence START WITH 1 INCREMENT BY 1;

INSERT INTO USUARIO VALUES (USUARIO_SEQUENCE.NEXTVAL, 'ADMIN@ADMIN.COM', 1, 'ADMIN123');

CREATE OR REPLACE TRIGGER TRG_VALIDACAO_USUARIO 
AFTER INSERT ON USUARIO
FOR EACH ROW 
BEGIN
	INSERT INTO VALIDACAO
		(VALIDACAO_ID, USUARIO_ID)
		VALUES 
		(VALIDACAO_SEQUENCE.NEXTVAL, :NEW.USUARIO_ID);
	
	
	
END;
/

CREATE OR REPLACE TRIGGER TRG_USUARIO_ROLE
BEFORE INSERT ON USUARIO
FOR EACH ROW
BEGIN
	:NEW.ROLE := 0;
END;
/

CL SCR

SELECT * FROM USUARIO;



