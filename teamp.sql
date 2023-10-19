-- 외래키 초기화
ALTER TABLE AUCTION_BOARD DROP CONSTRAINT FK_AUCTION_CATEGORY;
ALTER TABLE AUCTION_BOARD DROP CONSTRAINT FK_AUCTION_MEMBER;
ALTER TABLE DELIVERY DROP CONSTRAINT FK_DELIVERY_AUCTION;
ALTER TABLE DELIVERY DROP CONSTRAINT FK_DELIVERY_MEMBER;
ALTER TABLE INTEREST DROP CONSTRAINT FK_INTEREST_MEMBER;
ALTER TABLE INTEREST DROP CONSTRAINT FK_INTEREST_AUCTION;
ALTER TABLE COMMENTS DROP CONSTRAINT FK_COMMENTS_AUCTION;
ALTER TABLE COMMENTS DROP CONSTRAINT FK_COMMENTS_MEMBER;
ALTER TABLE REPLIES DROP CONSTRAINT FK_REPLIES_COMMENTS;
ALTER TABLE REPLIES DROP CONSTRAINT FK_REPLIES_MEMBER;
ALTER TABLE REPLIES DROP CONSTRAINT FK_REPLIES_AUCTION;


-- 시퀸스 초기화
DROP SEQUENCE SEQ_AUCTION;
DROP SEQUENCE SEQ_DELIVERY;
DROP SEQUENCE SEQ_INTEREST;
DROP SEQUENCE SEQ_COMMENT;
DROP SEQUENCE SEQ_REPLY;
DROP SEQUENCE SEQ_CATEGORY;

-- 테이블 초기화
DROP TABLE MEMBER;
DROP TABLE AUCTION_BOARD;
DROP TABLE CATEGORY;
DROP TABLE DELIVERY;
DROP TABLE INTEREST;
DROP TABLE COMMENTS;
DROP TABLE REPLIES;

-- 회원(MEMBER) 테이블
CREATE TABLE MEMBER (
    MEMBER_ID VARCHAR2(20) PRIMARY KEY, -- 회원 아이디
    MEMBER_PWD VARCHAR2(200) NOT NULL, -- 회원 비밀번호
    MEMBER_NICK VARCHAR2(20) UNIQUE NOT NULL, -- 회원 닉네임
    MEMBER_NAME VARCHAR2(20) NOT NULL, -- 회원 이름
    MEMBER_BIRTHDAY NUMBER NOT NULL, -- 회원 생년월일
    MEMBER_PHONE VARCHAR2(15) NOT NULL, -- 회원 전화번호
    MEMBER_SPHONE VARCHAR2(20) NOT NULL, -- 회원 안심번호
    MEMBER_EMAIL VARCHAR2(50), -- 회원 이메일
    MEMBER_ADDR VARCHAR2(200) NOT NULL, -- 회원 주소
    MEMBER_SIGNUP_DATE DATE DEFAULT SYSDATE, -- 회원 가입일
    MEMBER_AUTHORITY NUMBER DEFAULT 1, -- 회원 권한
    MEMBER_POINT NUMBER
);

-- AUCTION_BOARD 경매 게시판 테이블
CREATE TABLE AUCTION_BOARD (
    AUCTION_NO NUMBER PRIMARY KEY, -- 경매 번호
    AUCTION_TITLE VARCHAR2(20) NOT NULL, -- 경매 제목
    AUCTION_IMG VARCHAR2(500) NOT NULL, -- 경매 이미지
    AUCTION_DATE DATE DEFAULT SYSDATE, -- 경매 등록일
    ITEM_NAME VARCHAR2(100), -- 상품 이름
    ITEM_DESC VARCHAR2(2000), -- 상품 설명
    CURRENT_PRICE NUMBER(12), -- 현재 가격 (정밀도 수정)
    AUCTION_SMONEY NUMBER(12) NOT NULL,-- 경매 시작가
    AUCTION_EMONEY NUMBER(12) NOT NULL, -- 경매 최소 입찰가 (정밀도 수정)
    AUCTION_GMONEY NUMBER(12) NOT NULL, -- 경매 즉시 구매가 (정밀도 수정)
    AUCTION_NOWBUY_Y_N CHAR(1) DEFAULT 'N' CHECK (AUCTION_NOWBUY_Y_N IN ('Y', 'N')), -- 경매 즉시 구매 여부
    AUCTION_END_DATE DATE DEFAULT (SYSDATE + 30), -- 경매 마감일
    AUCTION_CHECK_NO NUMBER, -- 조회수
    AUCTION_ATTEND_NO NUMBER, -- 입찰인원수
    CATEGORY_NO NUMBER, -- 카테고리 외래키
    MEMBER_ID VARCHAR2(20) -- 회원 외래키
);

