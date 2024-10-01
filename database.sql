

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
    `point`     INT         NOT NULL
);

DROP TABLE IF EXISTS `userAsset`;
CREATE TABLE `userAsset`
(
    `assetNo`         INT AUTO_INCREMENT PRIMARY KEY,
    `userId`          VARCHAR(50)   NOT NULL,
    `consumeType`     VARCHAR(50)   NULL,
    `cashBank`        JSON          NULL,
    `cashAccount`     JSON          NULL,
    `cash`            JSON          NULL,
    `stockBank`       JSON          NULL,
    `stockAccount`    JSON          NULL,
    `stock`           JSON          NULL,
    `propertyBank`    JSON          NULL,
    `propertyAccount` JSON          NULL,
    `property`        JSON          NULL,
    `depositBank`     JSON          NULL,
    `depositAccount`  JSON          NULL,
    `deposit`         JSON          NULL,
    `consume`         VARCHAR(30)   NULL,
    `loanAmount`      INT           NULL,
    `loanPurpose`     VARCHAR(30)   NULL,
    `period`          INT           NULL,
    `interest`        DECIMAL(5, 2) NULL, -- 소수점 단위를 입력할 수 있도록 DECIMAL로 변경
    `createDate`      DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updateDate`      DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
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
    `rqutUrla`    varchar(1000) DEFAULT NULL
);

DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`
(
    `productNo`  INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `dclsMonth`  VARCHAR(45)  NULL,
    `korCoNm`    VARCHAR(45)  NULL,
    `finPrdtNm`  VARCHAR(45)  NULL,
    `joinWay`    VARCHAR(100) NULL,
    `mtrtInt`    VARCHAR(300) NULL,
    `spclCnd`    VARCHAR(300) NULL,
    `joinMember` VARCHAR(100) NULL,
    `etcNote`    VARCHAR(300) NULL,
    `type`       VARCHAR(45)  NOT NULL
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
    `newsNo`   int          NOT NULL primary Key,
    `title`    varchar(255) NOT NULL,
    `link`     varchar(255) NOT NULL,
    `category` varchar(50)  NOT NULL,
    `pubDate`  datetime     NOT NULL
);


# -----------------------------------------------------------------------------

insert into user (userId, nickName, userName, email, birthdate, point)
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
delete from board;
insert into board(title, content, userName, likes, comments, views)
values ('돈 잘 모으는 꿀팁 알려드림','돈을 많이 벌어야 합니다.','홍길동',30,0,51),
       ('연말정산 관련 정보 알려드림','연말 정산 관련 정보들입니다~','김하성',42,0,109),
       ('KB Star 정기예금 좋음','그렇다고 합니다','유재석',11,0,39),
       ('KB 맑은하늘 적금이 더 좋음','그렇다고 하네요','오타니',9,0,32),
       ('일주일에 5만원 쓰기 중','알뜰살뜰하네요','손흥민',15,0,50),
       ('방금 정책에 괜찮은 정책 떴음','정책 페이지로 가보세요','유재석',95,0,290),
       ('오늘 금융 뉴스 5개 읽었다','다들 뉴스보고 오세요','차범근',83,0,103),
       ('다들 왜이렇게 돈을 많이 모았냐?','나 평균보다 밑이네 열심히 모아야겠다','홍길동',139,0,482);

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

delete from news;

select * from userasset;
delete from userasset;

delete from board where title='일주일에 5만원 쓰기 중';
select * from user;
select * from board;
select * from userAsset;
select * from policy;



update user
set point = 1000
where userId = 3721153289;


SELECT * FROM userAsset WHERE userId = 3721153289
ORDER BY createDate DESC;

delete from
           userasset where
                         userId=3721153289;
INSERT INTO `userAsset` (
    `userId`,
    `cashBank`,
    `cashAccount`,
    `cash`,
    `stockBank`,
    `stockAccount`,
    `stock`,
    `propertyBank`,
    `propertyAccount`,
    `property`,
    `depositBank`,
    `depositAccount`,
    `deposit`,
    `consume`,
    `loanAmount`,
    `loanPurpose`,
    `period`,
    `interest`
) VALUES (
             '3721153289',
             '["우리은행", "신한은행"]',  -- 현금 은행 배열
             '["123-456-7890", "987-654-3210"]',  -- 현금 계좌 배열
             '["50000", "20000"]',  -- 현금 자산 배열
             '["미래에셋", "삼성증권"]',  -- 주식 은행 배열
             '["111-222-3333", "444-555-6666"]',  -- 주식 계좌 배열
             '["10000", "15000"]',  -- 주식 자산 배열
             '["국민은행", "부동산은행"]',  -- 부동산 은행 배열
             '["777-888-9999", "000-111-2222"]',  -- 부동산 계좌 배열
             '["300000", "400000"]',  -- 부동산 자산 배열
             '["농협은행", "하나은행"]',  -- 예적금 은행 배열
             '["333-444-5555", "666-777-8888"]',  -- 예적금 계좌 배열
             '["20000", "25000"]' ,  -- 예적금 자산 배열
             '소비유형1',
             2000000,
             '주택구입',
             24,
             3
         );

INSERT INTO `userAsset` (
    `userId`,
    `cashBank`,
    `cashAccount`,
    `cash`,
    `stockBank`,
    `stockAccount`,
    `stock`,
    `propertyBank`,
    `propertyAccount`,
    `property`,
    `depositBank`,
    `depositAccount`,
    `deposit`,
    `consume`,
    `loanAmount`,
    `loanPurpose`,
    `period`,
    `interest`,
    `createDate`,`updateDate`

) VALUES (
             '3721153289',
             '["우리은행", "신한은행"]',
             '["123-456-7890", "987-654-3210"]',
             '["45000", "18000"]',
             '["미래에셋", "삼성증권"]',
             '["111-222-3333", "444-555-6666"]',
             '["9000", "14000"]',
             '["국민은행", "부동산은행"]',
             '["777-888-9999", "000-111-2222"]',
             '["290000", "390000"]',
             '["농협은행", "하나은행"]',
             '["333-444-5555", "666-777-8888"]',
             '["18000", "23000"]',
             '소비유형1',
             2100000,
             '주택구입',
             25,
             3,
             DATE_SUB(CURRENT_DATE, INTERVAL 1 MONTH),
             DATE_SUB(CURRENT_DATE, INTERVAL 1 MONTH)
         );
INSERT INTO `userAsset` (
    `userId`,
    `cashBank`,
    `cashAccount`,
    `cash`,
    `stockBank`,
    `stockAccount`,
    `stock`,
    `propertyBank`,
    `propertyAccount`,
    `property`,
    `depositBank`,
    `depositAccount`,
    `deposit`,
    `consume`,
    `loanAmount`,
    `loanPurpose`,
    `period`,
    `interest`,
    `createDate`,`updateDate`

) VALUES (
             '3721153289',
             '["우리은행", "신한은행"]',
             '["123-456-7890", "987-654-3210"]',
             '["10000", "1000"]',
             '["미래에셋", "삼성증권"]',
             '["111-222-3333", "444-555-6666"]',
             '["5000", "10000"]',
             '["국민은행", "부동산은행"]',
             '["777-888-9999", "000-111-2222"]',
             '["20000", "30000"]',
             '["농협은행", "하나은행"]',
             '["333-444-5555", "666-777-8888"]',
             '["10000", "20000"]',
             '소비유형1',
             2100000,
             '주택구입',
             25,
             3,
             DATE_SUB(CURRENT_DATE, INTERVAL 5 MONTH),
             DATE_SUB(CURRENT_DATE, INTERVAL 5 MONTH)
         );
INSERT INTO `userAsset` (
    `userId`,
    `cashBank`,
    `cashAccount`,
    `cash`,
    `stockBank`,
    `stockAccount`,
    `stock`,
    `propertyBank`,
    `propertyAccount`,
    `property`,
    `depositBank`,
    `depositAccount`,
    `deposit`,
    `consume`,
    `loanAmount`,
    `loanPurpose`,
    `period`,
    `interest`
) VALUES (
             '3000220000',
             '["우리은행", "신한은행"]',  -- 현금 은행 배열
             '["123-456-7890", "987-654-3210"]',  -- 현금 계좌 배열
             '["100000", "110000"]',  -- 현금 자산 배열
             '["미래에셋", "삼성증권"]',  -- 주식 은행 배열
             '["111-222-3333", "444-555-6666"]',  -- 주식 계좌 배열
             '["30000", "2000"]',  -- 주식 자산 배열
             '["국민은행", "부동산은행"]',  -- 부동산 은행 배열
             '["777-888-9999", "000-111-2222"]',  -- 부동산 계좌 배열
             '["100000", "100000"]',  -- 부동산 자산 배열
             '["농협은행", "하나은행"]',  -- 예적금 은행 배열
             '["333-444-5555", "666-777-8888"]',  -- 예적금 계좌 배열
             '["10000", "1000"]' ,  -- 예적금 자산 배열
             '소비유형1',
             2000000,
             '주택구입',
             24,
             3
         );


select * from userasset;

SELECT * FROM userAsset
WHERE userId = '3721153289'
  AND createDate BETWEEN '2024-04-30 00:00:00' AND '2024-09-30 17:50:00'
ORDER BY createDate DESC;


ALTER TABLE `userAsset`
    MODIFY `cashBank` JSON NULL,
    MODIFY `cashAccount` JSON NULL,
    MODIFY `cash` JSON NULL,

    MODIFY `stockBank` JSON NULL,
    MODIFY `stockAccount` JSON NULL,
    MODIFY `stock` JSON NULL,

    MODIFY `propertyBank` JSON NULL,
    MODIFY `propertyAccount` JSON NULL,
    MODIFY `property` JSON NULL,

    MODIFY `depositBank` JSON NULL,
    MODIFY `depositAccount` JSON NULL,
    MODIFY `deposit` JSON NULL;

select * from news order by pubDate DESC;

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
    `interest`,
    `createDate`
) VALUES (
             '3721153289',
             '["우리은행", "신한은행", "국민은행"]',
             '["123-456-7890", "987-654-3210", "456-789-0123"]',
             '["300000", "30000", "2000"]',
             '["미래에셋", "삼성증권", "키움증권"]',
             '["111-222-3333", "444-555-6666", "777-888-9999"]',
             '["300000", "250000", "180000"]',
             '["농협은행", "하나은행", "카카오뱅크"]',
             '["333-444-5555", "666-777-8888", "999-000-1111"]',
             '["500000", "300000", "200000"]',
             '["삼성생명", "한화생명"]',
             '["종신보험", "암보험"]',
             '["1000000", "500000"]',
             '적극투자형',
             2500000,
             '주택구입',
             36,
             3.75,
             DATE_SUB(CURRENT_DATE, INTERVAL 1 MONTH)

         );

select * from userasset;

delete fuserasset
SELECT * FROM userAsset
WHERE userId = 3721153289
ORDER BY createDate DESC