USE solo_db;
SHOW TABLES;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `userId`    VARCHAR(50) NOT NULL PRIMARY KEY,
    `userName`  VARCHAR(50) NOT NULL,
    `nickName`  VARCHAR(50) NOT NULL,
    `email`     VARCHAR(50) NOT NULL,
    `birthdate` DATE        NOT NULL,
    `point`     INT         NOT NULL,
    `todayQuiz` INT DEFAULT 0
);
-- 부동산제거 / 보험 추가
DROP TABLE IF EXISTS userAsset;
CREATE TABLE `userAsset`
(
    `assetNo`           INT AUTO_INCREMENT PRIMARY KEY,
    `userId`            VARCHAR(50)   NOT NULL,
    `cashBank`          JSON          NULL,
    `cashAccount`       JSON          NULL,
    `cash`              JSON          NULL,
    `stockBank`         JSON          NULL,
    `stockAccount`      JSON          NULL,
    `stock`             JSON          NULL,
    `depositBank`       JSON          NULL,
    `depositAccount`    JSON          NULL,
    `deposit`           JSON          NULL,
    `insuranceCompany`  JSON          NULL,
    `insuranceName`     JSON          NULL,
    `insurance`         JSON          NULL,
    `type`              VARCHAR(30)   NULL,
    `loanAmount`        INT           NULL,
    `loanPurpose`       VARCHAR(30)   NULL,
    `period`            INT           NULL,
    `interest`          DECIMAL(5, 2) NULL, -- 소수점 단위를 입력할 수 있도록 DECIMAL로 변경
    `createDate`        DATETIME DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS `policy`;
CREATE TABLE `policy`
(
    `policyNo`    int          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `bizId`       varchar(100) NOT NULL,
    `polyBizTy`   varchar(100)  DEFAULT NULL,
    `polyBizSjnm` varchar(1000) DEFAULT NULL,
    `polyItcnCn`  varchar(2000) DEFAULT NULL,
    `sporCn`      varchar(3000) DEFAULT NULL,
    `rqutUrla`    varchar(1000) DEFAULT NULL,
    `polyRlmCd`   varchar(45)  NOT NULL
);

DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`
(
    `productNo`  INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `dclsMonth`  VARCHAR(45)  NULL,
    `finCoNo`    VARCHAR(45)  NULL,
    `korCoNm`    VARCHAR(45)  NULL,
    `finPrdtCd`  VARCHAR(45)  NULL,
    `finPrdtNm`  VARCHAR(45)  NULL,
    `joinWay`    VARCHAR(100) NULL,
    `mtrtInt`    VARCHAR(300) NULL,
    `spclCnd`    VARCHAR(300) NULL,
    `joinMember` VARCHAR(100) NULL,
    `etcNote`    VARCHAR(300) NULL,
    `type`       VARCHAR(45)  NOT NULL
);

DROP TABLE IF EXISTS `loan`;
CREATE TABLE `loan`
(
    `loanNo`      INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `dclsMonth`   VARCHAR(45)  NULL,
    `finCoNo`     VARCHAR(45)  NULL,
    `korCoNm`     VARCHAR(45)  NULL,
    `finPrdtCd`   VARCHAR(45)  NULL,
    `finPrdtNm`   VARCHAR(45)  NULL,
    `joinWay`     VARCHAR(100) NULL,
    `erlyRpayFee` VARCHAR(100) NULL,
    `dlyRate`     VARCHAR(100)  NULL,
    `loanLmt`     VARCHAR(100)  NULL
);

DROP TABLE IF EXISTS `option`;
CREATE TABLE `option`
(
    `optionNo`  INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `dclsMonth` VARCHAR(45) NULL,
    `finCoNo`   VARCHAR(45) NULL,
    `finPrdtCd` VARCHAR(45) NULL,
    `saveTrm`   VARCHAR(45) NULL,
    `intrRate`  DECIMAL(10, 2) NULL,
    `intrRate2` DECIMAL(10, 2) NULL,
    `type`      VARCHAR(45) NOT NULL
);

# board 테이블을 삭제하고 새로 만드려면 boardAttachment, like, comment 역시 새로 만들어줘야함, 외래키를 없앴기 때문..
DROP TABLE IF EXISTS `board`;
CREATE TABLE `board`
(
    `boardNo`    INTEGER AUTO_INCREMENT PRIMARY KEY,
    `title`      VARCHAR(200) NOT NULL,
    `content`    TEXT,
    `userName`   VARCHAR(50)  NOT NULL,
    `regDate`    DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updateDate` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `likes`      INTEGER  DEFAULT 0,
    `comments`   INTEGER  DEFAULT 0,
    `views`      INTEGER  DEFAULT 0
);

