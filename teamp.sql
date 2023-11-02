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
ALTER TABLE POINT DROP CONSTRAINT FK_POINT_MEMBER;


-- 시퀸스 초기화
DROP SEQUENCE SEQ_AUCTION;
DROP SEQUENCE SEQ_DELIVERY;
DROP SEQUENCE SEQ_INTEREST;
DROP SEQUENCE SEQ_COMMENT;
DROP SEQUENCE SEQ_REPLY;
DROP SEQUENCE SEQ_CATEGORY;
DROP SEQUENCE SEQ_POINT;

-- 테이블 초기화
DROP TABLE MEMBER;
DROP TABLE POINT;
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
    MEMBER_AUTHORITY VARCHAR2(10), -- 회원 권한
    MEMBER_POINT NUMBER
);

-- POINTLIST 포인트 충전 내역
CREATE TABLE POINT (
    MEMBER_ID VARCHAR2(20), -- 회원 외래키
    ADD_POINT NUMBER, -- 충전된 포인트
    ADD_DATE DATE DEFAULT SYSDATE -- 포인트 충전 시간 기록
);

-- AUCTION_BOARD 경매 게시판 테이블
CREATE TABLE AUCTION_BOARD (
    AUCTION_NO NUMBER PRIMARY KEY, -- 경매 번호
    AUCTION_TITLE VARCHAR2(200) NOT NULL, -- 경매 제목
    AUCTION_IMG VARCHAR2(2000) NOT NULL, -- 경매 이미지
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
    CURRENT_NUM NUMBER, -- 입찰 횟수
    CATEGORY_NO NUMBER, -- 카테고리 외래키
    MEMBER_ID VARCHAR2(20), -- 회원 외래키
    BUYER_ID VARCHAR2(20),
    BUYER_POINT NUMBER
);

CREATE TABLE ITEM_BUYER (
    AUCTION_NO NUMBER,
    MEMBER_ID VARCHAR2(200),
    ITEM_POINT NUMBER
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
    COMMENT_PARENT NUMBER,
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
-- 포인트(회원, 포인트)
ALTER TABLE POINT ADD CONSTRAINT FK_POINT_MEMBER FOREIGN KEY (MEMBER_ID) REFERENCES MEMBER;


-- 시퀸스 설정
CREATE SEQUENCE SEQ_AUCTION;
CREATE SEQUENCE SEQ_DELIVERY;
CREATE SEQUENCE SEQ_INTEREST;
CREATE SEQUENCE SEQ_COMMENT;
CREATE SEQUENCE SEQ_REPLY;
CREATE SEQUENCE SEQ_CATEGORY;
CREATE SEQUENCE SEQ_POINT;



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

ALTER SESSION SET NLS_DATE_FORMAT = 'YYYY-MM-DD HH:MI:SS';


commit;