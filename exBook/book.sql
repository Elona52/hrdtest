show tables;
USE hrdtest;
 

drop table Rental;
drop table book;

create table book(
BookID INT PRIMARY KEY,
Title VARCHAR(200) NOT NULL,
Author VARCHAR(200),
Publisher VARCHAR(200),
Price int NOT NULL CHECK (Price >= 0),
PubYear YEAR
);

select * from hrdtest;
select * from book;
desc book;

create table Rental(
RentalID int primary key,
MemberID int,
BookID int, 
RentDate Date,
ReturnDate Date,
foreign key(MemberID) references Member(MemberID),
foreign key(BookID) references Book(BookID)
);

select * from rental;
desc rental;


drop table Member;
create table Member (
    MemberID int primary key,
    Name varchar(100) not null,
    Phone varchar(100),
    Address varchar(100)
);

select * from member;
desc member;

-- -----------------------

INSERT INTO Member (MemberID, Name, Phone, Address) VALUES
(1, '홍길동', '010-1111-1111', '서울시 강남구'),
(2, '김철수', '010-2222-2222', '서울시 마포구'),
(3, '이영희', '010-3333-3333', '서울시 종로구');


INSERT INTO Book (BookID, Title, Author, Publisher, Price, PubYear) VALUES
(1, '자바 프로그래밍', '이영희', '한빛출판사', 25000, 2019),
(2, '데이터베이스 개론', '김철수', '교학사', 30000, 2021),
(3, '알고리즘 입문', '김나나', '영진출판사', 28000, 2022),
(4, '머신러닝 기초', '최유리', '에이콘', 35000, 2023),
(5, '네트워크 이해', '한동구', '한빛출판사', 27000, 2020);

INSERT INTO Rental (RentalID, MemberID, BookID, RentDate, ReturnDate) VALUES
(1, 1, 2, '2023-01-10', '2023-01-20'),
(2, 1, 4, '2023-02-05', NULL),          -- 홍길동, 반납 안 함
(3, 2, 1, '2023-03-12', '2023-03-22'),
(4, 2, 3, '2023-04-01', NULL),          -- 김철수, 반납 안 함
(5, 3, 5, '2023-05-20', '2023-05-30');




