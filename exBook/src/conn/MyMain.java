package conn;

import crud.CRUDClass;

public class MyMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		CRUDClass crud = new CRUDClass();
		
		crud.booksAfter2020();
		crud.booksBorrowedByHong();
		crud.unreturnedBooks();
		crud.rentalCountByBook();
		crud.mostExpensiveBook();
	}

}
