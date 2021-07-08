--------------------------------------------------------------------------------
-- 회원 테이블
--------------------------------------------------------------------------------
DROP TABLE USER;

CREATE TABLE "USER" (
	USER_ID	NUMBER		NOT NULL,
	USER_EMAIL	VARCHAR2(200)		NOT NULL,
	USER_PW	VARCHAR2(50)		NOT NULL,
	USER_NICKNAME	VARCHAR2(100)		NOT NULL,
	USER_REGDATE	DATE	DEFAULT SYSDATE	NOT NULL,
	USER_IS_ADMIN	CHAR(1)	DEFAULT 'N' CHECK(USER_IS_ADMIN IN('Y','N'))	NOT NULL
);

--------------------------------------------------------------------------------
-- 관리자용 회원 VIEW
--------------------------------------------------------------------------------
CREATE OR REPLACE VIEW USER_LIST AS
SELECT USER_ID, USER_EMAIL, USER_PW, USER_NICKNAME, USER_REGDATE, USER_IS_ADMIN
FROM "USER"
JOIN USER_INFO USING(USER_ID)
WHERE USER_EMAIL <> 'unreg';

COMMENT ON COLUMN "USER".USER_ID IS '회원 번호';
COMMENT ON COLUMN "USER".USER_EMAIL IS '회원 이메일';
COMMENT ON COLUMN "USER".USER_PW IS '회원 비밀번호';
COMMENT ON COLUMN "USER".USER_NICKNAME IS '회원 닉네임';
COMMENT ON COLUMN "USER".USER_REGDATE IS '회원 가입일';
COMMENT ON COLUMN "USER".USER_IS_ADMIN IS '관리자 여부(Y, N)';

--------------------------------------------------------------------------------
-- 회원 선택정보 테이블 
--------------------------------------------------------------------------------
DROP TABLE USER_INFO;

CREATE TABLE USER_INFO (
	USER_ID	NUMBER		NOT NULL,
	USER_PIC	VARCHAR2(500)		NULL,
	USER_LINK	VARCHAR2(1000)		NULL,
	USER_INTEREST	VARCHAR2(500)		NULL,
	USER_INTRODUCE	VARCHAR2(4000)		NULL
);

COMMENT ON COLUMN USER_INFO.USER_ID IS '회원 번호';
COMMENT ON COLUMN USER_INFO.USER_PIC IS '회원 프로필 사진';
COMMENT ON COLUMN USER_INFO.USER_LINK IS '회원 웹사이트 링크';
COMMENT ON COLUMN USER_INFO.USER_INTEREST IS '회원 관심분야';
COMMENT ON COLUMN USER_INFO.USER_INTRODUCE IS '회원 자기소개';

--------------------------------------------------------------------------------
-- 탈퇴한 회원 보관용 테이블
--------------------------------------------------------------------------------
DROP TABLE UNREG_USER;

CREATE TABLE UNREG_USER (
	USER_ID	NUMBER		NOT NULL,
	USER_EMAIL	VARCHAR2(200)		NOT NULL,
	USER_PW	VARCHAR2(50)		NOT NULL,
	USER_NICKNAME	VARCHAR2(100)		NOT NULL,
	USER_REGDATE	DATE	DEFAULT SYSDATE	NOT NULL,
	USER_IS_ADMIN	CHAR(1)	DEFAULT 'N'	CHECK(USER_IS_ADMIN IN('Y','N')) NOT NULL,
	USER_UNREGDATE	DATE	DEFAULT SYSDATE	NOT NULL
);

COMMENT ON COLUMN UNREG_USER.USER_ID IS '탈퇴한 회원 번호';
COMMENT ON COLUMN UNREG_USER.USER_EMAIL IS '탈퇴한 회원 이메일';
COMMENT ON COLUMN UNREG_USER.USER_PW IS '탈퇴한 회원 비밀번호';
COMMENT ON COLUMN UNREG_USER.USER_NICKNAME IS '탈퇴한 회원 닉네임';
COMMENT ON COLUMN UNREG_USER.USER_REGDATE IS '탈퇴한 회원 가입일';
COMMENT ON COLUMN UNREG_USER.USER_IS_ADMIN IS '탈퇴한 회원 관리자 여부(Y, N)';
COMMENT ON COLUMN UNREG_USER.USER_UNREGDATE IS '탈퇴한 회원 탈퇴일';


