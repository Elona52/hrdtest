package exe;

import crud.CrudClass;

public class Execute {
	

	public static void main(String[] args) {
		

		CrudClass crudclass = new CrudClass();

//		사원테이블 데이터 전체 삭제
		crudclass.deleteAll();
		crudclass.setAuto_Increment();
		crudclass.selectAll();

//		(1) 아티스트 등록: Artist 테이블에 다음 데이터를 삽입하시오.
		crudclass.insertArtist("아이유", "2008-09-18", "발라드", "EDAM엔터테인먼트");
		crudclass.insertArtist("BTS", "2013-06-13", "K-POP", "하이브");
		crudclass.insertArtist("블랙핑크", "2016-08-08", "K-POP", "YG엔터테인먼트");
		crudclass.selectArtistAll();

//		(1)-2 앨범 등록:
		crudclass.insertAlbum(1, "좋은 날", "2010-12-09", 500000);
		crudclass.insertAlbum(2, "MAP OF THE SOUL: 7", "2020-02-21", 4300000);
		crudclass.insertAlbum(3, "THE ALBUM", "2020-10-02", 1300000);
		crudclass.selectAlbumAll();

//		(2) 장르가 'K-POP'인 아티스트의 이름과 소속사를 조회.
		crudclass.selectGenre("K-POP");

//		(2)-2 판매량이 1,000,000 이상인 앨범의 제목과 판매량을 조회.
		crudclass.selectSales(1000000);
		

//		(2)-3 아티스트별 총 판매량을 구하시오.
		crudclass.selectSales();
		crudclass.selectAll();
		
//		(3) '아이유'의 소속사를 '카카오엔터테인먼트'로 수정하시오.
		crudclass.updatAgency("아이유", "카카오엔터테인먼");
		
		
//		(4) AlbumNo = 1 인 앨범을 삭제하시오.
		crudclass.deleteAlbumNo(1);

}


}