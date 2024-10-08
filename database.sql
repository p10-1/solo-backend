# USE solo_db;
# SHOW TABLES;
#
# DROP TABLE IF EXISTS `user`;
# CREATE TABLE `user`
# (
#     `userId`    VARCHAR(50) NOT NULL PRIMARY KEY,
#     `userName`  VARCHAR(50) NOT NULL,
#     `nickName`  VARCHAR(50) NOT NULL,
#     `email`     VARCHAR(50) NOT NULL,
#     `birthdate` DATE        NOT NULL,
#     `point`     INT         NOT NULL,
#     `todayQuiz` INT DEFAULT 0
# );
# -- 부동산제거 / 보험 추가
# DROP TABLE IF EXISTS userAsset;
# CREATE TABLE `userAsset`
# (
#     `assetNo`           INT AUTO_INCREMENT PRIMARY KEY,
#     `userId`            VARCHAR(50)   NOT NULL,
#     `cashBank`          JSON          NULL,
#     `cashAccount`       JSON          NULL,
#     `cash`              JSON          NULL,
#     `stockBank`         JSON          NULL,
#     `stockAccount`      JSON          NULL,
#     `stock`             JSON          NULL,
#     `depositBank`       JSON          NULL,
#     `depositAccount`    JSON          NULL,
#     `deposit`           JSON          NULL,
#     `insuranceCompany`  JSON          NULL,
#     `insuranceName`     JSON          NULL,
#     `insurance`         JSON          NULL,
#     `type`              VARCHAR(30)   NULL,
#     `loanAmount`        INT           NULL,
#     `loanPurpose`       VARCHAR(30)   NULL,
#     `period`            INT           NULL,
#     `interest`          DECIMAL(5, 2) NULL, -- 소수점 단위를 입력할 수 있도록 DECIMAL로 변경
#     `createDate`        DATETIME DEFAULT CURRENT_TIMESTAMP
# );
#
# DROP TABLE IF EXISTS `policy`;
# CREATE TABLE `policy`
# (
#     `policyNo`    int          NOT NULL AUTO_INCREMENT PRIMARY KEY,
#     `bizId`       varchar(100) NOT NULL,
#     `polyBizTy`   varchar(100)  DEFAULT NULL,
#     `polyBizSjnm` varchar(1000) DEFAULT NULL,
#     `polyItcnCn`  varchar(2000) DEFAULT NULL,
#     `sporCn`      varchar(3000) DEFAULT NULL,
#     `rqutUrla`    varchar(1000) DEFAULT NULL,
#     `polyRlmCd`   varchar(45)  NOT NULL
# );
#
# DROP TABLE IF EXISTS `product`;
# CREATE TABLE `product`
# (
#     `productNo`  INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
#     `dclsMonth`  VARCHAR(45)  NULL,
#     `finCoNo`    VARCHAR(45)  NULL,
#     `korCoNm`    VARCHAR(45)  NULL,
#     `finPrdtCd`  VARCHAR(45)  NULL,
#     `finPrdtNm`  VARCHAR(45)  NULL,
#     `joinWay`    VARCHAR(100) NULL,
#     `mtrtInt`    VARCHAR(300) NULL,
#     `spclCnd`    VARCHAR(300) NULL,
#     `joinMember` VARCHAR(100) NULL,
#     `etcNote`    VARCHAR(300) NULL,
#     `type`       VARCHAR(45)  NOT NULL
# );
#
# DROP TABLE IF EXISTS `loan`;
# CREATE TABLE `loan`
# (
#     `loanNo`      INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
#     `dclsMonth`   VARCHAR(45)  NULL,
#     `finCoNo`     VARCHAR(45)  NULL,
#     `korCoNm`     VARCHAR(45)  NULL,
#     `finPrdtCd`   VARCHAR(45)  NULL,
#     `finPrdtNm`   VARCHAR(45)  NULL,
#     `joinWay`     VARCHAR(100) NULL,
#     `erlyRpayFee` VARCHAR(100) NULL,
#     `dlyRate`     VARCHAR(100)  NULL,
#     `loanLmt`     VARCHAR(100)  NULL
# );
#
# DROP TABLE IF EXISTS `option`;
# CREATE TABLE `option`
# (
#     `optionNo`  INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
#     `dclsMonth` VARCHAR(45) NULL,
#     `finCoNo`   VARCHAR(45) NULL,
#     `finPrdtCd` VARCHAR(45) NULL,
#     `saveTrm`   VARCHAR(45) NULL,
#     `intrRate`  DECIMAL(10, 2) NULL,
#     `intrRate2` DECIMAL(10, 2) NULL,
#     `type`      VARCHAR(45) NOT NULL
# );
#
# # board 테이블을 삭제하고 새로 만드려면 boardAttachment, like, comment 역시 새로 만들어줘야함, 외래키를 없앴기 때문..
# DROP TABLE IF EXISTS `board`;
# CREATE TABLE `board`
# (
#     `boardNo`    INTEGER AUTO_INCREMENT PRIMARY KEY,
#     `title`      VARCHAR(200) NOT NULL,
#     `content`    TEXT,
#     `userName`   VARCHAR(50)  NOT NULL,
#     `regDate`    DATETIME DEFAULT CURRENT_TIMESTAMP,
#     `updateDate` DATETIME DEFAULT CURRENT_TIMESTAMP,
#     `likes`      INTEGER  DEFAULT 0,
#     `comments`   INTEGER  DEFAULT 0,
#     `views`      INTEGER  DEFAULT 0
# );
#
# DROP TABLE IF EXISTS `boardAttachment`;
# CREATE TABLE `boardAttachment`
# (
#     `attachmentNo` INTEGER AUTO_INCREMENT PRIMARY KEY,
#     `bno`          INTEGER      NOT NULL, -- 게시글 번호, FK
#     `filename`     VARCHAR(256) NOT NULL, -- 원본 파일 명
#     `path`         VARCHAR(256) NOT NULL, -- 서버에서의 파일 경로
#     `contentType`  VARCHAR(56),           -- content-type
#     `size`         INTEGER,               -- 파일의 크기
#     `regDate`      DATETIME DEFAULT CURRENT_TIMESTAMP
# );
#
# DROP TABLE IF EXISTS `comment`;
# CREATE TABLE `comment`
# (
#     `commentNo`   INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
#     `boardNo`     INT         NOT NULL,
#     `userName`      VARCHAR(50) NOT NULL,
#     `commentText` TEXT        NOT NULL,
#     `regDate`     DATETIME DEFAULT CURRENT_TIMESTAMP
# );
#
# DROP TABLE IF EXISTS `like`;
# CREATE TABLE `like`
# (
#     `likeNo`    INT         NOT NULL AUTO_INCREMENT PRIMARY KEY ,
#     `boardNo`   INT         NOT NULL,
#     `userName`    VARCHAR(50) NOT NULL
# );
#
# DROP TABLE IF EXISTS `news`;
# CREATE TABLE `news`
# (
#     `newsNo`   INT          NOT NULL PRIMARY KEY,
#     `title`    VARCHAR(255) NOT NULL,
#     `link`     VARCHAR(255) NOT NULL,
#     `category` VARCHAR(50)  NOT NULL,
#     `pubDate`  DATETIME     NOT NULL,
#     `imageUrl` VARCHAR(255) NULL
# );
#
# DROP TABLE IF EXISTS `quiz`;
# CREATE TABLE `quiz`
# (
#     `quizNo`      INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
#     `term`        VARCHAR(100) NOT NULL,
#     `description` TEXT         NOT NULL
# );



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
    `type`,
    `loanAmount`,
    `loanPurpose`,
    `period`,
    `interest`
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
        '자산 분산형',
        2000000,
        '주택구입',
        24,
        3.2
    );


select * from userAsset;


SELECT *
#     SUM(CAST(value AS UNSIGNED)) AS total_cash
FROM userAsset,
     JSON_TABLE(cash, '$[*]' COLUMNS (cash_sum JSON PATH '$')) AS ct,
     JSON_TABLE(stock, '$[*]' COLUMNS (stock_sum JSON PATH '$')) AS st,
     JSON_TABLE(deposit, '$[*]' COLUMNS (deposit_sum JSON PATH '$')) AS dt,
     JSON_TABLE(insurance, '$[*]' COLUMNS (insurance_sum JSON PATH '$')) AS it
WHERE type = '자산 분산형';

SELECT SUM(JSON_UNQUOTE(JSON_EXTRACT(cash, '$[*]'))) AS total_sum
FROM userAsset;