--------------------------------------------------------------------------------
--------------------------------------------------------------------------------
-- qna 게시판 테이블
--------------------------------------------------------------------------------
DROP TABLE QNA;

CREATE TABLE QNA (
	QNA_POST_ID	NUMBER		NOT NULL,
	QNA_TITLE	VARCHAR2(100)		NOT NULL,
	QNA_CONTENT	CLOB		NOT NULL,
	QNA_DATE	DATE	DEFAULT SYSDATE	NOT NULL,
	QNA_LIKE	NUMBER	DEFAULT 0	NOT NULL,
	QNA_STATUS	CHAR(1)	DEFAULT 'Y'	CHECK(QNA_STATUS IN('Y','N')) NOT NULL,
	QNA_CATEGORY_ID	NUMBER		NOT NULL,
	USER_ID	NUMBER		NOT NULL
);

COMMENT ON COLUMN QNA.QNA_POST_ID IS 'QnA게시글 번호';
COMMENT ON COLUMN QNA.QNA_TITLE IS 'QnA게시글 제목';
COMMENT ON COLUMN QNA.QNA_CONTENT IS 'QnA게시글 내용';
COMMENT ON COLUMN QNA.QNA_DATE IS 'QnA게시글 작성일';
COMMENT ON COLUMN QNA.QNA_LIKE IS 'QnA글 좋아요 수';
COMMENT ON COLUMN QNA.QNA_STATUS IS 'QnA글 상태(Y: 정상 / N: 삭제)';
COMMENT ON COLUMN QNA.QNA_CATEGORY_ID IS 'QnA카테고리 번호';
COMMENT ON COLUMN QNA.USER_ID IS 'QnA글 작성자';

--------------------------------------------------------------------------------
-- qna 게시판 카테고리
--------------------------------------------------------------------------------
DROP TABLE QNA_CATEGORIES;

CREATE TABLE QNA_CATEGORIES (
	QNA_CATEGORY_ID	NUMBER		NOT NULL,
	QNA_CATEGORY_NAME	VARCHAR2(50)		NOT NULL
);

COMMENT ON COLUMN QNA_CATEGORIES.QNA_CATEGORY_ID IS 'qna 카테고리 번호';
COMMENT ON COLUMN QNA_CATEGORIES.QNA_CATEGORY_NAME IS 'qna 카테고리 이름';

--------------------------------------------------------------------------------
-- qna 게시판 좋아요 테이블
--------------------------------------------------------------------------------
DROP TABLE QNA_LIKES;

CREATE TABLE QNA_LIKES (
	LIKES_ID	NUMBER		NOT NULL,
	QNA_POST_ID	NUMBER		NULL,
	USER_ID	NUMBER		NOT NULL
);

COMMENT ON COLUMN QNA_LIKES.LIKES_ID IS 'LIKE 번호';
COMMENT ON COLUMN QNA_LIKES.QNA_POST_ID IS 'qna 게시글 번호';
COMMENT ON COLUMN QNA_LIKES.USER_ID IS '회원 번호';

--------------------------------------------------------------------------------
-- qna 게시판 댓글 테이블
--------------------------------------------------------------------------------
DROP TABLE QNA_REPLY;

CREATE TABLE QNA_REPLY (
	REPLY_ID	NUMBER		NOT NULL,
	REPLY_CONTENT	VARCHAR2(4000)		NOT NULL,
	REPLY_LIKE	NUMBER	DEFAULT 0	NOT NULL,
    REPLY_STATUS	CHAR(1)	DEFAULT 'Y' CHECK(REPLY_STATUS IN('Y','N'))	NOT NULL,
	REPLY_DATE	DATE	DEFAULT SYSDATE	NOT NULL,
	QNA_POST_ID	NUMBER		NOT NULL,
	USER_ID	NUMBER		NOT NULL
);

