
create table kakaoMember (
    kakaoId      varchar(100) not null primary key,
    nickName     varchar(50)  not null,
    profileImage varchar(500) null,
    name         varchar(50)  not null,
    email        varchar(100) not null,
    birthdate    date         not null
);

CREATE TABLE user (
    userID	    varchar(30)	NOT NULL primary Key,
    pw		    varchar(30)	NULL,
    name	    varchar(10)	NULL,
    email	    varchar(30)	NULL,
    birth	    varchar(30)	NULL,
    nickName	varchar(30)	NULL,
    point 	    int	        NULL,
    grade	    varchar(10)	NULL,
    access	    boolean	    NULL
);

CREATE TABLE userData (
                          userNo	    int	                NOT NULL    primary Key    auto_Increment,
                          userID	    varchar(30)	        NOT NULL,
                          salary	    int	                NULL,
                          cash	    int	                NULL,
                          stock	    int	                NULL,
                          property    int	                NULL,
                          deposit	    int	                NULL,
                          consume	    varchar(30)	        NULL,
                          job	        varchar(30)	        NULL,
                          address	    varchar(30)	        NULL,
                          foreign key(userID) references user(userID)
);

-- user
insert into user(userID, pw, name, email, birth, nickName, point, grade, access)
values
    ('user0', '1234','user0','user0@mail', '2000-10-10', 'u', 0,  '초보솔로', 0),
    ('user1', '1234','user1','user1@mail', '2010-10-10', 'u1', 0,  '중급솔로', 0);

CREATE TABLE board (
   boardNo	int                     NOT NULL    primary Key     auto_Increment,
   userID	varchar(30)	            NOT NULL,
   title	varchar(300)	        NULL,
   content	varchar(3000)	        NULL,
   regDate	date	                NULL,
   modDate	date	                NULL,
   likes	int	                    NULL,
   views	int	                    NULL,
   foreign key(userID) references user(userID)
);

# CREATE TABLE board_attachment (
#     no              int             NOT NULL    primary key     auto_Increment,
#     userID          varchar(30)     NOT NULL,
#     filename        varchar(256)    NOT NULL,
#     path            varchar(256)    NOT NULL,
#     content_type    varchar(56),
#     size            integer,
#     boardNo         integer         NOT NULL,
#     reg_date        DATETIME        default now(),
#     CONSTRAINT      FOREIGN KEY (boardNo) REFERENCES board(boardNo)
# );

CREATE TABLE comment (
     commentNo   int             NOT NULL    primary Key     auto_Increment,
     boardID	 int	         NOT NULL,
     userID	     varchar(30)	 NOT NULL,
     commentID   varchar(30)	 NULL,
     content	 varchar(1000)	 NULL,
     regDate	 date            NULL,
     foreign key(userID) references user(userID)
);

-- board
insert into board(userID, title, content, regDate, modDate, likes, views)
values
    ('user0', '첫번째 글 제목', '내용입니다', now(),now(), 0, 0),
    ('user0', '두번째 글 제목', '내용입니다', now(),now(), 0, 0),
    ('user1', '세번째 글 제목', '내용입니다', now(),now(), 0, 0);
insert into board(userID, title, content, regDate, modDate, likes, views)
    values('user0', '네번째 글 제목', '내용입니다', now(),now(), 0, 0);

-- comment
insert into comment(boardID, userID, commentID, content, regDate)
values
    (1, 'user0', 'user1', '좋은글입니다', now()),
    (2, 'user0', 'user1', '좋은글입니다2', now()),
    (3, 'user1', 'user0', '좋은글입니다3', now()),
    (4, 'user1', 'user1', '제가 썼습니다', now());

SELECT * FROM board;

SELECT * FROM board_attachment;

SELECT * FROM comment;

# SELECT b.*, ba.no as baNo, ba.boardNo, ba.filename, ba.path, ba.content_type, ba.size, ba.reg_date as ba_reg_date
#     FROM board b left outer join board_attachment ba
#     on b.boardNo = ba.boardNo
# where b.boardNo = 23
# order by filename;

select sysdate() from dual;
