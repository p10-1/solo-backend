USE solo_db;
SHOW TABLES;

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
    `userId`      VARCHAR(30) NOT NULL PRIMARY KEY,
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
CREATE TABLE board
(
    boardNo    INTEGER AUTO_INCREMENT PRIMARY KEY,
    title      VARCHAR(200) NOT NULL,
    content    TEXT,
    userId     VARCHAR(50)  NOT NULL,
    regDate    DATETIME DEFAULT CURRENT_TIMESTAMP,
    updateDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    likes      INTEGER  DEFAULT 0,
    comments   INTEGER  DEFAULT 0,
    views      INTEGER  DEFAULT 0
);

DROP TABLE IF EXISTS boardAttachment;
CREATE TABLE boardAttachment
(
    attachmentNo INTEGER AUTO_INCREMENT PRIMARY KEY,
    bno          INTEGER      NOT NULL, -- 게시글 번호, FK
    filename     VARCHAR(256) NOT NULL, -- 원본 파일 명
    path         VARCHAR(256) NOT NULL, -- 서버에서의 파일 경로
    contentType  VARCHAR(56),           -- content-type
    size         INTEGER,               -- 파일의 크기
    regDate      DATETIME DEFAULT CURRENT_TIMESTAMP,
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
    FOREIGN KEY (boardNo) REFERENCES board (boardNo) ON DELETE CASCADE
);

DROP TABLE IF EXISTS news;
DROP TABLE IF EXISTS quizs;

insert into board(title, content, userId, likes, comments, views)
values ('오늘 로또 당첨 되는 꿈을 꿈', '돼지가 나와서 나를 똥에 밀어넣음', '8704441237', '100', 3, 350),
       ('사회 초년생의 부자 되는 방법1', '소비 지출 내역을 기록해서 절약 패턴 찾기', '1004539485', '30', 0, 100),
       ('사회 초년생의 부자 되는 방법2', '지출 내역에 카테고리를 분류해서 필요한 지출 고르기', '1004539485', '35', 0, 120),
       ('사회 초년생의 부자 되는 방법3', '고정 지출과 유동 지출을 분류해서 1년 계획 세우기', '1004539485', '35', 0, 120),
       ('사회 초년생의 부자 되는 방법4', '수정중', '1004539485', '40', 0, 150),
       ('사회 초년생의 부자 되는 방법5', '수정중', '1004539485', '42', 0, 158);
INSERT INTO user (userId, nickName, name, email, birthdate, point)
VALUES ('2004991238','sung_guen', '김성근', 'kimSungen@157','1955-09-05',0),
       ('6304009176','gwang_gill', '이광길', 'lee@021','1968-08-24',0),
       ('1004539355','오세훈짱', '정우성', 'jung@023','2002-07-11',0),
       ('4803392224','야구천재', '송승준', 'song@156','2000-11-29',0),
       ('9204451541','느림의미학', '유희관', 'lyu@128','1997-06-15',0);