COMMENT ON COLUMN QNA_REPLY.REPLY_ID IS 'QnA댓글 번호';
COMMENT ON COLUMN QNA_REPLY.REPLY_CONTENT IS 'QnA댓글 내용';
COMMENT ON COLUMN QNA_REPLY.REPLY_LIKE IS 'QnA댓글 좋아요 수';
COMMENT ON COLUMN QNA_REPLY.REPLY_STATUS IS 'QnA댓글 삭제여부(Y/N)';
COMMENT ON COLUMN QNA_REPLY.REPLY_DATE IS 'QnA댓글 작성일';
COMMENT ON COLUMN QNA_REPLY.QNA_POST_ID IS 'QnA게시글 번호';
COMMENT ON COLUMN QNA_REPLY.USER_ID IS 'QnA게시글 작성자';

--------------------------------------------------------------------------------
-- qna 게시판 댓글 좋아요 테이블
--------------------------------------------------------------------------------
DROP TABLE QNA_REPLY_LIKES;

CREATE TABLE QNA_REPLY_LIKES (
	LIKES_ID	NUMBER		NOT NULL,
	USER_ID	NUMBER		NOT NULL,
	REPLY_ID	NUMBER		NOT NULL
);

COMMENT ON COLUMN QNA_REPLY_LIKES.LIKES_ID IS 'LIKE 번호';
COMMENT ON COLUMN QNA_REPLY_LIKES.USER_ID IS '회원 번호';
COMMENT ON COLUMN QNA_REPLY_LIKES.REPLY_ID IS 'qna 댓글 번호';


--------------------------------------------------------------------------------
--------------------------------------------------------------------------------
-- apps 게시판 테이블
--------------------------------------------------------------------------------
DROP TABLE APPS;

CREATE TABLE APPS (
	APPS_POST_ID	NUMBER		NOT NULL,
	APPS_TITLE	VARCHAR2(100)		NOT NULL,
	APPS_CONTENT	CLOB		NOT NULL,
	APPS_ICON	VARCHAR2(50)		NOT NULL,
	APPS_URL	VARCHAR2(500)		NOT NULL,
	APPS_LIKE	NUMBER	DEFAULT 0	NOT NULL,
	APPS_DATE	DATE	DEFAULT SYSDATE	NOT NULL,
	APPS_STATUS	CHAR(1)	DEFAULT 'Y' CHECK(APPS_STATUS IN('Y','N'))	NOT NULL,
	APPS_CATEGORY_ID	NUMBER		NOT NULL,
	USER_ID	NUMBER		NOT NULL
);

COMMENT ON COLUMN APPS.APPS_POST_ID IS 'Apps 게시글 번호';
COMMENT ON COLUMN APPS.APPS_TITLE IS 'Apps 게시글 제목';
COMMENT ON COLUMN APPS.APPS_CONTENT IS 'Apps 게시글 내용';
COMMENT ON COLUMN APPS.APPS_ICON IS 'Apps 아이콘';
COMMENT ON COLUMN APPS.APPS_URL IS 'Apps 링크';
COMMENT ON COLUMN APPS.APPS_LIKE IS 'Apps게시글 종아요 수';
COMMENT ON COLUMN APPS.APPS_DATE IS 'Apps게시글 작성일';
COMMENT ON COLUMN APPS.APPS_STATUS IS 'Apps게시글 상태(Y: 정상 / N:삭제)';
COMMENT ON COLUMN APPS.APPS_CATEGORY_ID IS 'Apps게시글 카테고리 번호';
COMMENT ON COLUMN APPS.USER_ID IS '회원 번호';

--------------------------------------------------------------------------------
-- apps 태그 테이블
--------------------------------------------------------------------------------
DROP TABLE APPS_TAGS;

