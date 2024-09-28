USE solo_db;
SHOW TABLES;

# ########
DROP TABLE IF EXISTS user;
CREATE TABLE `user`
(
    `userId`    VARCHAR(50) NOT NULL PRIMARY KEY,
    `nickName`  VARCHAR(50) NOT NULL,
    `name`      VARCHAR(50) NOT NULL,
    `email`     VARCHAR(50) NOT NULL,
    `birthdate` DATE        NOT NULL,
    `point`     INT         NOT NULL
);

DROP TABLE IF EXISTS userAsset;

CREATE TABLE `userAsset`
(
    `userId`      VARCHAR(50) NOT NULL PRIMARY KEY,
    `cash`        INT         NULL,
    `stock`       INT         NULL,
    `property`    INT         NULL,
    `deposit`     INT         NULL,
    `consume`     VARCHAR(30) NULL,
    `loanAmount`  INT         NULL,
    `loanPurpose` VARCHAR(30) NULL,
    `period`      INT         NULL,
    `createDate`  DATETIME DEFAULT CURRENT_TIMESTAMP, -- 생성 시간을 저장하는 컬럼
    `updateDate`  DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (userId) REFERENCES user (userId)
);

-- 변경예정
CREATE TABLE `userAsset`
(
    `assetNo`     INT         AUTO_INCREMENT PRIMARY KEY,
    `userId`      VARCHAR(50) NOT NULL,
    `cash`        INT         NULL,
    `stock`       INT         NULL,
    `property`    INT         NULL,
    `deposit`     INT         NULL,
    `consume`     VARCHAR(30) NULL,
    `loanAmount`  INT         NULL,
    `loanPurpose` VARCHAR(30) NULL,
    `period`      INT         NULL,
    `createDate`  DATETIME DEFAULT CURRENT_TIMESTAMP, -- 생성 시간을 저장하는 컬럼
    `updateDate`  DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (userId) REFERENCES user (userId)
);

DROP TABLE IF EXISTS policy;
CREATE TABLE `policy`
(
    `policyNo`    int          NOT NULL AUTO_INCREMENT,
    `bizId`       varchar(100) NOT NULL,
    `polyBizTy`   varchar(100)  DEFAULT NULL,
    `polyBizSjnm` varchar(1000) DEFAULT NULL,
    `polyItcnCn`  varchar(2000) DEFAULT NULL,
    `sporCn`      varchar(3000) DEFAULT NULL,
    `rqutUrla`    varchar(1000) DEFAULT NULL,
    PRIMARY KEY (`policyNo`)
);

DROP TABLE IF EXISTS product;
CREATE TABLE `product`
(
    `productNo`  INT          NOT NULL AUTO_INCREMENT,
    `dclsMonth`  VARCHAR(45)  NULL,
    `korCoNm`    VARCHAR(45)  NULL,
    `finPrdtNm`  VARCHAR(45)  NULL,
    `joinWay`    VARCHAR(100) NULL,
    `mtrtInt`    VARCHAR(300) NULL,
    `spclCnd`    VARCHAR(300) NULL,
    `joinMember` VARCHAR(100) NULL,
    `etcNote`    VARCHAR(300) NULL,
    `type`       VARCHAR(45)  NOT NULL,
    PRIMARY KEY (`productNo`)
);

