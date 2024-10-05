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
    `rqutUrla`    varchar(1000) DEFAULT NULL
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

insert into user (userId, nickName, userName, email, birthdate, point)
values ('5704999188','오타니', '오타니', 'oh@188','2022-01-01',0),
       ('9702399454','홍길동', '홍길동', 'hong@454','2013-03-21',0),
       ('3101219225','박지성', '박지성', 'park@225','2007-05-19',0),
       ('6304009156','손흥민', '손흥민', 'son@156','2020-12-25',0),
       ('2004991237','김하성', '김하성', 'kim@237','2009-03-30',0),
       ('1004539485','유재석', '유재석', 'you@485','2004-01-19',0),
       ('8704441237','차범근', '차범근', 'cha@237','2009-03-30',0);


select * from news;

select * from user;



select * from userAsset where userId = '3704999150' order by createDate desc limit 1;
select * from product;

select * from product where finCoNo = '0010927' ORDER BY RAND() LIMIT 2;

update user
set point = 1000
where userId = 3711364352;

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
    `interest`
) VALUES (
             '3721153289',
             '["우리은행", "신한은행"]',  -- 현금 은행 배열
             '["123-456-7890", "987-654-3210"]',  -- 현금 계좌 배열
             '["50000", "20000"]',  -- 현금 자산 배열
             '["미래에셋", "삼성증권"]',  -- 주식 은행 배열
             '["111-222-3333", "444-555-6666"]',  -- 주식 계좌 배열
             '["10000", "15000"]',  -- 주식 자산 배열
             '["농협은행", "하나은행"]',  -- 예적금 은행 배열
             '["333-444-5555", "666-777-8888"]',  -- 예적금 계좌 배열
             '["20000", "25000"]' ,  -- 예적금 자산 배열
             '["KB손해보험"]',
             '["KB 빅플러스저축보험"]',
             '["400000"]',
             2000000,
             '주택구입',
             24,
             3.2
         );
select * from policy;

update user set todayQuiz = 0;