CREATE TABLE APPS_TAGS (
	APPS_POST_ID	NUMBER		NOT NULL,
	APPS_TAG_ID	NUMBER		NOT NULL
);

COMMENT ON COLUMN APPS_TAGS.APPS_POST_ID IS 'Apps 게시글 번호';
COMMENT ON COLUMN APPS_TAGS.APPS_TAG_ID IS 'Apps게시글 태그 번호';

--------------------------------------------------------------------------------
-- apps 좋아요 테이블
--------------------------------------------------------------------------------
DROP TABLE APPS_LIKES;

CREATE TABLE APPS_LIKES (
	LIKES_ID	NUMBER		NOT NULL,
	APPS_POST_ID	NUMBER		NULL,
	USER_ID	NUMBER		NOT NULL
);

COMMENT ON COLUMN APPS_LIKES.LIKES_ID IS 'LIKE번호';
COMMENT ON COLUMN APPS_LIKES.APPS_POST_ID IS 'Apps 게시글 번호';
COMMENT ON COLUMN APPS_LIKES.USER_ID IS '회원 번호';

--------------------------------------------------------------------------------
-- apps 카테고리 테이블
--------------------------------------------------------------------------------
DROP TABLE APPS_CATEGORIES;

CREATE TABLE APPS_CATEGORIES (
	APPS_CATEGORY_ID	NUMBER		NOT NULL,
	APPS_CATEGORY_NAME	VARCHAR2(100)		NOT NULL
);

COMMENT ON COLUMN APPS_CATEGORIES.APPS_CATEGORY_ID IS 'Apps게시글 카테고리 번호';
COMMENT ON COLUMN APPS_CATEGORIES.APPS_CATEGORY_NAME IS 'Apps게시글 카테고리 이름';

--------------------------------------------------------------------------------
-- tag 저장 테이블
--------------------------------------------------------------------------------
DROP TABLE TAGS;

CREATE TABLE TAGS (
	APPS_TAG_ID	NUMBER		NOT NULL,
	APPS_TAG_NAME	VARCHAR2(100)		NOT NULL
);

COMMENT ON COLUMN TAGS.APPS_TAG_ID IS 'apps 게시글 태그 번호';
COMMENT ON COLUMN TAGS.APPS_TAG_NAME IS 'apps 게시글 태그 이름';


--------------------------------------------------------------------------------
--------------------------------------------------------------------------------
-- PK, FK 설정
--------------------------------------------------------------------------------

-- 탈퇴 회원 테이블 PK
ALTER TABLE UNREG_USER ADD CONSTRAINT PK_UNREG_USER PRIMARY KEY (
	USER_ID
);

-- qna 게시판 테이블 PK
ALTER TABLE QNA ADD CONSTRAINT PK_QNA PRIMARY KEY (
	QNA_POST_ID
);

-- apps 게시판 테이블 PK
ALTER TABLE APPS ADD CONSTRAINT PK_APPS PRIMARY KEY (
	APPS_POST_ID
);

-- 회원 테이블 PK
ALTER TABLE "USER" ADD CONSTRAINT PK_USER PRIMARY KEY (
	USER_ID
);

-- qna 카테고리 테이블 PK
ALTER TABLE QNA_CATEGORIES ADD CONSTRAINT PK_QNA_CATEGORIES PRIMARY KEY (
	QNA_CATEGORY_ID
);

-- qna 댓글 테이블 PK
ALTER TABLE QNA_REPLY ADD CONSTRAINT PK_QNA_REPLY PRIMARY KEY (
	REPLY_ID
);

-- apps 좋아요 테이블 PK
ALTER TABLE APPS_LIKES ADD CONSTRAINT PK_APPS_LIKES PRIMARY KEY (
	LIKES_ID
);

-- apps 카테고리 테이블 PK
ALTER TABLE APPS_CATEGORIES ADD CONSTRAINT PK_APPS_CATEGORIES PRIMARY KEY (
	APPS_CATEGORY_ID
);

