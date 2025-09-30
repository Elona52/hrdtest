SHOW TABLES;

SHOW TABLES FROM employee;

drop table employee;

create table employee(
	EmpNo int auto_increment primary key,
	EmpName varchar(30) not null,
	Dept varchar(20) not null,
	HireDate date not null,
    Salary INT NOT NULL,
    CONSTRAINT chk_salary CHECK (Salary >= 2000000)
);

select * from employee;
drop table employee;

-- name: ex1
-- 1.사원등록 
INSERT INTO Employee (EmpName, Dept, HireDate, Salary) VALUES 
('홍길동', '영업부', '2020-03-01', 2500000),
('이순신', '인사부', '2019-07-15', 3200000),
('강감찬', '개발부', '2021-01-10', 2800000);

-- name: ex2
-- 2-1.'개발부'인 사원의 사번, 이름, 급여를 조회
select EmpNo, EmpName, Salary
from employee
where dept = '개발부';

-- name: ex3
-- 2-2. 급여가 3,000,000원 이상인 사원의 이름과 부서
select EmpName, Dept
from  employee
where salary >= 3000000;

-- name: ex4s
-- 3.이순신의 급여를 3,500,000원으로 수정
update employee
set salary = 3500000
where empName = '이순신';

-- name: ex5
-- 4.사번이 1번인 사원의 정보를 삭제
delete from employee
where empNo = 1;