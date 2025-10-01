package exe;

import crud.CRUDClass;

public class MyMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		CRUDClass crud = new CRUDClass();
		
		
		CRUDClass crudclass = new CRUDClass();

//		사원테이블 데이터 전체 삭제
		crudclass.deleteAll();
		crudclass.setAuto_Increment();

//		 1. 전체 회원 등록
		crudclass.ShopMember("홍길동", "010-1234-5678", "서울시 강남구", "2020-01-01", "A", "01");
		crudclass.ShopMember("이순신", "010-2222-3333", "부산시 해운대구", "2021-03-15", "B", "02");
		crudclass.ShopMember("강감찬", "010-7777-8888", "대구시 달서구", "2019-05-20", "C", "03");
		crudclass.selectMemberAll();

//		2-1. A등급 회원 조회
		crudclass.selectGrade("A");

//		2-2. 2020년 이후 회원 조회
		crudclass.selec2020Member(2020);

//		3. 판매 등록
		crudclass.Sale(1, 1000, 10, 10000, "P01");
		crudclass.Sale(2, 2000, 5, 10000, "P02");
		crudclass.Sale(3, 1500, 7, 10500, "P03");
		crudclass.selectSaleAll();
		

//		4-1. 회원별 총 구매금액
		crudclass.selectSales();
		crudclass.selectAll();
		
//		4-2. 구매금액이 높은 회원 조회
		crudclass.selectPrice();
		
		
//		5. 회원 등급 수정
		crudclass.updateGrade("이순신", "A");
		
		
// 		6. 회원 삭제
		crudclass.deleteMember(3);
	}

}