-- tag 테이블 PK
ALTER TABLE TAGS ADD CONSTRAINT PK_TAGS PRIMARY KEY (
	APPS_TAG_ID
);

-- qna 좋아요 테이블 PK
ALTER TABLE QNA_LIKES ADD CONSTRAINT PK_QNA_LIKES PRIMARY KEY (
	LIKES_ID
);

-- qna 답변 좋아요 테이블 PK
ALTER TABLE QNA_REPLY_LIKES ADD CONSTRAINT PK_QNA_REPLY_LIKES PRIMARY KEY (
	LIKES_ID
);

--------------------------------------------------------------------------------
-- 아우 귀찮아 ...!! 아무튼 이하 FK
--------------------------------------------------------------------------------
ALTER TABLE QNA ADD CONSTRAINT FK_QNA_CATEGORIES_TO_QNA_1 FOREIGN KEY (
	QNA_CATEGORY_ID
)
REFERENCES QNA_CATEGORIES (
	QNA_CATEGORY_ID
);

ALTER TABLE QNA ADD CONSTRAINT FK_USER_TO_QNA_1 FOREIGN KEY (
	USER_ID
)
REFERENCES "USER" (
	USER_ID
);

ALTER TABLE USER_INFO ADD CONSTRAINT FK_USER_TO_USER_INFO_1 FOREIGN KEY (
	USER_ID
)
REFERENCES "USER" (
	USER_ID
);

ALTER TABLE APPS ADD CONSTRAINT FK_APPS_CATEGORIES_TO_APPS_1 FOREIGN KEY (
	APPS_CATEGORY_ID
)
REFERENCES APPS_CATEGORIES (
	APPS_CATEGORY_ID
);

ALTER TABLE APPS ADD CONSTRAINT FK_USER_TO_APPS_1 FOREIGN KEY (
	USER_ID
)
REFERENCES "USER" (
	USER_ID
);

ALTER TABLE QNA_REPLY ADD CONSTRAINT FK_QNA_TO_QNA_REPLY_1 FOREIGN KEY (
	QNA_POST_ID
)
REFERENCES QNA (
	QNA_POST_ID
);

ALTER TABLE QNA_REPLY ADD CONSTRAINT FK_USER_TO_QNA_REPLY_1 FOREIGN KEY (
	USER_ID
)
REFERENCES "USER" (
	USER_ID
);

ALTER TABLE APPS_TAGS ADD CONSTRAINT FK_APPS_TO_APPS_TAGS_1 FOREIGN KEY (
	APPS_POST_ID
)
REFERENCES APPS (
	APPS_POST_ID
);

ALTER TABLE APPS_TAGS ADD CONSTRAINT FK_TAGS_TO_APPS_TAGS_1 FOREIGN KEY (
	APPS_TAG_ID
)
REFERENCES TAGS (
	APPS_TAG_ID
);

ALTER TABLE APPS_LIKES ADD CONSTRAINT FK_APPS_TO_APPS_LIKES_1 FOREIGN KEY (
	APPS_POST_ID
)
REFERENCES APPS (
	APPS_POST_ID
);

ALTER TABLE APPS_LIKES ADD CONSTRAINT FK_USER_TO_APPS_LIKES_1 FOREIGN KEY (
	USER_ID
)
REFERENCES "USER" (
	USER_ID
);

ALTER TABLE QNA_LIKES ADD CONSTRAINT FK_QNA_TO_QNA_LIKES_1 FOREIGN KEY (
	QNA_POST_ID
)
REFERENCES QNA (
	QNA_POST_ID
);

ALTER TABLE QNA_LIKES ADD CONSTRAINT FK_USER_TO_QNA_LIKES_1 FOREIGN KEY (
	USER_ID
)
REFERENCES "USER" (
	USER_ID
);

ALTER TABLE QNA_REPLY_LIKES ADD CONSTRAINT FK_USER_TO_QNA_REPLY_LIKES_1 FOREIGN KEY (
	USER_ID
)
REFERENCES "USER" (
	USER_ID
);

