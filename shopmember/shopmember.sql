show tables;
USE hrdtest;

show database hrdtest;


create table shopmember(
CustNo INT AUTO_INCREMENT PRIMARY KEY,
CustName VARCHAR(30) NOT NULL,
Phone VARCHAR(13) UNIQUE,
Address VARCHAR(50),
JoinDate DATE NOT NULL,
Grade CHAR(1) CHECK(Grade IN ('A','B','C')),
City CHAR(2)
);


select * from shopmember;
INSERT INTO ShopMember (CustName, Phone, Address, JoinDate, Grade, City) VALUES
('홍길동', '010-1234-5678', '서울시 강남구', '2020-01-01', 'A', '01'),
('이순신', '010-2222-3333', '부산시 해운대구', '2021-03-15', 'B', '02'),
('강감찬', '010-7777-8888', '대구시 달서구', '2019-05-20', 'C', '03');

-- 1.회원조회
-- 1-1. 고객등급이 A등급인 회원의 이름, 전화번호, 가입일자를 조회
SELECT CustName, Phone, JoinDate
FROM ShopMember
WHERE Grade = 'A';

-- 1-2. 가입일자가 2020년 이후인 회원을 조회
SELECT *
FROM ShopMember
WHERE JoinDate >= '2020-01-01';


-- 2.판매등록

CREATE TABLE Sale (
    SaleNo INT AUTO_INCREMENT PRIMARY KEY,   -- 판매번호
    CustNo INT,                              -- 회원번호 (외래키)
    PCost INT,                               -- 단가
    Amount INT,                              -- 수량
    Price INT,                               -- 금액
    PCode CHAR(3),                           -- 상품코드
    FOREIGN KEY (CustNo) REFERENCES ShopMember(CustNo)
);

INSERT INTO Sale(custno, PCost, Amount, Price, PCode) VALUES
(1, 1000, 10, 10000, 'P01'),
(2, 2000, 5, 10000, 'P02'),
(3, 1500, 7, 10500, 'P03');


-- 3.판매조회
-- 3-1. 회원별 총 구매금액(출력: 회원번호, 회원성명, 총금액)
select * from sale;
select * from ShopMember;

SELECT m.CustNo AS '회원번호',
       m.CustName AS '회원성명',
       SUM(s.Price*s.amount) AS '총금액'
FROM shopmember m
INNER JOIN sale s
    ON m.CustNo = s.CustNo
GROUP BY m.CustNo, m.CustName;



-- 3-2. 가장 구매금액이 높은 회원의 이름과 금액을 조회 
	SELECT CustNo, SUM(Price*amount) AS 총금액
	FROM sale
	GROUP BY CustNo
	ORDER BY 총금액 DESC
	LIMIT 1;
	   
	
-- 4.데이터 수정/삭제
-- 4-1. '이순신' 회원의 등급을 A로 수정하시오.
update shopmember
set grade = 'A'
where custname='이순신';


-- 4-2. CustNo = 3 인 회원을 삭제하시오.
delete from sale where custNo = 3;






