-- 카테고리 테이블
CREATE TABLE CATEGORY (
    CATEGORY_NO NUMBER PRIMARY KEY, -- 카테고리 코드
    CATEGORY_NAME VARCHAR2(50) -- 카테고리 이름
);

-- DELIVERY 배송 테이블
CREATE TABLE DELIVERY (
    DELIVERY_NO NUMBER PRIMARY KEY, -- 배송 번호
    DELIVERY_COMPANY VARCHAR2(50), -- 배송 업체
    START_DATE DATE, -- 배송 시작일
    COMPLETE_DATE DATE, -- 배송 완료일
    AUCTION_NO NUMBER, -- 경매게시판 외래키
    MEMBER_ID VARCHAR2(20) -- 회원 외래키
);

-- INTEREST 관심 등록 테이블
CREATE TABLE INTEREST (
    INTEREST_NO NUMBER PRIMARY KEY, -- 관심 등록 번호
    AUCTION_NO NUMBER, -- 경매게시판 외래키
    MEMBER_ID VARCHAR2(20) -- 회원 외래키
);

-- COMMENTS 댓글 테이블
CREATE TABLE COMMENTS (
    COMMENT_NO NUMBER PRIMARY KEY, -- 댓글 번호
    CONTENT VARCHAR2(1000), -- 댓글 내용
    COMMENT_DATE DATE, -- 댓글 작성일
    AUCTION_NO NUMBER, -- 경매게시판 외래키
    MEMBER_ID VARCHAR2(20) -- 회원 외래키
);

-- REPLIES 대댓글 테이블
CREATE TABLE REPLIES (
    REPLY_NO NUMBER PRIMARY KEY, -- 대댓글 번호
    REPLY_CONTENT VARCHAR2(1000), -- 대댓글 내용
    REPLY_DATE DATE, -- 대댓글 작성일
    COMMENT_NO NUMBER, -- 댓글 외래키
    AUCTION_NO NUMBER, -- 경매게시판 외래키
    MEMBER_ID VARCHAR2(20) -- 회원 외래키
);

-- 외래키 설정
-- 경매게시판 (카테고리, 회원)
ALTER TABLE AUCTION_BOARD ADD CONSTRAINT FK_AUCTION_CATEGORY FOREIGN KEY(CATEGORY_NO) REFERENCES CATEGORY;
ALTER TABLE AUCTION_BOARD ADD CONSTRAINT FK_AUCTION_MEMBER FOREIGN KEY (MEMBER_ID) REFERENCES MEMBER;
-- 배송 (경매게시판, 회원)
ALTER TABLE DELIVERY ADD CONSTRAINT FK_DELIVERY_AUCTION FOREIGN KEY (AUCTION_NO) REFERENCES AUCTION_BOARD;
ALTER TABLE DELIVERY ADD CONSTRAINT FK_DELIVERY_MEMBER FOREIGN KEY (MEMBER_ID) REFERENCES MEMBER;
-- 관심등록 (회원, 경매게시판)
ALTER TABLE INTEREST ADD CONSTRAINT FK_INTEREST_MEMBER FOREIGN KEY (MEMBER_ID) REFERENCES MEMBER;
ALTER TABLE INTEREST ADD CONSTRAINT FK_INTEREST_AUCTION FOREIGN KEY (AUCTION_NO) REFERENCES AUCTION_BOARD;
-- 댓글 (경매게시판, 회원)
ALTER TABLE COMMENTS ADD CONSTRAINT FK_COMMENTS_AUCTION FOREIGN KEY (AUCTION_NO) REFERENCES AUCTION_BOARD;
ALTER TABLE COMMENTS ADD CONSTRAINT FK_COMMENTS_MEMBER FOREIGN KEY (MEMBER_ID) REFERENCES MEMBER;
-- 대댓글 (댓글, 회원아이디, 경매게시판)
ALTER TABLE REPLIES ADD CONSTRAINT FK_REPLIES_COMMENTS FOREIGN KEY (COMMENT_NO) REFERENCES COMMENTS;
ALTER TABLE REPLIES ADD CONSTRAINT FK_REPLIES_MEMBER FOREIGN KEY (MEMBER_ID) REFERENCES MEMBER;
ALTER TABLE REPLIES ADD CONSTRAINT FK_REPLIES_AUCTION FOREIGN KEY (AUCTION_NO) REFERENCES AUCTION_BOARD;