ALTER TABLE QNA_REPLY_LIKES ADD CONSTRAINT FK_QNA_RPL_TO_QNA_RPL_LIKES FOREIGN KEY (
	REPLY_ID
)
REFERENCES QNA_REPLY (
	REPLY_ID
);


--------------------------------------------------------------------------------
--------------------------------------------------------------------------------
-- 시퀀스 설정
--------------------------------------------------------------------------------

-- 회원 번호용
CREATE SEQUENCE seq_user_no;

-- qna 게시글 번호용
CREATE SEQUENCE seq_qna_no;

-- qna 게시글 좋아요 번호용
CREATE SEQUENCE seq_qna_like_no;

-- qna 댓글 번호용 
CREATE SEQUENCE seq_qna_reply_no;

-- qna 카테고리 번호 용 
CREATE SEQUENCE seq_qna_category_no;

-- qna 댓글 좋아요 번호용 
CREATE SEQUENCE seq_qna_reply_like_no;

-- apps 게시글 번호용
CREATE SEQUENCE seq_apps_no;

-- apps 게시글 좋아요 번호용
CREATE SEQUENCE seq_apps_like_no;

-- apps 카테고리 번호 용 
CREATE SEQUENCE seq_apps_category_no;

-- tag 번호용
CREATE SEQUENCE seq_tag_no;


--------------------------------------------------------------------------------
--------------------------------------------------------------------------------
-- QNA 게시판 목록 출력을 위한 VIEW
-- 글번호, 카테고리이름, 작성일, 글제목, 작성자, 좋아요수, 댓글수, 글상태
--------------------------------------------------------------------------------
CREATE OR REPLACE VIEW QNA_LIST AS
    SELECT QNA_POST_ID, QNA_CATEGORY_ID, QNA_CATEGORY_NAME, TO_CHAR(QNA_DATE, 'YYYY-MM-DD HH24:MI:SS') QNA_DATE, 
               QNA_TITLE, USER_NICKNAME, QNA_LIKE, REPLY_COUNT, QNA_STATUS, USER_ID
    FROM QNA
    JOIN QNA_CATEGORIES USING(QNA_CATEGORY_ID)
    JOIN "USER" USING(USER_ID)
    LEFT JOIN (SELECT QNA_POST_ID, COUNT(*) REPLY_COUNT
                    FROM QNA_REPLY
                   WHERE REPLY_STATUS = 'Y'
                    GROUP BY QNA_POST_ID) USING(QNA_POST_ID)
;

--------------------------------------------------------------------------------
-- QNA 게시판 글 상세 조회 VIEW
-- 글번호, 카테고리 이름, 작성일, 제목, 작성자 닉네임, 좋아요 수, 댓글 수, 글상태, 내용, 회원번호
--------------------------------------------------------------------------------
CREATE OR REPLACE VIEW QNA_DETAIL AS
    SELECT QNA_POST_ID, QNA_CATEGORY_NAME, TO_CHAR(QNA_DATE, 'YYYY"년" MM"월" DD"일" HH24"시" MI"분"') QNA_DATE, 
               QNA_TITLE, USER_NICKNAME, QNA_LIKE, REPLY_COUNT, QNA_STATUS,
               QNA_CONTENT, USER_ID
    FROM QNA
    JOIN QNA_CATEGORIES USING(QNA_CATEGORY_ID)
    JOIN "USER" USING(USER_ID)
    LEFT JOIN (SELECT QNA_POST_ID, COUNT(*) REPLY_COUNT
                    FROM QNA_REPLY 
                    GROUP BY QNA_POST_ID) USING(QNA_POST_ID)
