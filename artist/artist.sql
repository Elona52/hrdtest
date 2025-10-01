show databases;
use hrdtest;

-- 1.artist 테이블 생성
create table artist(
ArtistNo int AUTO_INCREMENT PRIMARY KEY,
ArtistName VARCHAR(30) NOT NULL UNIQUE,
DebutDate DATE NOT NULL,
Genre VARCHAR(20) NOT NULL,
Agency VARCHAR(30)
);

-- 2_1.아티스트 등록
insert into artist (ArtistName,DebutDate,Genre,Agency)values
('아이유', '2008-09-18', '발라드', 'EDAM엔터테인먼트'),
('BTS', '2013-06-13', 'K-POP', '하이브'),
('블랙핑크', '2016-08-08', 'K-POP', 'YG엔터테인먼트');



create table album(
AlbumNo INT AUTO_INCREMENT PRIMARY KEY,
ArtistNo INT,
AlbumTitle VARCHAR(50) NOT NULL,
ReleaseDate DATE NOT NULL,
Sales INT CHECK(Sales >= 0),
FOREIGN KEY (ArtistNo) REFERENCES Artist(ArtistNo)
);

-- 2_2.앨범 등록
insert into album (ArtistNo,AlbumTitle,ReleaseDate,Sales)values
(1, '좋은 날', '2010-12-09', 500000),
(2, 'MAP OF THE SOUL: 7', '2020-02-21', 4300000),
(3, 'THE ALBUM', '2020-10-02', 1300000);

-- 3.조회
-- 장르가 'K-POP'인 아티스트의 이름과 소속사를 조회.
select ArtistNo,Agency
from artist
where Genre = 'K-POP';

-- 판매량이 1,000,000 이상인 앨범의 제목과 판매량을 조회.
select AlbumTitle,Sales
from album
where sales >= 1000000;


-- 아티스트별 총 판매량을 구하시오.
SELECT a.ArtistName AS '아티스트명',
       COALESCE(SUM(al.Sales), 0) AS '총 판매량'
FROM artist a
LEFT JOIN album al
    ON a.ArtistNo = al.ArtistNo
GROUP BY a.ArtistNo, a.ArtistName;

SELECT * FROM album;

-- 4.수정
-- '아이유'의 소속사를 '카카오엔터테인먼트'로 수정하시오.
update artist
set Agency = '카카오엔터테인먼트'
where ArtistName='아이유';


-- 5.삭제
-- AlbumNo = 1 인 앨범을 삭제하시오.
delete from album where AlbumNo = 1;