-- 시퀸스 설정
CREATE SEQUENCE SEQ_AUCTION;
CREATE SEQUENCE SEQ_DELIVERY;
CREATE SEQUENCE SEQ_INTEREST;
CREATE SEQUENCE SEQ_COMMENT;
CREATE SEQUENCE SEQ_REPLY;
CREATE SEQUENCE SEQ_CATEGORY;



SELECT * FROM AUCTION_BOARD;


INSERT INTO CATEGORY (CATEGORY_NO, CATEGORY_NAME)
VALUES (1, '전자제품');

INSERT INTO CATEGORY (CATEGORY_NO, CATEGORY_NAME)
VALUES (2, '의류');

INSERT INTO CATEGORY (CATEGORY_NO, CATEGORY_NAME)
VALUES (3, '도서');

INSERT INTO CATEGORY (CATEGORY_NO, CATEGORY_NAME)
VALUES (4, '가구');

INSERT INTO CATEGORY (CATEGORY_NO, CATEGORY_NAME)
VALUES (5, '음식');

COMMIT;

-- 가상 맴버 삽입

INSERT INTO MEMBER (MEMBER_ID, MEMBER_PWD, MEMBER_NICK, MEMBER_NAME, MEMBER_BIRTHDAY, MEMBER_PHONE, MEMBER_SPHONE, MEMBER_EMAIL, MEMBER_ADDR, MEMBER_AUTHORITY)
VALUES ('user123', 'hashedpassword1', 'user123nick', 'User One', 19900101, '1234567890', 'SP12345', 'user1@example.com', '123 Main St, City, Country', 1);

INSERT INTO MEMBER (MEMBER_ID, MEMBER_PWD, MEMBER_NICK, MEMBER_NAME, MEMBER_BIRTHDAY, MEMBER_PHONE, MEMBER_SPHONE, MEMBER_EMAIL, MEMBER_ADDR, MEMBER_AUTHORITY)
VALUES ('user456', 'hashedpassword2', 'user456nick', 'User Two', 19900202, '9876543210', 'SP67890', 'user2@example.com', '456 Elm St, Town, Country', 1);

INSERT INTO MEMBER (MEMBER_ID, MEMBER_PWD, MEMBER_NICK, MEMBER_NAME, MEMBER_BIRTHDAY, MEMBER_PHONE, MEMBER_SPHONE, MEMBER_EMAIL, MEMBER_ADDR, MEMBER_AUTHORITY)
VALUES ('user789', 'hashedpassword3', 'user789nick', 'User Three', 19900303, '1112223333', 'SP24680', 'user3@example.com', '789 Oak St, Village, Country', 1);

INSERT INTO MEMBER (MEMBER_ID, MEMBER_PWD, MEMBER_NICK, MEMBER_NAME, MEMBER_BIRTHDAY, MEMBER_PHONE, MEMBER_SPHONE, MEMBER_EMAIL, MEMBER_ADDR, MEMBER_AUTHORITY)
VALUES ('user012', 'hashedpassword4', 'user012nick', 'User Four', 19900404, '4445556666', 'SP13579', 'user4@example.com', '012 Pine St, Suburb, Country', 1);