DROP TABLE IF EXISTS `boardAttachment`;
CREATE TABLE `boardAttachment`
(
    `attachmentNo` INTEGER AUTO_INCREMENT PRIMARY KEY,
    `bno`          INTEGER      NOT NULL, -- 게시글 번호, FK
    `filename`     VARCHAR(256) NOT NULL, -- 원본 파일 명
    `path`         VARCHAR(256) NOT NULL, -- 서버에서의 파일 경로
    `contentType`  VARCHAR(56),           -- content-type
    `size`         INTEGER,               -- 파일의 크기
    `regDate`      DATETIME DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`
(
    `commentNo`   INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `boardNo`     INT         NOT NULL,
    `userName`      VARCHAR(50) NOT NULL,
    `commentText` TEXT        NOT NULL,
    `regDate`     DATETIME DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS `like`;
CREATE TABLE `like`
(
    `likeNo`    INT         NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    `boardNo`   INT         NOT NULL,
    `userName`    VARCHAR(50) NOT NULL
);

DROP TABLE IF EXISTS `news`;
CREATE TABLE `news`
(
    `newsNo`   INT          NOT NULL PRIMARY KEY ,
    `title`    VARCHAR(255) NOT NULL,
    `link`     VARCHAR(255) NOT NULL,
    `category` VARCHAR(50)  NOT NULL,
    `pubDate`  DATETIME     NOT NULL
);

DROP TABLE IF EXISTS `quiz`;
CREATE TABLE `quiz`
(
    `quizNo`      INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `term`        VARCHAR(100) NOT NULL,
    `description` TEXT         NOT NULL
);



# -----------------------------------------------------------------------------

select * from userAsset;

INSERT INTO `userAsset` (
    `userId`,
    `cashBank`,
    `cashAccount`,
    `cash`,
    `stockBank`,
    `stockAccount`,
    `stock`,
    `depositBank`,
    `depositAccount`,
    `deposit`,
    `insuranceCompany`,
    `insuranceName`,
    `insurance`,
    `loanAmount`,
    `loanPurpose`,
    `period`,
    `interest`,
    `createDate`
) VALUES
      (
          '3704999150',
          '["우리은행", "신한은행"]',
          '["123-456-7890", "987-654-3210"]',
          '[50000, 20000]',
          '["미래에셋", "삼성증권"]',
          '["111-222-3333", "444-555-6666"]',
          '[10000, 15000]',
          '["농협은행", "하나은행"]',
          '["333-444-5555", "666-777-8888"]',
          '[20000, 25000]',
          '["KB손해보험"]',
          '["KB 빅플러스저축보험"]',
          '[400000]',
          2000000,
          '주택구입',
          24,
          3.2,
          '2024-09-15 14:30:00'
      ),
      (
          '3704999150',
          '["하나은행", "국민은행"]',
          '["123-456-1111", "987-654-2222"]',
          '["30000", "15000"]',
          '["NH투자증권", "유안타증권"]',
          '["111-222-4444", "444-555-7777"]',
          '["8000", "12000"]',
          '["기업은행", "농협은행"]',
          '["333-444-1111", "666-777-2222"]',
          '["15000", "20000"]',
          '["삼성생명"]',
          '["삼성암보험"]',
          '["300000"]',
          1500000,
          '자동차구입',
          36,
          4.5,
          '2024-06-10 09:15:00'
      ),
      (
          '3704999150',
          '["신한은행", "우리은행"]',
          '["123-456-3333", "987-654-4444"]',
          '["40000", "25000"]',
          '["신영증권", "미래에셋"]',
          '["111-222-8888", "444-555-9999"]',
          '["12000", "18000"]',
          '["하나은행", "기업은행"]',
          '["333-444-3333", "666-777-4444"]',
          '["30000", "35000"]',
          '["DB손해보험"]',
          '["DB 건강보험"]',
          '["600000"]',
          2500000,
          '학자금대출',
          12,
          5.0,
          '2024-07-05 16:45:00'
      ),
      (
          '3704999150',
          '["국민은행", "기업은행"]',
          '["123-456-5555", "987-654-6666"]',
          '["70000", "10000"]',
          '["대신증권", "하나금융"]',
          '["111-222-0000", "444-555-1111"]',
          '["25000", "10000"]',
          '["신한은행", "농협은행"]',
          '["333-444-9999", "666-777-8888"]',
          '["50000", "15000"]',
          '["미래에셋"]',
          '["미래에셋 연금보험"]',
          '["500000"]',
          3000000,
          '주택임대',
          60,
          2.8,
          '2024-08-20 11:00:00'
      ),
      (
          '3704999150',
          '["하나은행", "신한은행"]',
          '["123-456-7777", "987-654-8888"]',
          '["60000", "30000"]',
          '["한국투자증권", "삼성증권"]',
          '["111-222-3333", "444-555-6666"]',
          '["20000", "25000"]',
          '["우리은행", "국민은행"]',
          '["333-444-2222", "666-777-3333"]',
          '["45000", "15000"]',
          '["삼성생명"]',
          '["삼성보험"]',
          '["350000"]',
          1800000,
          '교육자금',
          48,
          3.5,
          '2024-09-30 08:30:12'
      );


select * from policy where polyRlmCd = '참여권리';

select * from loan;