;
--------------------------------------------------------------------------------
-- 내 Qna 게시판 글 상세 조회 VIEW
-- 글번호, 글내용, 좋아요 수 , 댓글 수, 게시글 상태, 작성자 번호
--------------------------------------------------------------------------------
CREATE OR REPLACE VIEW MYPAGE_QNA_LIST AS
    SELECT QNA_POST_ID, QNA_TITLE, QNA_LIKE, REPLY_COUNT, QNA_STATUS, USER_ID
    FROM QNA
    JOIN QNA_CATEGORIES USING(QNA_CATEGORY_ID)
    JOIN "USER" USING(USER_ID)
    LEFT JOIN (SELECT QNA_POST_ID, COUNT(*) REPLY_COUNT
                    FROM QNA_REPLY 
	       	    WHERE REPLY_STATUS = 'Y'
                    GROUP BY QNA_POST_ID) USING(QNA_POST_ID);  

--------------------------------------------------------------------------------
-- Reply 게시판 글 상세 조회 VIEW
-- 댓글번호, 댓글내용, 댓글 좋아요수, 댓글 작성일, Qna게시판 번호, 작성자 번호, 작성자 닉네임, 댓글 상태 
--------------------------------------------------------------------------------
CREATE OR REPLACE VIEW QNA_REPLY_LIST AS
SELECT REPLY_ID, REPLY_CONTENT, REPLY_LIKE, TO_CHAR(REPLY_DATE, 'YYYY"년" MM"월" DD"일" HH24"시" MI"분"') REPLY_DATE,
QR.QNA_POST_ID, QR.USER_ID, USER_NICKNAME, REPLY_STATUS
FROM QNA_REPLY QR
JOIN QNA Q ON(Q.QNA_POST_ID = QR.QNA_POST_ID)
JOIN "USER" U ON(U.USER_ID = QR.USER_ID);

--------------------------------------------------------------------------------
--------------------------------------------------------------------------------
-- apps 게시판 목록 출력을 위한 VIEW
-- 글번호, 카테고리이름, 카테고리ID, 글 제목, 작성일, 아이콘 경로, 좋아요 수, 상태, 사용자 번호, 해시태그 이름, 해시태그 번호
--------------------------------------------------------------------------------
CREATE OR REPLACE VIEW apps_list AS
    SELECT apps_post_id, apps_category_name, apps_title, TO_CHAR(apps_date, 'YYYY-MM-DD HH24:MI:SS') apps_date, apps_icon, apps_like, apps_status, user_id, user_nickname, SUBSTR(apps_content,1,30) AS apps_content_substr, apps_category_id
    FROM apps
    JOIN apps_categories USING(apps_category_id)
    JOIN "USER" USING(user_id);
    
    
CREATE OR REPLACE VIEW apps_list AS
    SELECT apps_post_id, apps_category_name, apps_title, apps_date, apps_icon, apps_like, apps_status, user_id, user_nickname, SUBSTR(apps_content,1,30) AS apps_content_substr, apps_category_id
    FROM apps
    JOIN apps_categories USING(apps_category_id)
    JOIN "USER" USING(user_id);

SELECT apps_post_id, apps_category_name, apps_title, apps_date, apps_icon, apps_like, apps_status, user_id, user_nickname, SUBSTR(apps_content,1,30) AS apps_content_substr, COUNT()
FROM apps
JOIN apps_categories USING(apps_category_id)
JOIN "USER" USING(user_id);

SELECT * FROM apps_list WHERE apps_status = 'Y';
SELECT * FROM (
    SELECT ROWNUM RNUM, A.* FROM (
        SELECT * FROM apps_list
        WHERE apps_status = 'Y'
        ORDER BY apps_post_id DESC
    ) A
)
WHERE RNUM BETWEEN 1 AND 9;

SELECT COUNT(*) FROM apps_list WHERE apps_status = 'Y';