INSERT INTO MEMBER (MEMBER_ID, MEMBER_PWD, MEMBER_NICK, MEMBER_NAME, MEMBER_BIRTHDAY, MEMBER_PHONE, MEMBER_SPHONE, MEMBER_EMAIL, MEMBER_ADDR, MEMBER_AUTHORITY)
VALUES ('user345', 'hashedpassword5', 'user345nick', 'User Five', 19900505, '5556667777', 'SP02468', 'user5@example.com', '345 Cedar St, Village, Country', 1);


commit;

-- 가상 데이터를 AUCTION_BOARD 테이블에 삽입
INSERT INTO AUCTION_BOARD (AUCTION_NO, AUCTION_TITLE, AUCTION_IMG, AUCTION_DATE, ITEM_NAME, ITEM_DESC, CURRENT_PRICE, AUCTION_SMONEY, AUCTION_EMONEY, AUCTION_GMONEY, AUCTION_NOWBUY_Y_N, AUCTION_END_DATE, AUCTION_CHECK_NO, AUCTION_ATTEND_NO, CATEGORY_NO, MEMBER_ID)
VALUES (1, '가상 경매 1(1)', 'image1.jpg', TO_DATE('2023-11-15', 'YYYY-MM-DD'), '상품 1', '이 상품은 가상 상품입니다.', 5000, 1000, 3000, 7000, 'N', TO_TIMESTAMP('2023-12-30T00:00:00', 'YYYY-MM-DD"T"HH24:MI:SS'), 10, 20, 1, 'user123');

INSERT INTO AUCTION_BOARD (AUCTION_NO, AUCTION_TITLE, AUCTION_IMG, AUCTION_DATE, ITEM_NAME, ITEM_DESC, CURRENT_PRICE, AUCTION_SMONEY, AUCTION_EMONEY, AUCTION_GMONEY, AUCTION_NOWBUY_Y_N, AUCTION_END_DATE, AUCTION_CHECK_NO, AUCTION_ATTEND_NO, CATEGORY_NO, MEMBER_ID)
VALUES (2, '가상 경매 2(2)', 'image2.jpg', TO_DATE('2023-11-14', 'YYYY-MM-DD'), '상품 2', '이 상품도 가상 상품입니다.', 6000, 1500, 4500, 8000, 'N', TO_TIMESTAMP('2023-12-29T00:00:00', 'YYYY-MM-DD"T"HH24:MI:SS'), 30, 240, 2, 'user456');

INSERT INTO AUCTION_BOARD (AUCTION_NO, AUCTION_TITLE, AUCTION_IMG, AUCTION_DATE, ITEM_NAME, ITEM_DESC, CURRENT_PRICE, AUCTION_SMONEY, AUCTION_EMONEY, AUCTION_GMONEY, AUCTION_NOWBUY_Y_N, AUCTION_END_DATE, AUCTION_CHECK_NO, AUCTION_ATTEND_NO, CATEGORY_NO, MEMBER_ID)
VALUES (3, '가상 경매 3(1)', 'image3.jpg', TO_DATE('2023-11-13', 'YYYY-MM-DD'), '상품 3', '가상 상품 설명입니다.', 7000, 2000, 6000, 9500, 'N', TO_TIMESTAMP('2023-12-28T00:00:00', 'YYYY-MM-DD"T"HH24:MI:SS'), 50, 60, 1, 'user789');

INSERT INTO AUCTION_BOARD (AUCTION_NO, AUCTION_TITLE, AUCTION_IMG, AUCTION_DATE, ITEM_NAME, ITEM_DESC, CURRENT_PRICE, AUCTION_SMONEY, AUCTION_EMONEY, AUCTION_GMONEY, AUCTION_NOWBUY_Y_N, AUCTION_END_DATE, AUCTION_CHECK_NO, AUCTION_ATTEND_NO, CATEGORY_NO, MEMBER_ID)
VALUES (4, '가상 경매 4(2)', 'image4.jpg', TO_DATE('2023-11-12', 'YYYY-MM-DD'), '상품 4', '가상 상품 설명입니다.', 7500, 1200, 4000, 8500, 'N', TO_TIMESTAMP('2023-12-27T00:00:00', 'YYYY-MM-DD"T"HH24:MI:SS'), 70, 200, 2, 'user012');