DROP TABLE IF EXISTS board;
CREATE TABLE `board`
(
    `boardNo`    INTEGER AUTO_INCREMENT PRIMARY KEY,
    `title`      VARCHAR(200) NOT NULL,
    `content`    TEXT,
    `userId`     VARCHAR(50)  NOT NULL,
    `regDate`    DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updateDate` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `likes`      INTEGER  DEFAULT 0,
    `comments`   INTEGER  DEFAULT 0,
    `views`      INTEGER  DEFAULT 0,
    FOREIGN KEY (userId) REFERENCES user (userId)
);

DROP TABLE IF EXISTS boardAttachment;
CREATE TABLE `boardAttachment`
(
    `attachmentNo` INTEGER AUTO_INCREMENT PRIMARY KEY,
    `bno`          INTEGER      NOT NULL, -- 게시글 번호, FK
    `filename`     VARCHAR(256) NOT NULL, -- 원본 파일 명
    `path`         VARCHAR(256) NOT NULL, -- 서버에서의 파일 경로
    `contentType`  VARCHAR(56),           -- content-type
    `size`         INTEGER,               -- 파일의 크기
    `regDate`      DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT FOREIGN KEY (bno) REFERENCES board (boardNo)
);

DROP TABLE IF EXISTS comment;
CREATE TABLE `comment`
(
    `commentNo`   INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `boardNo`     INT         NOT NULL,
    `userId`      VARCHAR(50) NOT NULL,
    `commentText` TEXT        NOT NULL,
    `regDate`     DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (userId) REFERENCES user (userId),
    FOREIGN KEY (boardNo) REFERENCES board (boardNo) ON DELETE CASCADE
);

DROP TABLE IF EXISTS news;
CREATE TABLE news (
    `no` int NOT NULL primary Key,
    `title` varchar(255) NOT NULL,
    `link` varchar(255) NOT NULL,
    `category` varchar(50) NOT NULL,
    `author` varchar(255) NOT NULL,
    `pubDate` datetime NOT NULL,
    `description` varchar(4000) DEFAULT NULL
);
-- 변경예정
CREATE TABLE news (
                      `no` int NOT NULL primary Key,
                      `title` varchar(255) NOT NULL,
                      `link` varchar(255) NOT NULL,
                      `category` varchar(50) NOT NULL,
                      `author` varchar(255) NOT NULL,
                      `pubDate` datetime NOT NULL,
);


DROP TABLE IF EXISTS `like`;
CREATE TABLE `like`
(
    `likeNo`    INT         NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    `boardNo`   INT         NOT NULL,
    `userId`    VARCHAR(50) NOT NULL,
    FOREIGN KEY (boardNo) REFERENCES board (boardNo) ON DELETE CASCADE,
    FOREIGN KEY (userId) REFERENCES user (userId)
);


# -----------------------------------------------------------------------------

insert into user (userId, nickName, name, email, birthdate, point)
values ('5704999188','오타니', '오타니', 'oh@188','2022-01-01',0),
        ('9702399454','홍길동', '홍길동', 'hong@454','2013-03-21',0),
       ('3101219225','박지성', '박지성', 'park@225','2007-05-19',0),
       ('6304009156','손흥민', '손흥민', 'son@156','2020-12-25',0),
       ('2004991237','김하성', '김하성', 'kim@237','2009-03-30',0),
       ('1004539485','유재석', '유재석', 'you@485','2004-01-19',0),
       ('8704441237','차범근', '차범근', 'cha@237','2009-03-30',0);


insert into board(title, content, userId, likes, comments, views)
values ('오늘 로또 당첨 되는 꿈을 꿈', '돼지가 나와서 나를 똥에 밀어넣음', '8704441237', '100', 3, 350),
       ('사회 초년생의 부자 되는 방법1', '소비 지출 내역을 기록해서 절약 패턴 찾기', '1004539485', '30', 0, 100),
       ('사회 초년생의 부자 되는 방법2', '지출 내역에 카테고리를 분류해서 필요한 지출 고르기', '1004539485', '35', 0, 120),
       ('사회 초년생의 부자 되는 방법3', '고정 지출과 유동 지출을 분류해서 1년 계획 세우기', '1004539485', '35', 0, 120);


INSERT INTO user (userId, nickName, name, email, birthdate, point)
VALUES ('2004991238','sung_guen', '김성근', 'kimSungen@157','1955-09-05',0),
       ('6304009176','gwang_gill', '이광길', 'lee@021','1968-08-24',0),
       ('1004539355','오세훈짱', '정우성', 'jung@023','2002-07-11',0),
       ('4803392224','야구천재', '송승준', 'song@156','2000-11-29',0),
       ('9204451541','느림의미학', '유희관', 'lyu@128','1997-06-15',0);

insert into board(title, content, userId, likes, comments, views)
values ('돈 잘 모으는 꿀팁 알려드림','돈을 많이 벌어야 합니다.','9702399454',30,0,51),
       ('연말정산 관련 정보 알려드림','연말 정산 관련 정보들입니다~','2004991237',42,0,109),
       ('KB Star 정기예금 좋음','그렇다고 합니다','1004539485',11,0,39),
       ('KB 맑은하늘 적금이 더 좋음','그렇다고 하네요','5704999188',9,0,32),
       ('일주일에 5만원 쓰기 중','알뜰살뜰하네요','6304009156',15,0,50),
       ('방금 정책에 괜찮은 정책 떴음','정책 페이지로 가보세요','1004539485',95,0,290),
       ('오늘 금융 뉴스 5개 읽었다','다들 뉴스보고 오세요','8704441237',83,0,103),
       ('다들 왜이렇게 돈을 많이 모았냐?','나 평균보다 밑이네 열심히 모아야겠다','9702399454',139,0,482);

insert into board(title, content, userId, likes, comments, views)
values ('이 서비스 좀 좋은 듯','그렇지 않나요?','9702399454',0,0,0),
       ('자산 입력할 때 돈 없어서 슬펐음','열심히 모아야겠다 ','2004991237',0,0,0),
       ('적금 써본 거 중에 좋은 거 추천함','KB맑은 하늘 적금 추천함','1004539485',0,0,0),
       ('돈 모으는 법 알려주실 분','도와주세요','6304009156',0,0,0),
       ('일주일에 5만원 쓰기 중','알뜰살뜰하네요','6304009156',15,0,50),
       ('방금 정책에 괜찮은 정책 떴음','정책 페이지로 가보세요','1004539485',95,0,290),
       ('오늘 금융 뉴스 5개 읽었다','다들 뉴스보고 오세요','8704441237',83,0,103),
       ('다들 왜이렇게 돈을 많이 모았냐?','나 평균보다 밑이네 열심히 모아야겠다','9702399454',139,0,482);

INSERT INTO board(title, content, userId, likes, comments, views)
VALUES ('주식 투자 시작하는 방법','처음 주식을 시작하는 분들을 위한 가이드입니다.','1004539485', 0, 0, 0),
       ('가계부를 잘 쓰는 방법','가계부 작성 꿀팁을 공유합니다.','8704441237', 0, 0, 0),
       ('금리 인상에 따른 대출 전략','금리 인상에 대처하는 대출 관리 팁입니다.','6304009156', 76, 0,0 ),
       ('비상금 만들기, 어떻게 시작할까?','비상금을 준비하는 방법과 그 필요성에 대해 설명합니다.','2004991237', 30, 0, 80),
       ('월급 관리 이렇게 하세요','월급을 효과적으로 관리하는 5가지 팁을 공유합니다.','1004539485', 94, 0, 231);

INSERT INTO board(title, content, userId, likes, comments, views)
VALUES ('일주일에 5만원 쓰기 중','알뜰살뜰하네요','6304009156',15,0,50);
select *
from user;

select * from news;
select * from userasset;

delete from userasset;

select * from userasset;
delete from userasset;

delete from board where title='일주일에 5만원 쓰기 중';
select * from user;
select * from board;
select * from userAsset;
select * from policy;

update user
set point = 1000
where userId = 3711364352;


delete from news;
select * from policy;
select count(*) from news;