--------------------------------------------------------------------------------
-- apps 게시판 상세 출력을 위한 VIEW
-- 글번호, 카테고리이름, 카테고리ID, 글 제목, 작성일, 아이콘 경로, 좋아요 수, 상태, 사용자 번호, 해시태그 이름, 해시태그 번호
--------------------------------------------------------------------------------
CREATE OR REPLACE VIEW apps_view AS
    SELECT apps_post_id, apps_icon, apps_category_name, apps_title, user_nickname, apps_content, apps_url, apps_like, TO_CHAR(apps_date, 'YYYY"년" MM"월" DD"일" HH24"시" MI"분"') apps_date, user_id
    FROM apps 
    JOIN apps_categories USING(apps_category_id)
    JOIN "USER" USING(user_id);

CREATE OR REPLACE VIEW apps_view AS
    SELECT apps_post_id, apps_icon, apps_category_name, apps_title, user_nickname, apps_content, apps_url, apps_like, apps_date, user_id
    FROM apps 
    JOIN apps_categories USING(apps_category_id)
    JOIN "USER" USING(user_id);

--------------------------------------------------------------------------------
-- tags 샘플
--------------------------------------------------------------------------------
INSERT INTO tags VALUES(seq_tag_no.NEXTVAL, 'Javascript');
INSERT INTO tags VALUES(seq_tag_no.NEXTVAL, 'Java');
INSERT INTO tags VALUES(seq_tag_no.NEXTVAL, 'Oracle');
INSERT INTO tags VALUES(seq_tag_no.NEXTVAL, 'git');
COMMIT;
--------------------------------------------------------------------------------
-- apps category 샘플
--------------------------------------------------------------------------------
INSERT INTO apps_categories VALUES(seq_apps_category_no.NEXTVAL, 'OS');
INSERT INTO apps_categories VALUES(seq_apps_category_no.NEXTVAL, '개발툴');
INSERT INTO apps_categories VALUES(seq_apps_category_no.NEXTVAL, '라이브러리');
COMMIT;
--------------------------------------------------------------------------------
-- apps 샘플 게시물
--------------------------------------------------------------------------------
INSERT INTO apps 
VALUES(
    seq_apps_no.NEXTVAL, 
    '어떤 앱4', 
    '어떤 앱의 내용4', 
    'https://via.placeholder.com/300', 
    'https://naver.com', 
    DEFAULT, DEFAULT, DEFAULT, 
    2, 3
);
COMMIT;
--------------------------------------------------------------------------------
-- apps 샘플 태그
--------------------------------------------------------------------------------
INSERT INTO apps_tags VALUES(1, 1);
INSERT INTO apps_tags VALUES(1, 2);
INSERT INTO apps_tags VALUES(2, 3);
INSERT INTO apps_tags VALUES(2, 4);
INSERT INTO apps_tags VALUES(3, 1);
INSERT INTO apps_tags VALUES(3, 2);
INSERT INTO apps_tags VALUES(3, 3);
INSERT INTO apps_tags VALUES(3, 4);
INSERT INTO apps_tags VALUES(4, 4);
COMMIT;

--------------------------------------------------------------------------------
-- apps 게시물 별 태그 조회
--------------------------------------------------------------------------------
SELECT apps_tag_id, apps_tag_name FROM apps_tags JOIN tags USING(apps_tag_id) WHERE apps_post_id = 2;

--------------------------------------------------------------------------------
-- apps 게시글 삭제
--------------------------------------------------------------------------------
DELETE FROM apps WHERE apps_post_id = 55 AND user_id = 1;

--------------------------------------------------------------------------------
-- apps 게시글과 연결된 태그 삭제
--------------------------------------------------------------------------------
SELECT * FROM apps_tags WHERE apps_post_id = 55;
DELETE FROM apps_tags WHERE apps_post_id = 55;

--------------------------------------------------------------------------------
-- 관리자 메뉴 회원목록 출력을 위한 VIEW
--------------------------------------------------------------------------------
CREATE OR REPLACE VIEW USER_LIST AS
SELECT USER_ID, USER_EMAIL, USER_PW, USER_NICKNAME, USER_REGDATE, USER_IS_ADMIN
FROM "USER"
JOIN USER_INFO USING(USER_ID)
WHERE USER_EMAIL <> 'unreg';