INSERT INTO AUCTION_BOARD (AUCTION_NO, AUCTION_TITLE, AUCTION_IMG, AUCTION_DATE, ITEM_NAME, ITEM_DESC, CURRENT_PRICE, AUCTION_SMONEY, AUCTION_EMONEY, AUCTION_GMONEY, AUCTION_NOWBUY_Y_N, AUCTION_END_DATE, AUCTION_CHECK_NO, AUCTION_ATTEND_NO, CATEGORY_NO, MEMBER_ID)
VALUES (5, '가상 경매 5(1)', 'image5.jpg', TO_DATE('2023-11-11', 'YYYY-MM-DD'), '상품 5', '가상 상품 설명입니다.', 8000, 800, 3000, 6000, 'N', TO_TIMESTAMP('2023-12-26T00:00:00', 'YYYY-MM-DD"T"HH24:MI:SS'), 90, 100, 1, 'user345');

INSERT INTO AUCTION_BOARD (AUCTION_NO, AUCTION_TITLE, AUCTION_IMG, AUCTION_DATE, ITEM_NAME, ITEM_DESC, CURRENT_PRICE, AUCTION_SMONEY, AUCTION_EMONEY, AUCTION_GMONEY, AUCTION_NOWBUY_Y_N, AUCTION_END_DATE, AUCTION_CHECK_NO, AUCTION_ATTEND_NO, CATEGORY_NO, MEMBER_ID)
VALUES (6, '가상 경매 6(3)', 'image5.jpg', TO_DATE('2023-11-10', 'YYYY-MM-DD'), '상품 6', '가상 상품 설명입니다.', 8500, 800, 33000, 6000, 'N', TO_DATE('2023-12-25T00:00:00', 'YYYY-MM-DD"T"HH24:MI:SS'), 110, 160, 3, 'user345');

INSERT INTO AUCTION_BOARD (AUCTION_NO, AUCTION_TITLE, AUCTION_IMG, AUCTION_DATE, ITEM_NAME, ITEM_DESC, CURRENT_PRICE, AUCTION_SMONEY, AUCTION_EMONEY, AUCTION_GMONEY, AUCTION_NOWBUY_Y_N, AUCTION_END_DATE, AUCTION_CHECK_NO, AUCTION_ATTEND_NO, CATEGORY_NO, MEMBER_ID)
VALUES (7, '가상 경매 7(2)', 'image5.jpg', TO_DATE('2023-11-09', 'YYYY-MM-DD'), '상품 7', '가상 상품 설명입니다.', 9000, 800, 34000, 6000, 'N', TO_DATE('2023-12-24T00:00:00', 'YYYY-MM-DD"T"HH24:MI:SS'), 130, 140, 2, 'user345');

INSERT INTO AUCTION_BOARD (AUCTION_NO, AUCTION_TITLE, AUCTION_IMG, AUCTION_DATE, ITEM_NAME, ITEM_DESC, CURRENT_PRICE, AUCTION_SMONEY, AUCTION_EMONEY, AUCTION_GMONEY, AUCTION_NOWBUY_Y_N, AUCTION_END_DATE, AUCTION_CHECK_NO, AUCTION_ATTEND_NO, CATEGORY_NO, MEMBER_ID)
VALUES (8, '가상 경매 8(3)', 'image5.jpg', TO_DATE('2023-11-08', 'YYYY-MM-DD'), '상품 8', '가상 상품 설명입니다.', 9500, 800, 34000, 6000, 'N', TO_DATE('2023-12-23T00:00:00', 'YYYY-MM-DD"T"HH24:MI:SS'), 150, 120, 3, 'user345');

