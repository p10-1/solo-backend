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
    `views`      INTEGER  DEFAULT 0
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
    FOREIGN KEY (boardNo) REFERENCES board (boardNo) ON DELETE CASCADE
);



# ------------------------------------------------------------------------

SELECT *
FROM board
WHERE userId LIKE '%3%'
ORDER BY regDate DESC

select * from board;