INSERT INTO quiz (term, description)
VALUES ('예금', '일정 기간 동안 자금을 은행에 맡기고 이자를 받는 금융 상품'),
       ('적금', '일정 금액을 정해진 기간 동안 매월 납입하고 만기 시 이자를 받는 금융 상품'),
       ('대출', '금융기관이 고객에게 자금을 빌려주는 행위'),
       ('금리', '자금을 빌려주거나 예금했을 때 적용되는 이자율'),
       ('주식', '기업이 자금을 조달하기 위해 발행하는 증권으로, 주주에게 소유권을 제공'),
       ('채권', '기업이나 정부가 자금을 빌리기 위해 발행하는 유가증권'),
       ('자산', '개인이나 기업이 소유하고 있는 경제적 가치가 있는 모든 것'),
       ('부채', '개인이나 기업이 외부로부터 빌린 자금이나 채무'),
       ('신용등급', '개인이나 기업의 신용 상태를 평가한 등급'),
       ('가계부채', '가계가 금융기관으로부터 빌린 대출금'),
       ('환율', '서로 다른 국가의 통화 간 교환 비율'),
       ('펀드', '여러 투자자로부터 자금을 모아 다양한 자산에 투자하는 금융 상품'),
       ('ETF', '주식처럼 거래되는 펀드로, 여러 자산에 분산 투자'),
       ('파생상품', '기초 자산의 가격 변동에 따라 가치가 결정되는 금융 상품'),
       ('리스크', '투자나 대출 등에서 발생할 수 있는 손실의 가능성'),
       ('수익률', '투자금 대비 발생한 수익의 비율'),
       ('배당', '기업이 주주에게 이익의 일부를 지급하는 것'),
       ('모기지', '주택을 담보로 제공하고 금융기관으로부터 대출을 받는 것'),
       ('연금', '일정 기간 동안 납입하고 나중에 일정 금액을 지급받는 금융 상품'),
       ('보험료', '보험계약자가 보험회사에 지급하는 금액'),
       ('보험금', '사고나 질병 발생 시 보험회사가 지급하는 금액'),
       ('상환', '대출금을 갚는 행위'),
       ('기초자산', '파생상품의 가치가 결정되는 근거가 되는 자산'),
       ('매도', '자산을 판매하는 행위'),
       ('매수', '자산을 구매하는 행위'),
       ('유동성', '자산을 현금으로 쉽게 전환할 수 있는 정도'),
       ('채무불이행', '대출금 상환을 제때 하지 못하는 상황'),
       ('펀드매니저', '펀드를 운영하고 자산을 투자하는 전문가'),
       ('금융당국', '금융시장과 금융기관을 감독하는 정부 기관'),
       ('금융감독원', '금융 기관을 감독하고 금융 시스템의 안정을 관리하는 기관'),
       ('가치투자', '기업의 내재 가치를 분석하고 장기적으로 투자하는 방식'),
       ('배당주', '배당을 정기적으로 지급하는 주식'),
       ('성장주', '높은 성장 가능성이 있는 기업의 주식'),
       ('우선주', '배당과 청산 시 보통주보다 우선권이 있는 주식'),
       ('IPO', '기업이 주식을 처음으로 공개하는 것'),
       ('상장', '기업이 주식시장에 주식을 등록하고 거래할 수 있도록 하는 것'),
       ('공모주', '기업이 IPO를 통해 일반 투자자에게 판매하는 주식'),
       ('벤처캐피탈', '신생기업에 자금을 투자하는 투자회사'),
       ('스타트업', '혁신적인 아이디어를 바탕으로 새로 창업한 기업'),
       ('헤지펀드', '고위험, 고수익을 목표로 다양한 투자 전략을 사용하는 펀드'),
       ('레버리지', '자산을 구매할 때 외부 자금을 활용해 수익을 극대화하는 전략'),
       ('매출액', '기업이 상품이나 서비스를 판매하고 얻은 총 수익'),
       ('영업이익', '기업의 총매출에서 운영비를 제외한 순이익'),
       ('순이익', '모든 비용과 세금을 제외한 최종적인 이익'),
       ('주당순이익', '기업의 순이익을 총 발행 주식 수로 나눈 값'),
       ('자기자본이익률', '주주의 투자 대비 순이익 비율'),
       ('총자산이익률', '기업의 총자산 대비 순이익 비율'),
       ('채무자', '자금을 빌린 사람 또는 법인'),
       ('채권자', '자금을 빌려준 사람 또는 법인'),
       ('소액주주', '적은 양의 주식을 소유한 주주'),
       ('대주주', '기업의 의사결정에 영향을 미칠 수 있을 만큼 많은 주식을 소유한 주주'),
       ('거래량', '주식이나 채권 등이 거래된 수량'),
       ('거래대금', '거래된 주식이나 채권 등의 총 거래 금액'),
       ('상장폐지', '주식이 주식시장에서 더 이상 거래되지 않도록 하는 것'),
       ('한도액', '대출이나 신용카드에서 사용할 수 있는 최대 금액'),
       ('한도초과', '대출이나 신용카드에서 허용된 금액을 초과하는 것'),
       ('대환대출', '기존 대출을 상환하고 새로운 대출로 전환하는 것'),
       ('연체', '대출금이나 신용카드 결제를 제때 하지 못한 상태'),
       ('자동이체', '금융기관이 미리 지정된 날짜에 정기적으로 자금을 이체하는 서비스'),
       ('신용카드', '사용자가 금융기관과의 계약을 통해 발급받아 사용하는 카드로, 구매 시 대금을 나중에 결제'),
       ('체크카드', '사용자가 보유한 예금 계좌에서 즉시 결제금액이 인출되는 카드'),
       ('투자자', '자산을 구입해 수익을 기대하는 사람'),
       ('적격투자자', '일정 기준을 충족하여 복잡한 금융 상품에 투자할 자격이 있는 투자자'),
       ('비상장주식', '주식시장에 상장되지 않은 주식'),
       ('벤처기업', '신기술이나 아이디어를 바탕으로 창업된 중소기업'),
       ('지분율', '회사의 총 주식 중 특정 주주가 보유한 주식의 비율'),
       ('세금공제', '납부해야 할 세금에서 일정 금액을 차감하는 혜택'),
       ('양도소득세', '자산을 매도하여 얻은 소득에 대해 부과되는 세금'),
       ('증권거래세', '주식이나 채권 등의 증권 거래 시 부과되는 세금'),
       ('소득세', '개인이나 법인의 소득에 부과되는 세금'),
       ('법인세', '법인의 이익에 대해 부과되는 세금'),
       ('부가가치세', '상품이나 서비스의 생산 및 유통 과정에서 발생하는 가치 증가에 부과되는 세금'),
       ('중앙은행', '한 국가의 통화 정책을 관리하고 금융 시스템을 안정시키는 기관'),
       ('국채', '정부가 발행하는 채권'),
       ('회사채', '기업이 자금을 조달하기 위해 발행하는 채권'),
       ('주식시장', '주식이 거래되는 시장'),
       ('채권시장', '채권이 거래되는 시장'),
       ('국제금융시장', '서로 다른 국가 간 자금이 거래되는 금융시장'),
       ('투자신탁', '투자자의 자금을 모아 자산에 투자하고 수익을 분배하는 금융 상품'),
       ('비트코인', '분산된 디지털 통화로, 암호화된 블록체인 기술을 이용한 전자 화폐'),
       ('블록체인', '데이터를 투명하게 관리할 수 있는 분산 원장 기술'),
       ('금융 파산', '개인이 부채를 상환할 수 없는 상황에서 파산을 선언하는 것'),
       ('채권회수', '채무자가 대출금을 상환하지 못할 경우 채권자가 재산을 회수하는 절차'),
       ('소득 공제', '소득세를 계산할 때 일정 부분을 공제해주는 제도'),
       ('공매도', '자산을 보유하지 않고 미래에 낮은 가격으로 매입하기 위해 자산을 미리 판매하는 투자 전략'),
       ('비상장', '기업이 주식 시장에 상장되지 않은 상태'),
       ('증자', '회사가 자본금을 늘리기 위해 새로운 주식을 발행하는 것'),
       ('감자', '회사가 자본금을 줄이기 위해 발행 주식 수를 줄이는 것'),
       ('경영권', '기업의 경영 활동에 대한 통제권'),
       ('재무제표', '기업의 재무 상태와 경영 성과를 나타내는 문서'),
       ('신용위기', '신용 거래의 급격한 감소로 금융 시장에 불안이 발생하는 상황'),
       ('비상금', '예상치 못한 상황에 대비해 따로 마련해 둔 자금'),
       ('자본비용', '기업이 자본을 조달하는 데 드는 비용으로, 주주와 채권자에게 지불하는 비용을 의미'),
       ('베타계수', '특정 자산의 수익률 변동이 시장 전체의 변동과 얼마나 연관되어 있는지를 나타내는 지표'),
       ('듀레이션', '채권의 현금 흐름 가중 평균 기간을 나타내는 지표로, 채권의 금리 변화 민감도를 측정'),
       ('가중평균자본비용', '기업이 조달한 자본의 가중 평균 비용으로, 자본 구조에 따른 비용을 반영'),
       ('캡티브 마켓', '특정 제품이나 서비스가 독점적으로 제공되는 시장으로, 소비자가 선택할 수 있는 대안이 거의 없는 시장'),
       ('차입금 의존도', '기업이 외부 자금을 얼마나 의존하고 있는지를 나타내는 비율'),
       ('자본조정', '기업이 자본 구조를 재조정하여 자금 조달 방법을 변경하거나 재무 건전성을 개선하는 행위'),
       ('자기자본비율', '기업의 자기자본이 전체 자산에서 차지하는 비율로, 기업의 재무 건전성을 평가하는 지표'),
       ('차입부채', '기업이 외부에서 차입한 부채로, 은행 대출이나 회사채 등을 포함'),
       ('현금흐름할인법', '미래 현금 흐름을 현재 가치로 할인하여 기업의 가치를 평가하는 방법'),
       ('감가상각비', '자산의 가치가 시간 경과에 따라 감소하는 것을 회계적으로 반영한 비용'),
       ('주가수익비율', '주식의 시장가격을 1주당 순이익으로 나눈 비율로, 주식의 가치 평가에 사용됨'),
       ('주가순자산비율', '주식의 시장가격을 1주당 순자산으로 나눈 비율로, 주식의 가치 평가에 사용됨'),
       ('선도금리', '현재 시점에서 미래의 금리를 예측하는 금리로, 금융 시장의 미래 금리 전망을 반영'),
       ('금융파생상품', '기초 자산의 가격 변동에 따라 가치가 결정되는 금융 계약으로, 선물, 옵션 등이 포함됨'),
       ('헤지거래', '가격 변동 위험을 회피하기 위해 반대 방향의 포지션을 취하는 거래 전략'),
       ('차액결제거래', '기초 자산을 직접 거래하지 않고 가격 변동에 따른 차액만 결제하는 거래 방식'),
       ('모라토리엄', '국가가 외채 상환을 일시적으로 중단하는 조치로, 재정 위기 시 선언'),
       ('그린메일', '적대적 인수합병(M&A)을 시도하는 투자자가 대주주에게 보유 주식을 높은 가격에 매입하도록 압박하는 행위'),
       ('파킹거래', '실제 소유권을 제3자에게 일시적으로 넘겨 인수합병 규제를 회피하는 거래 방식'),
       ('매출채권', '기업이 상품이나 서비스를 제공하고 받을 금액으로, 아직 현금으로 회수되지 않은 금액'),
       ('포지션', '투자자가 금융 상품에 대해 취한 매수 또는 매도 입장'),
       ('기초자산', '파생상품 계약에서 가격 변동의 기준이 되는 자산으로, 주식, 채권, 통화 등이 포함됨'),
       ('알파 수익', '시장 평균 수익률을 초과하는 초과 수익을 의미하며, 운용 능력에 따라 발생'),
       ('계리사', '보험 수리나 연금 수리 등을 전문적으로 계산하는 전문가로, 보험 상품 개발에 참여'),
       ('헤지펀드', '고위험 고수익을 목표로 하는 사모펀드로, 다양한 투자 전략을 사용하여 수익을 추구'),
       ('양적완화', '중앙은행이 시장에 유동성을 공급하기 위해 국채 등을 대규모로 매입하는 통화정책'),
       ('비선형 금융상품', '가격 변동이 직선적이지 않고 복잡한 방식으로 변화하는 금융 상품으로, 옵션이 대표적'),
       ('스왑', '두 당사자가 금리, 통화 등의 금융 계약을 교환하는 파생상품 거래 방식'),
       ('풋옵션', '특정 자산을 미리 정한 가격에 미래 시점에서 팔 수 있는 권리를 부여하는 옵션 계약'),
       ('콜옵션', '특정 자산을 미리 정한 가격에 미래 시점에서 살 수 있는 권리를 부여하는 옵션 계약'),
       ('신용스프레드', '신용 위험에 따라 동일 만기의 국채와 회사채 사이의 금리 차이를 의미'),
       ('금융투자협회', '금융 투자업과 관련된 업무를 감독하고 규제하는 기관으로, 금융상품의 투명한 거래를 촉진'),
       ('BIS 자기자본비율', '은행의 자기자본이 위험자산에 대비해 얼마나 안전한지를 나타내는 비율로, 금융 건전성 지표'),
       ('바젤협약', '국제적으로 은행의 자본 규제를 강화하기 위해 체결된 협약으로, 금융 위기 예방을 목표로 함'),
       ('주택담보대출비율', '주택 가격에 대한 대출금 비율로, 대출 한도를 결정하는 중요한 지표'),
       ('총부채상환비율', '대출 신청자의 연소득 대비 총부채 상환액의 비율로, 대출 한도를 결정하는 데 사용'),
       ('변동금리', '시장 금리 변동에 따라 대출금리나 예금금리가 변동하는 방식'),
       ('고정금리', '대출 기간 동안 금리가 변동하지 않고 고정된 금리로 적용되는 방식'),
       ('특수목적회사', '특정 프로젝트나 거래를 위해 설립된 법인으로, 자산유동화나 구조조정 등에 사용'),
       ('신종자본증권', '자본 확충을 위해 발행되는 증권으로, 주식과 채권의 특성을 모두 가짐'),
       ('자산유동화', '부동산이나 채권과 같은 자산을 담보로 하여 유동화증권을 발행하는 금융 기법'),
       ('차입매수', '인수 대상 기업의 자산을 담보로 대출을 받아 그 기업을 인수하는 방식'),
       ('메자닌펀드', '주식과 채권의 중간 위험을 가진 투자상품으로, 전환사채, 신주인수권부사채 등이 포함됨'),
       ('전환사채', '채권 보유자가 일정 기간 이후에 발행회사의 주식으로 전환할 수 있는 권리가 있는 사채'),
       ('신주인수권부사채', '채권 보유자가 일정 기간 동안 발행회사의 주식을 일정 가격에 매입할 수 있는 권리가 있는 사채'),
       ('단기금융시장', '만기가 짧은 금융 상품들이 거래되는 시장으로, 기업어음(CP), 양도성 예금증서(CD) 등이 거래됨'),
       ('그로스해킹', '마케팅과 데이터를 결합하여 적은 비용으로 빠른 성장을 이루기 위한 전략'),
       ('액티브 펀드', '펀드매니저가 시장을 이기기 위해 적극적으로 운용하는 펀드'),
       ('패시브 펀드', '시장 지수를 추종하도록 설계된 펀드로, 장기적으로 안정적인 수익을 목표로 함');


SELECT *
FROM board
ORDER BY (views * 0.2 + comments * 0.4 + likes * 0.4) DESC
LIMIT 5;

select * from news;


delete from userAsset where assetNo = 22;

select * from userAsset;