INSERT INTO AUCTION_BOARD (AUCTION_NO, AUCTION_TITLE, AUCTION_IMG, AUCTION_DATE, ITEM_NAME, ITEM_DESC, CURRENT_PRICE, AUCTION_SMONEY, AUCTION_EMONEY, AUCTION_GMONEY, AUCTION_NOWBUY_Y_N, AUCTION_END_DATE, AUCTION_CHECK_NO, AUCTION_ATTEND_NO, CATEGORY_NO, MEMBER_ID)
VALUES (9, '가상 경매 9(3)', 'image5.jpg', TO_DATE('2023-11-07', 'YYYY-MM-DD'), '상품 9', '가상 상품 설명입니다.', 10000, 800, 34000, 6000, 'N', TO_DATE('2023-12-22T00:00:00', 'YYYY-MM-DD"T"HH24:MI:SS'), 170, 180, 3, 'user345');

INSERT INTO AUCTION_BOARD (AUCTION_NO, AUCTION_TITLE, AUCTION_IMG, AUCTION_DATE, ITEM_NAME, ITEM_DESC, CURRENT_PRICE, AUCTION_SMONEY, AUCTION_EMONEY, AUCTION_GMONEY, AUCTION_NOWBUY_Y_N, AUCTION_END_DATE, AUCTION_CHECK_NO, AUCTION_ATTEND_NO, CATEGORY_NO, MEMBER_ID)
VALUES (10, '가상 경매 10(2)', 'image5.jpg', TO_DATE('2023-11-06', 'YYYY-MM-DD'), '상품 10', '가상 상품 설명입니다.', 11000, 800, 34000, 6000, 'N', TO_DATE('2023-12-21T00:00:00', 'YYYY-MM-DD"T"HH24:MI:SS'), 190, 80, 2, 'user345');

INSERT INTO AUCTION_BOARD (AUCTION_NO, AUCTION_TITLE, AUCTION_IMG, AUCTION_DATE, ITEM_NAME, ITEM_DESC, CURRENT_PRICE, AUCTION_SMONEY, AUCTION_EMONEY, AUCTION_GMONEY, AUCTION_NOWBUY_Y_N, AUCTION_END_DATE, AUCTION_CHECK_NO, AUCTION_ATTEND_NO, CATEGORY_NO, MEMBER_ID)
VALUES (11, '가상 경매 11(3)', 'image5.jpg', TO_DATE('2023-11-05', 'YYYY-MM-DD'), '상품 11', '가상 상품 설명입니다.', 11500, 800, 34000, 6000, 'N', TO_DATE('2023-12-20T00:00:00', 'YYYY-MM-DD"T"HH24:MI:SS'), 210, 220, 3, 'user345');

INSERT INTO AUCTION_BOARD (AUCTION_NO, AUCTION_TITLE, AUCTION_IMG, AUCTION_DATE, ITEM_NAME, ITEM_DESC, CURRENT_PRICE, AUCTION_SMONEY, AUCTION_EMONEY, AUCTION_GMONEY, AUCTION_NOWBUY_Y_N, AUCTION_END_DATE, AUCTION_CHECK_NO, AUCTION_ATTEND_NO, CATEGORY_NO, MEMBER_ID)
VALUES (12, '가상 경매 12(3)', 'image5.jpg', TO_DATE('2023-11-04', 'YYYY-MM-DD'), '상품 12', '가상 상품 설명입니다.', 12000, 800, 34000, 6000, 'N', TO_DATE('2023-12-19T00:00:00', 'YYYY-MM-DD"T"HH24:MI:SS'), 230, 40, 3, 'user345');

INSERT INTO AUCTION_BOARD (AUCTION_NO, AUCTION_TITLE, AUCTION_IMG, AUCTION_DATE, ITEM_NAME, ITEM_DESC, CURRENT_PRICE, AUCTION_SMONEY, AUCTION_EMONEY, AUCTION_GMONEY, AUCTION_NOWBUY_Y_N, AUCTION_END_DATE, AUCTION_CHECK_NO, AUCTION_ATTEND_NO, CATEGORY_NO, MEMBER_ID)
VALUES (13, '가상 경매 13(1)', 'image5.jpg', TO_DATE('2023-11-03', 'YYYY-MM-DD'), '상품 13', '가상 상품 설명입니다.', 13000, 800, 34000, 6000, 'N', TO_DATE('2023-10-18T23:00:00', 'YYYY-MM-DD"T"HH24:MI:SS'), 250, 260, 1, 'user345');

commit;